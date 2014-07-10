/**
 * Created by Kye on 2014-07-10.
 */
public class Scale
{
    protected static String name[] = new String[]{"C", "D", "E", "F", "G", "A", "B"}; //0-6, length=7
    protected static String modifier[] = new String[]{"bb", "b", "", "#", "##"}; //0-4, length=5

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

    protected static Pitch[] generateSpecificScale(String scaleKey, String[] noteIntervals)
    {
        Note.Letter rootLetter = Note.Letter.valueOf(""+scaleKey.charAt(0));
        Note.Accidental rootAccidental;
        if (scaleKey.length()>1)
            rootAccidental = Note.Accidental.fromString(scaleKey.substring(1,scaleKey.length()));
        else
            rootAccidental = Note.Accidental.fromString("");
        Pitch[] temp = new Pitch[noteIntervals.length];
        temp[0] = new Pitch(rootLetter, rootAccidental);
        for (int i = 1; i< noteIntervals.length; ++i)
            temp[i] = Pitch.getHigherPitchWithInterval(temp[0], noteIntervals[i]);

        return temp;
    }

}
