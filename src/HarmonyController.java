import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


/**
 * Created by Kye on 2014-07-18.
 */
public class HarmonyController
{
    private HarmonyEngine engine;
    //private JFrame mainFrame;
    private HarmonyView panel;

    public HarmonyController (HarmonyEngine engine, HarmonyView panel)
    {
        this.engine = engine;
        //this.mainFrame = panel.mainFrame;
        this.panel=panel;
        this.panel.scorePanel.updateReference(engine.getSoprano(), engine.getAlto(), engine.getTenor(), engine.getBass());
        this.panel.scorePanel.updateCurrentChord(engine.currentChord);
        this.panel.scorePanel.updateKey(engine.key);
        setupActionListeners();
    }

    public void setupActionListeners()
    {
        panel.button0.addActionListener(new Action("Action", KeyEvent.VK_A));
        panel.button1.addActionListener(new Action());
        panel.button2.addActionListener(new Action());
        panel.button4.addActionListener(new Action());

        panel.makeBass.addActionListener(new Action());
        panel.makeChord.addActionListener(new Action());
        panel.prevButton.addActionListener(new Action());
        panel.nextButton.addActionListener(new Action());
        panel.completeAll.addActionListener(new Action());

        panel.exit.addActionListener(new MenuAction());
        panel.parallel5.addActionListener(new MenuAction());
        panel.parallel8.addActionListener(new MenuAction());
        panel.hidden5.addActionListener(new MenuAction());
        panel.hidden8.addActionListener(new MenuAction());
        panel.keyThroughSign.addActionListener(new MenuAction());
        panel.export.addActionListener(new MenuAction());
    }

