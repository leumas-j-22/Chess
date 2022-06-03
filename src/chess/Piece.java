package chess;

/**
 * Abstract class {@code Piece} that has {@code board}, {@code row}, {@code col}, {@code team}, and
 * {@code type} fields. Each chess piece - King, Queen, Rook, Bishop, Knight, and Pawn - will inherit
 * the fields and non-abstract methods from the {@code Piece} superclass. They will each implement the
 * {@code legalMove()} method in their respective class.
 * 
 * @author Sam Jones
 * @author Jacob Figueredo
 */
public abstract class Piece {
    /**
     * The chess board.
     */
    private Piece[][] board;

    /**
     * The row the piece is in.
     */
    private int row;

    /**
     * The column the piece is in.
     */
    private int col;

    /**
     * The team the piece is on ('w' for white, 'b' for black).
     */
    private char team;

    /**
     * The type of the piece.
     */
    private char type;


    /**
     * The {@code Piece} constuctor will be called in the {@code King}, {@code Queen}, {@code Rook},
     * {@code Bishop}, {@code Knight}, and {@code Pawn} subclasses to create the respective pieces.
     * <p>
     * The constructor needs protected-level access because the {@code Piece} class is in a different
     * package than the classes for the individual pieces.
     * 
     * @param board The board
     * @param row The row the piece is in
     * @param col The column the piece is in
     * @param team The team the piece is on ('w' for white, 'b' for black)
     * @param type The type of the piece
     */
    protected Piece(Piece[][] board, int row, int col, char team, char type){
        this.board = board;        
        this.row = row;
        this.col = col;
        this.team = team;
        this.type = type;        
    }


    /**
     * Sets the board that the piece will have access to.
     * 
     * @param board
     */
    public void setBoard(Piece[][] board){
        this.board = board;
    }
    
    /**
     * Sets the current row of the piece.
     * 
     * @param row
     */
    public void setRow(int row){
        this.row = row;
    }

    /**
     * Sets the current column of the piece.
     * 
     * @param col
     */
    public void setCol(int col){
        this.col = col;
    }

    /**
     * Sets the team of the piece.
     * 
     * @param team
     */
    public void setTeam(char team){
        this.team = team;
    }

    /**
     * Sets the type of the piece.
     * 
     * @param type
     */
    public void setType(char type){
        this.type = type;
    }

    /**
     * @return The chess board
     */
    public Piece[][] getBoard(){
        return board;
    }    

    /**
     * @return The row the piece is in
     */
    public int getRow(){
        return row;
    }

    /**
     * @return The column the piece is in.
     */
    public int getCol(){
        return col;
    }

    /**
     * @return The team the piece is on.
     */
    public char getTeam(){
        return team;
    }

    /**
     * @return The type of the piece.
     */
    public char getType(){
        return type;
    }

    /**
     * Determines if the space a user would like to move a piece to is different from the space the piece
     * is currently on.
     * 
     * @param finalRow The row the piece wants to move to
     * @param finalCol The column the piece wants to move to
     * @return {@code true} if the piece is moving to a different space, {@code false} otherwise
     */
    public boolean different_space(int finalRow, int finalCol){
        int rowChange = finalRow - row;
        int colChange = finalCol - col;
        if (rowChange == 0 && colChange == 0) return false;
        return true;
    }

    /**
     * Adds the current piece to the chess board. If the piece is on the White team, it adds it to the
     * ArrayList of White pieces in play. If the piece is on the Black team, it adds it to the ArrayList
     * of Black pieces in play.
     * <p>
     * This method is inherited by all the {@code Piece} subclasses ({@code King}, {@code Queen}, {@code Rook},
     * etc.), and will be called for the run-time type of the object (hence the use of {@code this}).
     * <p>
     * For example:
     * <p><blockquote><pre>
     * Piece bRook1 = new Rook(board, 0, 0, 'b', 'R');
     *bRook1.addPiece(); 
     * </pre></blockquote></p>
     * 
     * The dynamic type of bRook1 is {@code Rook}, so calling {@code addPiece()} above will add
     * the Rook to the board.
     */
    void addPiece(){
        board[row][col] = this;
        if (team == 'w') Chess.whiteAlive.add(this);
        else Chess.blackAlive.add(this);
    }

    /**
     * Removes the current piece from the board. If the piece is White, it is removed from the ArrayList
     * of White pieces in play. If it is Black, it is removed from the ArrayList of Black pieces
     * in play.
     */
    void removePiece(){
        board[row][col] = null;
        if (team == 'w') Chess.whiteAlive.remove(this);
        else Chess.blackAlive.remove(this);
    }

    /**
     * Overrides the {@code Object} class's {@code toString()} method. Prints out valuable information for
     * a given piece.
     * 
     * @return A String representation of the given Piece object
     */
    public String toString(){
        String team_as_str, type_as_str;
        int r;
        char c;

        if (team == 'b') team_as_str = "Black";
        else team_as_str = "White";

        type_as_str = Conversion.retrieve_type(this.getType());

        r = getRow();
        r = 8 - r;
        c = Conversion.retrieve_col(this.getCol());

        return "Piece Info\n" +
               "----------\n" +
               "Team:\t\t" + team_as_str + "\n" +
               "Type:\t\t" + type_as_str + "\n" +
               "Position:\t" + c + r + "\n";
    }

    /**
     * Determines if the new row and new column that a piece is moving to is a legal move for that piece.
     * This abstract method will be implemented in each of the subclasses.
     * 
     * @param newRow The new row the piece is moving to
     * @param newCol The new column the piece is moving to
     * @return {@code true} if it is a legal move, {@code false} otherwise
     */
    public abstract boolean legalMove(int newRow, int newCol);
}