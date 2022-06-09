package cz.cvut.fel.pjv.aspone.utils;

import cz.cvut.fel.pjv.aspone.board.Board;
import cz.cvut.fel.pjv.aspone.board.Square;
import cz.cvut.fel.pjv.aspone.piece.Piece;

import java.io.*;
import java.util.List;

/**
 * @author Mukan Atazhanov
 * @project atazhmuk
 * @created 17/05/2022 - 00:56
 */
public class Reader {

    public Object readFile(File file) {
        try {
            FileInputStream fileIn = new FileInputStream(file.getAbsolutePath());
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            Object obj = objectIn.readObject();

            System.out.println("The Game has been read from the file");
            objectIn.close();
            return obj;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
