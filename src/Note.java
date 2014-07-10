/**
 * Created by Kye on 2014-07-08.
 */
public class Note extends Pitch {

    protected int octave;

    public Note(Letter letter, Accidental accidental, int octave)
    {
        super(letter, accidental);
        this.octave = octave;
        this.chromaticNumber+=octave*12;
    }

    public Note(String noteName)
    {
        super(noteName.substring(0,noteName.length()-1));
        this.octave = Integer.parseInt(noteName.substring(noteName.length()-1, noteName.length()));
        this.chromaticNumber+=octave*12;
    }

    public int getOctave(){ return octave; }

    @Override
    public String toString()
    {
        return ""+letter+accidental+octave;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Note note = (Note) o;
        if (octave != note.octave) return false;
        if (accidental != note.accidental) return false;
        if (letter != note.letter) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = letter.hashCode();
        result = 31 * result + accidental.hashCode();
        result = 31 * result + octave;
        return result;
    }

    public static Note getOctaveLower (Note a){ return new Note(a.letter, a.accidental, a.octave-1); }
    public static Note getOctaveHigher(Note a){ return new Note(a.letter, a.accidental, a.octave+1); }

    public static int[][] qualityAndDegreeToSemitone = new int[][]{
            { -1,  0,  2,  4,  6,  7,  9, 11 },
            { -1,  1,  3, -1, -1,  8, 10, -1 },
            { -1,  2,  4, -1, -1,  9, 11, -1 },
            {  0, -1, -1,  5,  7, -1, -1, 12 },
            {  1,  3,  5,  6,  8, 10, 12, 13 }
    }; //row is by quality, column is semitone


    //Calculates intervals above a given note while preserving accidentals and not resorting to enharmonically equivalent note
    public static Note getIntervalHigher(Note a, int interval, char quality) // D/d - diminished, m - minor, M - major, P/p - perfect, A/a - augmented
    {
        boolean reverse = (interval<0);
        if(reverse)
            interval*=-1;

        int arrayIndex;
        switch(quality)
        {
            case 'D': arrayIndex=0; break;
            case 'd': arrayIndex=0; break; // Just in case
            case 'm': arrayIndex=1; break;
            case 'M': arrayIndex=2; break;
            case 'P': arrayIndex=3; break;
            case 'p': arrayIndex=3; break; // Just in case
            case 'A': arrayIndex=4; break;
            case 'a': arrayIndex=4; break; // Just in case
            default : arrayIndex=-1;
        }

        //Calculate semitones to move up by
        int deltaSemitone = qualityAndDegreeToSemitone[arrayIndex][((interval-1)%7+7)%7];

        //Working note values
        Letter tempLetter = a.letter;
        int tempOctave = a.octave;
        Accidental tempAccidental = a.accidental;

        //Fix Octaves
        int tempSemitone = a.getChromaticNumber();

        if(!reverse) {
            tempSemitone+=deltaSemitone/12;
            tempOctave += (deltaSemitone / 12) + (interval - 1) / 7 + (tempLetter.getNext(interval - 1).getChromaticNumber() < tempLetter.getChromaticNumber() ? 1 : 0);
        }
        else {
            tempSemitone-=deltaSemitone/12;
            tempOctave -= (deltaSemitone / 12) + (interval - 1) / 7 + (tempLetter.getNext(-(interval - 1)).getChromaticNumber() > tempLetter.getChromaticNumber() ? 1 : 0);
        }
        deltaSemitone %=12;

        //Fix Letter
        if(!reverse)
            tempLetter=tempLetter.getNext(interval-1);
        else
            tempLetter=tempLetter.getNext(-(interval-1));

        //Get new letterNote in natural and compare chromatic note;
        int tempSemitone2 = tempLetter.getChromaticNumber()+ tempOctave *12 + tempAccidental.getChromaticNumber();

        int diff;
        if(!reverse)
            diff = (deltaSemitone + ((interval-1)/7)*12) - (tempSemitone2 - tempSemitone);
        else
            diff = (tempSemitone - tempSemitone2) - (deltaSemitone + ((interval-1)/7)*12);
        tempAccidental = tempAccidental.getNext(diff);

        return new Note(tempLetter,tempAccidental, tempOctave);
    }

    // M2, m2, P4, A4, d5
    public static Note getIntervalHigher(Note a, String entry)
    {
        return getIntervalHigher(a, Integer.parseInt(entry.substring(1)), entry.charAt(0));
    }

    //Gets intervals below a given note, using a negative argument
    public static Note getIntervalLower(Note a, int interval, char quality)
    {
        return getIntervalHigher(a, -interval, quality);
    }
    public static Note getIntervalLower(Note a, String entry)
    {
        return getIntervalLower(a, Integer.parseInt(entry.substring(1)), entry.charAt(0));
    }
}
