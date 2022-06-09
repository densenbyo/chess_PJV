package cz.cvut.fel.pjv.aspone.utils;

import cz.cvut.fel.pjv.aspone.piece.Piece;
import lombok.Getter;
import lombok.Setter;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.sql.Array;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Mukan Atazhanov
 * @project atazhmuk
 * @created 18/05/2022 - 00:41
 */
@Getter
@Setter
public class Helper implements Serializable {
    Piece piece;
    String pieceSymbol;
    int color;
    int x;
    int y;

    public Helper(Piece piece, int x, int y) {
        this.piece = piece;
        this.x = x;
        this.y = y;
    }

    public Helper(int color, String pieceSymbol, int x, int y) {
        this.color = color;
        this.pieceSymbol = pieceSymbol;
        this.x = x;
        this.y = y;
    }
}
