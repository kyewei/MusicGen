import javax.swing.*;
import java.awt.event.ActionListener;
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
        setupActionListeners();
    }

    public void setupActionListeners()
    {
        panel.button0.addActionListener(new Action("Action", KeyEvent.VK_A));
        panel.button1.addActionListener(new Action());
        //panel.button2.addActionListener(new Action());
        //panel.button3.addActionListener(new Action());

        panel.exit.addActionListener(new Action());

        panel.makeBass.addActionListener(new Action());
        panel.startButton.addActionListener(new Action());
        panel.prevButton.addActionListener(new Action());
        panel.nextButton.addActionListener(new Action());
        //panel.selectUp.addActionListener(new Action());
        //panel.selectDown.addActionListener(new Action());
        panel.fun.addActionListener(new Action());

    }


    private class Action extends AbstractAction {

        public Action(String name, Integer mnemonic) {
            super(name);
            //putValue(MNEMONIC_KEY, mnemonic);
        }
        public Action()
        {
            super();
        }



        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource()==panel.button0 || e.getSource()==panel.button1/* || e.getSource()==panel.button2 || e.getSource()==panel.button3*/)
            {
                //((JButton)e.getSource()).setEnabled(false);
                //mainFrame.repaint();

                engine.reset();

                if (e.getSource()==panel.button0)
                    engine.currentProgression = engine.makeNLongChordProgression(8, 1, 1, 1);
                else if (e.getSource()==panel.button1)
                    engine.currentProgression = engine.makeNLongChordProgression(8, 2, 1, 1);
                /*else if (e.getSource()==panel.button2)
                    engine.currentProgression = engine.makeNLongChordProgression2(8, 1, 1, 1);
                else if (e.getSource()==panel.button3)
                    engine.currentProgression = engine.makeNLongChordProgression2(8, 2, 1, 1);
                */

                engine.buildBass();

                panel.progressionInfo.setText(engine.convertProgressionToRoman());
                panel.numberOfChordsInfo.setText(""+engine.numberOfChords);

                panel.scorePanel.updateCurrentChord(engine.currentChord);
                panel.scorePanel.updateReference(engine.getSoprano(), engine.getAlto(), engine.getTenor(), engine.getBass());

                //((JButton)e.getSource()).setEnabled(true);
                mainFrame.repaint();
            }
            else if (e.getSource()==panel.makeBass)
            {
                //((JButton)e.getSource()).setEnabled(false);
                //mainFrame.repaint();

                engine.buildBass();

                panel.progressionInfo.setText(engine.convertProgressionToRoman());
                panel.numberOfChordsInfo.setText(""+engine.numberOfChords);

                panel.scorePanel.updateCurrentChord(engine.currentChord);

                //((JButton)e.getSource()).setEnabled(true);
                mainFrame.repaint();
            }
            else if (e.getSource()==panel.startButton)
            {
                //((JButton)e.getSource()).setEnabled(false);
                //mainFrame.repaint();

                engine.next();
                panel.scorePanel.updateCurrentChord(engine.currentChord);

                //((JButton)e.getSource()).setEnabled(true);
                mainFrame.repaint();
            }
            else if (e.getSource()==panel.fun)
            {
                //((JButton)e.getSource()).setEnabled(false);
                //mainFrame.repaint();

                Object[] arrays = engine.makeProperProgression();
                engine.currentProgression = (int[])(arrays[0]);
                engine.inversions = (int[])(arrays[1]);
                engine.isSeventh = (boolean[])(arrays[2]);

                System.out.println(engine.convertProgressionToRoman());

                panel.progressionInfo.setText(engine.convertProgressionToRoman());
                panel.numberOfChordsInfo.setText(""+engine.numberOfChords);

                panel.scorePanel.updateCurrentChord(engine.currentChord);

                //((JButton)e.getSource()).setEnabled(true);
                mainFrame.repaint();
            }
            else if (e.getSource() ==panel.prevButton)
            {
                //((JButton)e.getSource()).setEnabled(false);
                //mainFrame.repaint();

                engine.goPrev();
                panel.scorePanel.updateCurrentChord(engine.currentChord);

                //((JButton)e.getSource()).setEnabled(true);
                mainFrame.repaint();
            }
            else if (e.getSource() ==panel.nextButton)
            {
                //((JButton)e.getSource()).setEnabled(false);
                //mainFrame.repaint();

                engine.goNext();
                panel.scorePanel.updateCurrentChord(engine.currentChord);

                //((JButton)e.getSource()).setEnabled(true);
                mainFrame.repaint();
            }
            else if (e.getSource() == panel.selectUp)
            {

            }
            else if (e.getSource() == panel.selectDown)
            {

            }
            else if (e.getSource()==panel.exit)
                System.exit(0);


        }
    }


}
