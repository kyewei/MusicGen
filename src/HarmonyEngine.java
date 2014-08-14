import java.util.*;

/**
 * Created by Kye on 2014-07-07.
 */
public class HarmonyEngine
{
    public HarmonyEngine()
    {
        //defaults
        numberOfChords=8;
        key = new Pitch("C");
        scale = new MajorScale(key);
        usedProper = false;

        reset();
        currentProgression = new int[]{1,3,4,5,4,1,5,1};
        buildBass();

        checkParallelFifths=true;
        checkParallelOctaves=true;
        checkHiddenFifths=true;
        checkHiddenOctaves=true;
    }

    public void reset()
    {
        boundaryBassHigh=null;

        currentChord=0;
        soprano = new Note[numberOfChords];
        alto = new Note[numberOfChords];
        tenor = new Note[numberOfChords];
        bass = new Note[numberOfChords];
        currentProgression = new int[numberOfChords];
        inversions = new int[numberOfChords];
        isSeventh = new boolean[numberOfChords];

    }

    public int[] currentProgression;
    public int numberOfChords;
    public Pitch key;
    public MajorScale scale;

    public Note[] soprano;
    public Note[] alto;
    public Note[] tenor;
    public Note[] bass;
    public int[] inversions;
    public boolean[] isSeventh;

    public Note[] getSoprano() { return soprano; }
    public Note[] getAlto() { return alto; }
    public Note[] getTenor() { return tenor; }
    public Note[] getBass() { return bass; }

    public Note boundaryBassHigh;

    public boolean usedProper;

    public boolean checkParallelFifths;
    public boolean checkParallelOctaves;
    public boolean checkHiddenFifths;
    public boolean checkHiddenOctaves;


    public int currentChord;
    public void goPrev()
    {
        if (currentChord>0)
            currentChord--;
    }

    public void goNext()
    {
        if (currentChord<numberOfChords)
            currentChord++;
    }

    public static int[][] arrangements = {
            {0, 2, 1, 0, 0, 1, 5, 11, 15}, //C3, G3, E4, C5
            {0, 0, 2, 1, 0, 1, 8, 12, 17}, //C3, C4, G4, E5
            {0, 1, 2, 0, 0, 1, 10, 12, 15}, //C3, E4, G4, C5
            {0, 0, 1, 2, 0, 1, 8, 10, 12}, //C3, C4, E4, G4
            {0, 1, 0, 2, 0, 1, 3, 8, 12}, //C3, E3, C4, G4
            {0, 2, 0, 1, 0, 1, 5, 8, 10}, //C3, G3, C4, E4
            {0, 1, 0, 2, 0, 1, 10, 15, 19}, //C3, E4, C5, G5
            {0, 2, 0, 1, 0, 1, 12, 15, 17} //C3, G4, C5, E5

    };

    //dynamically created by static initializer
    public static int[][] extendedRootPosition;
    public static int[][] extendedFirstInversion;
    public static int[][] extendedSecondInversion;
    public static int[][] extended7RootPosition;
    public static int[][] extended7FirstInversion;
    public static int[][] extended7SecondInversion;
    public static int[][] extended7ThirdInversion;

