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
        //mainFrame.setSize(1200, 720);
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

    public JButton button0, button1, /*button2, button3, */button4, makeBass, startButton, nextButton, prevButton, selectUp, selectDown, fun;
    public JLabel keyInfo, progressionInfo, numberOfChordsInfo;
    public JPanel keyPanel, progressionPanel, middlePanel, navigationPanel;
    //public JTextField middleText;
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
        progressionPanel.add(new JLabel("    Number of Chords: "));
        numberOfChordsInfo = new JLabel("8");
        progressionPanel.add(numberOfChordsInfo);
        progressionPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.PAGE_START;
        c.gridx = 0;
        c.gridy = 1;
        add(progressionPanel,c);

        //Middle Panel that allows users to input chord progression
        middlePanel = new JPanel();
        middlePanel.add(new JLabel("Generate Chord Progression Using Matrix: "));
        button0 = new JButton("Complex");
        button1 = new JButton("Simple");
        //button2 = new JButton("Complex, No Tonic");
        //button3 = new JButton("Simple, No Tonic");
        middlePanel.add(button0);
        middlePanel.add(button1);
        //middlePanel.add(button2);
        //middlePanel.add(button3);
        middlePanel.add(new JLabel(" or "));
        button4 = new JButton("Enter One");
        middlePanel.add(button4);
        middlePanel.setBorder(BorderFactory.createLineBorder(Color.black));
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.PAGE_START;
        c.gridx = 0;
        c.gridy = 2;
        add(middlePanel, c);

        //Navigation Panel
        navigationPanel = new JPanel();
        makeBass = new JButton("Make Bass");
        startButton = new JButton("Random Chord");
        prevButton = new JButton("Previous");
        nextButton = new JButton("Next");
        //selectUp = new JButton("Up");
        //selectDown = new JButton("Down");
       fun = new JButton("FunButton");
        navigationPanel.add(makeBass);
        navigationPanel.add(startButton);
        navigationPanel.add(prevButton);
        navigationPanel.add(nextButton);
        //navigationPanel.add(selectUp);
        //navigationPanel.add(selectDown);
        navigationPanel.add(fun);

        navigationPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.PAGE_START;
        c.gridx = 0;
        c.gridy = 3;
        add(navigationPanel, c);


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
            musicFont = new Font("Symbola", Font.PLAIN, 96);
            musicFont2 = new Font("Symbola", Font.PLAIN, 128);
            musicFont3 = new Font("Symbola", Font.PLAIN, 96);
            musicFont4 = new Font("Symbola", Font.PLAIN, 96);
        }

        private Font musicFont;
        private Font musicFont2;
        private Font musicFont3;
        private Font musicFont4;

        //references for drawing purposes only
        private Note[] soprano;
        private Note[] alto;
        private Note[] tenor;
        private Note[] bass;
        private int currentChord;

        public void updateReference(Note[] soprano, Note[] alto, Note[] tenor, Note[] bass)
        {
            this.soprano=soprano;
            this.alto=alto;
            this.tenor=tenor;
            this.bass=bass;
        }

        public void updateCurrentChord(int num)
        {
            currentChord=num;
        }

        public void importFont()
        {
            try {
                GraphicsEnvironment ge =
                        GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/Fonts/Symbola.ttf")));
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

            //Graphics2D g2 = (Graphics2D) g;
            //g2.scale(0.75, 0.75);

            //White background
            g.setColor(Color.WHITE);
            g.fillRect(0,0,1280,720);
            g.setColor(Color.BLACK);

            //Staff
            g.setFont(musicFont);
            for (int i = 0; i< 15; ++i)
            {
                g.drawString("\uD834\uDD1A", 10 + 64*i, 200);
                g.drawString("\uD834\uDD1A", 10 + 64*i, 400);
            }

            //Treble clef
            g.setFont(musicFont2);
            g.drawString("\uD834\uDD1E", 100, 205);
            //Bass clef
            g.setFont(musicFont3);
            g.drawString("\uD834\uDD22", 80, 400);

            //Whole notes
            //Upper clef; x:__ y: 120 + 8n
            //Lower clef; x:__ y: 320 + 8n
            //Upper clef higher ledger line: x:__ y:122-16n
            //Upper clef lower ledger line: x:__ y:218+16n
            //Lower clef higher ledger line: x:__ y:322-16n
            //Lower clef lower ledger line: x:__ y:418+16n


            g.setFont(musicFont4);

            drawNotes(g);

            g.drawString("^", 120 + 48 * currentChord, 500);

            /*for (int i = 0; i< 10; ++i)
                g.drawString("\uD834\uDD5D", 160+i*40, 120 + i*8);
            for (int i = 0; i< 10; ++i)
                g.drawString("\uD834\uDD5D", 160+i*40, 320 + i*8);
            */
            //g.setColor(Color.red);
            /*for (int i = 0; i<6; ++i)
            {

                g.fillRect(160, 218-16*i, 43, 2);
                g.fillRect(160+80, 218-16*i, 43, 2);

            }

            for (int i = 0; i<6; ++i)
            {

                g.fillRect(160, 402-16*i, 43, 2);
                g.fillRect(160+80, 402-16*i, 43, 2);

            }*/
        }

        public void drawNotes(Graphics g) //draws the notes and ledger lines
        {
            if (soprano.length== alto.length && tenor.length== bass.length && soprano.length ==tenor.length)
            {
                for (int i=0; i<soprano.length; ++i) {
                    if (soprano[i] != null) {
                        g.drawString("\uD834\uDD5D", 120 + i * 48, soprano[i].getLetterNum() * -8 + 432);
                        if (soprano[i].getLetterNum() >= 40) //Higher than A5
                        {
                            for (int j = 0; j < (soprano[i].getLetterNum() - 40) / 2 + 1; ++j)
                                g.fillRect(120 + 48 * i, 122 - 16 * j, 33, 2);
                        }
                    }
                    if (alto[i] != null) {
                        g.drawString("\uD834\uDD5D", 120 + i * 48, alto[i].getLetterNum() * -8 + 432);
                        if (alto[i].getLetterNum() <= 28) //Lower than C4
                        {
                            for (int j = 0; j < (28 - alto[i].getLetterNum()) / 2 + 1; ++j)
                                g.fillRect(120 + 48 * i, 218 + 16 * j, 33, 2);

                        }
                    }
                    if (tenor[i] != null) {
                        g.drawString("\uD834\uDD5D", 120 + i * 48, tenor[i].getLetterNum() * -8 + 536);
                        if (tenor[i].getLetterNum() >= 28) //Higher than C4
                        {
                            for (int j = 0; j < (tenor[i].getLetterNum() - 28) / 2 + 1; ++j)
                                g.fillRect(120 + 48 * i, 322 - 16 * j, 33, 2);
                        }
                    }
                    if (bass[i] != null) {
                        g.drawString("\uD834\uDD5D", 120 + i * 48, bass[i].getLetterNum() * -8 + 536);
                        if (bass[i].getLetterNum() <= 16) //Lower than E2
                        {
                            for (int j = 0; j < (16 - bass[i].getLetterNum()) / 2 + 1; ++j)
                                g.fillRect(120 + 48 * i, 418 + 16 * j, 33, 2);

                        }
                    }
                }
            }
        }


    }





}
