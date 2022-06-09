package cz.cvut.fel.pjv.aspone.piece;

import cz.cvut.fel.pjv.aspone.board.Board;
import cz.cvut.fel.pjv.aspone.board.Square;
import cz.cvut.fel.pjv.aspone.utils.CheckmateDetector;
import lombok.Getter;
import lombok.Setter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * The type Piece.
 *
 * @author Mukan Atazhanov
 * @project atazhmuk
 * @created 01 /04/2022 - 16:05 Piece class
 */
public abstract class Piece implements Serializable {
    /**
     * The constant LOGGER.
     */
    public static final Logger LOGGER = Logger.getLogger(Piece.class.getName());
    public CheckmateDetector checkmateDetector = new CheckmateDetector();

    @Getter
    private final int color;
    @Getter
    @Setter
    private Square currentSquare;
    @Getter
    transient Image img;
    @Getter
    @Setter
    private boolean wasMoved = false;
    @Getter
    private int pieceNumber;

    /**
     * The constant isEnPassantPawn.
     */
    @Getter
    public static boolean isEnPassantPawn = false;
    /**
     * The constant enPassantPawnPosition.
     */
    @Getter
    public static Square enPassantPawnPosition;
    private static int width = 150;
    private static int heigth = 150;

    public Piece(int color, Square initSq){
        this.color = color;
        this.currentSquare = initSq;
    }

    /**
     * Instantiates a new Piece.
     *
     * @param color the color
     * @param pos   the pos
     * @param img   the img
     */
    public Piece(int color, Square pos, String img) {
        this.color = color;
        this.currentSquare = pos;
        try {
            this.img = ImageIO.read(new File(img));
        } catch (IOException e) {
            LOGGER.info("File not found");
        }

        if(img.equals("img/WR.png") ||
           img.equals("img/BR.png")){
            this.pieceNumber = 0;
        }
        if(img.equals("img/WP.png") ||
           img.equals("img/BP.png")){
            this.pieceNumber = 1;
        }
        if(img.equals("img/WK.png") ||
           img.equals("img/BK.png")){
            this.pieceNumber = 2;
        }
    }

    /**
     * Draw piece
     *
     * @param g the g
     */
    public void draw(Graphics g) {
        int x = currentSquare.getX();
        int y = currentSquare.getY();
        g.drawImage(this.img, x, y, null);
    }

    /**
     * Gets legal moves.
     *
     * @param board the board
     * @return legal moves
     */
    public abstract List<Square> getLegalMoves(Board board);

    /**
     * Move boolean.
     *
     * @param destination (where piece is going)
     * @param board1      (curr board)
     * @return boolean if move is success
     */
    public boolean move(Square destination, Board board1) {
        Piece occup = destination.getOccupyingPiece();
        Square[][] board = board1.getBoard();

        //if piece is same color - return false and dont execute move
        if (occup != null) {
            if (occup.getColor() == this.color) {
                return false;
            }
            else {
                destination.capture(this);
            }
        }

        // executing en passant move
        if (isEnPassantPawn){
            int yPos = currentSquare.getYNum();
            int xPos = currentSquare.getXNum();
            int enPassX = enPassantPawnPosition.getXNum();
            int enPassY = enPassantPawnPosition.getYNum();
            if (enPassY == yPos && (enPassX == (xPos + 1) || (enPassX == (xPos - 1)))){
                if (pieceNumber == 1){
                    if (color == 0){
                        if (!destination.isOccupied() && destination.getYNum() != 7){
                            board[enPassY][enPassX].removePiece();
                        }
                    }
                    else {
                        if (!destination.isOccupied() && destination.getYNum() != 0){
                            board[enPassY][enPassX].removePiece();
                        }
                    }
                }
            }
        }

        //resetting en passant pawn
        enPassantPawnPosition = null;
        isEnPassantPawn = false;

        //setting en passant pawn if necessary
        if (pieceNumber == 1 && (currentSquare.getYNum() == destination.getYNum()-2 ||
            currentSquare.getYNum() == destination.getYNum()+2)){
            isEnPassantPawn = true;
            enPassantPawnPosition = destination;
        }

        //executing move and removing captured piece
        currentSquare.removePiece();
        this.currentSquare = destination;
        currentSquare.put(this);

        //queen promotion
        if (color == 0 && currentSquare.getYNum() == 7 && pieceNumber == 1) {
            currentSquare.removePiece();
            currentSquare.put(new Queen(0, currentSquare, "img/BQ.png"));
            wasMoved = true;
            return true;
        }
        else if (color == 1 && currentSquare.getYNum() == 0 && pieceNumber == 1) {
            currentSquare.removePiece();
            currentSquare.put(new Queen(1, currentSquare, "img/WQ.png"));
            wasMoved = true;
            System.out.println("QWEQWEQWE");
            return true;
        }

        //castling move
        else if (color == 0 && currentSquare.getXNum() == 6 && pieceNumber == 2 && !wasMoved){
            board[0][7].removePiece();
            Piece rook = new Rook(0,board[0][5],"img/BR.png");
            board[0][5].put(rook);
            rook.setWasMoved(true);
        }
        else if (color == 0 && currentSquare.getXNum() == 2 && pieceNumber == 2 && !wasMoved){
            board[0][0].removePiece();
            Piece rook = new Rook(0,board[0][3],"img/BR.png");
            board[0][3].put(rook);
            rook.setWasMoved(true);
        }
        else if (color == 1 && currentSquare.getXNum() == 6 && pieceNumber == 2 && !wasMoved){
            board[7][7].removePiece();
            Piece rook = new Rook(1,board[7][5],"img/WR.png");
            board[7][5].put(rook);
            rook.setWasMoved(true);
        }
        else if (color == 1 && currentSquare.getXNum() == 2 && pieceNumber == 2 && !wasMoved){
            board[7][0].removePiece();
            Piece rook = new Rook(1,board[7][3],"img/WR.png");
            board[7][3].put(rook);
            rook.setWasMoved(true);
        }
        return true;
    }

