package cz.cvut.fel.pjv.aspone.gui;

import cz.cvut.fel.pjv.aspone.board.Board;
import cz.cvut.fel.pjv.aspone.board.Square;
import cz.cvut.fel.pjv.aspone.utils.Clock;
import cz.cvut.fel.pjv.aspone.utils.Helper;
import cz.cvut.fel.pjv.aspone.utils.Reader;
import cz.cvut.fel.pjv.aspone.utils.Writer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

/**
 * The type Game win.
 *
 * @author Mukan Atazhanov
 * @project atazhmuk
 * @created 01 /04/2022 - 16:14 GUI class
 */
public class GameWin {
    /**
     * The constant LOGGER.
     */
    public static final Logger LOGGER = Logger.getLogger(GameWin.class.getName());

    private final JFrame gameWindow;
    /**
     * The Black clock.
     */
    public  Clock blackClock;
    /**
     * The White clock.
     */
    public  Clock whiteClock;
    private  Timer timer;
    private final Board board;
     Writer writer = new Writer();
     Reader reader = new Reader();
     Image whiteImg;

    public GameWin(List<Helper> toLoad, boolean asd) {
        blackClock = new Clock(0, 0, 0);
        whiteClock = new Clock(0, 0, 0);
        gameWindow = new JFrame("Chess");

        try {
            whiteImg = ImageIO.read(new File("img/Logo.png"));
            gameWindow.setIconImage(whiteImg);
        } catch (Exception e) {
            LOGGER.info("File Not Found");
        }

        gameWindow.setLocation(100, 100);
        gameWindow.setLayout(new BorderLayout(20,20));

        // Game Data window
        JPanel gameData = gameDataPanel("Black", "White", 0, 0, 0);
        gameData.setSize(gameData.getPreferredSize());
        gameWindow.add(gameData, BorderLayout.NORTH);

        this.board = new Board(this, toLoad, asd);

        gameWindow.add(board, BorderLayout.CENTER);

        gameWindow.add(buttons(), BorderLayout.SOUTH);

        gameWindow.setMinimumSize(gameWindow.getPreferredSize());
        gameWindow.setSize(gameWindow.getPreferredSize());
        gameWindow.setResizable(false);


        gameWindow.pack();
        gameWindow.setVisible(true);
        gameWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }

    public GameWin(List<Helper> toLoad) {
        blackClock = new Clock(0, 0, 0);
        whiteClock = new Clock(0, 0, 0);
        gameWindow = new JFrame("Chess");

        try {
            whiteImg = ImageIO.read(new File("img/Logo.png"));
            gameWindow.setIconImage(whiteImg);
        } catch (Exception e) {
            LOGGER.info("File Not Found");
        }

        gameWindow.setLocation(100, 100);
        gameWindow.setLayout(new BorderLayout(20,20));

        // Game Data window
        JPanel gameData = gameDataPanel("Black", "White", 0, 0, 0);
        gameData.setSize(gameData.getPreferredSize());
        gameWindow.add(gameData, BorderLayout.NORTH);

        this.board = new Board(this, toLoad);

        gameWindow.add(board, BorderLayout.CENTER);

        gameWindow.add(buttons(), BorderLayout.SOUTH);

        gameWindow.setMinimumSize(gameWindow.getPreferredSize());
        gameWindow.setSize(gameWindow.getPreferredSize());
        gameWindow.setResizable(false);


        gameWindow.pack();
        gameWindow.setVisible(true);
        gameWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }

    /**
     * Instantiates a new Game win.
     *
     * @param blackName the black name
     * @param whiteName the white name
     * @param hh        the hh
     * @param mm        the mm
     * @param ss        the ss
     */
    public GameWin(String blackName, String whiteName, int hh, int mm, int ss) {

        blackClock = new Clock(hh, ss, mm);
        whiteClock = new Clock(hh, ss, mm);
        gameWindow = new JFrame("Chess");

        try {
            Image whiteImg = ImageIO.read(new File("img/Logo.png"));
            gameWindow.setIconImage(whiteImg);
        } catch (Exception e) {
            LOGGER.info("File Not Found");
        }

        gameWindow.setLocation(100, 100);
        gameWindow.setLayout(new BorderLayout(20,20));

        // Game Data window
        JPanel gameData = gameDataPanel(blackName, whiteName, hh, mm, ss);
        gameData.setSize(gameData.getPreferredSize());
        gameWindow.add(gameData, BorderLayout.NORTH);

        this.board = new Board(this);

        gameWindow.add(board, BorderLayout.CENTER);

        gameWindow.add(buttons(), BorderLayout.SOUTH);

        gameWindow.setMinimumSize(gameWindow.getPreferredSize());
        gameWindow.setSize(gameWindow.getPreferredSize());
        gameWindow.setResizable(false);


        gameWindow.pack();
        gameWindow.setVisible(true);
        gameWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        reader = new Reader();
    }

