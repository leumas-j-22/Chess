package chess.pieces;

import chess.Piece;

/**
 * The <code>Rook</code> class extends the abstract class <code>Piece</code> and creates a Rook.
 * It implements the abstract method <code>legalMove()</code>, and all other instance variables and
 * non-abstract methods are inherited.
 * 
 * @author Sam Jones
 * @author Jacob Figueredo 
 * @see Piece
 */
public class Rook extends Piece {

    /**
     * Calls the <code>Piece</code> superclass constructor to create a Rook.
     * 
     * @param board The chess board
     * @param row The row the piece is in
     * @param col The column the piece is in
     * @param team The team the piece is on ('w' for white, 'b' for black)
     * @param type The type of the piece
     */
    public Rook(Piece[][] board, int row, int col, char team, char type){
        super(board, row, col, team, type);
    }
    
    /**
     * Checks if the entered space represents a legal move for a Rook. <code>Chess</code> already checks 
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

        int rowChange = finalRow - startingRow;
        int colChange = finalCol - startingCol;

        // Rook can only move horizontally or vertically
        if (rowChange != 0 && colChange != 0){
            return false;
        }

        // Moving along a row (columns are changing, row is constant)
        if (rowChange == 0){

            // Moving to the right
            if (finalCol > startingCol){
                
                for (int col = startingCol + 1; col < finalCol; col++){

                    if (board[startingRow][col] != null){
                        return false;
                    }
                }
            }
            // Moving to the left
            else {

                for (int col = startingCol - 1; col > finalCol; col--){

                    if (board[startingRow][col] != null){
                        return false;
                    }
                }
            }

            // col == finalCol at this point --> at destination
            if (board[startingRow][finalCol] != null && piece_moving_team == board[startingRow][finalCol].getTeam()){
                return false;
            } 
        }
        // Moving along a column (rows are changing, column is constant)
        else {

            // Using array indices, so this would represent moving down
            if (finalRow > startingRow){

                for (int row = startingRow + 1; row < finalRow; row++){

                    if (board[row][startingCol] != null){
                        return false;
                    }
                }
            }
            // Moving up
            else {

                for (int row = startingRow - 1; row > finalRow; row--){

                    if (board[row][startingCol] != null){
                        return false;
                    }
                }
            }

            // row == finalRow at this point --> at destination
            if (board[finalRow][startingCol] != null && piece_moving_team == board[finalRow][startingCol].getTeam()){
                return false;
            }
        }

        // Legal move at this point
        return true;
    }
}