package chess;

import chess.pieces.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * This class implements a game of Chess.
 * 
 * @author Sam Jones
 * @author Jacob Figueredo
 */
public class Chess {

    // Package-level static variables that can be accessed by other classes in the same package

    /**
     * The number of rows on the chess board.
     */
    static final int NUM_ROWS = 8;

    /**
     * The number of columns on the chess board.
     */
    static final int NUM_COLS = 8;

    /**
     * A Scanner object to read from standard input.
     */
    static Scanner scanner = new Scanner(System.in);

    /**
     * The chess board.
     */
    static Piece[][] board = new Piece[8][8];

    /**
     * The last entered move.
     */
    static String move = null;

    /**
     * To keep track of whether it is White or Black's move. If the move number divided by two yields a
     * remainder of zero, then it is White's move. If the move number divided by two yields a remainder of
     * one, then it is Black's move.
     */
    static int moveNumber = 0;

    /**
     * Variable that indicates whether or not the game is active.
     */
    static boolean play = true;

    /**
     * Variable that indicates whether the entered move is legal or not.
     */
    private static boolean validMove = false;

    /**
     * Variable that determines whether or not to write to the text file of moves.
     */
    static boolean write = true;

    /**
     * Variable that indicates if moves from the previous game will be automated.
     */
    static boolean automate = false;

    /**
     * An ArrayList for the White pieces still in play.
     */
    static ArrayList<Piece> whiteAlive = new ArrayList<>();

    /**
     * An ArrayList for the Black pieces still in play.
     */
    static ArrayList<Piece> blackAlive = new ArrayList<>();

    /**
     * An ArrayList for the White pieces that have been captured.
     */
    static ArrayList<Piece> whiteKilled = new ArrayList<>();

    /**
     * An ArrayList for the Black pieces that have been captured.
     */
    static ArrayList<Piece> blackKilled = new ArrayList<>();


