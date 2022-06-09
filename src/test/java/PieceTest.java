import cz.cvut.fel.pjv.aspone.board.Board;
import cz.cvut.fel.pjv.aspone.board.Square;
import cz.cvut.fel.pjv.aspone.piece.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Mukan Atazhanov
 * @project atazhmuk
 * @created 30/04/2022 - 15:49
 * Class to test piece moves
 */
public class PieceTest {

    private Board board;

    //just for creating board :)
    public Square[][] setBoard() {
        Square[][] board = new Square[8][8];
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                int posX = i % 2;
                int posY = j % 2;

                if((posX == 0 && posY == 0) || (posX == 1 && posY == 1)) {
                    board[i][j] = new Square(0, j, i, true);
                } else {
                    board[i][j] = new Square(1, j, i, true);
                }
            }
        }
        return board;
    }

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
     * Mock board
     */
    @BeforeEach
    public void mockSetUp() {
        board = mock(Board.class);
        when(board.getBoard()).thenReturn(setBoard());
    }

    /**
     * Test if bishop with coordinates x = {0} and y = {1} can step on square {3},{4} and cannot step on square {5},{6}
     * @param x0, x1, x2 - posX coordinates
     * @param y0, y1, y2 - posY coordinates
     */
    @ParameterizedTest
    @CsvSource({"0, 0, 1, 1, 2, 0", "1, 0, 3, 2, 6, 1", "0, 4, 3, 7, 5, 5"})
    public void bishopTest(int x0, int y0, int x1, int y1, int x2, int y2) {
        //set bishop pos
        Square square = new Square(0, x0, y0, true);
        Bishop bishop = new Bishop(0, square);
        square.put(bishop);

        //create fields according to posX, posY from CsvSource
        LinkedList<Square> squares = (LinkedList<Square>) bishop.getLegalMoves(board);
        Square goodMove = new Square(0, x1, y1, true);
        Square badMove = new Square(0, x2, y2, true);

        //assert
        assertTrue(findMoveInList(goodMove, squares));
        assertFalse(findMoveInList(badMove, squares));
    }

    /**
     * Test if king with coordinates x = {0} and y = {1} can step on square {2},{3} and cannot step on square {4},{5}
     * @param x0, x1, x2 - posX coordinates
     * @param y0, y1, y2 - posY coordinates
     */
    @ParameterizedTest
    @CsvSource({"0, 0, 1, 1, 2, 2", "0, 4, 1, 5, 2, 6", "4, 4, 5, 5, 6, 6"})
    public void kingTest(int x0, int y0, int x1, int y1, int x2, int y2) {
        //set bishop pos
        Square square = new Square(0, x0, y0, true);
        King king = new King(0, square);
        square.put(king);

        //create fields according to posX, posY from CsvSource
        LinkedList<Square> squares = (LinkedList<Square>) king.getLegalMoves(board);
        Square goodMove = new Square(0, x1, y1, true);
        Square badMove = new Square(0, x2, y2, true);

        //assert
        assertTrue(findMoveInList(goodMove, squares));
        assertFalse(findMoveInList(badMove, squares));
    }

    /**
     * Test if knight with coordinates x = {0} and y = {1} can step on square {2},{3} and cannot step on square {4},{5}
     * @param x0, x1, x2 - posX coordinates
     * @param y0, y1, y2 - posY coordinates
     */
    @ParameterizedTest
    @CsvSource({"0, 0, 2, 1, 2, 2", "4, 4, 6, 5, 6, 6", "0, 4, 2, 5, 2, 6"})
    public void knightTest(int x0, int y0, int x1, int y1, int x2, int y2) {
        //set bishop pos
        Square square = new Square(0, x0, y0, true);
        Knight knight = new Knight(0, square);
        square.put(knight);

        //create fields according to posX, posY from CsvSource
        LinkedList<Square> squares = (LinkedList<Square>) knight.getLegalMoves(board);
        Square goodMove = new Square(0, x1, y1, true);
        Square badMove = new Square(0, x2, y2, true);

        //assert
        assertTrue(findMoveInList(goodMove, squares));
        assertFalse(findMoveInList(badMove, squares));
    }

    /**
     * Test if pawn with color {0} and coordinates x = {1} and y = {2} can step on square {3},{4} and cannot step on square {5},{6}
     * @param x0, x1, x2 - posX coordinates
     * @param y0, y1, y2 - posY coordinates
     */
    @ParameterizedTest
    @CsvSource({"0, 0, 0, 0, 2, 0, 3", "0, 0, 4, 0, 6, 1, 7", "0, 4, 4, 4, 5, 4, 7",
            "1, 7, 7, 7, 5, 7, 4", "1, 7, 4, 7, 2, 7, 1", "1, 4, 4, 4, 2, 4, 1"})
    public void pawnTest(int color, int x0, int y0, int x1, int y1, int x2, int y2) {
        //set bishop pos
        Square square = new Square(0, x0, y0, true);
        Pawn pawn = new Pawn(color, square);
        square.put(pawn);

        //create fields according to posX, posY from CsvSource
        LinkedList<Square> squares = (LinkedList<Square>) pawn.getLegalMoves(board);
        Square goodMove = new Square(0, x1, y1, true);
        Square badMove = new Square(0, x2, y2, true);

        //assert
        assertTrue(findMoveInList(goodMove, squares));
        assertFalse(findMoveInList(badMove, squares));
    }

    /**
     * Test if queen with coordinates x = {0} and y = {1} can step on square {3},{4} and cannot step on square {5},{6}
     * @param x0, x1, x2 - posX coordinates
     * @param y0, y1, y2 - posY coordinates
     */
    @ParameterizedTest
    @CsvSource({"0, 0, 1, 1, 2, 3", "1, 0, 3, 2, 6, 1", "0, 4, 3, 7, 5, 5"})
    public void queenTest(int x0, int y0, int x1, int y1, int x2, int y2) {
        //set bishop pos
        Square square = new Square(0, x0, y0, true);
        Queen queen = new Queen(0, square);
        square.put(queen);

        //create fields according to posX, posY from CsvSource
        LinkedList<Square> squares = (LinkedList<Square>) queen.getLegalMoves(board);
        Square goodMove = new Square(0, x1, y1, true);
        Square badMove = new Square(0, x2, y2, true);

        //assert
        assertTrue(findMoveInList(goodMove, squares));
        assertFalse(findMoveInList(badMove, squares));
    }

    /**
     * Test if rook with coordinates x = {0} and y = {1} can step on square {2},{3} and cannot step on square {4},{5}
     * @param x0, x1, x2 - posX coordinates
     * @param y0, y1, y2 - posY coordinates
     */
    @ParameterizedTest
    @CsvSource({"0, 0, 0, 7, 4, 4","0, 4, 4, 4, 7, 7","4, 4, 4, 6, 0, 0"})
    public void rookTest(int x0, int y0, int x1, int y1, int x2, int y2) {
        //set bishop pos
        Square square = new Square(0, x0, y0, true);
        Rook rook = new Rook(0, square);
        square.put(rook);

        //create fields according to posX, posY from CsvSource
        LinkedList<Square> squares = (LinkedList<Square>) rook.getLegalMoves(board);
        Square goodMove = new Square(0, x1, y1, true);
        Square badMove = new Square(0, x2, y2, true);

        //assert
        assertTrue(findMoveInList(goodMove, squares));
        assertFalse(findMoveInList(badMove, squares));
    }
}
