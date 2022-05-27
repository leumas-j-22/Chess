package chess;
import java.util.HashMap;

/**
 * The <code>Conversion</code> class contains two hash maps. The first is for the type of a given piece.
 * The key is a <code>Character</code> (as specified in the project description) and the value is the
 * piece type as a <code>String</code>. The second hash map retrieves information about the column a piece
 * is in. An <code>Integer</code> - the column array index - is the key and a <code>Character</code> -
 * the corresponding official chess column - is the value.
 * 
 * @author Sam Jones
 */
public class Conversion {

    private static HashMap<Character, String> piece_type;
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
     * Initializes the two hash maps in the {@code Conversion} class if they are null. Then, takes the
     * inputted piece type and returns the full {@code String} version of it. This method uses the
     * piece_type hash map.
     * <p>
     * This is a static method so that an instance of the {@code Conversion} class does not need to be
     * created in order to call this method.
     * 
     * @param type The type of the piece as specified in the project description
     * @return The corresponding {@code String} version of the piece type, retrieved from the
     * piece_type hash map
     */
    public static String retrieve_type(char type){

        if (piece_type == null && piece_col == null){
            init();
        }

        return piece_type.get(type);
    }


    /**
     * Initializes the two hash maps in the {@code Conversion} class if they are null. Then, takes the
     * inputted piece column and returns the {@code char} version of it. This method uses the
     * piece_col hash map.
     * <p>
     * This is a static method so that an instance of the {@code Conversion} class does not need to be
     * created in order to call this method.
     * 
     * @param col The current column the piece is in (integer array index)
     * @return The corresponding {@code char} version of the current column, retrieved from the
     * piece_col hash map
     */
    public static char retrieve_col(int col){

        if (piece_type == null && piece_col == null){
            init();
        }

        return piece_col.get(col);
    }
    
    
    public static int retrieve_row(int row){
        return 8 - row;
    }
}