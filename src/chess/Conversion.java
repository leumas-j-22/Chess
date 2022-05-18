package chess;
import java.util.HashMap;

public class Conversion {

    private static HashMap<Character, String> piece_type;
    private static HashMap<Integer, Character> piece_col;

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

    public static String retrieve_type(char type){

        if (piece_type == null && piece_col == null){
            init();
        }

        return piece_type.get(type);
    }

    public static char retrieve_col(int col){

        if (piece_type == null && piece_col == null){
            init();
        }

        return piece_col.get(col);
    }  
}