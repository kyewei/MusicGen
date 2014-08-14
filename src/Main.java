public class Main {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                HarmonyView view = new HarmonyView();
                HarmonyEngine model = new HarmonyEngine();
                HarmonyController controller = new HarmonyController(model, view);
            }
        });

        //Example usages:

        //Note one = new Note(Note.Letter.G, Note.Accidental.Natural, 4);
        //Note two = new Note("G4");
        //assert(a.equals(b));

        //Note.Letter test = Note.Letter.A;
        //assert(Note.Letter.A == Note.Letter.G.getNext());

        //assert(Note.getIntervalLower(two, 5, 'P').equals(new Note ("C4")));
        //assert(Note.getIntervalHigher(a, 4, 'D').equals(new Note ("D4"));

        //Pitch[] chordpitches = Chord.getChordPitches("Bdim7");
        //for (Pitch pitch : chordpitches)
        //    System.out.println (pitch); // B D F Ab

        //MajorScale workingscale = new MajorScale("Bb");
        //for (Pitch pitch : workingscale.scale)
        //    System.out.println (pitch); // Bb C D Eb F G A
    }
}
