package chess;

import chess.pieces.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * The {@code Setup} class handles various administrative processes during the chess game. Some of these
 * include providing the option to automate all the moves from the previous game, setting the chess board,
 * populating the ArrayLists for the pieces in play, and providing a help function that the user can call
 * at any time.
 * 
 * @author Sam Jones
 */
class Setup {

    /**
     * Asks if the user would like to continue with the saved game or start a new one.
     * 
     * @return {@code true} if the user would like to continue with the previously-saved game, {@code false}
     * otherwise
     */
    static boolean init(){
        String continuation;
        System.out.print("Continue with the saved game or start a new game? If you restart, you will still have " +
                "the option to automate the moves from the\nprevious game. Enter 'y' to continue, 'n' to restart: ");

        while (true){
            continuation = Chess.scanner.nextLine();
            continuation = continuation.trim();

            if (continuation.equals("y")) return true;
            else if (continuation.equals("n")) return false;
            else System.out.print("Invalid input. Please enter 'y' to continue where you left off, or 'n' to start a new game: ");
        }
    }

    /**
     * Asks if the user would like to automate all the moves from the previous game.
     * 
     * @return {@code true} if the user would like to automate the moves, {@code false} otherwise
     */
    static boolean automate_input(){
        String automate;
        System.out.print("\nAutomate all the moves from the previous game? Enter 'y' to copy the moves, 'n' to start fresh: ");

        while (true){
            automate = Chess.scanner.nextLine();
            automate = automate.trim();

            if (automate.equals("y")) return true;
            else if (automate.equals("n")) return false;
            else System.out.print("Invalid input. Please enter 'y' to copy last game's moves, or 'n' to start a new game: ");
        }
    }

    /**
     * Called when all the moves have been automated from the previous game. This method switches where the
     * program is reading data from to Standard Input so that the user can enter new moves.
     * 
     * @return A buffer to read data from Standard Input
     */
    static BufferedReader end_automation(){
        if (Chess.moveNumber % 2 == 0) System.out.println("White's move:\n");
        else System.out.println("Black's move:\n");
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        Chess.automate = false;
        Chess.write = true;
        return br;
    }

    /**
     * Sets the board at the beginning of the game.
     * 
     * @param board The chess board
     */
    static void setBoard(Piece[][] board){

        // Pawns
        for (int col = 0; col < 8; col++){
            Piece bPawn = new Pawn(board, 1, col, 'b', 'p');
            Piece wPawn = new Pawn(board, 6, col, 'w', 'p');
            bPawn.addPiece();
            wPawn.addPiece();
        }

        // Knights
        Piece bKnight1 = new Knight(board, 0, 1, 'b', 'N');
        Piece bKnight2 = new Knight(board, 0, 6, 'b', 'N');
        Piece wKnight1 = new Knight(board, 7, 1, 'w', 'N');
        Piece wKnight2 = new Knight(board, 7, 6, 'w', 'N');
        bKnight1.addPiece();
        bKnight2.addPiece();
        wKnight1.addPiece();
        wKnight2.addPiece();

        // Bishops
        Piece bBishop1 = new Bishop(board, 0, 2, 'b', 'B');
        Piece bBishop2 = new Bishop(board, 0, 5, 'b', 'B');
        Piece wBishop1 = new Bishop(board, 7, 2, 'w', 'B');
        Piece wBishop2 = new Bishop(board, 7, 5, 'w', 'B');
        bBishop1.addPiece();
        bBishop2.addPiece();
        wBishop1.addPiece();
        wBishop2.addPiece();

        // Rooks
        Piece bRook1 = new Rook(board, 0, 0, 'b', 'R');
        Piece bRook2 = new Rook(board, 0, 7, 'b', 'R');
        Piece wRook1 = new Rook(board, 7, 0, 'w', 'R');
        Piece wRook2 = new Rook(board, 7, 7, 'w', 'R');
        bRook1.addPiece();
        bRook2.addPiece();
        wRook1.addPiece();
        wRook2.addPiece();

        // Queens
        Piece bQueen = new Queen(board, 0, 3, 'b', 'Q');
        Piece wQueen = new Queen(board, 7, 3, 'w', 'Q');
        bQueen.addPiece();
        wQueen.addPiece();

        // Kings
        Piece bKing = new King(board, 0, 4, 'b', 'K');
        Piece wKing = new King(board, 7, 4, 'w', 'K');
        bKing.addPiece();
        wKing.addPiece();
    }

    /**
     * Populates the ArrayList of White pieces in play and the ArrayList of Black pieces in play at the
     * beginning of a chess game.
     */
    private static void populate_al(){
        int[] startRows = {0, 1, 6, 7};
        Piece piece;

        for (int i = 0; i < startRows.length; i++){
            for (int j = 0; j < Chess.NUM_COLS; j++){
                piece = Chess.board[startRows[i]][j];

                // This case should not occur, but have it here just in case
                if (piece == null) continue;

                if (piece.getTeam() == 'w') Chess.whiteAlive.add(piece);
                else Chess.blackAlive.add(piece);
            }
        }
    }

    /**
     * Help menu the user can access at any time by entering "help".
     */
    static void help(){
        String input;
        System.out.print("\nHelp menu:\n" +
                    "White moves first, Black moves second, and it continues alternating.\n" +
                    "From left-to-right, the columns are labeled 'a' through 'h'. From top-to-bottom the" +
                    " rows are numbered '8' through '1'.\n" +
                    "To enter a valid move, indicate the starting and ending position, separated by a" +
                    " space, for the piece you would like to move.\nExample for a white pawn --> 'a2 a4'\n" +
                    "To request a draw, enter a valid move followed by 'draw?'. Ex: 'e7 e6 draw?'\n" +
                    "To resign from the game, enter 'resign'.\n" +
                    "Have fun!\n\n" +
                    "Enter 'q' to exit the help menu: ");

        while (true){
            input = Chess.scanner.nextLine();
            input = input.trim().toLowerCase();

            if (input.equals("q")){
                System.out.println("Successfully exited the help menu. Enter your next move.\n");
                return;
            }
            System.out.print("Invalid input. Please enter 'q' to exit the help menu: ");
        }
    }
}