package cz.cvut.fel.pjv.aspone.utils;

import cz.cvut.fel.pjv.aspone.board.Board;
import cz.cvut.fel.pjv.aspone.board.Square;
import cz.cvut.fel.pjv.aspone.piece.Bishop;
import cz.cvut.fel.pjv.aspone.piece.King;
import cz.cvut.fel.pjv.aspone.piece.Piece;
import cz.cvut.fel.pjv.aspone.piece.Queen;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.logging.Logger;

/**
 * The type Checkmate detector.
 *
 * @author Mukan Atazhanov
 * @project atazhmuk
 * @created 01 /04/2022 - 16:12 Checkmate detector class
 */
public class CheckmateDetector implements Serializable {

    /**
     * The constant LOGGER.
     */
    public static final Logger LOGGER = Logger.getLogger(CheckmateDetector.class.getName());

    private Board board;
    private LinkedList<Piece> wPieces;
    private LinkedList<Piece> bPieces;
    private LinkedList<Square> movableSquares;
    private LinkedList<Square> squares;
    private King bKing;
    private King wKing;
    private HashMap<Square, List<Piece>> wMoves;
    private HashMap<Square, List<Piece>> bMoves;
    List<Piece> pieces = new ArrayList<>();
    Piece piece;
    List<Square> mvs;

    /**
     * The constant whiteInCheck.
     */
    public static boolean whiteInCheck = false;
    /**
     * The constant blackInCheck.
     */
    public static boolean blackInCheck = false;

    public CheckmateDetector() {
        this.board = null;
        this.wPieces = null;
        this.bPieces = null;
        this.bKing = null;
        this.wKing = null;
    }

