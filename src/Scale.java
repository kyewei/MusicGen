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
        //Compares letter with representation in name[] array, and uses its index
        for (int i=0; i<name.length; ++i)
        {
            if (name[i].equals("" + scaleKey.charAt(0))) {
                index += i * 5; //5 scales based on a letter
                i = name.length;
            }
        }
        //If have accidentals, do a check to find its index
        if (scaleKey.length()>1)
        {
            for (int i = 0; i < modifier.length; ++i)
            {
                if (modifier[i].equals(scaleKey.substring(1, scaleKey.length())))
                {
                    index += i; //only one unique scale for each letter and accidental
                    i = modifier.length;
                }
            }
        }
        else
            index+=2;
        return index;
    }

    public static int indexFromKey(Pitch root)
    {
        int index =0;
        switch(root.letter)
        {
            case C: index=0; break;
            case D: index=5; break;
            case E: index=10; break;
            case F: index=15; break;
            case G: index=20; break;
            case A: index=25; break;
            case B: index=30; break;
            default : index=0;
        }
        switch(root.accidental)
        {
            case DoubleFlat: index+=0; break;
            case Flat: index+=1; break;
            case Natural: index+=2; break;
            case Sharp: index+=3; break;
            case DoubleSharp: index+=4; break;
            default : index=0;
        }
        return index;
    }

    protected static Pitch[] generateSpecificScale(String scaleKey, String[] noteIntervals)
    {
        return generateSpecificScale(new Pitch(scaleKey), noteIntervals);
    }

    protected static Pitch[] generateSpecificScale(Pitch root, String[] noteIntervals)
    {
        Pitch[] temp = new Pitch[noteIntervals.length];
        temp[0] = new Pitch(root);
        for (int i = 1; i< noteIntervals.length; ++i)
            temp[i] = Pitch.getHigherPitchWithInterval(temp[0], noteIntervals[i]);
        return temp;
    }

}
