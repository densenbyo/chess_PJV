package cz.cvut.fel.pjv.aspone.board;

import cz.cvut.fel.pjv.aspone.gui.GameWin;
import cz.cvut.fel.pjv.aspone.piece.*;
import cz.cvut.fel.pjv.aspone.player.Computer;
import cz.cvut.fel.pjv.aspone.utils.CheckmateDetector;
import cz.cvut.fel.pjv.aspone.utils.Helper;
import cz.cvut.fel.pjv.aspone.utils.Writer;
import cz.cvut.fel.pjv.aspone.utils.Timer;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.rmi.ServerError;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * The type Board.
 *
 * @author Mukan Atazhanov
 * @project atazhmuk
 * @created 01 /04/2022 - 16:02 Board class
 */
public class Board extends JPanel implements MouseListener, MouseMotionListener{
    /**
     * The constant LOGGER.
     */
    public static final Logger LOGGER = Logger.getLogger(Board.class.getName());

    /**
     * Representation of pieces
     */
    private final String WBISHOP = "img/WB.png";
    private final String WKING = "img/WK.png";
    private final String WKNIGHT = "img/WN.png";
    private final String WPAWN = "img/WP.png";
    private final String WQUEEN = "img/WQ.png";
    private final String WROOK = "img/WR.png";
    private final String BBISHOP = "img/BB.png";
    private final String BKING = "img/BK.png";
    private final String BKNIGHT = "img/BN.png";
    private final String BPAWN = "img/BP.png";
    private final String BQUEEN = "img/BQ.png";
    private final String BROOK = "img/BR.png";

    /**
     * The constant blackIsComputer.
     */
    public static boolean blackIsComputer;

    /**
     * Set black is computer.
     *
     * @param b the b
     */
    public static void setBlackIsComputer(boolean b){
        blackIsComputer = b;
    }

    private final Computer computer = new Computer();
    private final transient Writer utility = new Writer();

    /**
     * Logical and graphical representations of board
     */
    @Getter
    @Setter
    private Square[][] board;
    private transient GameWin gameWindow;
    /**
     * The B pieces.
     */
    public LinkedList<Piece> bPieces;
    /**
     * The W pieces.
     */
    public LinkedList<Piece> wPieces;
    /**
     * The Movable.
     */
    public  List<Square> movable;
    @Getter
    private boolean whiteTurn;
    private int movesCount = 1;
    private  Piece currPiece;
    private int currX;
    private int currY;
    /**
     * The constant whiteName.
     */
    public static String whiteName;
    /**
     * The constant blackName.
     */
    public static String blackName;
    public CheckmateDetector checkmateDetector;

    public King wk;
    public King bk;

