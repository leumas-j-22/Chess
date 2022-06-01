package chess;

import java.util.HashMap;

/**
 * The {@code Conversion} class contains two hash maps. The first is for the type of a given piece.
 * The key is a {@code Character} (as specified in the project description) and the value is the
 * piece type as a {@code String}. The second hash map retrieves information about the column a piece
 * is in. An {@code Integer} - the column array index - is the key and a {@code Character} -
 * the corresponding official chess column - is the value.
 * 
 * @author Sam Jones
 */
public class Conversion {
    /**
     * HashMap for piece type. The key is the character representation of the piece type, and the value
     * is the String representation.
     */
    private static HashMap<Character, String> piece_type;

    /**
     * HashMap for the column a given piece is in. The key is the integer array index for the column, and
     * the value is the corresponding alphabetical character.
     */
    private static HashMap<Integer, Character> piece_col;


    /**
     * Initializes the two hash maps if they are null.
     */
    private static void init(){
        piece_type = new HashMap<Character, String>();
        piece_type.put('p', "Pawn");
        piece_type.put('N', "Knight");
        piece_type.put('B', "Bishop");
        piece_type.put('R', "Rook");
        piece_type.put('Q', "Queen");
        piece_type.put('K', "King");

        piece_col = new HashMap<Integer, Character>();
        piece_col.put(0, 'a');
        piece_col.put(1, 'b');
        piece_col.put(2, 'c');
        piece_col.put(3, 'd');
        piece_col.put(4, 'e');
        piece_col.put(5, 'f');
        piece_col.put(6, 'g');
        piece_col.put(7, 'h');
    }

    /**
     * Calls the {@code init()} method if the two class-level HashMaps are null. Then, takes the
     * inputted piece type and returns the full {@code String} version of it. This method uses the
     * {@code piece_type} hash map.
     * <p>
     * This is a static method so that an instance of the {@code Conversion} class does not need to be
     * created in order to call this method.
     * <p>
     * Applicable character to String conversions:
     * <p>
     * <blockquote>
     * 'p' --- "Pawn" <p>
     * 'N' --- "Knight" <p>
     * 'B' --- "Bishop" <p>
     * 'R' --- "Rook" <p>
     * 'Q' --- "Queen" <p>
     * 'K' --- "King"
     * </blockquote>
     * 
     * @param type The type of the piece as specified in the project description
     * @return The corresponding {@code String} version of the piece type, retrieved from the
     * {@code piece_type} hash map
     */
    public static String retrieve_type(char type){

        if (piece_type == null && piece_col == null){
            init();
        }

        return piece_type.get(type);
    }

    /**
     * Calls the {@code init()} method if the two class-level HashMaps are null. Then, takes the
     * inputted piece column and returns the {@code char} version of it. This method uses the
     * {@code piece_col} hash map.
     * <p>
     * This is a static method so that an instance of the {@code Conversion} class does not need to be
     * created in order to call this method.
     * <p>
     * Applicable integer to character conversions:
     * <p>
     * <blockquote>
     * 0 --- 'a' <p>
     * 1 --- 'b' <p>
     * 2 --- 'c' <p>
     * 3 --- 'd' <p>
     * 4 --- 'e' <p>
     * 5 --- 'f' <p>
     * 6 --- 'g' <p>
     * 7 --- 'h' <p>
     * </blockquote>
     * 
     * @param col The current column the piece is in (integer array index)
     * @return The corresponding {@code char} version of the current column, retrieved from the
     * {@code piece_col} hash map
     */
    public static char retrieve_col(int col){

        if (piece_type == null && piece_col == null){
            init();
        }

        return piece_col.get(col);
    }
    
    /**
     * An actual chess board starts at the top with row 8 and descends to row 1 at the bottom. However,
     * I use a 2-D array for the board, so the top row is index 0 and it increases to index 7 at
     * the bottom row. This method converts a user-entered row (the row number on a physical chess board)
     * and returns the corresponding array index.
     * 
     * @param row The actual row on a regular chess board
     * @return The converted integer array index of the row
     */
    public static int retrieve_row(int row){
        return 8 - row;
    }
}