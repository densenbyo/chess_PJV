import cz.cvut.fel.pjv.aspone.board.Board;
import cz.cvut.fel.pjv.aspone.board.Square;
import cz.cvut.fel.pjv.aspone.piece.Bishop;
import cz.cvut.fel.pjv.aspone.piece.Knight;

import cz.cvut.fel.pjv.aspone.piece.Piece;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Mukan Atazhanov
 * @project atazhmuk
 * @created 06/04/2022 - 21:00
 * Class to test some move cases
 */

public class MoveTest {

    @Test
    public void MoveTest_PieceCanMakeMove(){
        //assert
        Board board = new Board();
        board.initializePieces();
        Square[][] squares = board.getBoard();

        Square square = new Square(0, 0, 0, true);
        Knight knight = new Knight(0, square);

        Square square2 = new Square(0, 2, 1, true);
        Bishop bishop2 = new Bishop(1, square2);
        square2.put(bishop2);

        assertTrue(knight.testMove(square2,board));
    }

    @Test
    public void MoveTest_PieceCannotMakeMove(){
        //assert
        Board board = new Board();
        board.initializePieces();
        Square[][] squares = board.getBoard();

        Square square = new Square(0, 0, 0, true);
        Knight knight = new Knight(0, square);

        Square square2 = new Square(0, 2, 1, true);
        Bishop bishop2 = new Bishop(0, square2);
        square2.put(bishop2);

        assertFalse(knight.testMove(square2,board));
    }

    /**
     * Process testing
     */
    @Test
    public void MoveTest_enPassantMove() {
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
        Square[][] cells = board.getBoard();

        cells[1][1].getOccupyingPiece().move(cells[3][1],board);
        cells[3][1].getOccupyingPiece().setWasMoved(true);

        List<Square> moves = cells[3][1].getOccupyingPiece().getLegalMoves(board);
        assertEquals(1,moves.size());

        cells[6][0].getOccupyingPiece().move(cells[4][0],board);
        cells[3][1].getOccupyingPiece().move(cells[4][1],board);
        cells[6][2].getOccupyingPiece().move(cells[4][2],board);

        assertTrue(Piece.isEnPassantPawn);
        assertSame(Piece.getEnPassantPawnPosition(), cells[4][2]);

        moves = cells[4][1].getOccupyingPiece().getLegalMoves(board);
        assertEquals(2,moves.size());
    }

    @Test
    public void MoveTest_castlingMove() {
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
        Square[][] cells = board.getBoard();

        List<Square> moves = cells[7][4].getOccupyingPiece().getLegalMoves(board);
        assertEquals(2,moves.size());

        cells[7][5].getOccupyingPiece().move(cells[4][5],board);
        cells[7][6].getOccupyingPiece().move(cells[4][6],board);

        moves = cells[7][4].getOccupyingPiece().getLegalMoves(board);
        assertEquals(3,moves.size());
    }

    @Test
    public void MoveTest_pawnPromotion() {
        Board board = new Board();
        Square[][] squares = new Square[8][8];

        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
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
        Square[][] testSquares = board.getBoard();

        testSquares[0][0].removePiece();
        testSquares[6][0].getOccupyingPiece().move(testSquares[0][0], board);

        assertEquals("Q", testSquares[0][0].getOccupyingPiece().toString());
    }

    @Test
    public void MoveTest_checkIfMoveDone() {
        Board board = new Board();
        Square[][] squares = new Square[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int posX = i % 2;
                int poxY = j % 2;

                if((posX == 0 && poxY == 0) || (posX == 1 && poxY == 1)) {
                    squares[i][j] = new Square(1, j, i, true);
                } else {
                    squares[i][j] = new Square(0, j, i, true);
                }
            }
        }
        board.setBoard(squares);
        board.initializePieces();

        Square[][] boxes = board.getBoard();

        boxes[1][1].getOccupyingPiece().move(boxes[2][1],board);
        assertSame("P", boxes[2][1].getOccupyingPiece().toString());
        assertNull(boxes[1][1].getOccupyingPiece());
    }
}
