/**
 * Created by Kye on 2014-07-08.
 */
public class MajorScale {

    //rationale is to generate all possible scales now, so chord lookup is fast
    //DoubleFlat, Flat, Natural, Sharp, DoubleSharp, so 5 for each scale letter, and 7 scale letters, 35 scales altogether
    //Array allScales is 35x7
    public static Pitch[][] allScales = new Pitch[35][7];

    //LetterNames, like C#, Bbb, G
    public static Pitch[] generateScale(String scaleKey)
    {
        Note.Letter rootLetter = Note.Letter.valueOf(""+scaleKey.charAt(0));
        Note.Accidental rootAccidental;
        if (scaleKey.length()>1)
            rootAccidental = Note.Accidental.fromString(scaleKey.substring(1,scaleKey.length()));
        else
            rootAccidental = Note.Accidental.fromString("");
        Pitch[] temp = new Pitch[7];
        temp[0] = new Pitch(rootLetter, rootAccidental);
        temp[1] = Pitch.getHigherPitchWithInterval(temp[0], "M2");
        temp[2] = Pitch.getHigherPitchWithInterval(temp[0], "M3");
        temp[3] = Pitch.getHigherPitchWithInterval(temp[0], "P4");
        temp[4] = Pitch.getHigherPitchWithInterval(temp[0], "P5");
        temp[5] = Pitch.getHigherPitchWithInterval(temp[0], "M6");
        temp[6] = Pitch.getHigherPitchWithInterval(temp[0], "M7");

        return temp;
    }

    public static int indexFromKey(String scaleKey)
    {
        int index = 0;
        for (int i=0; i<name.length; ++i)
        {
            if (name[i].equals("" + scaleKey.charAt(0))) {
                index += i * 5;
                i = name.length;
            }
        }
        if (scaleKey.length()>1)
        {
            for (int i = 0; i < modifier.length; ++i)
            {
                if (modifier[i].equals(scaleKey.substring(1, scaleKey.length())));
                {
                    index += i;
                    i = modifier.length;
                }
            }
        }
        else
            index+=2;
        return index;
    }

    protected static String name[] = new String[]{"C", "D", "E", "F", "G", "A", "B"}; //0-6
    protected static String modifier[] = new String[]{"bb", "b", "", "#", "##"}; //0-4
    static
    {
        //Order from 0-34 is Cbb, Cb, C, C#, C##, Dbb, Db, D ... etc

        for (int i =0; i< allScales.length; ++i)
        {
            allScales[i] = generateScale(""+name[i/5]+modifier[i%5]);
        }
    }
}
