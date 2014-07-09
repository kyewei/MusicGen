/**
 * Created by Kye on 2014-07-08.
 */
/*public class Chord {
    protected int noteCount;
    protected Note[] notes;

    public Chord(Pitch root, String quality, Note[] array)
    {
        this.noteCount = array.length();
        this.notes = array;
    }

    public int getNoteCount(){ return noteCount; }

    public static Chord identify (Note[] array)
    {

    }


    //Exemplars: C, Cm, Caug, Cdim, C7 (dominant, Cmaj7, Cm7
    //Return pitches involved in root position, in order
    public static getChordPitches(String chordName)
    {
        //Parsing
        Note.Letter rootLetter = Note.Letter.valueOf(""+chordName.charAt(0));
        //Sharps, flats, etc
        int indexOfQuality=1;
        for (int i =1; i<chordName.length(); ++i)
        {
            if (chordName.charAt(i) == 'b' || chordName.charAt(i) == '#')
                indexOfQuality = i + 1;
            else
                i=chordName.length();
        }
        Note.Accidental rootAccidental = Note.Accidental.fromString(chordName.substring(1,indexOfQuality));




        //Triad
        if ()
    }

}*/