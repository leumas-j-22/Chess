package chess.pieces;

import chess.Piece;

/**
 * The <code>Queen</code> class extends the abstract class <code>Piece</code> and creates a Queen.
 * It implements the abstract method <code>legalMove()</code>, and all other instance variables and
 * non-abstract methods are inherited.
 * 
 * @author Sam Jones
 * @author Jacob Figueredo 
 * @see Piece
 */
public class Queen extends Piece {

    /**
     * Calls the <code>Piece</code> superclass constructor to create a Queen.
     * 
     * @param board The chess board
     * @param row The row the piece is in
     * @param col The column the piece is in
     * @param team The team the piece is on ('w' for white, 'b' for black)
     * @param type The type of the piece
     */
    public Queen(Piece[][] board, int row, int col, char team, char type){
        super(board, row, col, team, type);
    }

    /**
     * Checks if the entered space represents a legal move for a Queen. <code>Chess</code> already checks 
     * if the entered move is out of bounds or if it isn't a move at all (row and column values don't change),
     * so those don't need to be checked here.
     * <p>
     * A Queen can either move as a Rook or a Bishop. If the Queen is moving as a Rook, a <code>Rook</code>
     * object is created and <code>legalMove()</code> is called on that object. If the Queen is moving as a
     * Bishop, a <code>Bishop</code> object is created and <code>legalMove()</code> is called on that object.
     * <p>
     * The board is based on 2D array indices, so up and down are "inverted". Going from a higher index
     * to a lower index is going "up", and going from a lower index to a higher index is "down".
     * 
     * @param finalRow The row you want to move the piece to
     * @param finalCol The column you want to move the piece to
     * @return <code>true</code> if it is a legal move for a Queen, <code>false</code> otherwise
     */
    public boolean legalMove(int finalRow, int finalCol){

        if ( !different_space(finalRow, finalCol) ) return false;
        int startingRow = getRow();
        int startingCol = getCol();
        Piece[][] board = getBoard();
        char piece_moving_team = getTeam();

        int rowChange = finalRow - startingRow;
        int colChange = finalCol - startingCol;

        // This represents the Queen moving as a Rook
        if (rowChange == 0 || colChange == 0){

            Rook queen_as_rook = new Rook(board, startingRow, startingCol, piece_moving_team, 'R');
            return queen_as_rook.legalMove(finalRow, finalCol);
        }
        // This represents the Queen moving as a Bishop
        else {

            Bishop queen_as_bishop = new Bishop(board, startingRow, startingCol, piece_moving_team, 'B');
            return queen_as_bishop.legalMove(finalRow, finalCol);
        }
    }
}