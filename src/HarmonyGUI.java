/**
 * Created by Kye on 2014-07-07.
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class HarmonyGUI extends JPanel {

    public static JFrame mainFrame;

    public static void createAndShowGUI() //Manage frame customizations
    {
        mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);
        mainFrame.setResizable(true);


        HarmonyGUI mainPanel = new HarmonyGUI();
        mainFrame.getContentPane().add(mainPanel);
        mainFrame.setJMenuBar(createMenuBar());

        mainFrame.setVisible(true);
    }

    public HarmonyGUI() //Manage panel customizations
    {
        super();
        setLayout(new BorderLayout());

        JButton button0 = new JButton("Hi");
        add(button0, BorderLayout.WEST);
    }

    public static JMenuBar createMenuBar() {
        JMenuBar menuBar;
        JMenu menu;
        JMenuItem menuItem;
        JCheckBoxMenuItem cbMenuItem;

        menuBar = new JMenuBar(); //Create the menu bar.

        //File Menu
        menu = new JMenu("File");
        menuBar.add(menu);
        menuItem = new JMenuItem("Export");
        menu.add(menuItem);
        menu.addSeparator(); //Separator
        menuItem = new JMenuItem("Exit", KeyEvent.VK_X);
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
        menu.add(menuItem);



        //Key Menu
        menu = new JMenu("Key");
        menuBar.add(menu);
        menuItem = new JMenuItem("Select Key through Letter Name");
        menu.add(menuItem);
        menuItem = new JMenuItem("Select Key through Number of Sharps/Flats");
        menu.add(menuItem);
        menuItem = new JMenuItem("Flip to relative Minor/Major key");
        menu.add(menuItem);


        //Harmony Rules Menu
        menu = new JMenu("Rules");
        menuBar.add(menu);
        cbMenuItem = new JCheckBoxMenuItem("Check for Parallel Fifths");
        menu.add(cbMenuItem);

        return menuBar;
    }

}
