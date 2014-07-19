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
        setupActionListeners();
    }

    public void setupActionListeners()
    {
        panel.button0.addActionListener(new Action("Action", KeyEvent.VK_A));
        panel.button1.addActionListener(new Action());
        panel.exit.addActionListener(new Action());

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
            if (e.getSource()==panel.button0)
            {
                engine.makeNLongChordProgression(8, 1);
                panel.progressionInfo.setText(engine.convertProgressionToRoman());
            }
            else if (e.getSource()==panel.button1)
            {
                engine.makeNLongChordProgression(8, 2);
                panel.progressionInfo.setText(engine.convertProgressionToRoman());
            }
            else if (e.getSource()==panel.exit)
                System.exit(0);


        }
    }


}
