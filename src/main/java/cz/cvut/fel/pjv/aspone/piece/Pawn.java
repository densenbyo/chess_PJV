package cz.cvut.fel.pjv.aspone.piece;

import cz.cvut.fel.pjv.aspone.board.Board;
import cz.cvut.fel.pjv.aspone.board.Square;

import java.util.LinkedList;
import java.util.List;

/**
 * The type Pawn.
 *
 * @author Mukan Atazhanov
 * @project atazhmuk
 * @created 01 /04/2022 - 16:13 Pawn class
 */
public class Pawn extends Piece{

    /**
     * Instantiates a new Pawn.
     *
     * @param color the color
     * @param pos   the pos
     * @param img   the img
     */
    public Pawn(int color, Square pos, String img) {
        super(color, pos, img);
    }

    /**
     * @param board1 current board
     * @return all legal Pawn's move
     */
    @Override
    public List<Square> getLegalMoves(Board board1) {
        LinkedList<Square> legalMoves = new LinkedList<>();
        Square[][] board = board1.getBoard();

        int xPos = this.getCurrentSquare().getXNum();
        int yPos = this.getCurrentSquare().getYNum();
        int color = this.getColor();

        if (color == 0) {
            if (!isWasMoved()) {
                if (!board[yPos+2][xPos].isOccupied() && !board[yPos+1][xPos].isOccupied()) {
                    legalMoves.add(board[yPos+2][xPos]);
                }
            }
            if (yPos+1 < 8) {
                if (!board[yPos+1][xPos].isOccupied()) {
                    legalMoves.add(board[yPos+1][xPos]);
                }
            }
            if (xPos+1 < 8 && yPos+1 < 8) {
                if (board[yPos+1][xPos+1].isOccupied()) {
                    legalMoves.add(board[yPos+1][xPos+1]);
                }
            }
            if (xPos-1 >= 0 && yPos+1 < 8) {
                if (board[yPos+1][xPos-1].isOccupied()) {
                    legalMoves.add(board[yPos+1][xPos-1]);
                }
            }
            if (isEnPassantPawn()){
                if (getEnPassantPawnPosition().getYNum() == yPos && (getEnPassantPawnPosition().getXNum() == (xPos + 1) ||
                   (getEnPassantPawnPosition().getXNum() == (xPos - 1)))){
                    if (getEnPassantPawnPosition().getXNum() == (xPos+1) && !board[yPos+1][xPos+1].isOccupied()){
                        legalMoves.add(board[yPos+1][xPos+1]);
                    }
                    else if(getEnPassantPawnPosition().getXNum() == (xPos-1) && !board[yPos+1][xPos-1].isOccupied()){
                        legalMoves.add(board[yPos+1][xPos-1]);
                    }
                }
            }
        }

        if (color == 1) {
            if (!isWasMoved()) {
                if (!board[yPos-2][xPos].isOccupied() && !board[yPos-1][xPos].isOccupied()) {
                    legalMoves.add(board[yPos-2][xPos]);
                }
            }
            if (yPos-1 >= 0) {
                if (!board[yPos-1][xPos].isOccupied()) {
                    legalMoves.add(board[yPos-1][xPos]);
                }
            }
            if (xPos+1 < 8 && yPos-1 >= 0) {
                if (board[yPos-1][xPos+1].isOccupied()) {
                    legalMoves.add(board[yPos-1][xPos+1]);
                }
            }
            if (xPos-1 >= 0 && yPos-1 >= 0) {
                if (board[yPos-1][xPos-1].isOccupied()) {
                    legalMoves.add(board[yPos-1][xPos-1]);
                }
            }
            if (isEnPassantPawn()){
                if (getEnPassantPawnPosition().getYNum() == yPos && (getEnPassantPawnPosition().getXNum() == (xPos + 1) ||
                   (getEnPassantPawnPosition().getXNum() == (xPos - 1)))){
                    if (getEnPassantPawnPosition().getXNum() == (xPos+1) && !board[yPos-1][xPos+1].isOccupied()){
                        legalMoves.add(board[yPos-1][xPos+1]);
                    }
                    else if(getEnPassantPawnPosition().getXNum() == (xPos-1) && !board[yPos-1][xPos-1].isOccupied()){
                        legalMoves.add(board[yPos-1][xPos-1]);
                    }
                }
            }
        }
        return legalMoves;
    }

    @Override
    public String toString() {
        return "P";
    }
}