    public class MenuAction extends AbstractAction {
        public MenuAction() { super(); }
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (e.getSource() == panel.parallel5)
            {
                engine.checkParallelFifths = !engine.checkParallelFifths;
            }
            else if (e.getSource() == panel.parallel8)
            {
                engine.checkParallelOctaves = !engine.checkParallelOctaves;
            }
            else if (e.getSource() == panel.hidden5)
            {
                engine.checkHiddenFifths = !engine.checkHiddenFifths;
            }
            else if (e.getSource() == panel.hidden8)
            {
                engine.checkHiddenOctaves = !engine.checkHiddenOctaves;
            }
            else if (e.getSource() == panel.keyThroughSign)
            {
                String[] options = {"Cb", "Gb", "Db", "Ab", "Eb", "Bb", "F", "C", "G", "D", "A", "E", "B", "F#", "C#"};

                int result = JOptionPane.showOptionDialog(null,
                        "What Major Key?",
                        "Key Changer", 0, JOptionPane.QUESTION_MESSAGE,
                        null, options, "C");
                //System.out.println("Answer: "+code);
                if (result!=-1)
                {
                    engine.key = new Pitch(options[result]);
                    engine.scale = new MajorScale(engine.key);
                    panel.scorePanel.updateKey(engine.key);
                    panel.keyInfo.setText(options[result]);
                    //engine.reset();
                    engine.currentChord=0;
                    for (int i=0; i< engine.soprano.length; ++i)
                    {
                        engine.chord[i].updateKey(engine.scale.scale[engine.currentProgression[i]-1]);
                        engine.soprano[i]=null;
                        engine.alto[i]=null;
                        engine.tenor[i]=null;
                        engine.bass[i]=null;
                    }
                    if (!engine.usedProper)
                        engine.buildBass();
                    else //if (engine.usedProper)
                        engine.buildProperBass();
                    panel.progressionInfo.setText(engine.convertProgressionToRoman());

                }
                panel.scorePanel.updateCurrentChord(engine.currentChord);
                panel.scorePanel.updateReference(engine.getSoprano(), engine.getAlto(), engine.getTenor(), engine.getBass());

                //mainFrame.repaint();
                panel.repaint();
            }
            else if (e.getSource() == panel.export)
            {
                JFileChooser choose = panel.fc;

                int result = choose.showSaveDialog(panel);

                if (result == JFileChooser.APPROVE_OPTION){
                    try {
                        File file = choose.getSelectedFile();
                        BufferedWriter output = new BufferedWriter(new FileWriter(file));

                        String separator = System.getProperty("line.separator");

                        output.write("%{ Generated by MusicTools %}");
                        output.write(separator+"\\version \"2.18.2\"");
                        output.write(separator+"\\header{"+separator+"  title = \"Chord Progression\"" + separator +"}");
                        output.write(separator+"settings = {"+separator+"  \\key "+ engine.key.printForLilypondAbsolute()+" \\major"+separator+"  \\time 4/4"+separator+"}");

                        output.write(separator+"soprano = {"+separator+"  ");
                        for (Note note : engine.soprano)
                            output.write(note.printForLilypondAbsolute()+" ");
                        output.write(separator+"}");

                        output.write(separator+"alto = {"+separator+"  ");
                        for (Note note : engine.alto)
                            output.write(note.printForLilypondAbsolute()+" ");
                        output.write(separator+"}");

                        output.write(separator+"tenor = {"+separator+"  ");
                        for (Note note : engine.tenor)
                            output.write(note.printForLilypondAbsolute()+" ");
                        output.write(separator+"}");

                        output.write(separator+"bass = {"+separator+"  ");
                        for (Note note : engine.bass)
                            output.write(note.printForLilypondAbsolute()+" ");
                        output.write(separator+"}");

                        output.write(separator+"\\score {");
                        output.write(separator+"  \\new ChoirStaff <<");
                        output.write(separator+"    \\new Staff = \"sa\" <<");
                        output.write(separator+"      \\clef treble");
                        output.write(separator+"      \\set Staff.midiInstrument = #\"choir aahs\"");
                        output.write(separator+"      \\new Voice = \"soprano\" {");
                        output.write(separator+"        \\voiceOne");
                        output.write(separator+"        << \\settings \\soprano >>");
                        output.write(separator+"      }");
                        output.write(separator+"      \\new Voice = \"alto\" {");
                        output.write(separator+"        \\voiceTwo");
                        output.write(separator+"        << \\settings \\alto >>");
                        output.write(separator+"      }");
                        output.write(separator+"    >>");
                        output.write(separator+"    \\new Staff = \"tb\" <<");
                        output.write(separator+"      \\clef bass");
                        output.write(separator+"      \\set Staff.midiInstrument = #\"choir aahs\"");
                        output.write(separator+"      \\new Voice = \"tenor\" {");
                        output.write(separator+"        \\voiceOne");
                        output.write(separator+"        << \\settings \\tenor >>");
                        output.write(separator+"      }");
                        output.write(separator+"      \\new Voice = \"bass\" {");
                        output.write(separator+"        \\voiceTwo");
                        output.write(separator+"        << \\settings \\bass >>");
                        output.write(separator+"      }");
                        output.write(separator+"    >>");
                        output.write(separator+"  >>");
                        output.write(separator+"  \\layout {}");
                        output.write(separator+"  \\midi {}");
                        output.write(separator+"}");
                        output.flush();
                        output.close();
                    } catch ( IOException error ) {
                        error.printStackTrace();
                    }

                }

            }
            else if (e.getSource() == panel.exit)
            {
                System.exit(0);
            }
        }
    }

    private class Action extends AbstractAction {

        public Action(String name, Integer mnemonic) {
            super(name);
            putValue(MNEMONIC_KEY, mnemonic);
        }
        public Action()
        {
            super();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource()==panel.button0 || e.getSource()==panel.button1)
            {
                engine.reset();

                int numberOfChords = engine.numberOfChords;

                if (e.getSource()==panel.button0)
                    engine.currentProgression = engine.makeNLongChordProgression(numberOfChords, 1, 1, 1);
                else if (e.getSource()==panel.button1)
                    engine.currentProgression = engine.makeNLongChordProgression(numberOfChords, 2, 1, 1);

                engine.usedProper=false;
                engine.buildBass();

                panel.progressionInfo.setText(engine.convertProgressionToRoman());
                panel.numberOfChordsInfo.setText(""+engine.numberOfChords);

                panel.scorePanel.updateCurrentChord(engine.currentChord);
                panel.scorePanel.updateReference(engine.getSoprano(), engine.getAlto(), engine.getTenor(), engine.getBass());

                //mainFrame.repaint();

            }
            else if (e.getSource()==panel.button2)
            {
                boolean result;
                do {
                    Object[] arrays = engine.makeProperProgression();
                    engine.numberOfChords = (int)(arrays[3]);
                    engine.reset();
                    engine.currentProgression = (int[])(arrays[0]);
                    engine.chord = (Chord[])(arrays[1]);
                    engine.tonicization = (int[])(arrays[2]);
                    engine.usedProper=true;
                    result = engine.buildProperBass();
                }while(!result);

                panel.progressionInfo.setText(engine.convertProgressionToRoman());
                panel.numberOfChordsInfo.setText(""+engine.numberOfChords);

                panel.scorePanel.updateCurrentChord(engine.currentChord);
                panel.scorePanel.updateReference(engine.getSoprano(), engine.getAlto(), engine.getTenor(), engine.getBass());

                //mainFrame.repaint();
            }
            else if (e.getSource()==panel.makeBass)
            {
                if (!engine.usedProper)
                    engine.buildBass();
                else //if (engine.usedProper)
                    engine.buildProperBass();

                panel.progressionInfo.setText(engine.convertProgressionToRoman());
                panel.numberOfChordsInfo.setText(""+engine.numberOfChords);

                panel.scorePanel.updateCurrentChord(engine.currentChord);

                //mainFrame.repaint();
            }
            else if (e.getSource()==panel.makeChord)
            {
                engine.nextDriver();
                panel.scorePanel.updateCurrentChord(engine.currentChord);

                //mainFrame.repaint();
            }
            else if (e.getSource()==panel.completeAll)
            {
                engine.doAllTheThings();

                panel.scorePanel.updateCurrentChord(engine.currentChord);
                //mainFrame.repaint();
            }
            else if (e.getSource()  ==panel.prevButton)
            {
                engine.goPrev();
                panel.scorePanel.updateCurrentChord(engine.currentChord);

                //mainFrame.repaint();
            }
            else if (e.getSource() == panel.nextButton)
            {
                engine.goNext();
                panel.scorePanel.updateCurrentChord(engine.currentChord);

                //mainFrame.repaint();
            }
            else if (e.getSource() == panel.button4)
            {
                String input = JOptionPane.showInputDialog("Enter chord progression separated by dashes '-' using functional chord notation: ");

                if (input!=null&& !input.equals("")) {
                    String[] input2 = input.trim().split("-");
                    int size = input2.length;
                    Chord[] chordx = new Chord[size];
                    int[] toniz = new int[size];

                    int[] chpro = new int[size];

                    engine.numberOfChords = size;
                    engine.reset();

                    for (int i = 0; i < input2.length; ++i) {
                        int[] temp = engine.recognizeFunctionalChordSymbol(input2[i]);
                        toniz[i] = temp[6];
                        chpro[i] = temp[0];
                        chordx[i] = new Chord(new Pitch(engine.scale.scale[(temp[0] - 1+temp[6])%7]), temp[1], temp[2], (char) (temp[3]), (char) (temp[4]), (char) (temp[5]));
                    }
                    engine.usedProper = true;
                    engine.currentProgression = chpro;
                    engine.chord = chordx;


                    panel.progressionInfo.setText(engine.convertProgressionToRoman());
                    panel.numberOfChordsInfo.setText("" + engine.numberOfChords);

                    panel.scorePanel.updateCurrentChord(engine.currentChord);
                    panel.scorePanel.updateReference(engine.getSoprano(), engine.getAlto(), engine.getTenor(), engine.getBass());

                    //mainFrame.repaint();
                }
            }
            //mainFrame.repaint();
            panel.repaint();


        }
    }


}
