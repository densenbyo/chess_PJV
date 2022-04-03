package cz.cvut.fel.pjv.aspone.piece;

import cz.cvut.fel.pjv.aspone.board.Board;
import cz.cvut.fel.pjv.aspone.board.Square;

import java.util.LinkedList;
import java.util.List;

import static cz.cvut.fel.pjv.aspone.utils.CheckmateDetector.blackInCheck;
import static cz.cvut.fel.pjv.aspone.utils.CheckmateDetector.whiteInCheck;

/**
 * The type King.
 *
 * @author Mukan Atazhanov
 * @project atazhmuk
 * @created 01 /04/2022 - 16:13 King class
 */
public class King extends Piece{

    /**
     * Instantiates a new King.
     *
     * @param color the color
     * @param pos   the pos
     * @param img   the img
     */
    public King(int color, Square pos, String img) {
        super(color, pos, img);
    }

    /**
     * @param board1 current board
     * @return all legal king's move
     */
    @Override
    public List<Square> getLegalMoves(Board board1) {
        LinkedList<Square> legalMoves = new LinkedList<>();
        Square[][] board = board1.getBoard();

        int x = this.getCurrentSquare().getXNum();
        int y = this.getCurrentSquare().getYNum();

        for (int i = 1; i > -2; i--) {
            for (int k = 1; k > -2; k--) {
                if(!(i == 0 && k == 0)) {
                    try {
                        if(!board[y + k][x + i].isOccupied() ||
                                board[y + k][x + i].getOccupyingPiece().getColor() != this.getColor()) {
                            legalMoves.add(board[y + k][x + i]);
                        }
                    } catch (ArrayIndexOutOfBoundsException ignored) {
                    }
                }
            }
        }

        if (!blackInCheck){
            if(getColor() == 0 && !isWasMoved() && board [0][7].getOccupyingPiece() != null &&
               board[0][7].getOccupyingPiece().getPieceNumber()==0  && !board[0][7].getOccupyingPiece().isWasMoved()){
                legalMoves.add(board[0][6]);
            }
            if(getColor() == 0 && !isWasMoved() && board [0][0].getOccupyingPiece() != null &&
               board[0][0].getOccupyingPiece().getPieceNumber() == 0  && !board[0][0].getOccupyingPiece().isWasMoved()){
                legalMoves.add(board[0][2]);
            }
        }

        if (!whiteInCheck){
            if(getColor() == 1 && !isWasMoved() && board [7][7].getOccupyingPiece() != null &&
               board[7][7].getOccupyingPiece().getPieceNumber() == 0  && !board[7][7].getOccupyingPiece().isWasMoved()){
                legalMoves.add(board[7][6]);
            }
            if(getColor() == 1 && !isWasMoved() && board [7][0].getOccupyingPiece() != null &&
               board[7][0].getOccupyingPiece().getPieceNumber() == 0  && !board[7][0].getOccupyingPiece().isWasMoved()){
                legalMoves.add(board[7][2]);
            }
        }

        return legalMoves;
    }

    @Override
    public String toString() {
        return "K";
    }
}
