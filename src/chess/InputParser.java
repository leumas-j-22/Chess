package chess;
import chess.pieces.*;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.IOException;

class InputParser {

    static boolean different_space(ArrayList<int[]> values){
        int[] startingSpace = values.get(0);
        int[] finalSpace = values.get(1);

        if ((startingSpace[0] == finalSpace[0]) && (startingSpace[1] == finalSpace[1])){
            return false;
        }

        return true;
    }


    // Only need the starting space for this method
    static boolean good_piece(int[] values){
        Piece piece = Chess.board[values[0]][values[1]];

        if (piece != null){
            if ( (Chess.moveNumber % 2 == 0 && piece.getTeam() == 'w') || (Chess.moveNumber % 2 == 1 && piece.getTeam() == 'b') ){
                return true;
            }
        }

        return false;
    }


    static ArrayList<int[]> parse_input(String move){
        ArrayList<int[]> result = new ArrayList<>();
        int startingRow, startingCol, finalRow, finalCol;

        // Example of a valid move: a2 a4
        if (move.length() == 5){
            startingRow = move.charAt(1);
            startingCol = move.charAt(0);
            finalRow = move.charAt(4);
            finalCol = move.charAt(3);

            startingRow = 7 - (startingRow - '1');
            startingCol = startingCol - 'a';
            finalRow = 7 - (finalRow - '1');
            finalCol = finalCol - 'a';

            int[] startingSpace = {startingRow, startingCol};
            int[] finalSpace = {finalRow, finalCol};
            result.add(startingSpace);
            result.add(finalSpace);

            if ( Chess.in_bounds(result) && different_space(result) && good_piece(startingSpace) ){
                return result;
            }
        }

        if (result.size() == 0){
            int[] filler = {-1};
            result.add(filler);
        }
        else result.get(0)[0] = -1;

        return result;
    }


    static boolean check_special_input(String move, BufferedWriter bw){
        int index;

        if (move.equals("stop")){
            stop(bw);
            return true;
        }
        if (move.equals("help")){
            Setup.help();
            return true;
        }
        if (move.length() > 5){

            if (move.equals("resign")){
                resign(bw);
                return true;
            }
            if ((index = has_two_spaces(move)) != -1){

                String thirdWord = move.substring(index, move.length());

                // Need to check that a valid move is entered along with draw?
                if (thirdWord.equals("draw?")){
                    draw(move, bw);
                    return true;
                }
            }
        }
        
        return false;
    }


    private static void resign(BufferedWriter bw){
        try{
            bw.write("resign");
            bw.newLine();

            if (Chess.moveNumber % 2 == 0){
                System.out.println("Black wins");
                bw.write("Black wins");
            }
            else {
                System.out.println("White wins");
                bw.write("White wins");
            }

            bw.newLine();
            Chess.play = false;
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }


    private static void draw(String move, BufferedWriter bw){
        try {
            System.out.println("draw");
            bw.write(move);
            bw.newLine();
            bw.write("draw");
            bw.newLine();
            Chess.play = false;
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }


    private static void stop(BufferedWriter bw){
        try {
            bw.write("stop");
            bw.newLine();
            Chess.play = false;
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }


    // Returns the index of the first character after the second space if the String has exactly two
    // spaces. Otherwise, returns -1.
    private static int has_two_spaces(String str){
        int count = 0;
        int index = 0;

        for (int i = 0; i < str.length(); i++){
            if (str.charAt(i) == ' '){
                count++;

                if (count == 2){
                    index = i;
                }
            }
        }

        if (count == 2) return index+1;
        return -1;
    }
}