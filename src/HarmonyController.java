import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;


/**
 * Created by Kye on 2014-07-18.
 */
public class HarmonyController
{
    private HarmonyEngine engine;
    private JFrame mainFrame;
    private HarmonyView panel;

    public HarmonyController (HarmonyEngine engine, HarmonyView panel)
    {
        this.engine = engine;
        this.mainFrame = panel.mainFrame;
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

                mainFrame.repaint();
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

                mainFrame.repaint();
            }
            else if (e.getSource()==panel.button2)
            {
                boolean result;
                do {
                    Object[] arrays = engine.makeProperProgression();
                    engine.numberOfChords = (int)(arrays[3]);
                    engine.reset();
                    engine.currentProgression = (int[])(arrays[0]);
                    engine.inversions = (int[])(arrays[1]);
                    engine.isSeventh = (boolean[])(arrays[2]);

                    engine.usedProper=true;
                    result = engine.buildProperBass();
                }while(!result);

                panel.progressionInfo.setText(engine.convertProgressionToRoman());
                panel.numberOfChordsInfo.setText(""+engine.numberOfChords);

                panel.scorePanel.updateCurrentChord(engine.currentChord);
                panel.scorePanel.updateReference(engine.getSoprano(), engine.getAlto(), engine.getTenor(), engine.getBass());

                mainFrame.repaint();
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

                mainFrame.repaint();
            }
            else if (e.getSource()==panel.makeChord)
            {
                engine.nextDriver();
                panel.scorePanel.updateCurrentChord(engine.currentChord);

                mainFrame.repaint();
            }
            else if (e.getSource()==panel.completeAll)
            {
                engine.doAllTheThings();

                panel.scorePanel.updateCurrentChord(engine.currentChord);
                mainFrame.repaint();
            }
            else if (e.getSource()  ==panel.prevButton)
            {
                engine.goPrev();
                panel.scorePanel.updateCurrentChord(engine.currentChord);

                mainFrame.repaint();
            }
            else if (e.getSource() == panel.nextButton)
            {
                engine.goNext();
                panel.scorePanel.updateCurrentChord(engine.currentChord);

                mainFrame.repaint();
            }


        }
    }


}
