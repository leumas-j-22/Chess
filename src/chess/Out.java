package chess;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * This class handles output writing and printing during a game. Printed output is sent to Standard
 * Output. There are also two text files that are created where output is written - "move-list.txt"
 * and "move-list-compact.txt". move-list contains more substantial and informative info from the game -
 * such as what pieces captures another piece and at what space during a particular move. On the other
 * hand, move-list-compact solely contains the list of entered moves. This file is used when the user
 * chooses to automate the moves from the previous game.
 * 
 * @author Sam Jones
 */
public class Out {

    /**
     * Prints the current state of the chess board. This is done in accordance with the project description.
     */
    public static void printBoard(){

        System.out.println();

        for (int row = 0; row < Chess.board.length; row++) {
            for (int col = 0; col < Chess.board[row].length; col++){

                if (Chess.board[row][col] == null){
                    if ( (row % 2 == 0 && col % 2 == 1) || (row % 2 == 1 && col % 2 == 0) ){
                        System.out.print("## ");
                    }
                    else {
                        System.out.print("   ");
                    }
                }
                else {
                    System.out.print("" + Chess.board[row][col].getTeam() + Chess.board[row][col].getType() + " ");
                }
            }
            System.out.println(8-row);
        }
        System.out.println(" a  b  c  d  e  f  g  h\n");
    }

    /**
     * Writes "Check" to the "move-list.txt" text file and a message for which King is in check.
     * 
     * @param bw The character buffer to write to
     */
    static void print_check(BufferedWriter bw){
        try {
            bw.write("Check");

            if (Chess.moveNumber % 2 == 1){
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
     * Writes "Checkmate" to both "move-list.txt" and "move-list-compact.txt" and a message for which
     * team wins.
     * 
     * @param wr The character buffer that writes to "move-list.txt"
     * @param wr_compact The character buffer that writes to "move-list-compact.txt"
     */
    static void print_checkmate(BufferedWriter wr, BufferedWriter wr_compact){
        Chess.play = false;

        try {
            wr.write("Checkmate");
            wr.newLine();
            wr_compact.write("Checkmate");
            wr_compact.newLine();

            if (Chess.moveNumber % 2 == 1){
                System.out.println("Checkmate\nBlack wins!");
                wr.write("Black wins!");
                wr_compact.write("Black wins!");
            }
            else {
                System.out.println("Checkmate\nWhite wins!");
                wr.write("White wins!");
                wr_compact.write("White wins!");
            }

            wr.newLine();
            wr_compact.newLine();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    } 
}