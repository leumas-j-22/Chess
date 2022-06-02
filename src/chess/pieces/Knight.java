package chess.pieces;

import chess.Piece;

/**
 * The <code>Knight</code> class extends the abstract class <code>Piece</code> and creates a Knight.
 * It implements the abstract method <code>legalMove()</code>, and all other instance variables and
 * non-abstract methods are inherited.
 * 
 * @author Sam Jones
 * @author Jacob Figueredo 
 * @see Piece
 */
public class Knight extends Piece {

    /**
     * Calls the <code>Piece</code> superclass constructor to create a Knight.
     * 
     * @param board The chess board
     * @param row The row the piece is in
     * @param col The column the piece is in
     * @param team The team the piece is on ('w' for white, 'b' for black)
     * @param type The type of the piece
     */
    public Knight(Piece[][] board, int row, int col, char team, char type){
        super(board, row, col, team, type);
    }

    /**
     * Checks if the entered space represents a legal move for a Knight. <code>Chess</code> already checks 
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

        if ( !different_space(finalRow, finalCol) ) return false;
        int startingRow = getRow();
        int startingCol = getCol();
        Piece[][] board = getBoard();
        char piece_moving_team = getTeam();

        // Utilize absolute value to capture "positive" and "negative" moves that mirror each other
        int rowChange = Math.abs(finalRow - startingRow);
        int colChange = Math.abs(finalCol - startingCol);

        // One of the two general moves a Knight can make --> a row change of 1 and a column change of 2
        if (rowChange == 1 && colChange == 2){

            // No piece at the space the Knight is moving to
            if (board[finalRow][finalCol] == null){
                return true;
            }
            // There is a piece in the space the Knight is moving to, but it is on the opposing team
            if (piece_moving_team != board[finalRow][finalCol].getTeam()){
                return true;
            }
        }
        // The second of the two general moves a Knight can make --> a row change of 2 and a column change of 1
        else if (rowChange == 2 && colChange == 1){

            // No piece at the space the Knight is moving to
            if (board[finalRow][finalCol] == null){
                return true;
            }
            // There is a piece in the space the Knight is moving to, but it is on the opposing team
            if (piece_moving_team != board[finalRow][finalCol].getTeam()){
                return true;
            }
        }

        // Not a legal move at this point
        return false;
    }
}