    public static int[][] tonicMatrix = new int[][]{
            {0,1,0,1,1,0,1,1,0,1,0,0,0,1,1,1,0,0,0,0,0,1,0,0,1,1,0,1,0,0,0,1,0,0,0,0,0},
            {0,0,0,0,1,0,0,1,0,0,0,0,0,1,0,0,1,0,0,0,0,1,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,1,1,0,0,0,0,1,0,0,0,0,0},
            {0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,1,1,0,0,0,0,1,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,1,1,0,1,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
    };


    public static int[][] predominantMatrix = new int[][]{
            {0,0,0,1,1,0,1,1,0,0,0,0,0,1,1,0,1,1,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0},
            {0,0,0,1,1,0,1,1,0,0,1,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,1,0,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,1,1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,1,1,1,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,1,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,1,1,1,1,0,1,1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,1,0,1,0,0,1,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,1,0,1,1,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,1,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,1,1,0,1,1,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
    };

    public static int[][] dominantMatrix = new int[][]{
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
    };

    public static int[] tonicEntry;
    public static int[] tonicExit;
    public static int[] predominantEntry;
    public static int[] predominantExit;
    public static int[] dominantEntry;
    public static int[] dominantExit;

    public static HashMap<String, Integer> stringToIndex = new HashMap<String, Integer>();
    public static HashMap<Integer, String> indexToString = new HashMap<Integer, String>();

    static {
        indexToString.put(0,"I");
        indexToString.put(1,"I6");
        indexToString.put(2,"I64");
        indexToString.put(3,"ii");
        indexToString.put(4,"ii6");
        indexToString.put(5,"ii64");
        indexToString.put(6,"ii7");
        indexToString.put(7,"ii65");
        indexToString.put(8,"ii43");
        indexToString.put(9,"ii42");
        indexToString.put(10,"iii");
        indexToString.put(11,"iii6");
        indexToString.put(12,"iii64");
        indexToString.put(13,"IV");
        indexToString.put(14,"IV6");
        indexToString.put(15,"IV64");
        indexToString.put(16,"IV7");
        indexToString.put(17,"IV65");
        indexToString.put(18,"IV43");
        indexToString.put(19,"IV42");
        indexToString.put(20,"V");
        indexToString.put(21,"V6");
        indexToString.put(22,"V64");
        indexToString.put(23,"V7");
        indexToString.put(24,"V65");
        indexToString.put(25,"V43");
        indexToString.put(26,"V42");
        indexToString.put(27,"vi");
        indexToString.put(28,"vi6");
        indexToString.put(29,"vi64");
        indexToString.put(30,"vii°");
        indexToString.put(31,"vii°6");
        indexToString.put(32,"vii°64");
        indexToString.put(33,"vii°7");
        indexToString.put(34,"vii°65");
        indexToString.put(35,"vii°43");
        indexToString.put(36,"vii°42");

        for (Map.Entry<Integer, String> entry : indexToString.entrySet())
            stringToIndex.put(entry.getValue(), entry.getKey());

        tonicEntry = new int[] { (int)stringToIndex.get("I") };
        tonicExit = new int[] {
                stringToIndex.get("I"),
                stringToIndex.get("I6")
        };
        predominantEntry = tonicExit;
        predominantExit = new int[] {
                stringToIndex.get("ii"),
                stringToIndex.get("ii6"),
                stringToIndex.get("ii7"),
                stringToIndex.get("ii65"),
                stringToIndex.get("ii43"),
                stringToIndex.get("IV"),
                stringToIndex.get("IV6"),
                stringToIndex.get("IV7"),
                stringToIndex.get("vi")
        };
        dominantEntry=predominantExit;
        dominantExit = new int[] {
                stringToIndex.get("V"),
                stringToIndex.get("V7"),
                stringToIndex.get("V65"),
                stringToIndex.get("V43"),
                stringToIndex.get("V42")
        };

        loop('0');
        loop('1');
        loop('2');
        loop('5');
        loop('6');
        loop('7');
        loop('8');
    }

    public void doAllTheThings ()
    {
        //clear
        currentChord=0;
        for (int i=currentChord; i< bass.length; ++i) //clear chords later since this change
        {
            tenor[i]=null;
            alto[i]=null;
            soprano[i]=null;
        }

        Object[] masterList = new Object[bass.length];
        int[] elementVisited = new int[bass.length];
        boolean isOverallFail = false;

        for (currentChord = 0; currentChord< bass.length; ++currentChord)
        {
            if (masterList[currentChord]==null) //good; we request chord positions that work
            {
                ArrayList<Object[]> result = next();

                if (result.size() > 0) //we got a non-empty list, proceed with scrambling, then pick top one
                {
                    masterList[currentChord] = result;

                    //scramble
                    Collections.shuffle(result);

                    //Import list of notes
                    Pitch[] possiblePitches;
                    if (!isSeventh[currentChord]) {
                        possiblePitches = scale.getDiatonicTriad(currentProgression[currentChord]);
                    } else {
                        possiblePitches = scale.getDiatonic7th(currentProgression[currentChord]);
                    }

                    int index = 0;
                    int[][] referenceArray = (int[][]) (result.get(index)[0]);
                    int element = (int) (result.get(index)[1]);

                    tenor[currentChord] = new Note(possiblePitches[referenceArray[element][1]], (bass[currentChord].getLetterNum() + referenceArray[element][6] - 1) / 7);
                    alto[currentChord] = new Note(possiblePitches[referenceArray[element][2]], (bass[currentChord].getLetterNum() + referenceArray[element][7] - 1) / 7);
                    soprano[currentChord] = new Note(possiblePitches[referenceArray[element][3]], (bass[currentChord].getLetterNum() + referenceArray[element][8] - 1) / 7);

                    //Is successful; do not need to manually increment
                }
                else //request returns an empty list
                {
                    masterList[currentChord] = null;
                    elementVisited[currentChord] = 0;
                    tenor[currentChord] = null;
                    alto[currentChord] = null;
                    soprano[currentChord] = null;
                    currentChord-=2;
                }
            }
            else //if(masterList[currentChord]!=null) //only reason it will not be null is if it is a failed attempt somewhere later
            {
                elementVisited[currentChord]++;

                ArrayList<Object[]> result = (ArrayList<Object[]>)(masterList[currentChord]);
                if (elementVisited[currentChord]<result.size())
                {
                    //Import list of notes
                    Pitch[] possiblePitches;
                    if (!isSeventh[currentChord]) {
                        possiblePitches = scale.getDiatonicTriad(currentProgression[currentChord]);
                    } else {
                        possiblePitches = scale.getDiatonic7th(currentProgression[currentChord]);
                    }

                    int index = elementVisited[currentChord];
                    int[][] referenceArray = (int[][]) (result.get(index)[0]);
                    int element = (int) (result.get(index)[1]);

                    tenor[currentChord] = new Note(possiblePitches[referenceArray[element][1]], (bass[currentChord].getLetterNum() + referenceArray[element][6] - 1) / 7);
                    alto[currentChord] = new Note(possiblePitches[referenceArray[element][2]], (bass[currentChord].getLetterNum() + referenceArray[element][7] - 1) / 7);
                    soprano[currentChord] = new Note(possiblePitches[referenceArray[element][3]], (bass[currentChord].getLetterNum() + referenceArray[element][8] - 1) / 7);
                }
                else //if (elementVisited[currentChord]>=result.size())
                {
                    masterList[currentChord] = null;
                    elementVisited[currentChord] = 0;
                    tenor[currentChord] = null;
                    alto[currentChord] = null;
                    soprano[currentChord] = null;
                    currentChord-=2;
                }
            }
            if (currentChord<=-2)
            {
                isOverallFail=true;
                currentChord=bass.length; //break out of loop
            }
        }
        if (isOverallFail)
        {
            System.out.println("No good combination at all");
        }
    }

    public void nextDriver()
    {
        if (currentChord<bass.length)
        {
            ArrayList<Object[]> result = next();
            if (result.size() > 0)
            {
                int index = (int) (Math.random() * result.size());

                //Import list of notes
                Pitch[] possiblePitches;
                if (!isSeventh[currentChord]) {
                    possiblePitches = scale.getDiatonicTriad(currentProgression[currentChord]);
                } else {
                    possiblePitches = scale.getDiatonic7th(currentProgression[currentChord]);
                }

                int[][] referenceArray = (int[][]) (result.get(index)[0]);
                int element = (int) (result.get(index)[1]);

                tenor[currentChord] = new Note(possiblePitches[referenceArray[element][1]], (bass[currentChord].getLetterNum() + referenceArray[element][6] - 1) / 7);
                alto[currentChord] = new Note(possiblePitches[referenceArray[element][2]], (bass[currentChord].getLetterNum() + referenceArray[element][7] - 1) / 7);
                soprano[currentChord] = new Note(possiblePitches[referenceArray[element][3]], (bass[currentChord].getLetterNum() + referenceArray[element][8] - 1) / 7);
                goNext();

            } else //if (result.size()==0)
            {
                System.out.println("No Matches Found");
                goPrev();
            }
        }
    }

    public ArrayList<Object[]> next()
    {   //note, does not increment
        for (int i=currentChord; i< bass.length; ++i) //clear chords later since this change
        {
            tenor[i]=null;
            alto[i]=null;
            soprano[i]=null;
        }

        //Get possible pitches and initialise
        //This diatonically gets minor or major depending on root position
        Pitch[] possiblePitches;

        boolean isSuccess;
        Note tempTenor, tempAlto, tempSoprano;

        int[][] referenceArray;

        //Import list of possible arrangements, depending on chord
        if (!isSeventh[currentChord])
        {
            switch (inversions[currentChord])
            {
                case 0: referenceArray = extendedRootPosition; break;
                case 1: referenceArray = extendedFirstInversion; break;
                case 2: referenceArray = extendedSecondInversion; break;
                default: System.out.println("Fallthrough"); referenceArray = extendedRootPosition; break;
            }
            possiblePitches = scale.getDiatonicTriad(currentProgression[currentChord]);
        }
        else
        {
            switch (inversions[currentChord])
            {
                case 0: referenceArray = extended7RootPosition; break;
                case 1: referenceArray = extended7FirstInversion; break;
                case 2: referenceArray = extended7SecondInversion; break;
                case 3: referenceArray = extended7ThirdInversion; break;
                default: System.out.println("Fallthrough"); referenceArray = extended7RootPosition; break;
            }
            possiblePitches = scale.getDiatonic7th(currentProgression[currentChord]);
        }

        if (currentChord==0)
            referenceArray=arrangements;

        ArrayList<Object[]> onesThatWork = new ArrayList<Object[]>();
        //each element is an array holding reference to referenceArray, type int[][], and
        // index number, type int

        for (int k =0; k< referenceArray.length; ++k)
        {
            isSuccess = false;

            tempTenor = new Note(possiblePitches[referenceArray[k][1]], (bass[currentChord].getLetterNum() + referenceArray[k][6] - 1) / 7);
            tempAlto = new Note(possiblePitches[referenceArray[k][2]], (bass[currentChord].getLetterNum() + referenceArray[k][7] - 1) / 7);
            tempSoprano = new Note(possiblePitches[referenceArray[k][3]], (bass[currentChord].getLetterNum() + referenceArray[k][8] - 1) / 7);

            //Boundaries for each voice that cannot be passed
            if (tempTenor.getChromaticNumber() > 55 || tempTenor.getChromaticNumber() < 36)
            {   //Boundaries G4 and C3
            }
            else if (tempAlto.getChromaticNumber() > 62 || tempAlto.getChromaticNumber() < 43)
            {   //Boundaries D5 and G3
            }
            else if (tempSoprano.getChromaticNumber() > 69 || tempSoprano.getChromaticNumber() < 48)
            {   //Boundaries A5 and C4
            }
            else if (tempTenor.getChromaticNumber() < boundaryBassHigh.getChromaticNumber())
            {   //No crossing
            }
            else if (tempTenor.getChromaticNumber() - bass[currentChord].getChromaticNumber() > 19)
            {   //Interval between voices can't be too wide
            }
            else if (tempAlto.getChromaticNumber() - tempTenor.getChromaticNumber() > 12)
            {
            }
            else if (tempSoprano.getChromaticNumber() - tempAlto.getChromaticNumber() > 12)
            {
            }
            else
            {
                isSuccess = true;
            }

            if (currentChord>0)
            {   //Harmony rules
                Note[] prev = new Note[4];
                prev[0] = bass[currentChord - 1];
                prev[1] = tenor[currentChord - 1];
                prev[2] = alto[currentChord - 1];
                prev[3] = soprano[currentChord - 1];
                Note[] now = new Note[4];
                now[0] = bass[currentChord];
                now[1] = tempTenor;
                now[2] = tempAlto;
                now[3] = tempSoprano;

                //what we want is to check every combination of two voices for various properties, effectively a cartesian product
                // so I don't forget: http://phrogz.net/lazy-cartesian-product

                breakToHere:
                for (int i = 0; i < 16; ++i)
                {
                    int first = (i / prev.length) % prev.length;
                    int second = i % prev.length;
                    if (first != second)
                    {
                        int outerInterval = Math.abs(prev[first].getLetterNum() - prev[second].getLetterNum()); //+1
                        int outerSemitone = Math.abs(prev[first].getChromaticNumber() - prev[second].getChromaticNumber()); //
                        int innerInterval = Math.abs(now[first].getLetterNum() - now[second].getLetterNum());
                        int innerSemitone = Math.abs(now[first].getChromaticNumber() - now[second].getChromaticNumber());

                        if (checkParallelFifths && (outerInterval % 7 == 4 && outerSemitone % 12 == 7) && (innerInterval % 7 == 4 && innerSemitone % 12 == 7))// No P5
                        {
                            if (prev[first].getChromaticNumber() - now[first].getChromaticNumber() != 0) //unless its the same notes
                            {
                                isSuccess = false;
                                break breakToHere;
                            }
                        }
                        if (checkParallelOctaves && (outerInterval % 7 == 0 && outerSemitone % 12 == 0) && (innerInterval % 7 == 0 && innerSemitone % 12 == 0))// No P8 or P1
                        {
                            if (prev[first].getChromaticNumber() - now[first].getChromaticNumber() != 0) //unless its the same notes
                            {
                                isSuccess = false;
                                break breakToHere;
                            }
                        }
                    }
                }

                if (isSuccess)
                {
                    //We also want to check for hidden 5ths or octaves in outer voices.
                    //This is when outer voices approach a perfect interval in similar motion
                    //It is not okay if soprano does not move by step to form the perfect interval
                    if (checkHiddenOctaves && (Math.abs(now[3].getChromaticNumber()-prev[3].getChromaticNumber())%12==0) && (Math.abs(now[3].getLetterNum()-prev[3].getLetterNum())%7==0))
                    {
                        int deltaTopInt=now[3].getLetterNum()-prev[3].getLetterNum();
                        int deltaBottomInt=now[0].getLetterNum()-prev[0].getLetterNum();
                        if (deltaTopInt*deltaBottomInt*-1 <0) //similar motion
                        {
                            if (Math.abs(deltaTopInt)>1) {
                                isSuccess = false;
                            }
                        }
                    }
                    if (checkHiddenFifths && (Math.abs(now[3].getChromaticNumber()-prev[3].getChromaticNumber())%12==7) && (Math.abs(now[3].getLetterNum()-prev[3].getLetterNum())%7 == 4))
                    {
                        int deltaTopInt=now[3].getLetterNum()-prev[3].getLetterNum();
                        int deltaBottomInt=now[0].getLetterNum()-prev[0].getLetterNum();
                        if (deltaTopInt*deltaBottomInt*-1 <0) //similar motion
                        {
                            if (Math.abs(deltaTopInt)>1) {
                                isSuccess = false;
                            }
                        }
                    }

                    //want close intervals between notes of the same part from previous chords
                    if (Math.abs(tempTenor.getChromaticNumber() - tenor[currentChord - 1].getChromaticNumber()) > 4) {//no more than M3
                        isSuccess = false;
                    }
                    if (Math.abs(tempAlto.getChromaticNumber() - alto[currentChord - 1].getChromaticNumber()) > 4) {//no more than M3
                        isSuccess = false;
                    }
                    if (Math.abs(tempSoprano.getChromaticNumber() - soprano[currentChord - 1].getChromaticNumber()) > 4) {//no more than M3
                        isSuccess = false;
                    }
                }
            }
            if (isSuccess)
            {
                onesThatWork.add(new Object[]{referenceArray, k});
            }
        }

        return onesThatWork;
    }
    public static void loop()
    {
        loop('p');
    }

    public static void loop(char arg) //method that found me all the possible inversions
    {
        //p for print all, 0 for 53, 1 for 63, 2 for 64, 5 for 753, 6 for 653, 7 for 643, 8 for 642
        Note[] notes;
        boolean use7=false;
        if (arg =='0'|| arg == '1' || arg == '2')
        {
            notes = new Note[]{new Note("C3"), new Note("E3"), new Note("G3"), new Note("C4"), new Note("E4"), new Note("G4"), new Note("C5"), new Note("E5"), new Note("G5"), new Note("C6"), new Note("E6"), new Note("G6"), new Note("C7")};
        }
        else //if (arg =='5' || arg =='6'|| arg=='7'|| arg=='8') and p
        {
            notes = new Note[]{new Note("C3"), new Note("E3"), new Note("G3"), new Note("B3"), new Note("C4"), new Note("E4"), new Note("G4"), new Note("B4"), new Note("C5"), new Note("E5"), new Note("G5"), new Note("B5"), new Note("C6"), new Note("E6"), new Note("G6"), new Note("B6"), new Note("C7")};
            use7=true;
        }
        boolean triplettrack = false;
        int tripletnum = 0;

        ArrayList<ArrayList<Note>> noteList = new ArrayList<ArrayList<Note>>();

        //Uses the fact that binary is either 1 or 0, just like whether a note is either used or not
        for (int i=(!use7?8192:131072); i>=0; --i)
        {
            ArrayList<Note> array = new ArrayList<Note>();
            String text = Integer.toBinaryString(i);
            while (text.length()<notes.length) //0 padding
            {
                text = "0"+text;
            }
            for (int j=0; j<text.length(); ++j){
                if (text.charAt(j)=='1')
                    array.add(notes[j]);
            }

            boolean isGood=true;

            //also want to allow for doubling of same note
            //so when it is length 3, iterate through doubling each one to check
            if (array.size()==3)
            {
                triplettrack=true;
                if(tripletnum==3)
                {
                    isGood=false;
                    tripletnum=0;
                    triplettrack=false;
                }
                else
                {
                    array.add(tripletnum,array.get(tripletnum));
                }
            }
            else if (array.size()!=4)
                isGood=false;
            if (isGood)
            {
                if (array.get(1).getChromaticNumber()-array.get(0).getChromaticNumber()>19)
                    isGood=false;
                if (array.get(2).getChromaticNumber()-array.get(1).getChromaticNumber()>12)
                    isGood=false;
                if (array.get(3).getChromaticNumber()-array.get(2).getChromaticNumber()>12)
                    isGood=false;
                if (array.get(0).getChromaticNumber()>=48)
                    isGood=false;

                int cCount=0, eCount=0, gCount=0, bCount=0;

                for (Note note : array){
                    if (note.toString().charAt(0)=='C')
                        cCount++;
                    else if (note.toString().charAt(0)=='E')
                        eCount++;
                    else if (note.toString().charAt(0)=='G')
                        gCount++;
                    else if (note.toString().charAt(0)=='B')
                        bCount++;
                }
                if (cCount==0 ||eCount==0)
                    isGood=false;
                if (!use7) {
                    if (array.get(0).toString().charAt(0) == 'C') {
                        if (arg != '0' && arg != 'p')
                            isGood = false;
                        if (cCount == 2 && eCount == 1 && gCount == 1) {
                        } else if (cCount == 1 && eCount == 1 && gCount == 2) {
                        } else if (cCount == 3 && eCount == 1 && gCount == 0) {
                        } else
                            isGood = false;
                    }
                    else if (array.get(0).toString().charAt(0) == 'E') {
                        if (arg != '1' && arg != 'p')
                            isGood = false;
                        if (cCount == 2 && eCount == 1 && gCount == 1) {
                        } else if (cCount == 1 && eCount == 2 && gCount == 1) {
                        } else if (cCount == 1 && eCount == 1 && gCount == 2) {
                        } else
                            isGood = false;
                    }
                    else if (array.get(0).toString().charAt(0) == 'G') {
                        if (arg != '2' && arg != 'p')
                            isGood = false;
                        if (cCount == 2 && eCount == 1 && gCount == 1) {
                        } else if (cCount == 1 && eCount == 2 && gCount == 1) {
                        } else if (cCount == 1 && eCount == 1 && gCount == 2) {
                        } else
                            isGood = false;
                    }
                }
                else //if (use7)
                {
                    if (array.get(0).toString().charAt(0) == 'C') {
                        if (arg != '5' && arg != 'p')
                            isGood = false;
                        if (cCount == 1 && eCount == 1 && gCount == 1 && bCount ==1) {
                        } else if (cCount == 2 && eCount == 1 && gCount == 0 && bCount ==1) {
                        } else
                            isGood = false;
                    }
                    else if (array.get(0).toString().charAt(0) == 'E') {
                        if (arg != '6' && arg != 'p')
                            isGood = false;
                        if (cCount == 1 && eCount == 1 && gCount == 1 && bCount ==1) {
                        } else
                            isGood = false;
                    }
                    else if (array.get(0).toString().charAt(0) == 'G') {
                        if (arg != '7' && arg != 'p')
                            isGood = false;
                        if (cCount == 1 && eCount == 1 && gCount == 1 && bCount ==1) {
                        } else
                            isGood = false;
                    }
                    else if (array.get(0).toString().charAt(0) == 'B') {
                        if (arg != '8' && arg != 'p')
                            isGood = false;
                        if (cCount == 1 && eCount == 1 && gCount == 1 && bCount ==1) {
                        } else
                            isGood = false;
                    }

                }

                if (isGood){
                    noteList.add(array);
                }
            }
            //tweak
            if (triplettrack)
            {
                i++;
                tripletnum++;
            }
        }

        int counter = 0;
        int[][] result = new int[noteList.size()][9];

        for (ArrayList<Note> array : noteList)
        {
            if (arg =='p')
            {
                System.out.print("{0, ");
                for (int k=1; k<=3; k++)
                {
                    if (array.get(k).toString().charAt(0)=='C')
                        System.out.print("0, ");
                    else if (array.get(k).toString().charAt(0)=='E')
                        System.out.print("1, ");
                    else if (array.get(k).toString().charAt(0)=='G')
                        System.out.print("2, ");
                    else if (array.get(k).toString().charAt(0)=='B')
                        System.out.print("3, ");
                }

                System.out.print("0, ");
                for (int k=5; k<=8; k++)
                {
                    System.out.print(""+(array.get(k-5).getLetterNum()-array.get(0).getLetterNum() + 1));
                    if (k<8)
                        System.out.print(", ");
                    else
                        System.out.print("}, //");
                }
                for (int k=0; k<4; k++) {
                    System.out.print("" + array.get(k));
                    if (k<3)
                        System.out.print(", ");
                    else
                        System.out.print("\n");
                }
            }
            else {
                result[counter][0]=0;
                for (int k=1; k<=3; k++)
                {
                    if (array.get(k).toString().charAt(0)=='C')
                        result[counter][k]=0;
                    else if (array.get(k).toString().charAt(0)=='E')
                        result[counter][k]=1;
                    else if (array.get(k).toString().charAt(0)=='G')
                        result[counter][k]=2;
                    else if (array.get(k).toString().charAt(0)=='B')
                        result[counter][k]=3;
                }
                result[counter][4]=1;
                for (int k=5; k<=8; k++)
                {
                    result[counter][k]=(array.get(k-5).getLetterNum()-array.get(0).getLetterNum() + 1);
                }
            }
            counter++;
        }
        switch (arg)
        {
            case '0': extendedRootPosition = result; break;
            case '1': extendedFirstInversion = result; break;
            case '2': extendedSecondInversion = result; break;
            case '5': extended7RootPosition = result; break;
            case '6': extended7FirstInversion = result; break;
            case '7': extended7SecondInversion = result; break;
            case '8': extended7ThirdInversion = result; break;
        }
    }


    public void buildBass()
    {
        for (int i=0; i<bass.length; ++i)
            bass[i]=null;

        Pitch[] possiblePitches = scale.getDiatonicTriad(currentProgression[0]);
        int octave;
        //Has chances of being 3 or 2, but has safeguard so that bass cannot be below E2
        octave = (Math.random()>(0.5)?3:((possiblePitches[0].getChromaticNumber()+ 2 * 12)>=28?2:3));
        bass[0] = new Note(possiblePitches[0], octave);

        for (int i=1; i< bass.length; ++i)
        {
            //allow only root or first inversion for bass note
            possiblePitches = scale.getDiatonicTriad(currentProgression[i]);

            Note[] tempNotes = new Note[4];
            tempNotes[0]=new Note(possiblePitches[0], 2);
            tempNotes[1]=new Note(possiblePitches[1], 2);
            tempNotes[2]=new Note(possiblePitches[0], 3);
            tempNotes[3]=new Note(possiblePitches[1], 3);

            //rule: vii can only be first inversion
            if (currentProgression[i]==7)
            {
                tempNotes= new Note[2];
                tempNotes[0]=new Note(possiblePitches[1], 2);
                tempNotes[1]=new Note(possiblePitches[1], 3);
            }

            //array to be sorted, in the format of nx3 array, {interval, element #, ranking}
            //lower ranking is better
            int [][] distance = new int[tempNotes.length][3];
            for (int j=0; j<tempNotes.length; ++j)
            {
                distance[j][0]=tempNotes[j].getLetterNum()-bass[i-1].getLetterNum(); //interval distance
                distance[j][1] = j;

                distance[j][2] = 5; //starting value
                if (tempNotes[j].getChromaticNumber()<28 || tempNotes[j].getChromaticNumber()>50) //out of bounds past E2 and D4
                    distance[j][2] = 20;

                if (Math.abs(distance[j][0])<=4) //smaller intervals are key.
                    distance[j][2]-=4-Math.abs(distance[j][0]);

                distance[j][2]+=Math.abs(tempNotes[j].getLetterNum()-22)/3; //prefer notes around middle of bass voice
            }
            java.util.Arrays.sort(distance, new java.util.Comparator<int[]>() {
                public int compare(int[] a, int[] b) {
                    return Integer.compare(Math.abs(a[2]), Math.abs(b[2]));
                }
            });

            int element;
            if (distance[1][2]<5)
                element = Math.random()<(distance[0][1]*1.0/(distance[0][1]+distance[1][1]))?1:0;
            else
                element=0;

            //keep track of which inversion is used and assign note
            if (currentProgression[i]==7)
                inversions[i] = 1;
            else
                inversions[i]=(distance[element][1]==1||distance[element][1]==3?1:0);
            bass[i] = tempNotes[distance[element][1]];
        }


        //find highest bass to set boundary
        for (Note note : bass)
        {
            if (boundaryBassHigh==null || boundaryBassHigh.getChromaticNumber()<note.getChromaticNumber())
                boundaryBassHigh=note;
        }
    }

    public int[] makeNLongChordProgression(int k, int whichMatrix, int startChord, int endChord)
    {
        //implementation is to loop k times, then check ending Chord

        int[] temp;
        do {
            temp = new int[k];
            temp[0] = startChord;
            for (int i = 1; i < k; ++i) {
                if (whichMatrix == 1)
                    temp[i] = MajorScale.returnNextProgression(temp[i - 1]);
                else //if (whichMatrix==2)
                    temp[i] = MajorScale.returnNextProgression2(temp[i - 1]);
            }
        }while(temp[k-1]!=endChord);
        return temp;
    }

    public Object[] makeProperProgression() // object array of size 3, 0=root, 1=inversions, 2=isSeventh
    {
        //Dynamically sized since progression size is controlled random
        ArrayList<String> position = new ArrayList<String>();
        position.ensureCapacity(16);
        
        position.add("I");

        //starting point in matrix array
        int currentPosition=0;

        //tonic harmony, 1-5 times
        int minForSection = (int)(Math.random()*5+1);

        for (int i=0; i< minForSection; ++i)
        {
            ArrayList<Integer> choices = new ArrayList<Integer>();
            for (int j=0; j< tonicMatrix.length; ++j)
                if (tonicMatrix[currentPosition][j]==1)
                    choices.add(j);
            if (choices.size()==0) { //Error output
                System.out.println("Current chord: "+indexToString.get(currentPosition) + ", " + i + " out of " + minForSection);
                for (String er: position)
                    System.out.print(er+"-");
            }
            int random = (int)(Math.random()*choices.size());

            currentPosition=choices.get(random);
            position.add(indexToString.get(choices.get(random)));

            if (i==minForSection-1) {
                i--;
                for (int k : tonicExit) {
                    if (k == stringToIndex.get(position.get(position.size() - 1))) {
                        i++;
                    }
                }
            }
        }

        //predominant harmony, 1-3 times
        minForSection = (int)(Math.random()*3+1);

        for (int i=0; i< minForSection; ++i)
        {
            ArrayList<Integer> choices = new ArrayList<Integer>();
            for (int j=0; j< predominantMatrix.length; ++j)
                if (predominantMatrix[currentPosition][j]==1)
                    choices.add(j);
            if (choices.size()==0) { //Error output
                System.out.println("Current chord: "+indexToString.get(currentPosition) + ", " + i + " out of " + minForSection);
                for (String er: position)
                    System.out.print(er+"-");
            }
            int random = (int)(Math.random()*choices.size());

            currentPosition=choices.get(random);
            position.add(indexToString.get(choices.get(random)));

            if (i==minForSection-1) {
                i--;
                for (int k : predominantExit) {
                    if (k == stringToIndex.get(position.get(position.size() - 1))) {
                        i++;
                    }
                }
            }
        }

        //dominant harmony, 1-2 times
        minForSection = (int)(Math.random()*2+1);

        for (int i=0; i< minForSection; ++i)
        {
            ArrayList<Integer> choices = new ArrayList<Integer>();
            for (int j=0; j< dominantMatrix.length; ++j)
                if (dominantMatrix[currentPosition][j]==1)
                    choices.add(j);
            if (choices.size()==0) { //Error output
                System.out.println("Current chord: "+indexToString.get(currentPosition) + ", " + i + " out of " + minForSection);
                for (String er: position)
                    System.out.print(er+"-");
            }
            int random = (int)(Math.random()*choices.size());

            currentPosition=choices.get(random);
            position.add(indexToString.get(choices.get(random)));

            if (i==minForSection-1) {
                i--;
                for (int k : dominantExit) {
                    if (k == stringToIndex.get(position.get(position.size() - 1))) {
                        i++;
                    }
                }
            }
        }
        position.add("I");

        int[] root = new int [position.size()];
        int[] inv = new int [position.size()];
        boolean[] is7 = new boolean[position.size()];

        int index = 0;
        for (String text : position)
        {
            String newText = text.toLowerCase();
            int split = newText.length();
            for (int end = 0; end<newText.length(); ++end)
            {
                if (newText.charAt(end)!='v' && newText.charAt(end)!='i'){
                    split=end;
                    break;
                }
            }

            //chord root
            String roman = text.substring(0,split).toLowerCase();
            //thankfully contains only i's and v's
            if (roman.contains("v"))
            {
                if (roman.charAt(0)=='v')
                    root[index] = 5 + roman.length()-1;
                else //if (roman.charAt(roman.length()-1)=='v')
                    root[index] = 5-(roman.length()-1);
            }
            else
                root[index] = roman.length();

            //Chord modifiers
            if (split<newText.length()) {
                //diminished
                boolean isDim = text.charAt(split) == '°';
                if (isDim)
                    split++;

                //Classifies chord as 7th/not 7th and identifies inversion
                String modifier = "";
                if (split < text.length())
                    modifier = text.substring(split);
                else {
                    is7[index] = false;
                    inv[index] = 0;
                }

                if (modifier.equals("") || modifier.equals("6") || modifier.equals("64")) {
                    is7[index] = false;
                    if (modifier.equals("6"))
                        inv[index] = 1;
                    else if (modifier.equals("64"))
                        inv[index] = 2;
                } else //if (modifier == "7" || modifier == "65" || modifier =="43" || modifier =="42")
                {
                    is7[index] = true;
                    if (modifier.equals("7"))
                        inv[index] = 0;
                    else if (modifier.equals("65"))
                        inv[index] = 1;
                    else if (modifier.equals("43"))
                        inv[index] = 2;
                    else if (modifier.equals("42"))
                        inv[index] = 3;
                }
            }
            ++index;
        }
        return new Object[]{root, inv, is7, root.length};
    }

    public boolean buildProperBass()
    {
        for (int i=0; i<bass.length; ++i)
            bass[i]=null;

        Pitch[] possiblePitches;

        if (!isSeventh[0])
            possiblePitches = scale.getDiatonicTriad(currentProgression[0]);
        else //if (isSeventh[0] == true)
            possiblePitches = scale.getDiatonic7th(currentProgression[0]);

        int octave = (key.chromaticNumber>7?2:3); //Higher than G
        bass[0] = new Note(possiblePitches[0], octave);

        for (int i=1; i< bass.length; ++i)
        {
            if (!isSeventh[i])
                possiblePitches = scale.getDiatonicTriad(currentProgression[i]);
            else //if (isSeventh[0] == true)
                possiblePitches = scale.getDiatonic7th(currentProgression[i]);

            Note[] tempNotes = new Note[3];
            tempNotes[0]=new Note(possiblePitches[inversions[i]], 2);
            tempNotes[1]=new Note(possiblePitches[inversions[i]], 3);
            tempNotes[2]=new Note(possiblePitches[inversions[i]], 4);

            //array to be sorted, in the format of nx2 array, {interval, element #}
            //lower ranking is better
            int [][] distance = new int[tempNotes.length][2];
            for (int j=0; j<tempNotes.length; ++j)
            {
                distance[j][0]=tempNotes[j].getLetterNum()-bass[i-1].getLetterNum(); //interval distance
                distance[j][1] = j;
            }
            java.util.Arrays.sort(distance, new java.util.Comparator<int[]>() {
                public int compare(int[] a, int[] b) {
                    return Integer.compare(Math.abs(a[0]), Math.abs(b[0]));
                }
            });

            int element=0;
            bass[i] = tempNotes[distance[element][1]];
        }

        //find highest bass to set boundary
        for (Note note : bass)
        {
            if (boundaryBassHigh==null || boundaryBassHigh.getChromaticNumber()<note.getChromaticNumber())
                boundaryBassHigh=note;
        }

        for (int i=0; i< bass.length; ++i) //searches for out of bounds, E2 28, D4 50
        {
            if (bass[i].getChromaticNumber()<28)
            {
                //for (int j=i; j< bass.length; ++j)
                //    bass[j]=Note.getOctaveHigher(bass[j]);
                return false;
            }
            if (bass[i].getChromaticNumber()>50)
            {
                //for (int j=i; j< bass.length; ++j)
                //    bass[j]=Note.getOctaveLower(bass[j]);
                return false;
            }
        }
        return true;
    }

    public String convertProgressionToRoman()
    {
        return convertProgressionToRoman(currentProgression, inversions, isSeventh);
    }

    public String convertProgressionToRoman(int[] progression, int[] inversions, boolean[] isSeventh)
    {
        String temp = "";
        for (int i=0; i< progression.length; ++i)
        {
            switch (progression[i])
            {
                case 1: temp+="I"; break;
                case 2: temp+="ii"; break;
                case 3: temp+="iii"; break;
                case 4: temp+="IV"; break;
                case 5: temp+="V"; break;
                case 6: temp+="vi"; break;
                case 7: temp+="vii°"; break;
            }
            if (!isSeventh[i]) {
                switch (inversions[i]) {
                    case 0: break;
                    case 1: temp += "6"; break;
                    case 2: temp += "64"; break;
                }
            }
            else// if (isSeventh[i])
            {
                switch (inversions[i]) {
                    case 0: temp+="7"; break;
                    case 1: temp+="65"; break;
                    case 2: temp+="43"; break;
                    case 3: temp+="42"; break;
                }
            }
            temp+="-";
        }
        return temp.substring(0,temp.length()-1);
    }
}
