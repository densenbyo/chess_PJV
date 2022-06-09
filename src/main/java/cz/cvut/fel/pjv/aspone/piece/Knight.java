package cz.cvut.fel.pjv.aspone.piece;

import cz.cvut.fel.pjv.aspone.board.Board;
import cz.cvut.fel.pjv.aspone.board.Square;

import java.util.LinkedList;
import java.util.List;

/**
 * The type Knight.
 *
 * @author Mukan Atazhanov
 * @project atazhmuk
 * @created 01 /04/2022 - 16:13 Knight class
 */
public class Knight extends Piece{

    public Knight(int color, Square initSq) {
        super(color, initSq);
    }

    /**
     * Instantiates a new Knight.
     *
     * @param color the color
     * @param pos   the pos
     * @param img   the img
     */
    public Knight(int color, Square pos, String img) {
        super(color, pos, img);
    }

    /**
     * @param board1 current board
     * @return all legal Knight's move
     */
    @Override
    public List<Square> getLegalMoves(Board board1) {
        LinkedList<Square> legalMoves = new LinkedList<>();
        Square[][] board = board1.getBoard();

        int x = this.getCurrentSquare().getXNum();
        int y = this.getCurrentSquare().getYNum();

        for (int i = 2; i > -3; i--) {
            for (int k = 2; k > -3; k--) {
                if(Math.abs(i) == 2 ^ Math.abs(k) == 2) {
                    if (k != 0 && i != 0) {
                        try {
                            legalMoves.add(board[y + k][x + i]);
                        } catch (ArrayIndexOutOfBoundsException ignored) {
                        }
                    }
                }
            }
        }

        return legalMoves;
    }

    @Override
    public String toString() {
        return "N";
    }
}
