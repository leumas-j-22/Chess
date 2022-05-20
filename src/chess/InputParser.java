package chess;
import java.io.BufferedWriter;
import java.io.IOException;

class InputParser {

    static boolean check_special_input(String move, BufferedWriter bw){

        int index;

        if (move.length() > 5){

            if (move.equals("resign")){
                resign(bw);
                return true;
            }
            if ((index = has_two_spaces(move)) != -1){

                String thirdWord = move.substring(index, move.length());

                // Need to check that a valid move is entered along with draw?
                if (thirdWord.equals("draw?")){
                    draw(move, bw);
                    return true;
                }
            }
        }
        
        System.out.println("Illegal move, try again");
        return false;
    }


    private static void resign(BufferedWriter bw){
        try{
            bw.write("resign");
            bw.newLine();

            if (Chess.moveNumber % 2 == 0){
                System.out.println("Black wins");
                bw.write("Black wins");
            }
            else {
                System.out.println("White wins");
                bw.write("White wins");
            }

            bw.newLine();
            Chess.play = false;
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }


    private static void draw(String move, BufferedWriter bw){
        try {
            System.out.println("draw");
            bw.write(move);
            bw.newLine();
            bw.write("draw");
            bw.newLine();
            Chess.play = false;
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }


    // Returns the index of the first character after the second space if the String has exactly two
    // spaces. Otherwise, returns -1.
    private static int has_two_spaces(String str){
        int count = 0;
        int index = 0;

        for (int i = 0; i < str.length(); i++){
            if (str.charAt(i) == ' '){
                count++;

                if (count == 2){
                    index = i;
                }
            }
        }

        if (count == 2) return index+1;
        return -1;
    }
}