public class Main {

    public static void main(String[] args) {
        /*javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                HarmonyGUI.createAndShowGUI();
            }
        });
        System.out.println("Hello World!");*/

        Note a = new Note(Note.Letter.C, Note.Accidental.Natural, 4);
        Note b = new Note("G4");
        System.out.println(a.equals(b));
        System.out.println(a.hashCode());
        System.out.println(b.hashCode());


        //Note.Letter test = Note.Letter.A;
        //System.out.println(Note.Letter.A == Note.Letter.G.getNext());

        //System.out.println(Note.getIntervalLower(a, 5, 'A'));
        //System.out.println(Note.getIntervalHigher(a, 1, 'P'));
        /*System.out.println(Note.getIntervalHigher(a, 1, 'P'));
        System.out.println(Note.getIntervalHigher(a, 1, 'A'));
        System.out.println(Note.getIntervalHigher(a, 2, 'D'));
        System.out.println(Note.getIntervalHigher(a, 2, 'm'));
        System.out.println(Note.getIntervalHigher(a, 2, 'M'));
        System.out.println(Note.getIntervalHigher(a, 2, 'A'));
        System.out.println(Note.getIntervalHigher(a, 3, 'D'));
        System.out.println(Note.getIntervalHigher(a, 3, 'm'));
        System.out.println(Note.getIntervalHigher(a, 3, 'M'));
        System.out.println(Note.getIntervalHigher(a, 3, 'A'));
        System.out.println(Note.getIntervalHigher(a, 4, 'D'));
        System.out.println(Note.getIntervalHigher(a, 4, 'P'));
        System.out.println(Note.getIntervalHigher(a, 4, 'A'));
        System.out.println(Note.getIntervalHigher(a, 5, 'D'));
        System.out.println(Note.getIntervalHigher(a, 5, 'P'));
        System.out.println(Note.getIntervalHigher(a, 5, 'A'));
        System.out.println(Note.getIntervalHigher(a, 6, 'D'));
        System.out.println(Note.getIntervalHigher(a, 6, 'm'));
        System.out.println(Note.getIntervalHigher(a, 6, 'M'));
        System.out.println(Note.getIntervalHigher(a, 6, 'A'));
        System.out.println(Note.getIntervalHigher(a, 7, 'D'));
        System.out.println(Note.getIntervalHigher(a, 7, 'm'));
        System.out.println(Note.getIntervalHigher(a, 7, 'M'));
        System.out.println(Note.getIntervalHigher(a, 7, 'A'));
        System.out.println(Note.getIntervalHigher(a, 8, 'D'));
        System.out.println(Note.getIntervalHigher(a, 8, 'P'));
        System.out.println(Note.getIntervalHigher(a, 8, 'A'));*/

        /*for (Pitch pitch : MajorScale.allScales[MajorScale.indexFromKey("Cb")]) //C major scale
            System.out.println(pitch);*/

        Pitch[] temp = new Pitch[7];
        temp[0] = new Pitch(Note.Letter.C, Note.Accidental.Sharp);
        temp[1] = Pitch.getHigherPitchWithInterval(temp[0], "M2");
        temp[2] = Pitch.getHigherPitchWithInterval(temp[0], "M3");
        temp[3] = Pitch.getHigherPitchWithInterval(temp[0], "P4");
        temp[4] = Pitch.getHigherPitchWithInterval(temp[0], "P5");
        temp[5] = Pitch.getHigherPitchWithInterval(temp[0], "M6");
        temp[6] = Pitch.getHigherPitchWithInterval(temp[0], "M7");
        for (Pitch thing : temp)
            System.out.println(thing);

    }
}
