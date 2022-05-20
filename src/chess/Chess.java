package chess;
import chess.pieces.*;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 * This class implements a game of Chess.
 * 
 * @author Sam Jones
 * @author Jacob Figueredo
 */
public class Chess {

    private static Scanner scanner = new Scanner(System.in);
    private static Piece[][] board = new Piece[8][8];

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
     */
    private static void setBoard(){

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
     * Prints the current state of the Chess board. This is done in accordance with the project description.
     */
    public static void printBoard(){

        System.out.println();

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++){

                if (board[row][col] == null){
                    if (row % 2 == 0 && col % 2 == 1){
                        System.out.print("## ");
                    }
                    else if (row % 2 == 1 && col % 2 == 0){
                        System.out.print("## ");
                    }
                    else {
                        System.out.print("   ");
                    }
                }
                else {
                    System.out.print("" + board[row][col].getTeam() + board[row][col].getType() + " ");
                }
            }
            System.out.println(8-row);
        }

        System.out.println(" a  b  c  d  e  f  g  h\n");
    }


    private static boolean init(){

        String continuation;
        System.out.print("Continue with the saved game or start a new game? Enter 'y' to continue, 'n' to restart: ");

        while (true){
            continuation = scanner.nextLine();
            continuation = continuation.trim();

            if (continuation.equals("y")) return true;
            else if (continuation.equals("n")) return false;
            else System.out.print("Invalid input. Please enter 'y' to continue where you left off, or 'n' to start a new game: ");
        }
    }


    /**
     * Runs a game of chess. White starts with the first move.
     * 
     * @param args Command-line input to the program
     */
    public static void main(String[] args){

        // TODO - add in case if there is no existing game that was paused
        boolean continuation = init();
        
        try {
            FileWriter fw = new FileWriter("data/move-list.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            int moveNumber = 0;
            boolean play = true;
            String move;

            setBoard();
            printBoard();
    
            while (play){
    
                move = scanner.nextLine();
                move = move.trim();
    
                if (move.equals("resign")){
    
                }
    
            }

            bw.close();
            fw.close();
        }
        catch (IOException e){
            System.out.println("An error occurred\n");
            e.printStackTrace();
        }       
    }
}