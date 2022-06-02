package chess;

import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 * This class parses input from the user. Input is broken up into two categories - regular input and special
 * input. Regular input is any 5-character, regular move, such as "e2 e4" (see Pawn promotion exception).
 * Special input involves the keywords "stop", "help", "resign", and "draw?".
 * <p>
 * One special input that is grouped with regular input is Pawn promotion. For example, "a7 a8 Q". This is
 * grouped with regular input because the user does not have to enter the 'Q' and can instead just enter
 * "a7 a8".
 * <p>
 * Appropriate action and returns are made for all cases.
 * 
 * @author Sam Jones
 */
class InputParser {

    /**
     * Checks if the starting space is different from the ending space for a user-entered move. A chess
     * piece must move to a different space than the one it is currently occupying.
     * 
     * @param values An ArrayList of integer arrays - the first array representing the starting space and
     * the second representing the ending space
     * @return {@code true} if the starting space differs from the ending space, {@code false} otherwise
     */
    static boolean different_space(ArrayList<int[]> values){
        int[] startingSpace = values.get(0);
        int[] finalSpace = values.get(1);

        if ((startingSpace[0] == finalSpace[0]) && (startingSpace[1] == finalSpace[1])){
            return false;
        }

        return true;
    }

    /**
     * Verifies that there is a piece in the spot the user wants to move from and also that it is that
     * piece's turn to move.
     * 
     * @param values An integer array representing the starting space
     * @return {@code true} if there is a piece in the space and it is its turn to move, {@code false}
     * otherwise
     */
    static boolean good_piece(int[] values){
        Piece piece = Chess.board[values[0]][values[1]];

        if (piece != null){
            if ( (Chess.moveNumber % 2 == 0 && piece.getTeam() == 'w') || (Chess.moveNumber % 2 == 1 && piece.getTeam() == 'b') ){
                return true;
            }
        }

        return false;
    }


    // TODO - more testing with this (things like potential errors)
    /**
     * Determines if the input entered by the user matches the correct form for Pawn promotion input.
     * 
     * @param move The move the user enters
     * @return {@code true} if it is good pawn promotion input, {@code false} otherwise
     */
    static boolean good_promotion_input(String move){
        if (move.length() == 7 && has_two_spaces(move) != -1){
            if (move.charAt(6) == 'Q') return true;
            if (move.charAt(6) == 'R') return true;
            if (move.charAt(6) == 'B') return true;
            if (move.charAt(6) == 'N') return true;
        }

        return false;
    }

    // TODO - more testing with this
    /**
     * Parses a regular move inputted by the user. Converts regular chess rows and columns to the
     * corresponding array indices. For example, the move "a2 a4" will be returned as an ArrayList of
     * integer arrays. The first array will contain {6, 0} (corresponding to a2) and the second array will
     * contain {4, 0} (corresponding to a4). This method calls the {@code in_bounds(ArrayList<int> values)}
     * method from the {@code Chess} class and also the {@code different_space()} and {@code good_piece()}
     * methods.
     * 
     * @param move The move the user enters
     * @return An ArrayList of integer arrays - the first array representing the starting space and the
     * second representing the ending space. The first index of the first array is -1 if the user has
     * entered a bad move.
     */
    static ArrayList<int[]> parse_input(String move){
        ArrayList<int[]> result = new ArrayList<>();
        int startingRow, startingCol, finalRow, finalCol;

        // Example of a valid move: a2 a4
        if (move.length() == 5 || good_promotion_input(move)){
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

    /**
     * Checks for any special input the user may have entered. "stop" ends the current game, "help"
     * brings the user to the help menu, a user can resign with "resign", and a user can request a draw
     * with "draw?" appended to an otherwise legal move.
     * 
     * @param move The move the user entered
     * @param bw The character buffer to write to
     * @return {@code true} if the user has entered special input, {@code false} otherwise
     */
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

                if (thirdWord.equals("draw?")){
                    String first_and_second_words = move.substring(0, index-1);
                    System.out.println("FIRST AND SECOND WORDS: " + first_and_second_words);
                    ArrayList<int[]> move_as_al = parse_input(first_and_second_words);
                    int[] startingSpace = move_as_al.get(0);

                    if (startingSpace[0] != -1){
                        int[] endingSpace = move_as_al.get(1);
                        Piece piece = Chess.board[startingSpace[0]][startingSpace[1]];

                        if (piece.legalMove(endingSpace[0], endingSpace[1])){
                            draw(move, bw);
                            return true;
                        }
                    }
                }
            }
        }
        
        return false;
    }

    /**
     * Writes the appropriate resign documentation to the text file and ends the game if a user resigns.
     * 
     * @param bw The character buffer to write to
     */
    private static void resign(BufferedWriter bw){
        try{
            if (Chess.write) bw.write("resign");
            if (Chess.write) bw.newLine();

            if (Chess.moveNumber % 2 == 0){
                System.out.println("Black wins");
                if (Chess.write) bw.write("Black wins");
            }
            else {
                System.out.println("White wins");
                if (Chess.write) bw.write("White wins");
            }

            if (Chess.write) bw.newLine();
            Chess.play = false;
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Writes the appropriate draw documentation to the text file and ends the game if a user requests
     * a draw.
     * 
     * @param move The move the user entered
     * @param bw The character buffer to write to
     */
    private static void draw(String move, BufferedWriter bw){
        try {
            System.out.println("draw");
            if (Chess.write) bw.write(move);
            if (Chess.write) bw.newLine();
            if (Chess.write) bw.write("draw");
            if (Chess.write) bw.newLine();
            Chess.play = false;
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Writes "stop" to the text file and ends the game if the user enters "stop".
     * 
     * @param bw The character buffer to write to
     */
    private static void stop(BufferedWriter bw){
        try {
            if (Chess.write) bw.write("stop");
            if (Chess.write) bw.newLine();
            Chess.play = false;
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * For a move that has three elements (two spaces) (Example: "e2 e4 draw?"). Returns the first
     * character after the second space if the String has exactly two spaces.
     * 
     * @param str The move the user entered
     * @return The index of the first character after the second space, otherwise -1
     */
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