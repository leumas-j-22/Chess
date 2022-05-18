package chess;
import chess.pieces.*;

/**
 * This class implements a game of Chess.
 * 
 * @author Sam Jones
 * @author Jacob Figueredo
 */
public class Chess {

    /**
     * Checks if the row and column values for a starting square and ending square are out of bounds.
     * 
     * @param startingRow The row the piece is currently in
     * @param startingCol The column the piece is currently in
     * @param finalRow The row the piece is trying to move to
     * @param finalCol The column the piece is trying to move to
     * @return <code>true</code> if any of the values are out of bounds, <code>false</code> otherwise.
     */
    public static boolean out_of_bounds(int startingRow, int startingCol, int finalRow, int finalCol){

        if (startingRow < 0 || startingRow > 7){
            return true;
        }
        if (startingCol < 0 || startingCol > 7){
            return true;
        }
        if (finalRow < 0 || finalRow > 7){
            return true;
        }
        if (finalCol < 0 || finalCol > 7){
            return true;
        }

        return false;
    }


    /**
     * Sets the board at the beginning of the game. 
     * 
     * @param board An 8x8 board to be filled with pieces
     */
    public static void setBoard(Piece[][] board){

        // Pawns
        for (int col = 0; col < 8; col++){
            Piece bPawn = new Pawn(board, 1, col, 'b', 'p');
            Piece wPawn = new Pawn(board, 6, col, 'w', 'p');
            bPawn.addPiece();
            wPawn.addPiece();
        }

        
        // Knights
        Piece bKnight1 = new Knight(board, 0, 1, 'b', 'N');
        Piece bKnight2 = new Knight(board, 0, 6, 'b', 'N');
        Piece wKnight1 = new Knight(board, 7, 1, 'w', 'N');
        Piece wKnight2 = new Knight(board, 7, 6, 'w', 'N');

        bKnight1.addPiece();
        bKnight2.addPiece();
        wKnight1.addPiece();
        wKnight2.addPiece();


        // Bishops
        Piece bBishop1 = new Bishop(board, 0, 2, 'b', 'B');
        Piece bBishop2 = new Bishop(board, 0, 5, 'b', 'B');
        Piece wBishop1 = new Bishop(board, 7, 2, 'w', 'B');
        Piece wBishop2 = new Bishop(board, 7, 5, 'w', 'B');

        bBishop1.addPiece();
        bBishop2.addPiece();
        wBishop1.addPiece();
        wBishop2.addPiece();


        // Rooks
        Piece bRook1 = new Rook(board, 0, 0, 'b', 'R');
        Piece bRook2 = new Rook(board, 0, 7, 'b', 'R');
        Piece wRook1 = new Rook(board, 7, 0, 'w', 'R');
        Piece wRook2 = new Rook(board, 7, 7, 'w', 'R');

        bRook1.addPiece();
        bRook2.addPiece();
        wRook1.addPiece();
        wRook2.addPiece();


        // Queens
        Piece bQueen = new Queen(board, 0, 3, 'b', 'Q');
        Piece wQueen = new Queen(board, 7, 3, 'w', 'Q');

        bQueen.addPiece();
        wQueen.addPiece();


        // Kings
        Piece bKing = new King(board, 0, 4, 'b', 'K');
        Piece wKing = new King(board, 7, 4, 'w', 'K');

        bKing.addPiece();
        wKing.addPiece();
    }


    /**
     * Runs a game of chess.
     * 
     * @param args Command-line input to the program
     */
    public static void main(String[] args){

        Piece[][] board = new Piece[8][8];
        setBoard(board);
        printBoard(board);
        
    }


    /**
     * Prints the current state of the Chess board.
     * 
     * @param board Board to be printed
     */
    public static void printBoard(Piece[][] board){

        System.out.println();

        for (int i = 0; i < board.length; i++) {
            for (int u = 0; u < board[i].length; u++) {
                if (board[i][u] == null) {
                    if (i % 2 == 0 && u % 2 == 1) {
                        System.out.print("## ");
                    } else if (i % 2 == 1 && u % 2 == 0) {
                        System.out.print("## ");
                    } else {
                        System.out.print("   ");
                    }
                } else {
                    System.out.print("" + board[i][u].getTeam() + board[i][u].getType() + " ");
                }
            }
            System.out.print(8-i + "  ");
            System.out.println();
        }

        System.out.println(" a  b  c  d  e  f  g  h");
        System.out.println();
    }
}