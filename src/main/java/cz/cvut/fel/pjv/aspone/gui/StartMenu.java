package cz.cvut.fel.pjv.aspone.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Objects;
import java.util.logging.Logger;

import static cz.cvut.fel.pjv.aspone.board.Board.setBlackIsComputer;

/**
 * The type Start menu.
 *
 * @author Mukan Atazhanov
 * @project atazhmuk
 * @created 01 /04/2022 - 16:14
 */
public class StartMenu implements Runnable{
    /**
     * The constant LOGGER.
     */
    public static final Logger LOGGER = Logger.getLogger(StartMenu.class.getName());
    /**
     * The constant COMPUTER_TEXT.
     */
    public static final String COMPUTER_TEXT = "Computer";

    @Override
    public void run() {
        final JFrame startWindow = new JFrame("Aspon E");
        try {
            Image whiteImg = ImageIO.read(new File("img/Logo.png"));
            startWindow.setIconImage(whiteImg);
        } catch (Exception e) {
            LOGGER.info("File Not Found");
        }

        // Set window properties
        startWindow.setLocation(300,100);
        startWindow.setResizable(false);
        startWindow.setSize(300, 300);
        Box components = Box.createVerticalBox();
        startWindow.add(components);

        // Game title
        final JPanel titlePanel = new JPanel();
        components.add(titlePanel);
        final JLabel titleLabel = new JLabel("Aspon E chess game");
        titlePanel.add(titleLabel);

        // Black player selections
        final JPanel blackPanel = new JPanel();
        components.add(blackPanel, BorderLayout.EAST);
        final JLabel blackPiece = new JLabel();

        blackPanel.add(new JLabel("Black"));
        final JTextField blackInput = new JTextField("", 10);
        blackPanel.add(blackInput);

        // White player selections
        final JPanel whitePanel = new JPanel();
        components.add(whitePanel);

        whitePanel.add(new JLabel("White"));
        final JTextField whiteInput = new JTextField("", 10);
        whitePanel.add(whiteInput);

        final JCheckBox blackComputerButton = new JCheckBox(COMPUTER_TEXT);
        blackPanel.add(blackComputerButton);

        // Timer selection
        final String[] minSecInts = new String[60];
        for (int i = 0; i < 60; i++) {
            if (i < 10) {
                minSecInts[i] = "0" + i;
            } else {
                minSecInts[i] = Integer.toString(i);
            }
        }

        final JComboBox<String> seconds = new JComboBox<>(minSecInts);
        final JComboBox<String> minutes = new JComboBox<>(minSecInts);
        final JComboBox<String> hours = new JComboBox<>(new String[] {"0","1","2","3"});

        Box timerSettings = Box.createHorizontalBox();

        hours.setMaximumSize(hours.getPreferredSize());
        minutes.setMaximumSize(minutes.getPreferredSize());
        seconds.setMaximumSize(minutes.getPreferredSize());

        timerSettings.add(hours);
        timerSettings.add(Box.createHorizontalStrut(10));
        timerSettings.add(seconds);
        timerSettings.add(Box.createHorizontalStrut(10));
        timerSettings.add(minutes);

        timerSettings.add(Box.createVerticalGlue());

        components.add(timerSettings);

        // Buttons
        Box buttons = Box.createHorizontalBox();
        final JButton quit = new JButton("Quit");

        quit.addActionListener(e -> startWindow.dispose());

        final JButton instr = new JButton("Info");

        instr.addActionListener(e -> JOptionPane.showMessageDialog(startWindow,
                "Basic info about game.\n" +
                        "Firstly set names next to the labels. If you check\n" +
                        "in computer checkbox. Name will be setted automatically\n" +
                        "as 'Computer'. Choose time and click Start. \n" +
                        "Leaving timer empty will start game with unlimited timer. \n" +
                        "That is not actually good place to find out how to play chess. \n" +
                        "Rather go check YouTube.",
                "Info",
                JOptionPane.PLAIN_MESSAGE));

        final JButton start = new JButton("Start");

        start.addActionListener(e -> {
            String bn = blackInput.getText();
            setBlackIsComputer(blackComputerButton.isSelected());
            if(blackComputerButton.isSelected()) {
                bn = "Computer";
            }
            String wn = whiteInput.getText();
            System.out.println(bn);
            System.out.println(wn);
            LOGGER.info("PlayerBlack{" + "name=" + bn + "}");
            LOGGER.info("PlayerWhite{" + "name=" + wn + "}");

            int hh = Integer.parseInt((String) Objects.requireNonNull(hours.getSelectedItem()));
            int mm = Integer.parseInt((String) Objects.requireNonNull(minutes.getSelectedItem()));
            int ss = Integer.parseInt((String) Objects.requireNonNull(seconds.getSelectedItem()));
            new GameWin(bn, wn, hh, mm, ss);
            startWindow.dispose();
        });
        buttons.add(start);
        buttons.add(Box.createHorizontalStrut(10));
        buttons.add(instr);
        buttons.add(Box.createHorizontalStrut(10));
        buttons.add(quit);
        components.add(buttons);
        Component space = Box.createGlue();
        components.add(space);
        startWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startWindow.setVisible(true);
    }
}
