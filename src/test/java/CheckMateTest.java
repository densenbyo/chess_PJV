import cz.cvut.fel.pjv.aspone.board.Square;
import cz.cvut.fel.pjv.aspone.piece.Bishop;
import cz.cvut.fel.pjv.aspone.piece.King;
import cz.cvut.fel.pjv.aspone.utils.CheckmateDetector;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Mukan Atazhanov
 * @project atazhmuk
 * @created 30/04/2022 - 17:01
 * Class to test CheckMate
 */
public class CheckMateTest {

    CheckmateDetector checkmateDetector = new CheckmateDetector();

    @Test
    public void pieceCanMove() {
        Square square = new Square(0, 0, 0, true);
        Bishop bishop = new Bishop(0, square);
        square.put(bishop);

        Square square2 = new Square(1, 1, 1, true);
        Bishop bishop2 = new Bishop(1, square);
        square2.put(bishop2);

        assertTrue(checkmateDetector.boolTestMove(bishop, square2));
    }

    @Test
    public void pieceCantMove() {
        Square square = new Square(0, 0, 0, true);
        Bishop bishop = new Bishop(0, square);
        square.put(bishop);

        Square square2 = new Square(0, 1, 1, true);
        Bishop bishop2 = new Bishop(0, square);
        square2.put(bishop2);

        assertFalse(checkmateDetector.boolTestMove(bishop, square2));
    }

    @Test
    public void wPieceCantTakeKing() {
        Square square = new Square(0, 0, 0, true);
        Bishop bishop = new Bishop(0, square);
        square.put(bishop);

        Square square2 = new Square(1, 2, 2, true);
        King king = new King(1, square2);
        square2.put(king);

        assertFalse(checkmateDetector.boolTestMove(bishop,square2));
    }

    @Test
    public void bPieceCantTakeKing() {
        Square square = new Square(0, 0, 0, true);
        Bishop bishop = new Bishop(0, square);
        square.put(bishop);

        Square square2 = new Square(0, 2, 2, true);
        King king = new King(0, square2);
        square2.put(king);

        assertFalse(checkmateDetector.boolTestMove(bishop,square2));
    }
}
