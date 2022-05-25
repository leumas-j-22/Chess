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
    static Piece[][] board = new Piece[8][8];
    static int moveNumber = 0;
    static boolean play = true;

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
            Piece movingPiece;
            String move;
            int[] move_as_ia;
            int startingRow, startingCol, finalRow, finalCol;
            boolean validMove = false;

            Setup.setBoard(board);
            printBoard();
    
            while (play){
    
                move = scanner.nextLine();
                move = move.trim();
    
                validMove = InputParser.check_special_input(move, bw);

                if ( !validMove ){
                    move_as_ia = InputParser.parse_input(move);

                    if (move_as_ia[0] != -1){
                        startingRow = move_as_ia[0];
                        startingCol = move_as_ia[1];
                        finalRow = move_as_ia[2];
                        finalCol = move_as_ia[3];
                        movingPiece = board[startingRow][startingCol];
                        validMove = movingPiece.legalMove(finalRow, finalCol);

                        if (validMove){
                            movingPiece.setRow(finalRow);
                            movingPiece.setCol(finalCol);
                            board[finalRow][finalCol] = movingPiece;
                            board[startingRow][startingCol] = null;
                            bw.write(move);
                            bw.newLine();
                            printBoard();
                        }
                    }
                }

                if (validMove){
                    if ( !(move.equals("stop")) ) moveNumber++;
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