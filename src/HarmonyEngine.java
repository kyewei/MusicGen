/**
 * Created by Kye on 2014-07-07.
 */
public class HarmonyEngine
{
    public HarmonyEngine()
    {

    }


    public int[] currentProgression;

    public int[] makeNLongChordProgression (int k, int whichMatrix) //whichMatrix is 1 or 2 for now
    {
        int chord=1;
        String result = "";
        int counter =1;

        while (counter !=k)
        {
            result = "" + chord;
            do {
                if (whichMatrix==1)
                    chord = MajorScale.returnNextProgression(chord);
                else //if (whichMatrix==2)
                    chord = MajorScale.returnNextProgression2(chord);
                result += chord;
                counter++;
            } while (chord != 1);

            if (counter !=k)
            {
                counter = 1;
                result = "";
            }
        }
        currentProgression = new int[k];
        for (int i = 0; i<k; ++i)
        {
            currentProgression[i]=((int)(result.charAt(i)))-'0';

        }
        return currentProgression;
    }

    public String convertProgressionToRoman()
    {
        String temp = "";
        for (int k : currentProgression)
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




    /*public static Note[][] harmonyFromBaseLine (Note[] baseLine, int numberOfNotes)
    {
        if (baseLine == null || baseLine.length!=numberOfNotes ||numberOfNotes==0)
            return null;
        return makeHarmony(null, null, numberOfNotes, baseLine);
    }

    public static Note[][] harmonyFromMelodyLine (Note[] melodyLine, int numberOfNotes)
    {
        if (melodyLine ==null || melodyLine.length!=numberOfNotes ||numberOfNotes==0)
            return null;
        return makeHarmony(melodyLine, null, numberOfNotes, null);
    }

    public static Note[][] harmonyFromProgression (int[] progression, int numberOfNotes)
    {
        if (progression ==null || progression.length!=numberOfNotes ||numberOfNotes==0)
            return null;
        return makeHarmony(null, progression, numberOfNotes, null);
    }*/


    /*private static int[] possibleChords (Note a, Note b, Pitch key)
    {

    }*/

    /*public static Note[][] makeHarmony (Note[] melodyLine, int[] progression, int numberOfNotes, Note[] baseLine, Pitch key)
    {
        boolean melodyExist = melodyLine!=null ?(melodyLine.length == numberOfNotes):false;
        boolean progressionExist = progression!=null ?(progression.length == numberOfNotes):false;
        boolean baseLineExist = baseLine!=null ?(baseLine.length == numberOfNotes):false;
        //Arrays must not be null and match length

        if (!(melodyExist || progressionExist || baseLineExist)) //can't make out of nothing
            return null;

        //Verify that all notes from passed in Lines are part of diatonic key
        //No passing notes, etc allowed, else invalidate entire sequence



        //Generate progresion to follow if none exists, or else generate in tandem with Lines


        //Now follow progression with or without Lines to make melody
    }*/
}
