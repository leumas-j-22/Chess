package chess.pieces;

/**
 * The <code>King</code> class extends the abstract class <code>Piece</code> and creates a King.
 * It implements the abstract method <code>legalMove()</code>, and all other instance variables and
 * non-abstract methods are inherited.
 * 
 * @author Sam Jones
 * @author Jacob Figueredo 
 * @see Piece
 */
public class King extends Piece {

    /**
     * Calls the <code>Piece</code> superclass constructor to create a King.
     * 
     * @param board The chess board
     * @param row The row the piece is in
     * @param col The column the piece is in
     * @param team The team the piece is on ('w' for white, 'b' for black)
     * @param type The type of the piece
     */
    public King(Piece[][] board, int row, int col, char team, char type){
        super(board, row, col, team, type);
    }


    /**
     * Checks if the entered space represents a legal move for a King. <code>Chess</code> already checks 
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
    public boolean legalMove(int finalRow, int finalCol){

        int startingRow = getRow();
        int startingCol = getCol();
        Piece[][] board = getBoard();
        char piece_moving_team = getTeam();

        int rowChange = Math.abs(finalRow - startingRow);
        int colChange = Math.abs(finalRow - startingCol);

        if (rowChange == 1 && colChange == 1){

            if (board[finalRow][finalCol] == null){
                return true;
            }
            if (piece_moving_team != board[finalRow][finalCol].getTeam()){
                return true;
            }
        }
        else if (rowChange == 1 && colChange == 0){

            if (board[finalRow][finalCol] == null){
                return true;
            }
            if (piece_moving_team != board[finalRow][finalCol].getTeam()){
                return true;
            }
        }
        else if (rowChange == 0 && colChange == 1){

            if (board[finalRow][finalCol] == null){
                return true;
            }
            if (piece_moving_team != board[finalRow][finalCol].getTeam()){
                return true;
            }
        }

        // Not a legal move at this point
        return false;
    }
}