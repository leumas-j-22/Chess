package chess;
import chess.pieces.*;
import java.util.Scanner;
import java.util.ArrayList;
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

    static final int NUM_ROWS = 8;
    static final int NUM_COLS = 8;
    static Scanner scanner = new Scanner(System.in);
    static Piece[][] board = new Piece[8][8];
    static int moveNumber = 0;
    static boolean play = true;
    static ArrayList<Piece> whiteAlive = new ArrayList<>();
    static ArrayList<Piece> blackAlive = new ArrayList<>();
    static ArrayList<Piece> whiteKilled = new ArrayList<>();
    static ArrayList<Piece> blackKilled = new ArrayList<>();


    // For an entered move by the user - need to check if both starting space and ending space are in bounds
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


    // Checking if a move is in bounds when you know the piece is starting from a space that is in bounds
    public static boolean in_bounds(int finalRow, int finalCol){
        if ( (finalRow >= 0 && finalRow <= 7) && (finalCol >= 0 && finalCol <= 7)) return true;

        return false;
    }


    /**
     * Prints the current state of the Chess board. This is done in accordance with the project description.
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


    // legalMove() method in each piece class checks if there is a piece on the same team in the space
    // the piece is moving to - don't need to check that here
    static boolean enemy_piece(int finalRow, int finalCol){
        if (board[finalRow][finalCol] == null) return false;
        return true;
    }


    static void kill_piece(Piece attacker, Piece captured, BufferedWriter bw){
        try {
            if (captured.getTeam() == 'w'){
                whiteKilled.add(captured);
                whiteAlive.remove(captured);
            }
            else {
                blackKilled.add(captured);
                blackAlive.remove(captured);
            }

            bw.write("   " + attacker.getTeam() + attacker.getType() + " captures " + captured.getTeam() +
                        captured.getType() + " at space " + Conversion.retrieve_col(captured.getCol()) +
                        Conversion.retrieve_row(captured.getRow()));
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }


    static void check_print(BufferedWriter bw){
        try {
            bw.write("Check");
            if (moveNumber % 2 == 1) bw.write("   White King is in check!");
            else bw.write("   Black King is in check!");
            bw.newLine();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }


    /**
     * Runs a game of chess. White starts with the first move.
     * 
     * @param args Command-line input to the program
     */
    public static void main(String[] args){

        // TODO - add in case if there is no existing game that was paused
        boolean continuation = Setup.init();
        
        try {
            FileWriter fw;

            // Continuing with a paused game - appending to the list of moves
            if (continuation) fw = new FileWriter("data/move-list.txt", true);
            // Starting a new game - truncating any existing contents in the list of moves
            else fw  = new FileWriter("data/move-list.txt", false);

            BufferedWriter bw = new BufferedWriter(fw);
            Piece movingPiece, enemyPiece;
            String move;
            ArrayList<int[]> move_as_al = new ArrayList<>();
            int startingRow, startingCol, finalRow, finalCol;
            boolean validMove = false;
            boolean king_in_check = false;

            Setup.setBoard(board);
            King blackKing = (King) board[0][4];
            King whiteKing = (King) board[7][4];
            printBoard();
            System.out.println("Welcome to Chess, enter the first move! Type 'help' at any time for" +
                                " additional information on gameplay.\n");
    
            while (play){
    
                move = scanner.nextLine();
                move = move.trim();
    
                validMove = InputParser.check_special_input(move, bw);

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
                            bw.write(move);

                            if (enemy_piece(finalRow, finalCol)){
                                enemyPiece = board[finalRow][finalCol];
                                kill_piece(movingPiece, enemyPiece, bw);
                            }

                            bw.newLine();
                            movingPiece.setRow(finalRow);
                            movingPiece.setCol(finalCol);
                            board[finalRow][finalCol] = movingPiece;
                            board[startingRow][startingCol] = null;
                            printBoard();

                            // Black just moved - check if White is in check
                            if (moveNumber % 2 == 1){
                                if (king_in_check = whiteKing.check(blackAlive, whiteKing.getRow(), whiteKing.getCol())){
                                    System.out.println("White King is in check!");
                                    check_print(bw);
                                }
                            }
                            // White just moved - check if Black is in check
                            else {
                                if (king_in_check = blackKing.check(whiteAlive, blackKing.getRow(), blackKing.getCol())){
                                    System.out.println("Black King is in check!");
                                    check_print(bw);
                                }
                            }
                        }
                    }
                }

                if (validMove){
                    if ( !(move.equals("stop") || move.equals("help")) ) moveNumber++;
                }
                else System.out.println("Illegal move, try again");
            }

            bw.newLine();
            bw.write("Number of moves: " + moveNumber);
            bw.close();
            fw.close();
        }
        catch (IOException e){
            System.out.println("An error occurred\n");
            e.printStackTrace();
        }       
    }
}