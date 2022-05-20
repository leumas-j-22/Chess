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

    static Scanner scanner = new Scanner(System.in);
    private static Piece[][] board = new Piece[8][8];
    private static int moveNumber = 0;
    private static boolean play = true;

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


    private static void resign(BufferedWriter bw){
        try{
            bw.write("resign");
            bw.newLine();

            if (moveNumber % 2 == 0){
                System.out.println("Black wins");
                bw.write("Black wins");
            }
            else {
                System.out.println("White wins");
                bw.write("White wins");
            }

            play = false;
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
            String move;

            Setup.setBoard(board);
            printBoard();
    
            while (play){
    
                move = scanner.nextLine();
                move = move.trim();
    
                if (move.equals("resign")){
                    resign(bw);
                }


                moveNumber++;
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