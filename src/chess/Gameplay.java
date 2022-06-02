package chess;

import chess.pieces.King;
import chess.pieces.Pawn;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 * This class includes static methods for various gameplay scenarios in a game of Chess.
 * 
 * @author Sam Jones
 */
public class Gameplay {

    /**
     * Checks if an enemy piece is present in the space the current piece would like to move to. The
     * {@code legalMove()} method in the class for each piece already checks if there is a piece on the same team
     * in the desired space, so that does not need to be verified here.
     * 
     * @param finalRow The row the piece is moving to
     * @param finalCol The column the piece is moving to
     * @return {@code true} if an enemy piece is present in the space, {@code false} otherwise
     */
    static boolean enemy_piece(int finalRow, int finalCol){
        if (Chess.board[finalRow][finalCol] == null) return false;
        return true;
    }

    /**
     * When a piece is captured on the board. The piece is removed from its respective ArrayList of alive
     * pieces and added to the corresponding ArrayList of captured pieces.
     * <p>
     * This method also writes a message to the text file, describing the capture.
     * 
     * @param attacker The attacking piece
     * @param captured The captured piece
     * @param bw The character buffer to write to
     */
    static void kill_piece(Piece attacker, Piece captured, BufferedWriter bw){
        try {
            if (captured.getTeam() == 'w'){
                Chess.whiteKilled.add(captured);
                Chess.whiteAlive.remove(captured);
            }
            else {
                Chess.blackKilled.add(captured);
                Chess.blackAlive.remove(captured);
            }

            if (Chess.write) bw.write("   " + attacker.getTeam() + attacker.getType() + " captures " + captured.getTeam() +
                        captured.getType() + " at space " + Conversion.retrieve_col(captured.getCol()) +
                        Conversion.retrieve_row(captured.getRow()));
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Handles whenever a King may be in check. If a King is in check, a message is printed to Standard
     * Output and also written to the text file.
     * 
     * @param king The King that may be in check
     * @param wr The character buffer to write to
     * @return {@code true} if the King is in check, {@code false} otherwise
     */
    static boolean handle_check(King king, BufferedWriter wr){
        boolean check;
        ArrayList<Piece> enemyPieces;

        if (Chess.moveNumber % 2 == 1) enemyPieces = Chess.blackAlive;
        else enemyPieces = Chess.whiteAlive;

        if (check = king.check(enemyPieces, king.getRow(), king.getCol())){
            if (Chess.write) Chess.print_check(wr);
        }

        return check;
    }

    /**
     * Handles when there is a possibility for a Pawn to be promoted.
     * 
     * @param movingPiece The piece that just moved (Chess.java already has identified that it is a Pawn)
     * @param finalRow The row the piece just moved to
     * @param finalCol The column the piece just moved to
     */
    static void handle_promotion(Piece movingPiece, int finalRow, int finalCol){
        Pawn pawn = (Pawn) movingPiece;

        if (pawn.valid_promotion()){
            if (Chess.move.length() == 7) movingPiece = pawn.promotion(pawn, Chess.move.charAt(6));
            else movingPiece = pawn.promotion(pawn, 'Q');
            Chess.board[finalRow][finalCol].removePiece();
            movingPiece.addPiece();
        } 
    } 
}