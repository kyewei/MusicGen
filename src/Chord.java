/**
 * Created by Kye on 2014-07-08.
 */
public class Chord {
    protected int noteCount;
    protected Note[] notes;

    public Chord(Pitch root, String quality, Note[] array)
    {
        this.noteCount = array.length;
        this.notes = array;
    }

    public int getNoteCount(){ return noteCount; }

    /*public static Chord identify (Note[] array)
    {

    }*/


    //Exemplars: C, Cm, Caug, Cdim, Chalfdim, C7, Cmaj7, Cm7, Cdim7, Chalfdim7, C9, C11, C13
    //Return pitches involved in root position, in order
    public static Pitch[] getChordPitches(String chordName)
    {
        //Parsing
        Note.Letter rootLetter = Note.Letter.valueOf(""+chordName.charAt(0));
        //Sharps, flats, etc
        int indexOfAccidental=1;
        for (int i =1; i<chordName.length(); ++i)
        {
            if (chordName.charAt(i) == 'b' || chordName.charAt(i) == '#')
                indexOfAccidental = i + 1;
            else
                i=chordName.length();
        }
        Note.Accidental rootAccidental = Note.Accidental.fromString(chordName.substring(1,indexOfAccidental));

        int numberOfNotes=3; //Default triad
        switch(chordName.charAt(chordName.length()-1))
        {
            case '7': numberOfNotes=4; break;
            case '9': numberOfNotes=5; break; // Just in case
            case '1': numberOfNotes=6; break;
            case '3': numberOfNotes=7; break;
            default : numberOfNotes=3;
        }

        String chordQuality = chordName.substring(indexOfAccidental, chordName.length()-(numberOfNotes>3?1:0) - (numberOfNotes>5?1:0));

        Pitch[] temp = new Pitch[numberOfNotes];

        //Root is always the root
        temp[0]=new Pitch(rootLetter, rootAccidental);

        //Third is major third, take from major scale, case for augmented and major chords
        //Else :Third is minor third, take from minor scale
        int scaleIndex = Scale.indexFromKey(temp[0]);

        if (chordQuality.equals("") || chordQuality.equals("aug") || chordQuality.equals("maj"))
            temp[1]=new Pitch(MajorScale.allScales[scaleIndex][2]);
        else //if (chordQuality.equals("m") || chordQuality.equals("dim") || chordQuality.equals("halfdim"))
            temp[1]=new Pitch(HarmonicMinorScale.allScales[scaleIndex][2]);

        //Fifth is Perfect 5, take from major
        //Fifth is Dim 5, take from major and add flat
        if (chordQuality.equals("") || chordQuality.equals("m") || chordQuality.equals("maj"))
            temp[2]=new Pitch(MajorScale.allScales[scaleIndex][4]);
        else if (chordQuality.equals("dim") || chordQuality.equals("halfdim"))
            temp[2]=new Pitch(MajorScale.allScales[scaleIndex][4].letter, MajorScale.allScales[scaleIndex][4].accidental.getNext(-1));
        else //if (chordQuality.equals("aug"))
            temp[2]=new Pitch(MajorScale.allScales[scaleIndex][4].letter, MajorScale.allScales[scaleIndex][4].accidental.getNext(+1));

        if (numberOfNotes>3)
        {
            //stuff for 7th
            //dominant chord, lowered 7th
            if (chordQuality.equals("") || chordQuality.equals("m") || chordQuality.equals("halfdim"))
                temp[3]=new Pitch(MajorScale.allScales[scaleIndex][6].letter, MajorScale.allScales[scaleIndex][6].accidental.getNext(-1));
            else if (chordQuality.equals("dim"))
                temp[3]=new Pitch(MajorScale.allScales[scaleIndex][6].letter, MajorScale.allScales[scaleIndex][6].accidental.getNext(-2));
            else //if (chordQuality.equals("maj"))
                temp[3]=new Pitch(MajorScale.allScales[scaleIndex][6]);

            //probably missing some more, will complete fully later, right now just using major variants
            if (numberOfNotes>4)
            {
                //stuff for 9th
                //major second above root
                temp[4]=new Pitch(MajorScale.allScales[scaleIndex][1]);

                if (numberOfNotes>5)
                {
                    //stuff for 11th
                    //P4 above root
                    temp[5]=new Pitch(MajorScale.allScales[scaleIndex][3]);

                    if (numberOfNotes>6)
                    {
                        //stuff for 13th
                        //M6 above root
                        temp[5]=new Pitch(MajorScale.allScales[scaleIndex][5]);
                    }
                }
            }
        }
        return temp;
    }

}