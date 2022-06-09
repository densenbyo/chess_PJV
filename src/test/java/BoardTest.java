import cz.cvut.fel.pjv.aspone.board.Board;
import cz.cvut.fel.pjv.aspone.board.Square;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Mukan Atazhanov
 * @project atazhmuk
 * @created 06/04/2022 - 21:27
 * Class to test Board creation
 */

public class BoardTest {

    //find move
    public boolean findMoveInList(Square square, LinkedList<Square> squares) {
        for(Square sq : squares) {
            if(sq.getXNum() ==  square.getXNum() && sq.getYNum() == square.getYNum()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Process testing
     */
    @Test
    public void checkIfBoardInitSuccess() {
        Board board = new Board();
        Square[][] squares = new Square[8][8];
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                int xMod = x % 2;
                int yMod = y % 2;

                if((xMod == 0 && yMod == 0) || (xMod == 1 && yMod == 1)) {
                    squares[x][y] = new Square(1, y, x, true);
                } else {
                    squares[x][y] = new Square(0, y, x, true);
                }
            }
        }
        board.setBoard(squares);
        board.initializePieces();

        Square[][] boxes = board.getBoard();

        //assert
        assertSame("R", boxes[0][0].getOccupyingPiece().toString());
        assertSame("R", boxes[7][7].getOccupyingPiece().toString());
        assertSame("P", boxes[1][1].getOccupyingPiece().toString());
        assertSame("P", boxes[6][1].getOccupyingPiece().toString());
        assertSame("B", boxes[0][2].getOccupyingPiece().toString());
        assertSame("B", boxes[7][5].getOccupyingPiece().toString());
        assertSame("N", boxes[0][1].getOccupyingPiece().toString());
        assertSame("N", boxes[7][6].getOccupyingPiece().toString());
        assertSame("K", boxes[0][4].getOccupyingPiece().toString());
        assertSame("K", boxes[7][4].getOccupyingPiece().toString());
        assertSame("Q", boxes[0][3].getOccupyingPiece().toString());
        assertSame("Q", boxes[7][3].getOccupyingPiece().toString());
    }

    @Test
    public void checkCheck() {
        Board board = new Board();
        Square[][] squares = new Square[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int posX = i % 2;
                int posY = j % 2;

                if((posX == 0 && posY == 0) || (posX == 1 && posY == 1)) {
                    squares[i][j] = new Square(1, j, i, true);
                } else {
                    squares[i][j] = new Square(0, j, i, true);
                }
            }
        }
        board.setBoard(squares);
        board.initializePieces();
        Square[][] testSquare = board.getBoard();

        testSquare[7][1].getOccupyingPiece().move(testSquare[2][3],board);
        LinkedList<Square> moves = (LinkedList<Square>) testSquare[2][3].getOccupyingPiece().getLegalMoves(board);

        assertTrue(findMoveInList(testSquare[0][4],moves));
        assertTrue(board.checkmateDetector.blackInCheck());
    }
}
