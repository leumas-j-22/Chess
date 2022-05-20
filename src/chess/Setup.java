package chess;
import chess.pieces.*;

class Setup {

    static boolean init(){

        String continuation;
        System.out.print("Continue with the saved game or start a new game? Enter 'y' to continue, 'n' to restart: ");

        while (true){
            continuation = Chess.scanner.nextLine();
            continuation = continuation.trim();

            if (continuation.equals("y")) return true;
            else if (continuation.equals("n")) return false;
            else System.out.print("Invalid input. Please enter 'y' to continue where you left off, or 'n' to start a new game: ");
        }
    }


    /**
     * Sets the board at the beginning of the game.
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
}