    public Board(){
        board = new Square[8][8];
        bPieces = new LinkedList<>();
        wPieces = new LinkedList<>();

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                int xMod = x % 2;
                int yMod = y % 2;

                if((xMod == 0 && yMod == 0) || (xMod == 1 && yMod == 1)) {
                    board[x][y] = new Square(this, 1, y, x);
                } else {
                    board[x][y] = new Square(this, 0, y, x);
                }
            }
        }
    }

    public Board(Square[][] test) {
        board = test;
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                int xMod = x % 2;
                int yMod = y % 2;

                if((xMod == 0 && yMod == 0) || (xMod == 1 && yMod == 1)) {
                    board[x][y] = new Square(this, 1, y, x);
                } else {
                    board[x][y] = new Square(this, 0, y, x);
                }
                this.add(board[x][y]);
            }
        }

        initializePieces();
    }

    public Board(GameWin gameWindow, List<Helper> toLoad, boolean asd) {
        this.gameWindow = gameWindow;
        board = new Square[8][8];
        bPieces = new LinkedList<>();
        wPieces = new LinkedList<>();
        setLayout(new GridLayout(8, 8, 0, 0));

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                int xMod = x % 2;
                int yMod = y % 2;

                if((xMod == 0 && yMod == 0) || (xMod == 1 && yMod == 1)) {
                    board[x][y] = new Square(this, 1, y, x);
                } else {
                    board[x][y] = new Square(this, 0, y, x);
                }
                this.add(board[x][y]);
            }
        }

        initializePieces(toLoad, asd);

        this.setPreferredSize(new Dimension(400, 400));
        this.setMaximumSize(new Dimension(400, 400));
        this.setMinimumSize(this.getPreferredSize());
        this.setSize(new Dimension(400, 400));

        whiteTurn = true;
        utility.writeNewGame();
    }

    public Board(GameWin gameWindow, List<Helper> toLoad) {
        this.gameWindow = gameWindow;
        board = new Square[8][8];
        bPieces = new LinkedList<>();
        wPieces = new LinkedList<>();
        setLayout(new GridLayout(8, 8, 0, 0));

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                int xMod = x % 2;
                int yMod = y % 2;

                if((xMod == 0 && yMod == 0) || (xMod == 1 && yMod == 1)) {
                    board[x][y] = new Square(this, 1, y, x);
                } else {
                    board[x][y] = new Square(this, 0, y, x);
                }
                this.add(board[x][y]);
            }
        }

        initializePieces(toLoad);

        this.setPreferredSize(new Dimension(400, 400));
        this.setMaximumSize(new Dimension(400, 400));
        this.setMinimumSize(this.getPreferredSize());
        this.setSize(new Dimension(400, 400));

        whiteTurn = true;
        utility.writeNewGame();
    }

    /**
     * Instantiates a new Board.
     *
     * @param gameWindow current game window
     */
    public Board(GameWin gameWindow) {
        this.gameWindow = gameWindow;
        board = new Square[8][8];
        bPieces = new LinkedList<>();
        wPieces = new LinkedList<>();
        setLayout(new GridLayout(8, 8, 0, 0));

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                int xMod = x % 2;
                int yMod = y % 2;

                if((xMod == 0 && yMod == 0) || (xMod == 1 && yMod == 1)) {
                    board[x][y] = new Square(this, 1, y, x);
                } else {
                    board[x][y] = new Square(this, 0, y, x);
                }
                this.add(board[x][y]);
            }
        }

        initializePieces();

        this.setPreferredSize(new Dimension(400, 400));
        this.setMaximumSize(new Dimension(400, 400));
        this.setMinimumSize(this.getPreferredSize());
        this.setSize(new Dimension(400, 400));

        whiteTurn = true;
        utility.writeNewGame();
    }

    public void initializePieces(List<Helper> toLoad, boolean asd) {
        for(Helper helper : toLoad) {
            if(helper.getColor() == 0) {
                switch (helper.getPieceSymbol()) {
                    case "B" -> {
                        board[helper.getX()][helper.getY()].put(new Bishop(0, board[helper.getX()][helper.getY()], BBISHOP));
                        bPieces.add(new Bishop(0, board[helper.getX()][helper.getY()], BBISHOP));
                    }
                    case "K" -> {
                        bk = new King(0, board[helper.getX()][helper.getY()], BKING);
                        board[helper.getX()][helper.getY()].put(bk);
                        bPieces.add(board[helper.getX()][helper.getY()].getOccupyingPiece());
                    }
                    case "N" -> {
                        board[helper.getX()][helper.getY()].put(new Knight(0, board[helper.getX()][helper.getY()], BKNIGHT));
                        bPieces.add(board[helper.getX()][helper.getY()].getOccupyingPiece());
                    }
                    case "Q" -> {
                        board[helper.getX()][helper.getY()].put(new Queen(0, board[helper.getX()][helper.getY()], BQUEEN));
                        bPieces.add(board[helper.getX()][helper.getY()].getOccupyingPiece());
                    }
                    case "R" -> {
                        board[helper.getX()][helper.getY()].put(new Rook(0, board[helper.getX()][helper.getY()], BROOK));
                        bPieces.add(board[helper.getX()][helper.getY()].getOccupyingPiece());
                    }
                    case "P" -> {
                        board[helper.getX()][helper.getY()].put(new Pawn(0, board[helper.getX()][helper.getY()], BPAWN));
                        bPieces.add(board[helper.getX()][helper.getY()].getOccupyingPiece());
                    }
                }
            } else if(helper.getColor() == 1){
                switch (helper.getPieceSymbol()) {
                    case "B" -> {
                        board[helper.getX()][helper.getY()].put(new Bishop(1, board[helper.getX()][helper.getY()], WBISHOP));
                        wPieces.add(new Bishop(1, board[helper.getX()][helper.getY()], WBISHOP));
                    }
                    case "K" -> {
                        wk = new King(1, board[helper.getX()][helper.getY()], WKING);
                        board[helper.getX()][helper.getY()].put(wk);
                        wPieces.add(board[helper.getX()][helper.getY()].getOccupyingPiece());
                    }
                    case "N" -> {
                        board[helper.getX()][helper.getY()].put(new Knight(1, board[helper.getX()][helper.getY()], WKNIGHT));
                        wPieces.add(board[helper.getX()][helper.getY()].getOccupyingPiece());
                    }
                    case "Q" -> {
                        board[helper.getX()][helper.getY()].put(new Queen(1, board[helper.getX()][helper.getY()], WQUEEN));
                        wPieces.add(board[helper.getX()][helper.getY()].getOccupyingPiece());
                    }
                    case "R" -> {
                        board[helper.getX()][helper.getY()].put(new Rook(1, board[helper.getX()][helper.getY()], WROOK));
                        wPieces.add(board[helper.getX()][helper.getY()].getOccupyingPiece());
                    }
                    case "P" -> {
                        board[helper.getX()][helper.getY()].put(new Pawn(1, board[helper.getX()][helper.getY()], WPAWN));
                        wPieces.add(board[helper.getX()][helper.getY()].getOccupyingPiece());
                    }
                }
            }
        }
        if(wk != null && bk != null) {
            checkmateDetector = new CheckmateDetector(this, wPieces, bPieces, wk, bk);
        } else {
            System.err.println("Kings have to be in game");
            System.exit(1);
        }
    }

    public void initializePieces(List<Helper> toLoad) {
        for(Helper helper : toLoad) {
            if(helper.getPiece().getColor() == 0) {
                if(helper.getPiece().toString().equals("B")) {
                    board[helper.getX()][helper.getY()].put(new Bishop(0, board[helper.getX()][helper.getY()], BBISHOP));
                    bPieces.add(new Bishop(0, board[helper.getX()][helper.getY()], BBISHOP));
                } else if(helper.getPiece().toString().equals("K")) {
                    bk = new King(0, board[helper.getX()][helper.getY()], BKING);
                    board[helper.getX()][helper.getY()].put(bk);
                    bPieces.add(board[helper.getX()][helper.getY()].getOccupyingPiece());
                }
                else if(helper.getPiece().toString().equals("N")) {
                    board[helper.getX()][helper.getY()].put(new Knight(0, board[helper.getX()][helper.getY()], BKNIGHT));
                    bPieces.add(board[helper.getX()][helper.getY()].getOccupyingPiece());
                }
                else if(helper.getPiece().toString().equals("Q")) {
                    board[helper.getX()][helper.getY()].put(new Queen(0, board[helper.getX()][helper.getY()], BQUEEN));
                    bPieces.add(board[helper.getX()][helper.getY()].getOccupyingPiece());
                }
                else if(helper.getPiece().toString().equals("R")) {
                    board[helper.getX()][helper.getY()].put(new Rook(0, board[helper.getX()][helper.getY()], BROOK));
                    bPieces.add(board[helper.getX()][helper.getY()].getOccupyingPiece());
                }
                else if(helper.getPiece().toString().equals("P")) {
                    board[helper.getX()][helper.getY()].put(new Pawn(0, board[helper.getX()][helper.getY()], BPAWN));
                    bPieces.add(board[helper.getX()][helper.getY()].getOccupyingPiece());
                }
            } else {
                if(helper.getPiece().toString().equals("B")) {
                    board[helper.getX()][helper.getY()].put(new Bishop(1, board[helper.getX()][helper.getY()], WBISHOP));
                    wPieces.add(new Bishop(1, board[helper.getX()][helper.getY()], WBISHOP));
                } else if(helper.getPiece().toString().equals("K")) {
                    bk = new King(1, board[helper.getX()][helper.getY()], WKING);
                    board[helper.getX()][helper.getY()].put(bk);
                    wPieces.add(board[helper.getX()][helper.getY()].getOccupyingPiece());
                }
                else if(helper.getPiece().toString().equals("N")) {
                    board[helper.getX()][helper.getY()].put(new Knight(1, board[helper.getX()][helper.getY()], WKNIGHT));
                    wPieces.add(board[helper.getX()][helper.getY()].getOccupyingPiece());
                }
                else if(helper.getPiece().toString().equals("Q")) {
                    board[helper.getX()][helper.getY()].put(new Queen(1, board[helper.getX()][helper.getY()], WQUEEN));
                    wPieces.add(board[helper.getX()][helper.getY()].getOccupyingPiece());
                }
                else if(helper.getPiece().toString().equals("R")) {
                    board[helper.getX()][helper.getY()].put(new Rook(1, board[helper.getX()][helper.getY()], WROOK));
                    wPieces.add(board[helper.getX()][helper.getY()].getOccupyingPiece());
                }
                else if(helper.getPiece().toString().equals("P")) {
                    board[helper.getX()][helper.getY()].put(new Pawn(1, board[helper.getX()][helper.getY()], WPAWN));
                    wPieces.add(board[helper.getX()][helper.getY()].getOccupyingPiece());
                }
            }
        }
        checkmateDetector = new CheckmateDetector(this, wPieces, bPieces, wk, bk);
    }

    /**
     *  initialize board, means put chess pieces to the board
     */
    public void initializePieces() {
        //put black color pieces
        board[0][3].put(new Queen(0, board[0][3], BQUEEN));
        bk = new King(0, board[0][4], BKING);
        board[0][4].put(bk);
        board[0][0].put(new Rook(0, board[0][0], BROOK));
        board[0][7].put(new Rook(0, board[0][7], BROOK));
        board[0][1].put(new Knight(0, board[0][1], BKNIGHT));
        board[0][6].put(new Knight(0, board[0][6], BKNIGHT));
        board[0][2].put(new Bishop(0, board[0][2], BBISHOP));
        board[0][5].put(new Bishop(0, board[0][5], BBISHOP));

        //put white color pieces
        board[7][3].put(new Queen(1, board[7][3], WQUEEN));
        wk = new King(1, board[7][4], WKING);
        board[7][4].put(wk);
        board[7][0].put(new Rook(1, board[7][0], WROOK));
        board[7][7].put(new Rook(1, board[7][7], WROOK));
        board[7][1].put(new Knight(1, board[7][1], WKNIGHT));
        board[7][6].put(new Knight(1, board[7][6], WKNIGHT));
        board[7][2].put(new Bishop(1, board[7][2], WBISHOP));
        board[7][5].put(new Bishop(1, board[7][5], WBISHOP));

        //put pawns
        for (int x = 0; x < 8; x++) {
            board[1][x].put(new Pawn(0, board[1][x], BPAWN));
            board[6][x].put(new Pawn(1, board[6][x], WPAWN));
        }

        for(int y = 0; y < 2; y++) {
            for (int x = 0; x < 8; x++) {
                bPieces.add(board[y][x].getOccupyingPiece());
                wPieces.add(board[7-y][x].getOccupyingPiece());
            }
        }
        checkmateDetector = new CheckmateDetector(this, wPieces, bPieces, wk, bk);
    }

    /**
     * @param g
     * prints components in square/cell etc
     */
    @Override
    public void paintComponent(Graphics g) {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Square sq = board[y][x];
                sq.paintComponent(g);
            }
        }
        if (currPiece != null) {
            if ((currPiece.getColor() == 1 && whiteTurn)
                    || (currPiece.getColor() == 0 && !whiteTurn)) {
                final Image i = currPiece.getImg();
                g.drawImage(i, currX, currY, null);
            }
        }
    }

    /**
     * @param e
     * mouse listener for pressing
     */
    @Override
    public void mousePressed(MouseEvent e) {
        currX = e.getX();
        currY = e.getY();

        Square sq = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));

        if (sq.isOccupied()) {
            currPiece = sq.getOccupyingPiece();
            if (currPiece.getColor() == 0 && whiteTurn)
                return;
            if (currPiece.getColor() == 1 && !whiteTurn)
                return;
            sq.setDisPiece(false);
        }
        repaint();
    }

    /**
     * @param e
     * mouse listener for releasing
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        Square sq = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));
        Timer timeHelper = new Timer();
        timeHelper.start();

        if (currPiece != null) {
            if (currPiece.getColor() == 0 && whiteTurn) return;
            if (currPiece.getColor() == 1 && !whiteTurn) return;

            List<Square> legalMoves = currPiece.getLegalMoves(this);
            movable = checkmateDetector.getAllowableSquares();

            if (legalMoves.contains(sq) && movable.contains(sq) && checkmateDetector.testMove(currPiece, sq)) {
                sq.setDisPiece(true);
                currPiece.move(sq, this);
                currPiece.setWasMoved(true);
                checkmateDetector.update();
                utility.writeMove(sq,currPiece,whiteTurn,movesCount);

                LOGGER.info("Move{" +
                        "player=" + currPiece.toString() +
                        "xPos=" + sq.getXNum() +
                        "yPos=" + sq.getYNum() +
                        "}");

                if (!whiteTurn) movesCount++;

                if(checkmateDetector.blackInCheck()) {
                    System.out.println("Zdarova");
                }else if(checkmateDetector.whiteInCheck()) {
                    System.out.println("SalamAleikum");
                }

                if (checkmateDetector.blackCheckMated()) {
                    currPiece = null;
                    repaint();
                    this.removeMouseListener(this);
                    this.removeMouseMotionListener(this);
                    utility.writeMate();

                    gameWindow.checkmateOccurred(0);
                    LOGGER.info("Black checkmated");

                } else if (checkmateDetector.whiteCheckMated()) {
                    currPiece = null;
                    repaint();
                    this.removeMouseListener(this);
                    this.removeMouseMotionListener(this);
                    utility.writeMate();

                    gameWindow.checkmateOccurred(1);
                    LOGGER.info("White checkmated");

                } else if (checkmateDetector.checkStaleMate(whiteTurn)){
                    gameWindow.checkmateOccurred(3);
                    LOGGER.info("Stalemate");
                } else {
                    currPiece = null;
                    whiteTurn = !whiteTurn;
                    movable = checkmateDetector.getAllowableSquares();
                }

                if (blackIsComputer){
                    Piece compPiece = computer.makeMove(bPieces, this);
                    compPiece.setWasMoved(true);
                    compPiece.getCurrentSquare().setDisPiece(true);
                    movable = checkmateDetector.getAllowableSquares();
                    utility.writeMove(compPiece.getCurrentSquare(), compPiece, whiteTurn, movesCount);
                    whiteTurn = !whiteTurn;
                    movesCount++;

                    LOGGER.info("Move{" +
                            "comp=" +compPiece +
                            "xPos=" + compPiece.getCurrentSquare().getXNum() +
                            "yPos"+ compPiece.getCurrentSquare().getYNum() +
                            "}");
                }

            } else {
                if(legalMoves.contains(sq) && !movable.contains(sq) && checkmateDetector.testMove(currPiece, sq)) {
                    System.out.println("I WAS HERE");
                    sq.setDisPiece(true);
                    currPiece.move(sq, this);
                    currPiece.setWasMoved(true);
                    checkmateDetector.update();
                    utility.writeMove(sq,currPiece,whiteTurn,movesCount);

                    LOGGER.info("Move{" +
                            "player=" + currPiece.toString() +
                            "xPos=" + sq.getXNum() +
                            "yPos=" + sq.getYNum() +
                            "}");

                    if (!whiteTurn) movesCount++;
                    if(checkmateDetector.blackInCheck()) {
                        System.out.println("???");
                    }
                    if (checkmateDetector.blackCheckMated()) {
                        currPiece = null;
                        repaint();
                        this.removeMouseListener(this);
                        this.removeMouseMotionListener(this);
                        utility.writeMate();

                        gameWindow.checkmateOccurred(0);
                        LOGGER.info("Black checkmated");

                    } else if (checkmateDetector.whiteCheckMated()) {
                        currPiece = null;
                        repaint();
                        this.removeMouseListener(this);
                        this.removeMouseMotionListener(this);
                        utility.writeMate();

                        gameWindow.checkmateOccurred(1);
                        LOGGER.info("White checkmated");

                    } else if (checkmateDetector.checkStaleMate(whiteTurn)){
                        gameWindow.checkmateOccurred(3);
                        LOGGER.info("Stalemate");
                    } else {
                        currPiece = null;
                        whiteTurn = !whiteTurn;
                        movable = checkmateDetector.getAllowableSquares();
                    }
                    if (blackIsComputer){
                        Piece compPiece = computer.makeMove(bPieces, this);
                        compPiece.setWasMoved(true);
                        compPiece.getCurrentSquare().setDisPiece(true);
                        movable = checkmateDetector.getAllowableSquares();
                        utility.writeMove(compPiece.getCurrentSquare(), compPiece, whiteTurn, movesCount);
                        whiteTurn = !whiteTurn;
                        movesCount++;

                        LOGGER.info("Move{" +
                                "comp=" +compPiece +
                                "xPos=" + compPiece.getCurrentSquare().getXNum() +
                                "yPos"+ compPiece.getCurrentSquare().getYNum() +
                                "}");
                    }

                }else {
                    System.out.println("I AM HERE1");
                }
                System.out.println("?????");
                try {
                    currPiece.getCurrentSquare().setDisPiece(true);
                    currPiece = null;
                } catch (Exception ex) {
                }
            }
        } else {
            System.out.println("Something wrong");
        }
        int timeSpend = timeHelper.getTime();
        LOGGER.info("Time: " + timeSpend + " ns");
        repaint();
    }

    public List<Helper> getAllPieces(Square[][] sq) {
        List<Helper> res = new ArrayList<>();
        for(int i = 0; i < sq.length;  i++) {
            for(int j = 0; j < sq.length; j++) {
                if(sq[i][j] != null) {
                    if(sq[i][j].getOccupyingPiece() != null) {
                        res.add(new Helper(sq[i][j].getOccupyingPiece(), i, j));
                    }
                }
            }
        }
        return res;
    }

    /**
     * @param e
     * mouse listener for dragging
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        currX = e.getX() - 24;
        currY = e.getY() - 24;
        repaint();
    }

    /**
     * No need to implement
     */

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {}
}
