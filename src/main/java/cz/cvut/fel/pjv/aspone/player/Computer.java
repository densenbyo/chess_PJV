package cz.cvut.fel.pjv.aspone.player;

import cz.cvut.fel.pjv.aspone.board.Board;
import cz.cvut.fel.pjv.aspone.board.Square;
import cz.cvut.fel.pjv.aspone.piece.Piece;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

/**
 * The type Computer.
 *
 * @author Mukan Atazhanov
 * @project atazhmuk
 * @created 01 /04/2022 - 16:14 Computer class
 */
public class Computer {

    /**
     * The constant LOGGER.
     */
    public static final java.util.logging.Logger LOGGER = Logger.getLogger(Computer.class.getName());
    /**
     * The Rand.
     */
    public Random rand = new Random();

    /**
     * Make move piece.
     *
     * @param pieces list of pieces
     * @param board  board
     * @return moved piece
     */
    public Piece makeMove(LinkedList<Piece> pieces, Board board){
        for (int i = 0; i < 60; i++) {
            try {
                int upperbound = pieces.size();
                int int_random = rand.nextInt(upperbound) + 1;
                Piece currentPiece = pieces.get(int_random);
                List<Square> moves = currentPiece.getLegalMoves(board);
                upperbound = moves.size();
                int_random = rand.nextInt(upperbound);
                Square currentSquare = moves.get(int_random);
                if (currentPiece.move(currentSquare, board)){
                    return currentPiece;
                }
            } catch (Exception e) {
                LOGGER.info("No moves");
            }
        }return null;
    }
}
