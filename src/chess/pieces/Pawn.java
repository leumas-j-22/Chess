package chess.pieces;

/**
 * The <code>Pawn</code> class extends the abstract class <code>Piece</code> and creates a Pawn.
 * It implements the abstract method <code>legalMove()</code>, and all other instance variables and
 * non-abstract methods are inherited.
 * 
 * @author Sam Jones
 * @author Jacob Figueredo 
 * @see Piece
 */
public class Pawn extends Piece {

    /**
     * Calls the <code>Piece</code> superclass constructor to create a Pawn.
     * 
     * @param board The chess board
     * @param row The row the piece is in
     * @param col The column the piece is in
     * @param team The team the piece is on ('w' for white, 'b' for black)
     * @param type The type of the piece
     */
    public Pawn(Piece[][] board, int row, int col, char team, char type){
        super(board, row, col, team, type);
    }


    /**
     * Checks if the entered space represents a legal move for a Pawn. <code>Chess</code> already checks 
     * if the entered move is out of bounds or if it isn't a move at all (row and column values don't change),
     * so those don't need to be checked here.
     * <p>
     * The board is based on 2D array indices, so up and down are "inverted". Going from a higher index
     * to a lower index is going "up", and going from a lower index to a higher index is "down".
     * 
     * @param finalRow The row you want to move the piece to
     * @param finalCol The column you want to move the piece to
     * @return <code>true</code> if it is a legal move, <code>false</code> otherwise
     */
    public boolean legalMove(int newRow, int newCol){

        // TODO
        return true;
    }


    /**
     * Promotes a Pawn if it has reached the other end of the board.
     * 
     * @param pawn_to_promote The Pawn to promote
     * @param upgrade A character representing the piece to upgrade to. 'Q' for Queen, 'R' for Rook, 'B'
     *                for Bishop, and 'N' for Knight
     * @return The piece the Pawn is promoted to
     */
    public static Piece promotion(Piece pawn_to_promote, char upgrade){

        Piece[][] board = pawn_to_promote.getBoard();
        char team = pawn_to_promote.getTeam();
        int row = pawn_to_promote.getRow();
        int col = pawn_to_promote.getCol();
        Piece replacement = null;

        if (upgrade == 'Q'){
            replacement = new Queen(board, row, col, team, 'Q');
        }
        else if (upgrade == 'R'){
            replacement = new Rook(board, row, col, team, 'R');
        }
        else if (upgrade == 'B'){
            replacement = new Bishop(board, row, col, team, 'B');
        }
        else if (upgrade == 'N'){
            replacement = new Knight(board, row, col, team, 'N');
        }

        return replacement;
    }
}