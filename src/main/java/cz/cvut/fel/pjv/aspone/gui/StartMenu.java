package cz.cvut.fel.pjv.aspone.gui;

import cz.cvut.fel.pjv.aspone.board.Board;
import cz.cvut.fel.pjv.aspone.board.Square;
import cz.cvut.fel.pjv.aspone.utils.Helper;
import cz.cvut.fel.pjv.aspone.utils.Reader;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import static cz.cvut.fel.pjv.aspone.board.Board.*;

/**
 * The type Start menu.
 *
 * @author Mukan Atazhanov
 * @project atazhmuk
 * @created 01 /04/2022 - 16:14
 */
public class StartMenu implements Runnable{

    static List<String> tempList = new ArrayList<>(Arrays.asList("B", "K", "N", "P", "R", "Q"));
    /**
     * The constant LOGGER.
     */
    public static final Logger LOGGER = Logger.getLogger(StartMenu.class.getName());
    /**
     * The constant COMPUTER_TEXT.
     */
    public static final String COMPUTER_TEXT = "Computer";

     Reader reader = new Reader();
     BufferedReader bf;
     Helper helper;

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

        blackPanel.add(new JLabel("Black"));
        final JTextField blackInput = new JTextField("Black", 10);
        blackPanel.add(blackInput);

        // White player selections
        final JPanel whitePanel = new JPanel();
        components.add(whitePanel);

        whitePanel.add(new JLabel("White"));
        final JTextField whiteInput = new JTextField("White", 10);
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
            blackName = bn;
            String wn = whiteInput.getText();
            whiteName = wn;

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

        final JButton loadGame = new JButton("Load");

        loadGame.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int response = fileChooser.showOpenDialog(null);
            fileChooser.setCurrentDirectory(new File("."));

            if(response == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    List<Helper> toLoad = (List<Helper>) reader.readFile(file);
                    /*for(Helper helper : toLoad) {
                        System.out.println(helper.getPiece().getColor() + " " + helper.getPiece().toString() + " " + helper.getX() + " " + helper.getY());
                    }*/
                    new GameWin(toLoad);
                    startWindow.dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        final JButton manual = new JButton("Manually");
        manual.addActionListener(e -> {
            System.out.println("Basic rules of manually setting pieces");
            System.out.println("Board size is 8 x 8");
            System.out.println("Indexing of board start from 0 to 7");
            System.out.println("Colors are 0 or 1 [0 is black and 1 is white]");
            System.out.println("Piece symbols are default PGN type symbols");
            System.out.println("In game HAVE TO BE 2 KING [white and black]");
            System.out.println("Input type is [Color, Piece symbol, Y pos, X pos]");
            System.out.println("Example [0, P, 0, 0] (without [])");
            System.out.println("Write [QUIT] to finish setting");
            System.out.println();
            try {
                List<Helper> toLoad = manuallySettingPiece();
                new GameWin(toLoad, true);
                startWindow.dispose();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        buttons.add(start);
        buttons.add(Box.createHorizontalStrut(10));
        buttons.add(loadGame);
        buttons.add(Box.createHorizontalStrut(10));
        buttons.add(manual);
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

    public List<Helper> manuallySettingPiece() throws IOException {
        List<Helper> toLoad = new ArrayList<>();
        String test;
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter pieces");
        while((test = bf.readLine()) != null) {
            if(test.equals("QUIT")){
                break;
            }
            String[] temp = test.split(", ");
            if(validateToLoad(temp)) {
                Helper helper = new Helper(Integer.parseInt(temp[0]), temp[1], Integer.parseInt(temp[2]), Integer.parseInt(temp[3]));
                toLoad.add(helper);
            }
        }
        bf.close();
        System.out.println("Manually Setted");
        return toLoad;
    }

    public boolean validateToLoad(String[] toLoad) {
        if(toLoad.length > 4) {
            System.err.println("Bad length");
            System.exit(1);
            return false;
        } else if(!toLoad[0].equals("0") && !toLoad[0].equals("1")) {
            System.err.println("Bad color type");
            System.exit(1);
            return false;
        } else if(Integer.parseInt(toLoad[2]) < 0 && Integer.parseInt(toLoad[2]) > 7) {
            System.err.println("Bad Y pos");
            System.exit(1);
            return false;
        } else if(Integer.parseInt(toLoad[3]) < 0 && Integer.parseInt(toLoad[3]) > 7) {
            System.err.println("Bad X pos");
            System.exit(1);
            return false;
        } else if(!tempList.contains(toLoad[1])) {
            System.err.println("Bad piece symbol");
            System.exit(1);
            return false;
        }
        return true;
    }
}
