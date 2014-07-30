import java.util.ArrayList;

/**
 * Created by Kye on 2014-07-07.
 */
public class HarmonyEngine
{
    public HarmonyEngine()
    {
        //defaults
        numberOfChords=8;
        currentProgression = new int[]{1,3,4,5,4,1,5,1};
        key = new Pitch("C");
        scale = new MajorScale(key);
        currentChord=0;

        soprano = new Note[numberOfChords];
        alto = new Note[numberOfChords];
        tenor = new Note[numberOfChords];
        bass = new Note[numberOfChords];
        inversions = new int[numberOfChords];
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

    public Note[] getSoprano() { return soprano; }
    public Note[] getAlto() { return alto; }
    public Note[] getTenor() { return tenor; }
    public Note[] getBass() { return bass; }

    public int[][] arrangements = {
            {0, 2, 1, 0, 0, 1, 5, 11, 15}, //C3, G3, E4, C5
            {0, 0, 2, 1, 0, 1, 8, 12, 17}, //C3, C4, G4, E5
            {0, 1, 2, 0, 0, 1, 10, 12, 15}, //C3, E4, G4, C5
            {0, 0, 1, 2, 0, 1, 8, 10, 12}, //C3, C4, E4, G4
            {0, 1, 0, 2, 0, 1, 3, 8, 12}, //C3, E3, C4, G4
            {0, 2, 0, 1, 0, 1, 5, 8, 10}, //C3, G3, C4, E4
            {0, 1, 0, 2, 0, 1, 10, 15, 19}, //C3, E4, C5, G5
            {0, 2, 0, 1, 0, 1, 12, 15, 17} //C3, G4, C5, E5
    };

    public int[][] extendedRootPosition = { //allow doubling
            {0, 1, 2, 0, 0, 1, 3, 5, 8}, //C3, E3, G3, C4
            {0, 1, 0, 2, 0, 1, 3, 8, 12}, //C3, E3, C4, G4
            {0, 1, 0, 0, 0, 1, 3, 8, 15}, //C3, E3, C4, C5
            {0, 2, 0, 1, 0, 1, 5, 8, 10}, //C3, G3, C4, E4
            {0, 2, 1, 0, 0, 1, 5, 10, 15}, //C3, G3, E4, C5
            {0, 0, 1, 2, 0, 1, 8, 10, 12}, //C3, C4, E4, G4
            {0, 0, 1, 0, 0, 1, 8, 10, 15}, //C3, C4, E4, C5
            {0, 0, 2, 1, 0, 1, 8, 12, 17}, //C3, C4, G4, E5
            {0, 0, 0, 1, 0, 1, 8, 15, 17}, //C3, C4, C5, E5
            {0, 1, 2, 0, 0, 1, 10, 12, 15}, //C3, E4, G4, C5
            {0, 1, 0, 2, 0, 1, 10, 15, 19}, //C3, E4, C5, G5
            {0, 1, 0, 0, 0, 1, 10, 15, 22}, //C3, E4, C5, C6
            {0, 2, 0, 1, 0, 1, 12, 15, 17}, //C3, G4, C5, E5
            {0, 2, 1, 0, 0, 1, 12, 17, 22} //C3, G4, E5, C6
    };

    public int[][] extendedFirstInversion = {
            {0, 2, 0, 1, 0, 1, 3, 6, 8}, //E3, G3, C4, E4
            {0, 2, 0, 2, 0, 1, 3, 6, 10}, //E3, G3, C4, G4
            {0, 2, 0, 0, 0, 1, 3, 6, 13}, //E3, G3, C4, C5
            {0, 2, 1, 0, 0, 1, 3, 8, 13}, //E3, G3, E4, C5
            {0, 2, 2, 0, 0, 1, 3, 10, 13}, //E3, G3, G4, C5
            {0, 0, 1, 2, 0, 1, 6, 8, 10}, //E3, C4, E4, G4
            {0, 0, 2, 0, 0, 1, 6, 10, 13}, //E3, C4, G4, C5
            {0, 0, 2, 1, 0, 1, 6, 10, 15}, //E3, C4, G4, E5
            {0, 0, 2, 2, 0, 1, 6, 10, 17}, //E3, C4, G4, G5
            {0, 0, 0, 2, 0, 1, 6, 13, 17}, //E3, C4, C5, G5
            {0, 1, 2, 0, 0, 1, 8, 10, 13}, //E3, E4, G4, C5
            {0, 1, 0, 2, 0, 1, 8, 13, 17}, //E3, E4, C5, G5
            {0, 2, 0, 1, 0, 1, 10, 13, 15}, //E3, G4, C5, E5
            {0, 2, 0, 2, 0, 1, 10, 13, 17}, //E3, G4, C5, G5
            {0, 2, 0, 0, 0, 1, 10, 13, 20}, //E3, G4, C5, C6
            {0, 2, 1, 0, 0, 1, 10, 15, 20}, //E3, G4, E5, C6
            {0, 2, 2, 0, 0, 1, 10, 17, 20} //E3, G4, G5, C6
    };


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

    public Note boundaryBassHigh;


    public void start()
    {
        for (int i=0; i< bass.length; ++i) //clear chords later since this change
        {
            tenor[i]=null;
            alto[i]=null;
            soprano[i]=null;
        }

        //Get possible pitches and initialise
        Pitch[] possiblePitches = scale.getDiatonicTriad(currentProgression[0]);
        boolean isSuccess;
        Note tempTenor, tempAlto, tempSoprano;

        //Loop getting a random arrangement until one matches criteria
        int startElement = (int)(Math.random()*arrangements.length);
        int counter = startElement;
        do {
            isSuccess=true;

            //More efficient to make counter object here, and compare counter object than to make one for every command below
            tempTenor = new Note(possiblePitches[arrangements[counter][1]], (bass[0].getLetterNum() + arrangements[counter][6]-1)/7);
            tempAlto = new Note(possiblePitches[arrangements[counter][2]], (bass[0].getLetterNum() + arrangements[counter][7]-1)/7);
            tempSoprano = new Note(possiblePitches[arrangements[counter][3]], (bass[0].getLetterNum() + arrangements[counter][8]-1)/7);


            counter=(counter+1)%arrangements.length;

            //Boundaries for each voice that cannot be passed

            //Boundaries G4 and C3
            if (tempTenor.getChromaticNumber()>55 || tempTenor.getChromaticNumber()<36) {
                isSuccess = false; continue;
            }
            //Boundaries D5 and G3
            if (tempAlto.getChromaticNumber()>62 || tempAlto.getChromaticNumber()<43) {
                isSuccess = false; continue;
            }
            //Boundaries A5 and C4
            if (tempSoprano.getChromaticNumber()>69 || tempSoprano.getChromaticNumber()<48){
                isSuccess = false; continue;
            }

            //No crossing
            if (tempTenor.getChromaticNumber() < boundaryBassHigh.getChromaticNumber()) {
                isSuccess = false; continue;
            }

                //Interval between voices can't be too wide
            if (tempTenor.getChromaticNumber()-bass[0].getChromaticNumber()>19) {
                isSuccess = false; continue;
            }
            if (tempAlto.getChromaticNumber()-tempTenor.getChromaticNumber()>12) {
                isSuccess = false; continue;
            }
            if (tempSoprano.getChromaticNumber()-tempAlto.getChromaticNumber()>12) {
                isSuccess = false; continue;
            }
        } while(!isSuccess && startElement!=counter);

        if (startElement==counter) {
            System.out.println("No Matches Found");
            bass[0]=null;
            tenor[0]=null;
            alto[0]=null;
            soprano[0]=null;
            currentChord=0;
        }
        else
        {
            tenor[0]=tempTenor;
            alto[0]=tempAlto;
            soprano[0]=tempSoprano;

            currentChord=1;
        }

        //System.out.println(""+bass[0]+" "+tenor[0]+" "+alto[0]+" "+soprano[0]);
    }

    public void reset()
    {
        boundaryBassHigh=null;
        currentChord=0;
        for (int i=0; i<bass.length; ++i)
            bass[i]=null;
        for (int i=0; i<tenor.length; ++i)
            tenor[i]=null;
        for (int i=0; i<alto.length; ++i)
            alto[i]=null;
        for (int i=0; i<soprano.length; ++i)
            soprano[i]=null;


    }

    public void next()
    {
        for (int i=currentChord; i< bass.length; ++i) //clear chords later since this change
        {
            tenor[i]=null;
            alto[i]=null;
            soprano[i]=null;
        }
        if (currentChord<bass.length) //makes no sense to make chords farther than the last possible one
        {
            //Get possible pitches and initialise
            Pitch[] possiblePitches = scale.getDiatonicTriad(currentProgression[currentChord]);
            boolean isSuccess;
            Note tempTenor, tempAlto, tempSoprano;


            //Loop getting a random arrangement until one matches criteria
            int startElement = (int) (Math.random() * arrangements.length);
            int counter = startElement;
            do {
                isSuccess = true;
                int[][] referenceArray;
                if (inversions[currentChord] == 0)
                    referenceArray = extendedRootPosition;
                else //if (inversions[currentChord]==1)
                    referenceArray = extendedFirstInversion;

                tempTenor = new Note(possiblePitches[referenceArray[counter][1]], (bass[currentChord].getLetterNum() + referenceArray[counter][6] - 1) / 7);
                tempAlto = new Note(possiblePitches[referenceArray[counter][2]], (bass[currentChord].getLetterNum() + referenceArray[counter][7] - 1) / 7);
                tempSoprano = new Note(possiblePitches[referenceArray[counter][3]], (bass[currentChord].getLetterNum() + referenceArray[counter][8] - 1) / 7);
                counter = (counter + 1) % referenceArray.length;


                //Boundaries for each voice that cannot be passed

                //Boundaries G4 and C3
                if (tempTenor.getChromaticNumber() > 55 || tempTenor.getChromaticNumber() < 36) {
                    isSuccess = false;
                    System.out.println("a"+ counter);
                    continue;
                }
                //Boundaries D5 and G3
                if (tempAlto.getChromaticNumber() > 62 || tempAlto.getChromaticNumber() < 43) {
                    isSuccess = false;
                    System.out.println("b"+ counter);
                    continue;
                }
                //Boundaries A5 and C4
                if (tempSoprano.getChromaticNumber() > 69 || tempSoprano.getChromaticNumber() < 48) {
                    isSuccess = false;
                    System.out.println("c"+ counter);
                    continue;
                }
                //No crossing
                if (tempTenor.getChromaticNumber() < boundaryBassHigh.getChromaticNumber()) {
                    isSuccess = false;
                    System.out.println("d"+ counter);
                    continue;
                }
                //Interval between voices can't be too wide
                if (tempTenor.getChromaticNumber() - bass[currentChord].getChromaticNumber() > 19) {
                    isSuccess = false;
                    System.out.println("e"+ counter);
                    continue;
                }
                if (tempAlto.getChromaticNumber() - tempTenor.getChromaticNumber() > 12) {
                    isSuccess = false;
                    System.out.println("f"+ counter);
                    continue;
                }
                if (tempSoprano.getChromaticNumber() - tempAlto.getChromaticNumber() > 12) {
                    isSuccess = false;
                    System.out.println("g"+ counter);
                    continue;
                }


                //Harmony rules

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
                for (int i=0; i<16; ++i)
                {
                    int first = (i/prev.length)%prev.length;
                    int second = i%prev.length;
                    if (first!=second)
                    {
                        int outerInterval = Math.abs(prev[first].getLetterNum() - prev[second].getLetterNum()); //+1
                        int outerSemitone = Math.abs(prev[first].getChromaticNumber() - prev[second].getChromaticNumber()); //

                        int innerInterval = Math.abs(now[first].getLetterNum() - now[second].getLetterNum());
                        int innerSemitone = Math.abs(now[first].getChromaticNumber() - now[second].getChromaticNumber());

                        if ((outerInterval%7 ==4 && outerSemitone%12 == 7) && (innerInterval%7 ==4 && innerSemitone%12 == 7))// No P5
                        {
                            if (prev[first].getChromaticNumber() - now[first].getChromaticNumber()!=0) //unless its the same notes
                            {
                                isSuccess = false;
                                System.out.println("h"+ counter);
                                break breakToHere;
                            }
                        }

                        if ((outerInterval%7 ==0 && outerSemitone%12 == 0) && (innerInterval%7 ==0 && innerSemitone%12 == 0))// No P8
                        {
                            if (prev[first].getChromaticNumber() - now[first].getChromaticNumber()!=0) //unless its the same notes
                            {
                                isSuccess = false;
                                System.out.println("i"+ counter);
                                break breakToHere;
                            }
                        }
                    }
                }
                if (!isSuccess)
                    continue;

                //want close intervals between notes of the same part from previous chords
                if (Math.abs(tempTenor.getChromaticNumber() - tenor[currentChord - 1].getChromaticNumber()) > 5) {//no more than M3
                    isSuccess = false;
                    continue;
                }
                if (Math.abs(tempAlto.getChromaticNumber() - alto[currentChord - 1].getChromaticNumber()) > 5) {//no more than M3
                    isSuccess = false;
                    continue;
                }
                if (Math.abs(tempSoprano.getChromaticNumber() - soprano[currentChord - 1].getChromaticNumber()) > 5) {//no more than M3
                    isSuccess = false;
                    continue;
                }
                //No parallel octaves


            } while (!isSuccess && startElement != counter);

            if (startElement == counter) {
                System.out.println("No Matches Found");
                tenor[currentChord] = null;
                alto[currentChord] = null;
                soprano[currentChord] = null;
            } else {
                tenor[currentChord] = tempTenor;
                alto[currentChord] = tempAlto;
                soprano[currentChord] = tempSoprano;

                currentChord++;
            }

        }
    }

    public void loop() //method that found me all the possible inversions
    {
        Note[] notes = {new Note("C3"), new Note("E3"), new Note("G3"), new Note("C4"), new Note("E4"), new Note("G4"), new Note("C5"), new Note("E5"), new Note("G5"), new Note("C6"), new Note("E6"), new Note("G6"), new Note("C7")};
        for (int i=8192; i>=0; --i)
        {
            ArrayList<Note> array = new ArrayList<Note>();
            String text = Integer.toBinaryString(i);
            while (text.length()<13)
            {
                text = "0"+text;
            }
            //System.out.println(text);
            for (int j=0; j<text.length(); ++j){
                if (text.charAt(j)=='1')
                    array.add(notes[j]);
            }

            boolean isGood=true;

            if (array.size()!=4)
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

                int cCount=0, eCount=0, gCount=0;

                for (Note note : array){
                    if (note.toString().charAt(0)=='C')
                        cCount++;
                    if (note.toString().charAt(0)=='E')
                        eCount++;
                    if (note.toString().charAt(0)=='G')
                        gCount++;
                }
                if (cCount==0 ||eCount==0)
                    isGood=false;
                if (array.get(0).toString().charAt(0)=='C')
                {
                    if (cCount==2 && eCount==1 &&gCount==1)
                    {}
                    else if (cCount==3 && eCount==1 &&gCount==0)
                    {}
                    else
                        isGood=false;
                }
                if (array.get(0).toString().charAt(0)=='E')
                {
                    if (cCount==2 && eCount==1 &&gCount==1)
                    {}
                    else if (cCount==1 && eCount==2 &&gCount==1)
                    {}
                    else if (cCount==1 && eCount==1 &&gCount==2)
                    {}
                    else
                        isGood=false;
                }


                //{0, 2, 0, 1, 0, 1, 12, 15, 17} //C3, G4, C5, E5
                if (isGood){
                    System.out.print("{0, ");
                    for (int k=1; k<=3; k++)
                    {
                        if (array.get(k).toString().charAt(0)=='C')
                            System.out.print("0, ");
                        if (array.get(k).toString().charAt(0)=='E')
                            System.out.print("1, ");
                        if (array.get(k).toString().charAt(0)=='G')
                            System.out.print("2, ");
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
            }


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
                if (tempNotes[j].getChromaticNumber()<28 || tempNotes[j].getChromaticNumber()>48) //out of bounds
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

            for (int[] arr : distance)
                System.out.println(arr[0]+" "+arr[1]+" "+arr[2]);

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

    /*public void makeMelody(int whichMatrix)
    {
        //default is to make a 16 note melody, with two phrases of 8 notes each
        //will use chord progressions to make melody sound nice
        //first phrase will end on a V, such as a half cadence, and start with I
        //second phrase will end with VI, and will start with the same note as first phrase's ending note


        //initialise and generate progression to follow, and first note
        int[] phrase1, phrase2;
        phrase1 = makeNLongChordProgression(8, whichMatrix, 1, 5);
        phrase2 = makeNLongChordProgression(8, whichMatrix, 1, 1);
        Note[] melody = new Note[16];
        Pitch tempPitch = scale.getDiatonicTriad(phrase1[0])[(int)(Math.random()*3)];
        int octave = tempPitch.letter.getLetterNum()<=2?5:4;// E4 to D5
        melody[0] = new Note(tempPitch, octave);

        boolean directionUp = Math.random()<0.5;

        for (int i=1; i<16; ++i)
        {
            //getting the notes of the chord
            Pitch[] triad;
            if (i<8)
                triad= scale.getDiatonicTriad(phrase1[i]);
            else //if (i>=8)
                triad= scale.getDiatonicTriad(phrase2[i-8]);

            int[] intervals = new int[3];

            for (int j =0; j< 3; ++j)
            {
                intervals[j] = triad[j].getLetterNum() - melody[i - 1].letter.getLetterNum(); //interval difference
                if (Math.abs(intervals[j]-7)<Math.abs(intervals[j]))
                    intervals[j]=intervals[j]-7;

                System.out.println(intervals[j]);
            }
            System.out.println("\n");
            int index=0, distance=10;
            for (int j =0; j< 3; ++j)
            {
                if (Math.abs(intervals[j])< distance && intervals[j]!=0) //find minimum interval, but not allow 0
                {
                    distance = Math.abs(intervals[j]);
                    index = j;
                }
            }

            //Making the actual note


            melody[i] = new Note(triad[index], octave);
            //melody[i] = scale.getDiatonicTriad(phrase1[i])[(int)(Math.random()*3)];
        }
        System.out.println("\n");


        //Note[] sopranoLine = new Note[16];
        //Add octave and make first note
        //int octave = melody[0].getLetterNum()<=2?5:4;
        //sopranoLine[0]=new Note(melody[0], octave);

        //Add nearest note with specified pitch;
        //for (int i=1; i<16;++i)
        //{

        //}

        System.out.print(convertProgressionToRoman(phrase1));
        System.out.println("     "+convertProgressionToRoman(phrase2));

        for (Pitch pitch : melody)
        {
            System.out.print(pitch+ "  ");
        }
        System.out.print("\n");

    }*/

    public int[] makeNLongChordProgression2(int k, int whichMatrix, int startChord, int endChord)
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


    public int[] makeNLongChordProgression (int k, int whichMatrix, int startChord, int endChord) //whichMatrix is 1 or 2 for now
    {
        //implementation is to loop until seeing endChord, then check if k length

        int chord=startChord;  //always start with I chord

        String result = "";
        int counter =1;

        while (counter !=k) //runs for k times
        {
            result = "" + chord;
            do {
                if (whichMatrix==1)
                    chord = MajorScale.returnNextProgression(chord);
                else //if (whichMatrix==2)
                    chord = MajorScale.returnNextProgression2(chord);
                result += chord;
                counter++;
            } while (chord != endChord);

            if (counter !=k)
            {
                counter = 1;
                result = "";
                chord=startChord;
            }
        }
        int[] tempProgression = new int[k];
        for (int i = 0; i<k; ++i)
        {
            tempProgression[i]=((int)(result.charAt(i)))-'0';

        }
        numberOfChords = k;
        return tempProgression;
    }

    public String convertProgressionToRoman()
    {
        return convertProgressionToRoman(currentProgression);
    }

    public String convertProgressionToRoman(int[] progression)
    {
        String temp = "";
        for (int k : progression)
        {
            switch (k)
            {
                case 1: temp+="I"; break;
                case 2: temp+="ii"; break;
                case 3: temp+="iii"; break;
                case 4: temp+="IV"; break;
                case 5: temp+="V"; break;
                case 6: temp+="vi"; break;
                case 7: temp+="vii°"; break;
            }
            temp+="-";

        }
        return temp.substring(0,temp.length()-1);
    }

}