    /**
     * Used to check if entered spaces are in bounds. The chess board is an 8x8 2-D array, with both rows
     * and columns going from indices 0 to 7.
     * <p>
     * This method is useful for determining if the starting and ending spaces entered by a user are in
     * bounds. For example, consider the user entering the move "e2 e4". The first int array in the ArrayList
     * will contain {6, 4} (e2 converted to array indices). And the second int array will contain {4, 4}
     * (e4 converted to array indices).
     * 
     * @param values An ArrayList of int arrays, each int array representing a different space on the board
     * @return {@code true} if the entered move is in bounds, {@code false} otherwise
     */
    public static boolean in_bounds(ArrayList<int[]> values){
        for (int[] ia : values){
            for (int i = 0; i < ia.length; i++){
                if (ia[i] < 0 || ia[i] > 7){
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Checks if an entered move is in bounds when you know the current piece is starting from a legal
     * position. finalRow and finalCol should both be array indices.
     * 
     * @param finalRow The row the piece is moving to
     * @param finalCol The column the piece is moving to
     * @return {@code true} if the entered move is in bounds, {@code false} otherwise
     */
    public static boolean in_bounds(int finalRow, int finalCol){
        if ( (finalRow >= 0 && finalRow <= 7) && (finalCol >= 0 && finalCol <= 7)) return true;

        return false;
    }

    /**
     * Prints the current state of the chess board. This is done in accordance with the project description.
     */
    public static void printBoard(){

        System.out.println();

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++){

                if (board[row][col] == null){
                    if ( (row % 2 == 0 && col % 2 == 1) || (row % 2 == 1 && col % 2 == 0) ){
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

    /**
     * Appropriately writes "Check" to the text file and a message for which King is in check.
     * 
     * @param bw The character buffer to write to
     */
    static void print_check(BufferedWriter bw){
        try {
            bw.write("Check");

            if (moveNumber % 2 == 1){
                bw.write("   White King is in check!");
                System.out.println("White King is in check!");
            }
            else {
                bw.write("   Black King is in check!");
                System.out.println("Black King is in check!");
            }

            bw.newLine();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Runs a game of chess. White starts with the first move.
     * 
     * @param rd Buffer to read moves from - buffer is reading from Standard Input, or a text file with
     * a list of moves from the previous game
     * @param wr Buffer to write expanded gameplay info to
     * @param wr_compact Buffer to write compact gameplay info to
     */
    private static void play(BufferedReader rd, BufferedWriter wr, BufferedWriter wr_compact){
        Piece movingPiece, enemyPiece;
        ArrayList<int[]> move_as_al = new ArrayList<>();
        int startingRow, startingCol, finalRow, finalCol;
        boolean king_in_check = false;

        Setup.setBoard(board);
        King blackKing = (King) board[0][4];
        King whiteKing = (King) board[7][4];
        printBoard();
        System.out.println("Welcome to Chess, enter the first move! Type 'help' at any time for" +
                            " additional information on gameplay.\n");

        try {
            while (play){

                if ( (move = rd.readLine()) != null){
                    move = move.trim();
                    validMove = InputParser.check_special_input(move, wr);

                    if ( !validMove ){
                        move_as_al = InputParser.parse_input(move);

                        if (move_as_al.get(0)[0] != -1){
                            startingRow = move_as_al.get(0)[0];
                            startingCol = move_as_al.get(0)[1];
                            finalRow = move_as_al.get(1)[0];
                            finalCol = move_as_al.get(1)[1];
                            movingPiece = board[startingRow][startingCol];
                            validMove = movingPiece.legalMove(finalRow, finalCol);

                            if (validMove){
                                if (write) wr.write(move);
                                if (write) wr_compact.write(move);

                                // Handles when an enemy piece is present in the space the current piece is moving to
                                if (Gameplay.enemy_piece(finalRow, finalCol)){
                                    enemyPiece = board[finalRow][finalCol];
                                    Gameplay.kill_piece(movingPiece, enemyPiece, wr);
                                }

                                movingPiece.setRow(finalRow);
                                movingPiece.setCol(finalCol);
                                board[finalRow][finalCol] = movingPiece;
                                board[startingRow][startingCol] = null;

                                // Possibility for Pawn promotion
                                if (movingPiece instanceof Pawn && (finalRow == 0 || finalRow == 7)){
                                    Gameplay.handle_promotion(movingPiece, finalRow, finalCol);
                                }

                                if (write) wr.newLine();
                                if (write) wr_compact.newLine();
                                printBoard();

                                // Black just moved - check if White is in check
                                if (moveNumber % 2 == 1) king_in_check = Gameplay.handle_check(whiteKing, wr);
                                // White just moved - check if Black is in check
                                else king_in_check = Gameplay.handle_check(blackKing, wr);
                            }
                        }
                    }

                    if (validMove){
                        if ( !(move.equals("stop") || move.equals("help")) ) moveNumber++;
                    }
                    else System.out.println("Illegal move, try again");
                }
                else {
                    if (automate) rd = Setup.end_automation();
                }
            }

            if (write) wr.newLine();
            if (write) wr.write("Number of moves: " + moveNumber + "\n");
            if (write) wr.newLine();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Handles where to read data (moves) from and where to write it to. Once this has been determined,
     * the play() method is called to begin a game of chess.
     * 
     * @param args Command-line input to the program
     */
    public static void main(String[] args){

        // TODO - add in case if there is no existing game that was paused
        boolean continuation = Setup.init();
        if (!continuation) automate = Setup.automate_input();
        
        try {
            FileReader fr = null;
            FileWriter fw1 = null, fw2 = null;
            BufferedReader br = null;
            BufferedWriter bw1 = null, bw2 = null;
            InputStreamReader isr = null;

            if (automate){
                write = false;
                fr = new FileReader("data/move-list-compact.txt");
                br = new BufferedReader(fr);
                fw1 = new FileWriter("data/move-list.txt", true);
                fw2 = new FileWriter("data/move-list-compact.txt", true);
            }
            else {
                isr = new InputStreamReader(System.in);
                br = new BufferedReader(isr);

                // Continuing with a paused game - appending to the list of moves
                if (continuation){
                    fw1 = new FileWriter("data/move-list.txt", true);
                    fw2 = new FileWriter("data/move-list-compact.txt", true);
                }
                // Starting a new game - truncating any existing contents in the list of moves
                else {
                    fw1 = new FileWriter("data/move-list.txt", false);
                    fw2 = new FileWriter("data/move-list-compact.txt", false);
                }
            }

            bw1 = new BufferedWriter(fw1);
            bw2 = new BufferedWriter(fw2);
            play(br, bw1, bw2);

            br.close();
            bw1.close();
            bw2.close();
            fw1.close();
            fw2.close();
            if (fr != null) fr.close();
            if (isr != null) isr.close();
            scanner.close();
        }
        catch (IOException e){
            System.out.println("An error occurred\n");
            e.printStackTrace();
        }       
    }
}