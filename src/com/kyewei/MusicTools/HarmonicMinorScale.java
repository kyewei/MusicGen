/**
 * Created by Kye on 2014-07-10.
 */
package com.kyewei.MusicTools;

public class HarmonicMinorScale extends Scale {
    public HarmonicMinorScale(String key) {
        scale = allScales[indexFromKey(key)];
    }

    public HarmonicMinorScale(Pitch key) {
        scale = allScales[indexFromKey(key)];
    }

    //LetterNames, like C#, Bbb, G
    public static Pitch[] generateScale(String scaleKey) {
        return generateSpecificScale(scaleKey, new String[]{"P1", "M2", "m3", "P4", "P5", "m6", "M7"});
    }

    public static Pitch[] generateScale(Pitch root) {
        return generateSpecificScale(new Pitch(root), new String[]{"P1", "M2", "m3", "P4", "P5", "m", "M7"});
    }

    static {
        allScales = new Pitch[5 * 7][7];
        for (int i = 0; i < allScales.length; ++i) {
            allScales[i] = generateScale("" + name[i / 5] + modifier[i % 5]);
        }
    }
}