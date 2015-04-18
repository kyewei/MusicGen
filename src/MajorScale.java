/**
 * Created by Kye on 2014-07-08.
 */
public class MajorScale extends Scale {

    public MajorScale(String key) {
        scale = allScales[indexFromKey(key)];
    }

    public MajorScale(Pitch key) {
        scale = allScales[indexFromKey(key)];
    }

    //LetterNames, like C#, Bbb, G
    public static Pitch[] generateScale(String scaleKey) {
        return generateSpecificScale(scaleKey, new String[]{"P1", "M2", "M3", "P4", "P5", "M6", "M7"});
    }

    public static Pitch[] generateScale(Pitch root) {
        return generateSpecificScale(new Pitch(root), new String[]{"P1", "M2", "M3", "P4", "P5", "M6", "M7"});
    }

    static double[][] chordProgression = {
            {0.002, 0.143, 0.025, 0.309, 0.376, 0.12, 0.025},
            {0.274, 0.002, 0.04, 0.032, 0.564, 0.04, 0.048},
            {0.063, 0.127, 0.003, 0.34, 0.063, 0.383, 0.021},
            {0.304, 0.132, 0.025, 0.002, 0.38, 0.025, 0.132},
            {0.793, 0.021, 0.021, 0.039, 00.002, 0.119, 0.005},
            {0.115, 0.232, 0.069, 0.139, 0.286, 0.043, 0.116},
            {0.583, 0, 0.027, 0.138, 0.194, 0.055, 0.003}
    };

    static double[][] chordProgression2 = {
            {0, 0, 0.25, 0.25, 0.25, 0.25, 0},
            {0, 0, 0, 0, 0.5, 0, 0.5},
            {0, 0, 0, 0, 0, 1, 0},
            {0.25, 0.25, 0, 0, 0.25, 0, 0.25},
            {0.5, 0, 0, 0, 0, 0.5, 0},
            {0, 0.5, 0, 0.5, 0, 0, 0},
            {0.5, 0, 0, 0, 0.5, 0, 0}
    };
    //row is each diatonic chord, column element is probability of getting chord after previous said chord

    public static int returnNextProgression(int currentProgressionDegree) {
        currentProgressionDegree -= 1; //array start at 0
        double random = Math.random();
        double probabilityCounter = 0;
        for (int i = 0; i < chordProgression[currentProgressionDegree].length; ++i) {
            if (probabilityCounter <= random && random < probabilityCounter + chordProgression[currentProgressionDegree][i])
                return i + 1;
            probabilityCounter += chordProgression[currentProgressionDegree][i];
        }
        return currentProgressionDegree;
    }

    public static int returnNextProgression2(int currentProgressionDegree) {
        currentProgressionDegree -= 1; //array start at 0
        double random = Math.random();
        double probabilityCounter = 0;
        for (int i = 0; i < chordProgression2[currentProgressionDegree].length; ++i) {
            if (probabilityCounter <= random && random < probabilityCounter + chordProgression2[currentProgressionDegree][i])
                return i + 1;
            probabilityCounter += chordProgression2[currentProgressionDegree][i];
        }
        return currentProgressionDegree;
    }

    static {
        //Order from 0-34 is Cbb, Cb, C, C#, C##, Dbb, Db, D ... etc

        //rationale is to generate all possible scales now, so chord lookup is fast
        //DoubleFlat, Flat, Natural, Sharp, DoubleSharp, so 5 for each scale letter, and 7 scale letters, 35 scales altogether
        //Array allScales is 35x7

        allScales = new Pitch[5 * 7][7];
        for (int i = 0; i < allScales.length; ++i) {
            allScales[i] = generateScale("" + name[i / 5] + modifier[i % 5]);
        }
    }
}