    /**
     * Method for testing
     * @param destination where is going
     * @param board1 current board
     * @return boolean
     */
    public boolean testMove(Square destination, Board board1) {
        Piece occup = destination.getOccupyingPiece();
        Square[][] board = board1.getBoard();

        //if piece is same color - return false and dont execute move
        if (occup != null) {
            if (occup.getColor() == this.color) return false;
            else return true;
        }
        return true;
    }

    /**
     * Get linear occupations int [ ].
     *
     * @param board current board
     * @param x     current xPos
     * @param y     current yPos
     * @return used linear squares
     */
    public int[] getLinearOccupations(Square[][] board, int x, int y) {
        int lastYtop = 0;
        int lastYbot = 7;
        int lastXleft = 0;
        int lastXright = 7;

        for (int i = 0; i < y; i++) {
            if (board[i][x].isOccupied()) {
                if (board[i][x].getOccupyingPiece().getColor() != this.color) {
                    lastYtop = i;
                } else lastYtop = i + 1;
            }
        }
        for (int i = 7; i > y; i--) {
            if (board[i][x].isOccupied()) {
                if (board[i][x].getOccupyingPiece().getColor() != this.color) {
                    lastYbot = i;
                } else lastYbot = i - 1;
            }
        }
        for (int i = 0; i < x; i++) {
            if (board[y][i].isOccupied()) {
                if (board[y][i].getOccupyingPiece().getColor() != this.color) {
                    lastXleft = i;
                } else lastXleft = i + 1;
            }
        }
        for (int i = 7; i > x; i--) {
            if (board[y][i].isOccupied()) {
                if (board[y][i].getOccupyingPiece().getColor() != this.color) {
                    lastXright = i;
                } else lastXright = i - 1;
            }
        }
        return new int[]{lastYtop, lastYbot, lastXleft, lastXright};
    }

    /**
     * Gets diagonal occupations.
     *
     * @param board current board
     * @param x     current xPos
     * @param y     current yPos
     * @return user diagonal squares
     */
    public List<Square> getDiagonalOccupations(Square[][] board, int x, int y) {
        LinkedList<Square> diagOccup = new LinkedList<>();

        int xNW = x - 1;
        int xSW = x - 1;
        int xNE = x + 1;
        int xSE = x + 1;
        int yNW = y - 1;
        int ySW = y + 1;
        int yNE = y - 1;
        int ySE = y + 1;

        while (xNW >= 0 && yNW >= 0) {
            if (board[yNW][xNW].isOccupied()) {
                if (board[yNW][xNW].getOccupyingPiece().getColor() != this.color) {
                    diagOccup.add(board[yNW][xNW]);
                }
                break;
            } else {
                diagOccup.add(board[yNW][xNW]);
                yNW--;
                xNW--;
            }
        }
        while (xSW >= 0 && ySW < 8) {
            if (board[ySW][xSW].isOccupied()) {
                if (board[ySW][xSW].getOccupyingPiece().getColor() != this.color) {
                    diagOccup.add(board[ySW][xSW]);
                }
                break;
            } else {
                diagOccup.add(board[ySW][xSW]);
                ySW++;
                xSW--;
            }
        }
        while (xSE < 8 && ySE < 8) {
            if (board[ySE][xSE].isOccupied()) {
                if (board[ySE][xSE].getOccupyingPiece().getColor() != this.color) {
                    diagOccup.add(board[ySE][xSE]);
                }
                break;
            } else {
                diagOccup.add(board[ySE][xSE]);
                ySE++;
                xSE++;
            }
        }
        while (xNE < 8 && yNE >= 0) {
            if (board[yNE][xNE].isOccupied()) {
                if (board[yNE][xNE].getOccupyingPiece().getColor() != this.color) {
                    diagOccup.add(board[yNE][xNE]);
                }
                break;
            } else {
                diagOccup.add(board[yNE][xNE]);
                yNE--;
                xNE++;
            }
        }
        return diagOccup;
    }
}
