package com.kyewei.MusicTools;
import javax.swing.*;

public class Main extends JApplet {
    public static HarmonyView view;
    public static HarmonyEngine model;
    public static HarmonyController controller;
    public static JFrame mainFrame;

    public Main() {
        //Need a constructor
    }

    public void init() {
        try {
            javax.swing.SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    view = new HarmonyView();
                    model = new HarmonyEngine();
                    controller = new HarmonyController(model, view);
                    getContentPane().add(view);
                    setJMenuBar(view.menuBar);
                    setMinimumSize(view.getSize());
                    //System.out.println(getHeight()+" "+getWidth());
                    view.setVisible(true);
                    System.out.println("Output Console: ");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Main applet = new Main();
        applet.init();

        mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(true);
        mainFrame.getContentPane().add(applet);
        mainFrame.pack();
        mainFrame.setVisible(true);
        mainFrame.setMinimumSize(applet.getSize());
        mainFrame.setPreferredSize(applet.getSize());
        //System.out.println(applet.getHeight()+" "+applet.getWidth());
        //System.out.println(mainFrame.getSize().getHeight()+" "+mainFrame.getSize().getWidth());
        // 705.0 1034.0

        mainFrame.setVisible(true);


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