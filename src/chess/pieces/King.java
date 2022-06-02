package chess.pieces;

import chess.Chess;
import chess.Conversion;
import chess.Piece;
import java.util.ArrayList;

/**
 * The {@code King} class extends the abstract class {@code Piece} and creates a King.
 * It implements the abstract method {@code legalMove()}, and all other instance variables and
 * non-abstract methods are inherited.
 * 
 * @author Sam Jones
 * @author Jacob Figueredo 
 * @see Piece
 */
public class King extends Piece {

    /**
     * Calls the {@code Piece} superclass constructor to create a King.
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
     * Checks if the entered space represents a legal move for a King. {@code Chess} already checks 
     * if the entered move is out of bounds or if it isn't a move at all (row and column values don't change),
     * so those don't need to be checked here.
     * <p>
     * The board is based on 2D array indices, so up and down are "inverted". Going from a higher index
     * to a lower index is going "up", and going from a lower index to a higher index is "down".
     * 
     * @param finalRow The row you want to move the piece to
     * @param finalCol The column you want to move the piece to
     * @return {@code true} if it is a legal move, {@code false} otherwise
     */
    public boolean legalMove(int finalRow, int finalCol){

        if ( !different_space(finalRow, finalCol) ) return false;
        int startingRow = getRow();
        int startingCol = getCol();
        Piece[][] board = getBoard();
        char piece_moving_team = getTeam();

        int rowChange = Math.abs(finalRow - startingRow);
        int colChange = Math.abs(finalCol - startingCol);

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

    /**
     * Determines the number of enemy pieces who have the King in check. This method loops through the pieces
     * on the opposing team and sees if any of them can capture the King with a single move. It uses the
     * {@code legalMove()} method for each piece. If there are two enemy pieces who have the King in
     * check, the King is in checkmate and the game is over. 
     * 
     * @param enemyPieces ArrayList of enemy pieces
     * @param kRow The row the King is in
     * @param kCol The column the King is in
     * @return The number of enemy pieces who have the King in check
     */
    public ArrayList<Piece> check(ArrayList<Piece> enemyPieces){
        ArrayList<Piece> enemies_with_path = new ArrayList<>();
        int kRow = getRow();
        int kCol = getCol();

        for (Piece piece : enemyPieces){
            if (piece.legalMove(kRow, kCol)) enemies_with_path.add(piece);
        }

        return enemies_with_path;
    }

    
    // First int array is space king starts from
    public ArrayList<int[]> checkmate(ArrayList<Piece> enemyPieces, char enemyColor){
        ArrayList<int[]> safeMoves = new ArrayList<>();
        int kRow = getRow();
        int kCol = getCol();
        char friendlyColor = getTeam();
        Piece[][] board = getBoard();

        // Simulates the King actually moving to a different spot. Save the backup to replace at the end
        Piece kingBackup = board[kRow][kCol];
        board[kRow][kCol] = null;
        Piece enemyDecoy;
        int startRow, startCol;

        for (startRow = kRow - 1; startRow <= kRow + 1; startRow++){

            for (startCol = kCol - 1; startCol <= kCol + 1; startCol++){
                if ( !Chess.in_bounds(startRow, startCol) ) continue;

                if (legalMove(startRow, startCol)){

                    if ( (enemyDecoy = board[startRow][startCol]) != null) enemyDecoy.setTeam(friendlyColor);

                    for (int i = 0; i < enemyPieces.size(); i++){
                        if (enemyPieces.get(i).legalMove(startRow, startCol)){
                            System.out.println("The space " + Conversion.retrieve_col(startCol) + Conversion.retrieve_row(startRow) +
                                    " is not safe for the King");
                            if (enemyDecoy != null) enemyDecoy.setTeam(enemyColor);
                            break;
                        }

                        if (i == enemyPieces.size() - 1){
                            System.out.println("The space " + Conversion.retrieve_col(startCol) + Conversion.retrieve_row(startRow) +
                                    " is SAFE for the King");
                            if (enemyDecoy != null) enemyDecoy.setTeam(enemyColor);
                            int[] potentialSpace = new int[2];
                            potentialSpace[0] = startRow;
                            potentialSpace[1] = startCol;
                            safeMoves.add(potentialSpace);
                        }
                    }
                }
            }
        }

        board[kRow][kCol] = kingBackup;
        return safeMoves;
    }
}