package cz.cvut.fel.pjv.aspone.piece;

import cz.cvut.fel.pjv.aspone.board.Board;
import cz.cvut.fel.pjv.aspone.board.Square;

import java.util.LinkedList;
import java.util.List;

/**
 * The type Rook.
 *
 * @author Mukan Atazhanov
 * @project atazhmuk
 * @created 01 /04/2022 - 16:13 Rook class
 */
public class Rook extends Piece{

    public Rook(int color, Square initSq) {
        super(color, initSq);
    }

    /**
     * Instantiates a new Rook.
     *
     * @param color the color
     * @param pos   the pos
     * @param img   the img
     */
    public Rook(int color, Square pos, String img) {
        super(color, pos, img);
    }

    /**
     * @param board1 current board
     * @return all legal Rook's move
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
        return legalMoves;
    }

    @Override
    public String toString() {
        return "R";
    }
}
