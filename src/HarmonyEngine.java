/**
 * Created by Kye on 2014-07-07.
 */
public class HarmonyEngine
{

    public HarmonyEngine()
    {

    }

    public static String makeNLongChordProgression (int k, int whichOne) //whichOne is 1 or 2 for now
    {
        int chord=1;
        String result = "";

        int counter =1;

        while (counter !=k)
        {
            result = "" + chord + " \n";

            do {
                if (whichOne==1)
                    chord = MajorScale.returnNextProgression(chord);
                else //if (whichOne==2)
                    chord = MajorScale.returnNextProgression2(chord);
                result += chord + " \n";
                counter++;
            } while (chord != 1);
            result += "\n";

            if (counter !=k)
            {
                counter = 1;
                result = "";
            }
        }

        return result;
    }



}