    /**
     * Instantiates a new Checkmate detector.
     *
     * @param board   the board
     * @param wPieces the w pieces
     * @param bPieces the b pieces
     * @param wKing   the w king
     * @param bKing   the b king
     */
    public CheckmateDetector(Board board, LinkedList<Piece> wPieces,
                             LinkedList<Piece> bPieces, King wKing, King bKing) {
        this.board = board;
        this.wPieces = wPieces;
        this.bPieces = bPieces;
        this.bKing = bKing;
        this.wKing = wKing;

        squares = new LinkedList<>();
        movableSquares = new LinkedList<>();
        wMoves = new HashMap<>();
        bMoves = new HashMap<>();

        Square[][] brd = board.getBoard();

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                squares.add(brd[y][x]);
                wMoves.put(brd[y][x], new LinkedList<>());
                bMoves.put(brd[y][x], new LinkedList<>());
            }
        }
        update();
    }

    /**
     * updates board after any change
     */
    public void update() {
        Iterator<Piece> wIter = wPieces.iterator();
        Iterator<Piece> bIter = bPieces.iterator();

        for (List<Piece> pieces : wMoves.values()) {
            pieces.clear();
        }
        for (List<Piece> pieces : bMoves.values()) {
            pieces.clear();
        }
        movableSquares.clear();

        while (wIter.hasNext()) {
            piece = wIter.next();

            if (!piece.getClass().equals(King.class)) {
                if (piece.getCurrentSquare() == null) {
                    wIter.remove();
                    continue;
                }
                mvs = piece.getLegalMoves(board);
                for (Square mv : mvs) {
                    pieces = wMoves.get(mv);
                    pieces.add(piece);
                }
            }
        }

        while (bIter.hasNext()) {
            piece = bIter.next();

            if (!piece.getClass().equals(King.class)) {
                if (piece.getCurrentSquare() == null) {
                    wIter.remove();
                    continue;
                }
                mvs = piece.getLegalMoves(board);
                for (Square mv : mvs) {
                    List<Piece> pieces = bMoves.get(mv);
                    pieces.add(piece);
                }
            }
        }
    }

    /**
     * check if BKING is in Check
     *
     * @return true if in check
     */
    public boolean blackInCheck() {
        update();
        Square sq = bKing.getCurrentSquare();
        if (wMoves.get(sq).isEmpty()) {
            movableSquares.addAll(squares);
            blackInCheck = false;
            return false;
        } else {
            blackInCheck = true;
            LOGGER.info("BLACK CHECK");
            return true;
        }
    }

    /**
     * check if WKNIG is in Check
     *
     * @return true if in check
     */
    public boolean whiteInCheck() {
        update();
        Square sq = wKing.getCurrentSquare();
        List<Square> opponentsMoves;

        for (Piece piece : bPieces) {
            opponentsMoves = piece.getLegalMoves(board);
            if (opponentsMoves.contains(sq)){
                whiteInCheck = true;
                LOGGER.info("WHITE CHECK");
                return true;
            }
        }
        whiteInCheck = false;
        return false;
    }

    /**
     * checks if there is stalemate situation on board
     *
     * @param b true = white and false = black
     * @return true if in stalemate
     */
    public boolean checkStaleMate(boolean b){
        if(b){
            return wMoves.isEmpty();
        }else{
            return bMoves.isEmpty();
        }
    }

    /**
     * Black check mated boolean.
     *
     * @return true if black in check mate
     */
    public boolean blackCheckMated() {
        //boolean checkmate = true;
        if (!this.blackInCheck()) {
            return false;
        }
        if (canEvade(wPieces, bKing)) {
            return false;
        }
        List<Piece> threats = wMoves.get(bKing.getCurrentSquare());
        if (canCapture(bMoves, threats, bKing)) {
            return false;
        }
        if (canBlock(threats, bMoves, bKing)) {
            return false;
        }
        return true;
    }

    /**
     * White check mated boolean.
     *
     * @return true if white in check mate
     */
    public boolean whiteCheckMated() {
        if (!this.whiteInCheck()) {
            return false;
        }
        if (canEvade(bPieces, wKing)) {
            return false;
        }
        List<Piece> threats = bMoves.get(wKing.getCurrentSquare());
        if (canCapture(wMoves, threats, wKing)) {
            return false;
        }
        if (canBlock(threats, wMoves, wKing)) {
            return false;
        }
        return true;
    }

    /**
     * @param pieces list of pieces
     * @param tKing king
     * @return true if king can escape from check
     */

    private boolean canEvade(LinkedList<Piece> pieces, King tKing) {
        int whitePiecesNum = pieces.size();
        List<Square> kingsMoves = tKing.getLegalMoves(board);
        List<Square> opponentMoves;

        for (Square square : kingsMoves) {
            int helper = 1;
            if (square.getOccupyingPiece()!=null){
                continue;
            }

            for (Piece piece : pieces) {
                opponentMoves = piece.getLegalMoves(board);
                if (opponentMoves.contains(square)){
                    break;
                }
                helper += helper;
                if (helper == whitePiecesNum){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param poss map of all pieces position
     * @param threats list of threats
     * @param king king
     * @return true if attacking piece can be captured
     */

    private boolean canCapture(Map<Square,List<Piece>> poss,
                               List<Piece> threats, King king) {

        boolean capture = false;
        if (threats.size() == 1) {
            Square sq = threats.get(0).getCurrentSquare();

            if (king.getLegalMoves(board).contains(sq)) {
                movableSquares.add(sq);
                if (testMove(king, sq)) {
                    capture = true;
                }
            }

            List<Piece> caps = poss.get(sq);
            ConcurrentLinkedDeque<Piece> captures = new ConcurrentLinkedDeque<>(caps);

            if (!captures.isEmpty()) {
                movableSquares.add(sq);
                for (Piece p : captures) {
                    if (testMove(p, sq)) {
                        capture = true;
                    }
                }
            }
        }
        return capture;
    }

    /**
     * @param threats all threats
     * @param blockMoves map of block moves
     * @param king king
     * @return true if possible to block check
     */

    private boolean canBlock(List<Piece> threats,
                             Map <Square,List<Piece>> blockMoves, King king) {
        boolean blockAble = false;

        if (threats.size() == 1) {
            Square ts = threats.get(0).getCurrentSquare();
            Square ks = king.getCurrentSquare();
            Square[][] brdArray = board.getBoard();

            if (ks.getXNum() == ts.getXNum()) {
                int max = Math.max(ks.getYNum(), ts.getYNum());
                int min = Math.min(ks.getYNum(), ts.getYNum());

                for (int i = min + 1; i < max; i++) {
                    List<Piece> blocks =
                            blockMoves.get(brdArray[i][ks.getXNum()]);
                    ConcurrentLinkedDeque<Piece> blockers =
                            new ConcurrentLinkedDeque<>(blocks);
                    if (!blockers.isEmpty()) {
                        movableSquares.add(brdArray[i][ks.getXNum()]);

                        for (Piece p : blockers) {
                            if (testMove(p,brdArray[i][ks.getXNum()])) {
                                blockAble = true;
                            }
                        }
                    }
                }
            }

            if (ks.getYNum() == ts.getYNum()) {
                int max = Math.max(ks.getXNum(), ts.getXNum());
                int min = Math.min(ks.getXNum(), ts.getXNum());

                for (int i = min + 1; i < max; i++) {
                    List<Piece> blocks =
                            blockMoves.get(brdArray[ks.getYNum()][i]);
                    ConcurrentLinkedDeque<Piece> blockers =
                            new ConcurrentLinkedDeque<>(blocks);
                    if (!blockers.isEmpty()) {
                        movableSquares.add(brdArray[ks.getYNum()][i]);
                        for (Piece p : blockers) {
                            if (testMove(p, brdArray[ks.getYNum()][i])) {
                                blockAble = true;
                            }
                        }
                    }
                }
            }
            Class<? extends Piece> tC = threats.get(0).getClass();

            if (tC.equals(Queen.class) || tC.equals(Bishop.class)) {
                int kX = ks.getXNum();
                int kY = ks.getYNum();
                int tX = ts.getXNum();
                int tY = ts.getYNum();

                if (kX > tX && kY > tY) {
                    for (int i = tX + 1; i < kX; i++) {
                        tY++;
                        List<Piece> blocks =
                                blockMoves.get(brdArray[tY][i]);
                        ConcurrentLinkedDeque<Piece> blockers =
                                new ConcurrentLinkedDeque<>(blocks);
                        if (!blockers.isEmpty()) {
                            movableSquares.add(brdArray[tY][i]);
                            for (Piece p : blockers) {
                                if (testMove(p, brdArray[tY][i])) {
                                    blockAble = true;
                                }
                            }
                        }
                    }
                }

                if (kX > tX && tY > kY) {
                    for (int i = tX + 1; i < kX; i++) {
                        tY--;
                        List<Piece> blocks =
                                blockMoves.get(brdArray[tY][i]);
                        ConcurrentLinkedDeque<Piece> blockers =
                                new ConcurrentLinkedDeque<>(blocks);
                        if (!blockers.isEmpty()) {
                            movableSquares.add(brdArray[tY][i]);
                            for (Piece p : blockers) {
                                if (testMove(p, brdArray[tY][i])) {
                                    blockAble = true;
                                }
                            }
                        }
                    }
                }

                if (tX > kX && kY > tY) {
                    for (int i = tX - 1; i > kX; i--) {
                        tY++;
                        List<Piece> blocks =
                                blockMoves.get(brdArray[tY][i]);
                        ConcurrentLinkedDeque<Piece> blockers =
                                new ConcurrentLinkedDeque<>(blocks);
                        if (!blockers.isEmpty()) {
                            movableSquares.add(brdArray[tY][i]);
                            for (Piece p : blockers) {
                                if (testMove(p, brdArray[tY][i])) {
                                    blockAble = true;
                                }
                            }
                        }
                    }
                }

                if (tX > kX && tY > kY) {
                    for (int i = tX - 1; i > kX; i--) {
                        tY--;
                        List<Piece> blocks =
                                blockMoves.get(brdArray[tY][i]);
                        ConcurrentLinkedDeque<Piece> blockers =
                                new ConcurrentLinkedDeque<>(blocks);
                        if (!blockers.isEmpty()) {
                            movableSquares.add(brdArray[tY][i]);
                            for (Piece p : blockers) {
                                if (testMove(p, brdArray[tY][i])) {
                                    blockAble = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return blockAble;
    }

    /**
     * Gets allowable squares.
     *
     * @return list of allowed squares
     */
    public List<Square> getAllowableSquares() {
        movableSquares.clear();
        if (whiteInCheck()) {
            whiteCheckMated();
        } else if (blackInCheck()) {
            blackCheckMated();
        } else {
            return movableSquares;
        }
        return movableSquares;
    }

    /**
     * Test move boolean.
     *
     * @param piece  piece
     * @param square square
     * @return true if move is valid
     */
    public boolean testMove(Piece piece, Square square) {
        Piece c = square.getOccupyingPiece();
        if (c != null){
            if (c.getPieceNumber() == 2){
                System.out.println("test0");
                return false;
            }
            if(piece.getColor() == 1 && c.getColor() == 1) {
                System.out.println("SALAM");
                return false;
            } else if(piece.getColor() == 0 && c.getColor() == 0) {
                System.out.println("POPOLAM");
                return false;
            }
        }

        if (piece.getColor() == 1 && blackInCheck()) {
            System.out.println("test1");
            return false;
        }
        else if (piece.getColor() == 0 && whiteInCheck()) {
            System.out.println("test2");
            return false;
        }

        Square init = piece.getCurrentSquare();

        piece.move(square, board);
        update();

        if (piece.getColor() == 0 && blackInCheck()) {
            System.out.println("test3");
            piece.move(init, board);
            if (c != null) {
                square.put(c);
            }
            update();
            return false;
        } else if (piece.getColor() == 1 && whiteInCheck()) {
            System.out.println("test4");
            piece.move(init, board);
            if (c != null) {
                square.put(c);
            }
            update();
            return false;
        } else {
            System.out.println("TEST MOVE IS GOOD");
            update();
            movableSquares.addAll(squares);
            return true;
        }
    }

    public boolean boolTestMove(Piece piece, Square square) {
        Piece piece1 = square.getOccupyingPiece();
        if(piece1 != null) {
            if(piece1.toString().equals("K")){
                return false;
            }
            if(piece1.getColor() == piece.getColor()) {
                return false;
            }
        }
        return true;
    }
}
