package cz.cvut.fel.pjv.aspone.utils;

import cz.cvut.fel.pjv.aspone.board.Square;
import cz.cvut.fel.pjv.aspone.piece.Piece;

import java.io.*;

import static cz.cvut.fel.pjv.aspone.board.Board.blackName;
import static cz.cvut.fel.pjv.aspone.board.Board.whiteName;

/**
 * The type Writer.
 *
 * @author Mukan Atazhanov
 * @project atazhmuk
 * @created 01 /04/2022 - 16:12 Writer class - writes activity to file with .txt format
 */
public class Writer {

    private int whiteKingMovesCount = 0;
    private int blackKingMovesCount = 0;
    String name;

    /**
     * writs new game statement with names of players
     */
    public void writeNewGame(){
        try {
            FileWriter writer = new FileWriter("moves.txt", true);
            writer.write("\r\n");
            writer.write("----------NEW GAME----------");
            writer.write("\r\n");
            writer.write("White Player: " + whiteName);
            writer.write("\r\n");
            writer.write("Black Player: " + blackName);
            writer.write("\r\n");
            writer.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * if games end write # at the end
     */
    public void writeMate(){
        try {
            FileWriter writer = new FileWriter("moves.txt", true);
            writer.write("#");
            writer.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * writing move in the text file
     *
     * @param square     init square of piece
     * @param piece      the piece
     * @param whiteTurn  the white turn
     * @param movesCount the moves count
     */
    public void writeMove(Square square, Piece piece, boolean whiteTurn, int movesCount){
        int y = square.getYNum();
        int x = square.getXNum();
        String writeX = xToLetter(x);
        int writeY = yInvert(y);
        String pieceChar = piece.toString();
        String message = pieceChar + writeX + writeY + " ";

        //castling moves
        if (pieceChar.equals("K") && whiteKingMovesCount == 0 && writeX.equals("g") && whiteTurn){
            message = "0-0 ";
        }
        else if (pieceChar.equals("K") && blackKingMovesCount == 0 && writeX.equals("g") && !whiteTurn){
            message = "0-0 ";
        }

        else if (pieceChar.equals("K") && whiteKingMovesCount == 0 && writeX.equals("c") && whiteTurn){
            message = "0-0-0 ";
        }

        else if (pieceChar.equals("K") && blackKingMovesCount == 0 && writeX.equals("c") && !whiteTurn){
            message = "0-0-0 ";
        }

        if (pieceChar.equals("K")){
            if (whiteTurn){
                whiteKingMovesCount++;
            }else blackKingMovesCount++;
        }

        try {
            FileWriter writer = new FileWriter("moves.txt", true);
            if (whiteTurn){
                writer.write(movesCount + " " + message);
            }else {
                writer.write(message);
                writer.write("\r\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * X to letter string.
     *
     * @param x coordinate
     * @return letter representation of column
     */
    public String xToLetter(int x){
        return switch (x) {
            case 0 -> "a";
            case 1 -> "b";
            case 2 -> "c";
            case 3 -> "d";
            case 4 -> "e";
            case 5 -> "f";
            case 6 -> "g";
            default -> "h";
        };
    }

    /**
     * Y invert int.
     *
     * @param y coordinate
     * @return inverted number from 0-8 to 8-0
     */
    public int yInvert(int y){
        return switch (y) {
            case 0 -> 8;
            case 1 -> 7;
            case 2 -> 6;
            case 3 -> 5;
            case 4 -> 4;
            case 5 -> 3;
            case 6 -> 2;
            default -> 1;
        };
    }

    public void writeFile(Object obj) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter file name!");
        name = br.readLine();
        try (FileOutputStream fos = new FileOutputStream(name + ".doc");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            // write object to file
            oos.writeObject(obj);
            System.out.println("Game is saved!");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
