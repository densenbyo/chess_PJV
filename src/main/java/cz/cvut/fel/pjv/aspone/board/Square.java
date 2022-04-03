package cz.cvut.fel.pjv.aspone.board;

import cz.cvut.fel.pjv.aspone.piece.Piece;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;

/**
 * The type Square.
 *
 * @author Mukan Atazhanov
 * @project atazhmuk
 * @created 01 /04/2022 - 16:07 Square/Cell class
 */
public class Square extends JComponent {
    @Getter
    private Piece occupyingPiece;
    @Setter
    private boolean disPiece;

    @Getter
    private final Board board;
    @Getter
    private final int color;
    @Getter
    private final int xNum;
    @Getter
    private final int yNum;

    /**
     * Instantiates a new Square.
     *
     * @param board the board
     * @param color the color
     * @param xNum  the x num
     * @param yNum  the y num
     */
    public Square(Board board, int color, int xNum, int yNum) {
        this.board = board;
        this.color = color;
        this.disPiece = true;
        this.xNum = xNum;
        this.yNum = yNum;
        this.setBorder(BorderFactory.createEmptyBorder());
    }

    /**
     * Is occupied boolean.
     *
     * @return the boolean
     */
    public boolean isOccupied() {
        return (this.occupyingPiece != null);
    }

    /**
     * Put.
     *
     * @param piece put piece
     */
    public void put(Piece piece) {
        this.occupyingPiece = piece;
        this.setDisPiece(true);
        piece.setCurrentSquare(this);
    }

    /**
     * remove piece
     */
    public void removePiece() {
        this.occupyingPiece = null;
    }

    /**
     * Capture.
     *
     * @param piece capture piece
     */
    public void capture(Piece piece) {
        Piece occupyingPiece = getOccupyingPiece();
        if (occupyingPiece.getColor() == 0) board.bPieces.remove(occupyingPiece);
        if (occupyingPiece.getColor() == 1) board.wPieces.remove(occupyingPiece);
        this.occupyingPiece = piece;
    }

    /**
     * @param g
     * prints square/cell
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.color == 1) {
            g.setColor(new Color(158,158, 158));
        } else {
            g.setColor(new Color(87, 85, 62));
        }
        g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());

        if(occupyingPiece != null && disPiece) {
            occupyingPiece.draw(g);
        }
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + xNum;
        result = prime * result + yNum;
        return result;
    }
}
