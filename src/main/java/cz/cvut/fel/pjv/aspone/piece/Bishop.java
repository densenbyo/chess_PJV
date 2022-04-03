package cz.cvut.fel.pjv.aspone.piece;

import cz.cvut.fel.pjv.aspone.board.Board;
import cz.cvut.fel.pjv.aspone.board.Square;

import java.util.List;

/**
 * The type Bishop.
 *
 * @author Mukan Atazhanov
 * @project atazhmuk
 * @created 01 /04/2022 - 16:13 Bishop class
 */
public class Bishop extends Piece{

    /**
     * Instantiates a new Bishop.
     *
     * @param color the color
     * @param pos   the pos
     * @param img   the img
     */
    public Bishop(int color, Square pos, String img) {
        super(color, pos, img);
    }

    /**
     * @param board1 current board
     * @return all legal bishop's move
     */
    @Override
    public List<Square> getLegalMoves(Board board1) {
        Square[][] board = board1.getBoard();
        int x = this.getCurrentSquare().getXNum();
        int y = this.getCurrentSquare().getYNum();
        return getDiagonalOccupations(board, x, y);
    }

    @Override
    public String toString() {
        return "B";
    }
}
