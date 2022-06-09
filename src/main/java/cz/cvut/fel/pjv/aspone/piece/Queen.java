package cz.cvut.fel.pjv.aspone.piece;

import cz.cvut.fel.pjv.aspone.board.Board;
import cz.cvut.fel.pjv.aspone.board.Square;

import java.util.LinkedList;
import java.util.List;

/**
 * The type Queen.
 *
 * @author Mukan Atazhanov
 * @project atazhmuk
 * @created 01 /04/2022 - 16:13 Queen class
 */
public class Queen extends Piece{

    public Queen(int color, Square initSq) {
        super(color, initSq);
    }

    /**
     * Instantiates a new Queen.
     *
     * @param color the color
     * @param pos   the pos
     * @param img   the img
     */
    public Queen(int color, Square pos, String img) {
        super(color, pos, img);
    }

    /**
     * @param board1 current board
     * @return all legal Queen's move
     */
    @Override
    public List<Square> getLegalMoves(Board board1) {
        LinkedList<Square> legalMoves = new LinkedList<>();
        Square[][] board = board1.getBoard();

        int x = this.getCurrentSquare().getXNum();
        int y = this.getCurrentSquare().getYNum();
        int[] occupations = getLinearOccupations(board, x, y);

        for (int i = occupations[0]; i <= occupations[1]; i++) {
            if (i != y) legalMoves.add(board[i][x]);
        }
        for (int i = occupations[2]; i <= occupations[3]; i++) {
            if (i != x) legalMoves.add(board[y][i]);
        }

        List<Square> bMoves = getDiagonalOccupations(board, x, y);
        legalMoves.addAll(bMoves);
        return legalMoves;
    }

    @Override
    public String toString() {
        return "Q";
    }
}
