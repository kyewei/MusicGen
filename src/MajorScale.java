/**
 * Created by Kye on 2014-07-08.
 */
public class MajorScale extends Scale
{
    public static Pitch[][] allScales = new Pitch[5*7][7];

    //LetterNames, like C#, Bbb, G
    public static Pitch[] generateScale(String scaleKey)
    {
        return generateSpecificScale(scaleKey, new String[]{"P1", "M2", "M3", "P4", "P5", "M6", "M7"});
    }
    public static Pitch[] generateScale(Pitch root)
    {
        return generateSpecificScale(new Pitch(root), new String[]{"P1", "M2", "M3", "P4", "P5", "M6", "M7"});
    }


    static
    {
        //Order from 0-34 is Cbb, Cb, C, C#, C##, Dbb, Db, D ... etc

        //rationale is to generate all possible scales now, so chord lookup is fast
        //DoubleFlat, Flat, Natural, Sharp, DoubleSharp, so 5 for each scale letter, and 7 scale letters, 35 scales altogether
        //Array allScales is 35x7

        for (int i =0; i< allScales.length; ++i)
        {
            allScales[i] = generateScale(""+name[i/5]+modifier[i%5]);
        }
    }
}
