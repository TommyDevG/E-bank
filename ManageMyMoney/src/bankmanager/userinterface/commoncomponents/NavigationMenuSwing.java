package bankmanager.userinterface.commoncomponents;


import bankmanager.userinterface.frame.FileChooserFrame;
import bankmanager.userinterface.frame.listoperation.ListOperationFrame;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

/* MenuDemo.java requires images/middle.gif. */

/*
 * This class is just like MenuLookDemo, except the menu items
 * actually do something, thanks to event listeners.
 */
public class NavigationMenuSwing {

    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenu listMenu;
    private JFrame parentFrame;

    public JMenuBar createMenuBar(JFrame parentFrameCall) {

        parentFrame = parentFrameCall;

        menuBar = new JMenuBar();
        fileMenu = new JMenu();

        //Create the menu bar.
        menuBar = new JMenuBar();

        //Build the first menu.
        fileMenu = new JMenu("Récuperer fichier");
        fileMenu.setMnemonic(KeyEvent.VK_1);
        // Seulement pour JMenuItem
//        fileMenu.setAccelerator(KeyStroke.getKeyStroke(
//                KeyEvent.VK_F1, ActionEvent.ACTION_PERFORMED
//        ));
        fileMenu.getAccessibleContext().setAccessibleDescription(
                "Ouvre la page pour ajouter des opérations");
        // Pour les menus, il faut utiliser MenuListener
        //fileMenu.addActionListener(this);
        fileMenu.addMenuListener(new ActionMenuListener());

        //Build second menu in the menu bar.
        listMenu = new JMenu("Voir mes opérations");
        listMenu.setMnemonic(KeyEvent.VK_2);
        // Seulement pour JMenuItem
//        menuTab.setAccelerator(KeyStroke.getKeyStroke(
//                KeyEvent.VK_F2, ActionEvent.ACTION_PERFORMED
//        ));
        listMenu.getAccessibleContext().setAccessibleDescription(
                "Ouvre la page pour voir mes opérations");
        // Pour les menus, il faut utiliser MenuListener
        //listMenu.addActionListener(this);
        listMenu.addMenuListener(new ActionMenuListener());

        menuBar.add(fileMenu);
        menuBar.add(listMenu);
        return menuBar;
    }

    class ActionMenuListener implements MenuListener {
        @Override
        public void menuSelected(MenuEvent menuEvent) {

            Object source = menuEvent.getSource();

            if (source == fileMenu) {
                parentFrame.dispose();
                FileChooserFrame fileChooserFrame = new FileChooserFrame();
                fileChooserFrame.createAndShowGUI();
            } else {
                if (source == listMenu) {
                    parentFrame.dispose();
                    ListOperationFrame listOperationFrame = new ListOperationFrame();
                    listOperationFrame.createAndShowGUI();
                }
            }
        }

        @Override
        public void menuDeselected(MenuEvent menuEvent) {
        }

        @Override
        public void menuCanceled(MenuEvent menuEvent) {
        }
    }
}