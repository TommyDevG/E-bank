package bankmanager.userinterface.frame.home;

import bankmanager.userinterface.commoncomponents.NavigationMenuSwing;

import javax.swing.*;

public class HomeFrame extends JFrame  {

    public HomeFrame() {
        super("Accueil");

        //Create and set up the content pane.
        NavigationMenuSwing navigationMenuSwing = new NavigationMenuSwing();
        this.setJMenuBar(navigationMenuSwing.createMenuBar(this));
        //this.setContentPane(navigationMenu.createContentPane());

    }

    public static void createAndShowGUI(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }

        //Create and set up the window.
        HomeFrame frame = new HomeFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//        NavigationMenu navigationMenu = new NavigationMenu();
//
//        frame.setJMenuBar(navigationMenu.createMenuBar());
//        frame.setContentPane(navigationMenu.createContentPane());

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

}
