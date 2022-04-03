package cz.cvut.fel.pjv.aspone;

import cz.cvut.fel.pjv.aspone.gui.StartMenu;

import javax.swing.*;

/**
 * The type Game.
 *
 * @author Mukan Atazhanov
 * @project atazhmuk
 * @created 01 /04/2022 - 16:07
 */
public class Game implements Runnable{
    public void run() {
        SwingUtilities.invokeLater(new StartMenu());
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
}