    private JPanel gameDataPanel(final String bn, final String wn,
                                 final int hh, final int mm, final int ss) {

        JPanel gameData = new JPanel();
        gameData.setLayout(new GridLayout(3,2,0,0));

        JLabel w = new JLabel(wn);
        JLabel b = new JLabel(bn);

        w.setHorizontalAlignment(JLabel.CENTER);
        w.setVerticalAlignment(JLabel.CENTER);
        b.setHorizontalAlignment(JLabel.CENTER);
        b.setVerticalAlignment(JLabel.CENTER);

        w.setSize(w.getMinimumSize());
        b.setSize(b.getMinimumSize());

        gameData.add(w);
        gameData.add(b);

        // CLOCKS
        final JLabel bTime = new JLabel(blackClock.toString());
        final JLabel wTime = new JLabel(whiteClock.toString());

        bTime.setHorizontalAlignment(JLabel.CENTER);
        bTime.setVerticalAlignment(JLabel.CENTER);
        wTime.setHorizontalAlignment(JLabel.CENTER);
        wTime.setVerticalAlignment(JLabel.CENTER);

        if (!(hh == 0 && mm == 0 && ss == 0)) {
            timer = new Timer(1000, null);
            timer.addActionListener(e -> {
                boolean turn = board.isWhiteTurn();
                if (turn) {
                    whiteClock.decr();
                    wTime.setText(whiteClock.toString());

                    if (whiteClock.outOfTime()) {
                        timer.stop();
                        int n = JOptionPane.showConfirmDialog(
                                gameWindow,
                                bn + " wins by time! Play a new game? \n" +
                                        "Choosing \"No\" quits the game.",
                                bn + " wins!",
                                JOptionPane.YES_NO_OPTION);

                        if (n == JOptionPane.YES_OPTION) {
                            new GameWin(bn, wn, hh, mm, ss);
                        }
                        gameWindow.dispose();
                    }
                } else {
                    blackClock.decr();
                    bTime.setText(blackClock.toString());

                    if (blackClock.outOfTime()) {
                        timer.stop();
                        int n = JOptionPane.showConfirmDialog(
                                gameWindow,
                                wn + " wins by time! Play a new game? \n" +
                                        "Choosing \"No\" quits the game.",
                                wn + " wins!",
                                JOptionPane.YES_NO_OPTION);

                        if (n == JOptionPane.YES_OPTION) {
                            new GameWin(bn, wn, hh, mm, ss);
                        }
                        gameWindow.dispose();
                    }
                }
            });
            timer.start();
        } else {
            wTime.setText("Untimed game");
            bTime.setText("Untimed game");
        }

        gameData.add(wTime);
        gameData.add(bTime);

        gameData.setPreferredSize(gameData.getMinimumSize());

        return gameData;
    }

    //buttons
    private JPanel buttons() {
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1, 3, 10, 0));

        final JButton quit = new JButton("Quit");

        quit.addActionListener(e -> {
            int n = JOptionPane.showConfirmDialog(
                    gameWindow,
                    "Are you sure you want to quit?",
                    "Confirm quit", JOptionPane.YES_NO_OPTION);

            if (n == JOptionPane.YES_OPTION) {
                if (timer != null) timer.stop();
                gameWindow.dispose();
            }
        });

        final JButton saveGame = new JButton("Save");

        saveGame.addActionListener(e -> {
            try {
                writer.writeFile(this.board.getAllPieces(this.board.getBoard()));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        final JButton nGame = new JButton("New game");

        nGame.addActionListener(e -> {
            int n = JOptionPane.showConfirmDialog(
                    gameWindow,
                    "Are you sure you want to begin a new game?",
                    "Confirm new game", JOptionPane.YES_NO_OPTION);

            if (n == JOptionPane.YES_OPTION) {
                SwingUtilities.invokeLater(new StartMenu());
                gameWindow.dispose();
            }
        });

        /*final JButton loadGame = new JButton("Load");

        loadGame.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int response = fileChooser.showOpenDialog(null);
            fileChooser.setCurrentDirectory(new File("."));

            if(response == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    System.out.println(reader.readFile(file).getClass().getName());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });*/

        buttons.add(nGame);
        buttons.add(saveGame);
        //buttons.add(loadGame);
        buttons.add(quit);
        buttons.setPreferredSize(buttons.getMinimumSize());
        return buttons;
    }

    /**
     * method called when mate occurred and screen has to be changed
     *
     * @param c - case 0 - white won, 1 - black won, else - stalemate
     */
    public void checkmateOccurred (int c) {
        if (c == 0) {
            if (timer != null) timer.stop();
            int n = JOptionPane.showConfirmDialog(
                    gameWindow,
                    "White wins by checkmate! Set up a new game? \n" +
                            "Choosing \"No\" lets you look at the final situation.",
                    "White wins!",
                    JOptionPane.YES_NO_OPTION);

            if (n == JOptionPane.YES_OPTION) {
                SwingUtilities.invokeLater(new StartMenu());
                gameWindow.dispose();
            }
        } else if(c==1) {
            if (timer != null) timer.stop();
            int n = JOptionPane.showConfirmDialog(
                    gameWindow,
                    "Black wins by checkmate! Set up a new game? \n" +
                            "Choosing \"No\" lets you look at the final situation.",
                    "Black wins!",
                    JOptionPane.YES_NO_OPTION);

            if (n == JOptionPane.YES_OPTION) {
                SwingUtilities.invokeLater(new StartMenu());
                gameWindow.dispose();
            }
        }else{
            if (timer != null) timer.stop();
            int n = JOptionPane.showConfirmDialog(
                    gameWindow,
                    "It's a stalemate! Set up a new game? \n" +
                            "Choosing \"No\" lets you look at the final situation.",
                    "It's a tie!",
                    JOptionPane.YES_NO_OPTION);

            if (n == JOptionPane.YES_OPTION) {
                SwingUtilities.invokeLater(new StartMenu());
                gameWindow.dispose();
            }
        }
    }
}
