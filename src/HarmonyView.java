/**
 * Created by Kye on 2014-07-07.
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class HarmonyView extends JPanel {

    public JFrame mainFrame;

    public void createParentFrameAndConnectToIt() //Manage frame customizations
    {
        mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1200, 720);
        //mainFrame.setMinimumSize(new Dimension(450,450));
        mainFrame.setResizable(true);

        mainFrame.getContentPane().add(this);
        createMenuBar();
        mainFrame.setJMenuBar(menuBar);

        mainFrame.pack();
        mainFrame.setVisible(true);
        mainFrame.setMinimumSize(mainFrame.getSize());

        mainFrame.setVisible(true);
    }

    public JButton button0, button1;
    public JLabel keyInfo, progressionInfo;
    public JPanel keyPanel, progressionPanel, middlePanel;
    public JTextField middleText;
    public ScorePanel scorePanel;

    public HarmonyView() //Manage panel customizations
    {
        super();
        setLayout(new GridBagLayout());
        GridBagConstraints c;

        //Key section up top
        keyPanel = new JPanel();
        keyPanel.add(new JLabel("Current Key: "));
        keyInfo = new JLabel("C");
        keyPanel.add(keyInfo);
        keyPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.PAGE_START;
        c.gridx = 0;
        c.gridy = 0;
        add(keyPanel,c);

        //Progression section up top
        progressionPanel = new JPanel();
        progressionPanel.add(new JLabel("Current Progression: "));
        progressionInfo = new JLabel("I-iii-IV-V-IV-I-V-I");
        progressionPanel.add(progressionInfo);
        progressionPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.PAGE_START;
        c.gridx = 0;
        c.gridy = 1;
        add(progressionPanel,c);

        //Middle Panel that allows users to input notes
        middlePanel = new JPanel();
        middlePanel.add(new JLabel("Generate Chord Progression Using: "));
        button0 = new JButton("First Matrix");
        button1 = new JButton("Second Matrix");
        middleText = new JTextField(20);
        middlePanel.add(button0);
        middlePanel.add(button1);
        middlePanel.add(middleText);
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.PAGE_START;
        c.gridx = 0;
        c.gridy = 2;
        add(middlePanel, c);

        //Panel where score is drawn
        scorePanel = new ScorePanel();
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 3;
        add(scorePanel, c);


        createParentFrameAndConnectToIt();
    }
    public JMenuBar menuBar;
    public JMenuItem export, exit, keyThroughLetterName, keyThroughSign, flipToRelativeKey;
    private void createMenuBar()
    {
        JMenu menu;
        //JMenuItem menuItem;
        JCheckBoxMenuItem cbMenuItem;

        menuBar = new JMenuBar(); //Create the menu bar.

        //File Menu
        menu = new JMenu("File");
        menuBar.add(menu);
        export = new JMenuItem("Export");
        menu.add(export);
        menu.addSeparator(); //Separator
        exit = new JMenuItem("Exit", KeyEvent.VK_X);
        menu.add(exit);



        //Key Menu
        menu = new JMenu("Key");
        menuBar.add(menu);
        keyThroughLetterName = new JMenuItem("Select Key through Letter Name");
        menu.add(keyThroughLetterName);
        keyThroughSign = new JMenuItem("Select Key through Number of Sharps/Flats");
        menu.add(keyThroughSign);
        flipToRelativeKey = new JMenuItem("Flip to relative Minor/Major key");
        menu.add(flipToRelativeKey);


        //Harmony Rules Menu
        menu = new JMenu("Rules");
        menuBar.add(menu);
        cbMenuItem = new JCheckBoxMenuItem("Check for Parallel Fifths");
        menu.add(cbMenuItem);
    }


    class ScorePanel extends JPanel
    {
        // \uD834\uDD1E is Treble clef
        // \uD834\uDD22 is bass clef
        // \uD834\uDD1A is five-line staff
        // \uD834\uDD5D is whole note

        public ScorePanel()
        {
            setPreferredSize(new Dimension(1024,600));
            setMinimumSize(new Dimension(1024, 600));
            importFont();
            musicFont = new Font("Symbola", Font.PLAIN, 120);
            musicFont2 = new Font("Symbola", Font.PLAIN, 160);
            musicFont3 = new Font("Symbola", Font.PLAIN, 120);
            musicFont4 = new Font("Symbola", Font.PLAIN, 120);
        }

        private Font musicFont;
        private Font musicFont2;
        private Font musicFont3;
        private Font musicFont4;

        public void importFont()
        {
            try {
                GraphicsEnvironment ge =
                        GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src\\Fonts\\Symbola.ttf")));
                GraphicsEnvironment graphicsEnvironment =
                        GraphicsEnvironment.getLocalGraphicsEnvironment();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            catch(FontFormatException e)
            {
                e.printStackTrace();
            }
        }


        @Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);

            //White background
            g.setColor(Color.WHITE);
            g.fillRect(0,0,1280,720);
            g.setColor(Color.BLACK);

            //Staff
            g.setFont(musicFont);
            for (int i = 0; i< 12; ++i)
            {
                g.drawString("\uD834\uDD1A", 40 + 80*i, 200);
                g.drawString("\uD834\uDD1A", 40 + 80*i, 400);
            }

            //Treble clef
            g.setFont(musicFont2);
            g.drawString("\uD834\uDD1E", 160, 206);
            //Bass clef
            g.setFont(musicFont3);
            g.drawString("\uD834\uDD22", 130, 400);

            //Whole notes
            //Upper clef; x:__ y: 100 + 10n
            //Lower clef; x:__ y: 300 + 10n
            g.setFont(musicFont4);
            for (int i = 0; i< 10; ++i)
                g.drawString("\uD834\uDD5D", 160+i*40, 100 + i*10);
            for (int i = 0; i< 10; ++i)
                g.drawString("\uD834\uDD5D", 160+i*40, 300 + i*10);

        }

    }





}
