package chess.pieces;

import chess.Piece;

/**
 * The <code>Bishop</code> class extends the abstract class <code>Piece</code> and creates a Bishop.
 * It implements the abstract method <code>legalMove()</code>, and all other instance variables and
 * non-abstract methods are inherited.
 * 
 * @author Sam Jones 
 * @author Jacob Figueredo
 * @see Piece
 */
public class Bishop extends Piece {

    /**
     * Calls the <code>Piece</code> superclass constructor to create a Bishop.
     * 
     * @param board The chess board
     * @param row The row the piece is in
     * @param col The column the piece is in
     * @param team The team the piece is on ('w' for white, 'b' for black)
     * @param type The type of the piece
     */    
    public Bishop(Piece[][] board, int row, int col, char team, char type){
        super(board, row, col, team, type);
    }

    /**
     * Checks if the entered space represents a legal move for a Bishop. <code>Chess</code> already checks 
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

        // Not necessary, but just makes it easier to understand in the loops
        int row = startingRow;

        int rowChange = Math.abs(finalRow - startingRow);
        int colChange = Math.abs(finalCol - startingCol);

        // Any valid bishop move involves changing the row and the column
        if (rowChange == 0 || colChange == 0){
            return false;
        }

        // Any valid bishop move moves vertically as much as it does horizontally
        if (rowChange != colChange){
            return false;
        }

        // Moving diagonally, up and to the right
        if (finalRow < startingRow && finalCol > startingCol){

            for (int col = startingCol + 1; col < finalCol; col++){

                row--;

                if (board[row][col] != null){
                    return false;
                }
            }
        }
        // Moving diagonally, down and to the right
        else if (finalRow > startingRow && finalCol > startingCol){

            for (int col = startingCol + 1; col < finalCol; col++){

                row++;

                if (board[row][col] != null){
                    return false;
                }
            }
        }
        // Moving diagonally, up and to the left
        else if (finalRow < startingRow && finalCol < startingCol){

            for (int col = startingCol - 1; col > finalCol; col--){

                row--;

                if (board[row][col] != null){
                    return false;
                }
            }
        }
        // Moving diagonally, down and to the left
        else {

            for (int col = startingCol - 1; col > finalCol; col--){

                row++;

                if (board[row][col] != null){
                    return false;
                }
            }
        }

        // At finalRow, finalCol at this point --> at destination
        if (board[finalRow][finalCol] != null && piece_moving_team == board[finalRow][finalCol].getTeam()){
            return false;
        }

        // Legal move at this point
        return true;
    }
}