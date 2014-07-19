public class Main {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                HarmonyView view = new HarmonyView();
                HarmonyEngine model = new HarmonyEngine();
                HarmonyController controller = new HarmonyController(model, view);
            }
        });

        //Note a = new Note(Note.Letter.G, Note.Accidental.Natural, 4);
        //Note b = new Note("G4");
        //System.out.println(a.equals(b));
        //System.out.println(a.hashCode());
        //System.out.println(b.hashCode());

        //Note.Letter test = Note.Letter.A;
        //System.out.println(Note.Letter.A == Note.Letter.G.getNext());

        //System.out.println(Note.getIntervalLower(a, 5, 'A'));
        //System.out.println(Note.getIntervalHigher(a, 1, 'P'));

        /*Pitch[] chordpitches = Chord.getChordPitches("Ddim7");
        for (Pitch pitch : chordpitches)
            System.out.println (pitch);*/

        //MajorScale workingscale = new MajorScale("Bb");
        /*for (Pitch pitch : workingscale.scale)
            System.out.println (pitch);*/

        //Note test = new Note("A4");
        //Note test2 = new Note("A3");

        //System.out.println(test.equals(test2));
        //System.out.println(((Pitch)test).equals(((Pitch)test2)));
    }
}
