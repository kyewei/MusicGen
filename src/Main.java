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

        /*for (Pitch[] pitchScales : MajorScale.allScales) //C major scale
        {
            for (Pitch pitch : pitchScales) //C major scale
                System.out.print(pitch+ " ");
            System.out.println("");
        }*/

        Pitch[] chordpitches = Chord.getChordPitches("Ddim7");
        for (Pitch pitch : chordpitches)
            System.out.println (pitch);


    }
}
