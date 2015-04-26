package com.kyewei.MusicTools;

/**
 * Created by Kye on 2014-07-07.
 */
public class Pitch {
    protected Letter letter;
    protected Accidental accidental;

    protected int chromaticNumber, letterNum;

    public Pitch(Letter letter, Accidental accidental) //Constructor based on specified letter and accidental
    {
        this.letter = letter;
        this.accidental = accidental;
        this.chromaticNumber = letter.getChromaticNumber() + accidental.getChromaticNumber();
        this.letterNum = letter.getLetterNum();
    }

    public Pitch(String noteName) //converts notename to Pitch
    {
        this.letter = Letter.valueOf(noteName.substring(0, 1));
        if (noteName.length() > 1)
            this.accidental = Accidental.fromString(noteName.substring(1, noteName.length()));
        else
            this.accidental = Accidental.fromString("");
        this.chromaticNumber = letter.getChromaticNumber() + accidental.getChromaticNumber();
        this.letterNum = letter.getLetterNum();
    }

    public Pitch(Pitch pitch) //copy constructor
    {
        this(pitch.letter, pitch.accidental);
    }

    public int getChromaticNumber()//accessor method
    {
        return chromaticNumber;
    }

    public int getLetterNum() {
        return letterNum;
    }

    //Gets interval (<8th) using Note class's method of the same name
    public static Pitch getHigherPitchWithInterval(Pitch a, int interval, char quality) // D/d - diminished, m - minor, M - major, P/p - perfect, A/a - augmented
    {
        Note temporary = Note.getIntervalHigher(new Note(a.letter, a.accidental, 4), interval, quality); //Makes new note with random octave
        return new Pitch(temporary.letter, temporary.accidental); //discards octave and makes note
        //return ((Pitch)temporary); //upconverts
    }

    // M2, m2, P4, A4, d5
    //Alternate method with string input for interval
    public static Pitch getHigherPitchWithInterval(Pitch a, String entry) {
        return getHigherPitchWithInterval(a, Integer.parseInt(entry.substring(1)), entry.charAt(0));
    }

    @Override
    public String toString() {
        return "" + letter + accidental;
    }

    public String printForLilypondAbsolute() {
        String str = "" + letter.toString().toLowerCase();
        if (accidental == Accidental.Sharp)
            str += "is";
        else if (accidental == Accidental.Flat)
            str += "es";
        return str;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pitch pitch = (Pitch) o;
        if (accidental != pitch.accidental) return false;
        if (letter != pitch.letter) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = letter.hashCode();
        result = 31 * result + accidental.hashCode();
        return result;
    }

    public enum Accidental {
        DoubleFlat("bb", -2), Flat("b", -1), Natural("", 0), Sharp("#", 1), DoubleSharp("##", 2);

        private String name; //musical symbol in string form
        private int chromaticNumber; //effect on a natural note's semitone

        private Accidental(String name, int num) {
            this.name = name;
            this.chromaticNumber = num;
        }

        public int getChromaticNumber() {
            return chromaticNumber;
        } //accessor method

        @Override
        public String toString() {
            return name;
        }

        public static Accidental fromString(String name) {
            for (Accidental accidental : Accidental.values()) {
                if (accidental.toString().equals(name)) {
                    return accidental;
                }
            }
            throw new IllegalArgumentException("No enum Pitch.Accidental." + name); //If an invalid thing is sent in, gives error
        }

        public Accidental getNext(int howMany) //gets symbol relative to current accidental, bb -> b -> _ -> # -> ##
        {
            //Positive modulus
            int index = (((this.ordinal() + howMany) % Accidental.values().length) + Accidental.values().length) % Accidental.values().length;
            return Accidental.values()[index];
        }

        public Accidental getNext() {
            return this.getNext(1);
        } //default
    }

    public enum Letter {
        C(0), D(2), E(4), F(5), G(7), A(9), B(11);

        private int chromaticNumber; //natural note's semitone in a system where C is 0
        private int letterNum;

        private Letter(int num) {
            this.chromaticNumber = num;
            this.letterNum = (((int) (this.name().charAt(0)) - 'C') + 7) % 7;
        }

        public int getChromaticNumber() {
            return chromaticNumber;
        }

        @Override
        public String toString() {
            return this.name();
        }

        public int getLetterNum() {
            return letterNum;
        }

        public static Letter getLetterFromLetterNum(int letterNum) {
            return Letter.valueOf("" + ((char) ((letterNum > 4 ? letterNum - 7 : letterNum) + 'C')));
        }

        public Letter getNext() {
            return this.getNext(1);
        }

        public Letter getNext(int howMany) //gets letter relative to current letter, C,D,E,F,G,A,B
        {
            //Positive modulus
            int index = (((this.ordinal() + howMany) % Letter.values().length) + Letter.values().length) % Letter.values().length;
            return Letter.values()[index];
        }
    }
}
