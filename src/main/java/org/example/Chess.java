package org.example;
import java.util.*;


public class Chess {
    public static Scanner kb = new Scanner(System.in); //collects user input from the console
    public static String[][] board = {{"r","n","b","q","k","b","n","r"},{"p","p","p","p","p","p","p","p"},{" "," "," "," "," "," "," "," "},{" "," "," "," "," "," "," "," "},{" "," "," "," "," "," "," "," "},{" "," "," "," "," "," "," "," "},{"P","P","P","P","P","P","P","P"},{"R","N","B","Q","K","B","N","R"}};
    public static int turn = 0; //Keeps track who's turn it is
    public static int row1; //Player's row selection for the piece to move
    public static int col1; //Player's column selection for the piece to move
    public static int row2; //Player's row selection for the location of the selected piece to move
    public static int col2; //Player's column selection for the location of the selected piece to move
    public static boolean gameEnd = false; //Keeps track of when the game is over or not

    //main method to run program
    public static void main(String[] args) {
        System.out.println("---------- Chess ----------");
        System.out.println("");
        System.out.println("White is capitalized pieces, black is lowercase pieces.");
        System.out.println("Player 1 is uppercase, Player 2 is lowercase");
        System.out.println("");
        System.out.println("");
        playChess(board,turn,gameEnd,row1,col1,row2,col2);


    }

//methods used to run the overall program

    public static boolean isKingCheck(String[][] board, int turn, int row2, int col2){ //checks if either player's king is in check
        if(turn % 2 == 0){
            // Check straight lines
            int y = getKingRow(board,turn); //row of king
            int count = 0; //iteration counter for up/down/left/right directions
            for(int a = getKingRow(board,turn); a < board.length; a++) { // LEFT and RIGHT (directions)
                if(count % 2 == 0){
                    //direction LEFT (approaching column 0)
                    for(int b = (getKingColumn(board,turn) - 1); b >= 0; b--) { // square by square from the king and out in the current direction (square in direction)
                        if(board[y][b].equals("r") || board[y][b].equals("q")){ //square contains opponent rook or queen
                            return true;
                        }
                        else if(board[y][b].equals("P") || board[y][b].equals("N") || board[y][b].equals("B") || board[y][b].equals("R") || board[y][b].equals("Q")){ //square contains friendly piece
                            b = -1;
                        }
                    }
                }
                else if(count % 2 != 0){
                    //direction RIGHT (approaching column 7)
                    for(int b = (getKingColumn(board,turn) + 1); b < board[a].length; b++) { // square by square from the king and out in the current direction (square in direction)
                        if(board[y][b].equals("r") || board[y][b].equals("q")){ //square contains opponent rook or queen
                            return true;
                        }
                        else if(board[y][b].equals("P") || board[y][b].equals("N") || board[y][b].equals("B") || board[y][b].equals("R") || board[y][b].equals("Q")){ //square contains friendly piece
                            b = 8;
                        }
                    }
                }
                count += 1;
            }

            int z = getKingColumn(board,turn); //column of king
            count = 0;
            for(int c = 0; c < 8; c++) { // UP and DOWN (directions)
                if(count % 2 == 0){
                    //direction UP (approaching row 0)
                    for(int d = (getKingRow(board,turn) - 1); d >= 0; d--) { // square by square from the king and out in the current direction (square in direction)
                        if(board[d][z].equals("r") || board[d][z].equals("q")){ //square contains opponent rook or queen
                            return true;
                        }
                        else if(board[d][z].equals("P") || board[d][z].equals("N") || board[d][z].equals("B") || board[d][z].equals("R") || board[d][z].equals("Q")){ //square contains friendly piece
                            d = -1;
                        }
                    }
                }
                else if(count % 2 != 0){
                    //direction DOWN (approaching row 7)
                    for(int d = (getKingRow(board,turn) + 1); d < board.length; d++) { // square by square from the king and out in the current direction (square in direction)
                        if(board[d][z].equals("r") || board[d][z].equals("q")){ //square contains opponent rook or queen
                            return true;
                        }
                        else if(board[d][z].equals("P") || board[d][z].equals("N") || board[d][z].equals("B") || board[d][z].equals("R") || board[d][z].equals("Q")){ //square contains friendly piece
                            d = 8;
                        }
                    }
                }
                count += 1;
            }


            count = 0;
            int work = getKingColumn(board,turn);
            // Check diagonals
            for(int e = (getKingColumn(board,turn) + 1); e < board.length; e++){ // RIGHT-UP and RIGHT-DOWN
                work = getKingColumn(board,turn);
                if(count % 2 == 0){
                    for(int f = (getKingRow(board,turn) - 1); f >= 0; f--){ // square by square from the king and out in the current direction
                        work += 1;
                        if(work > 7){
                            break;
                        }

                        if(board[f][work].equals("b") || board[f][work].equals("q")){ //square contains opponent bishop or queen
                            return true;
                        }
                        else if(board[f][work].equals("P") || board[f][work].equals("N") || board[f][work].equals("B") || board[f][work].equals("R") || board[f][work].equals("Q")){ //square contains friendly piece
                            f = -1;
                        }
                    }
                    e = e -1;
                }
                else if(count % 2 != 0){
                    for(int f = (getKingRow(board,turn) + 1); f < board.length; f++){ // square by square from the king and out in the current direction
                        work += 1;
                        if(work > 7){
                            break;
                        }

                        if(board[f][work].equals("b") || board[f][work].equals("q")){ //square contains opponent bishop or queen
                            return true;
                        }
                        else if(board[f][work].equals("P") || board[f][work].equals("N") || board[f][work].equals("B") || board[f][work].equals("R") || board[f][work].equals("Q")){ //square contains friendly piece
                            f = 9;
                        }
                    }
                }
                count += 1;
            }

            count = 0;
            for(int g = (getKingColumn(board,turn) - 1); g >= 0; g--){ // LEFT-UP and LEFT-DOWN
                work = getKingColumn(board,turn);
                if(count % 2 == 0){
                    for(int h = (getKingRow(board,turn) - 1); h >= 0; h--){ // square by square from the king and out in the current direction
                        work -= 1;
                        if(work < 0){
                            break;
                        }

                        if(board[h][work].equals("b") || board[h][work].equals("q")){ //square contains opponent bishop or queen
                            return true;
                        }
                        else if(board[h][work].equals("P") || board[h][work].equals("N") || board[h][work].equals("B") || board[h][work].equals("R") || board[h][work].equals("Q")){ //square contains friendly piece
                            h = -1;
                        }
                    }
                }
                else if(count % 2 != 0){
                    for(int h = (getKingRow(board,turn) + 1); h < board.length; h++){ // square by square from the king and out in the current direction
                        work -= 1;
                        if(work < 0){
                            break;
                        }

                        if(board[h][work].equals("b") || board[h][work].equals("q")){ //square contains opponent bishop or queen
                            return true;
                        }
                        else if(board[h][work].equals("P") || board[h][work].equals("N") || board[h][work].equals("B") || board[h][work].equals("R") || board[h][work].equals("Q")){ //square contains friendly piece
                            h = 9;
                        }
                    }
                }
                count += 1;
            }
            count = 0;

            // Check pawns
            if(board[getKingRow(board,turn) - 1][getKingColumn(board,turn) + 1].equals("p") || board[getKingRow(board,turn) - 1][getKingColumn(board,turn) - 1].equals("p")){ //squares where pawns would threaten the king contains pawns
                return true;
            }

            // Check king, this is to find if a square is legal to move to only
        /*if(){ //squares where a king would threaten the king contains a king
            return true;
        }*/

            // Check knights -
            if(getKingRow(board,turn) <= 5 && getKingColumn(board,turn) <= 5 && getKingRow(board,turn) >= 2 && getKingColumn(board,turn) >= 2){ //squares where knights would threaten the king contains knights
                if(board[getKingRow(board,turn) + 1][getKingColumn(board,turn) - 2].equals("n") || board[getKingRow(board,turn) + 1][getKingColumn(board,turn) + 2].equals("n") || board[getKingRow(board,turn) - 1][getKingColumn(board,turn) - 2].equals("n") || board[getKingRow(board,turn) - 1][getKingColumn(board,turn) + 2].equals("n") || board[getKingRow(board,turn) + 2][getKingColumn(board,turn) + 1].equals("n") || board[getKingRow(board,turn) + 2][getKingColumn(board,turn) - 1].equals("n") || board[getKingRow(board,turn) - 2][getKingColumn(board,turn) - 1].equals("n") || board[getKingRow(board,turn) - 2][getKingColumn(board,turn) + 1].equals("n")){
                    return true;
                }
            }
            else if(((getKingRow(board,turn) >= 1 && getKingRow(board,turn) <= 5) && (getKingColumn(board,turn) >= 1 && getKingColumn(board,turn) <= 6))){
                if(board[getKingRow(board,turn) + 2][getKingColumn(board,turn) - 1].equals("n") || board[getKingRow(board,turn) + 2][getKingColumn(board,turn) + 1].equals("n")){
                    return true;
                }
            }
            else if(getKingRow(board,turn) < 1 && (getKingColumn(board,turn) >= 1) && getKingColumn(board,turn) <= 6){
                if(board[getKingRow(board,turn) + 2][getKingColumn(board,turn) + 1].equals("n") || board[getKingRow(board,turn) + 2][getKingColumn(board,turn) - 1].equals("n")){
                    return true;
                }
            }
            else if(getKingRow(board,turn) > 6 && getKingColumn(board,turn) >= 1 && getKingColumn(board,turn) <= 6){
                if(board[getKingRow(board,turn) - 2][getKingColumn(board,turn) + 1].equals("n") || board[getKingRow(board,turn) - 2][getKingColumn(board,turn) - 1].equals("n")){
                    return true;
                }
            }
            else if(getKingRow(board,turn) > 6 && getKingColumn(board,turn) > 1 && getKingColumn(board,turn) < 6){
                //System.out.println("yeet");
                if(board[getKingRow(board,turn) - 1][getKingColumn(board,turn) + 2].equals("n") || board[getKingRow(board,turn) - 1][getKingColumn(board,turn) - 2].equals("n")){
                    return true;
                }
            }
            else if(getKingRow(board,turn) < 1 && getKingColumn(board,turn) < 1){
                if(board[getKingRow(board,turn) + 2][getKingColumn(board,turn) + 1].equals("n") || board[getKingRow(board,turn) + 1][getKingColumn(board,turn) + 2].equals("n")){
                    return true;
                }
            }
            else if(getKingRow(board,turn) > 6 && getKingColumn(board,turn) > 6){
                if(board[getKingRow(board,turn) - 2][getKingColumn(board,turn) - 1].equals("n") || board[getKingRow(board,turn) - 1][getKingColumn(board,turn) - 2].equals("n")){
                    return true;
                }
            }


        }
        else if(turn % 2 != 0){
            // Check straight lines
            int y = getKingRow(board,turn); //row of king
            int count = 0; //iteration counter for up/down/left/right directions
            for(int a = getKingRow(board,turn); a < board.length; a++) { // LEFT and RIGHT (directions)
                if(count % 2 == 0){
                    //direction LEFT (approaching column 0)
                    for(int b = (getKingColumn(board,turn) - 1); b >= 0; b--) { // square by square from the king and out in the current direction (square in direction)
                        if(board[y][b].equals("R") || board[y][b].equals("Q")){ //square contains opponent rook or queen
                            return true;
                        }
                        else if(board[y][b].equals("p") || board[y][b].equals("n") || board[y][b].equals("b") || board[y][b].equals("r") || board[y][b].equals("q")){ //square contains friendly piece
                            b = -1;
                        }
                    }
                }
                else if(count % 2 != 0){
                    //direction RIGHT (approaching column 7)
                    for(int b = (getKingColumn(board,turn) + 1); b < board[a].length; b++) { // square by square from the king and out in the current direction (square in direction)
                        if(board[y][b].equals("R") || board[y][b].equals("Q")){ //square contains opponent rook or queen
                            return true;
                        }
                        else if(board[y][b].equals("p") || board[y][b].equals("n") || board[y][b].equals("b") || board[y][b].equals("r") || board[y][b].equals("q")){ //square contains friendly piece
                            b = 8;
                        }
                    }
                }
                count += 1;
            }

            int z = getKingColumn(board,turn); //column of king
            count = 0;
            for(int c = 0; c < 8; c++) { // UP and DOWN (directions)
                if(count % 2 == 0){
                    //direction UP (approaching row 0)
                    for(int d = (getKingRow(board,turn) - 1); d >= 0; d--) { // square by square from the king and out in the current direction (square in direction)
                        if(board[d][z].equals("R") || board[d][z].equals("Q")){ //square contains opponent rook or queen
                            return true;
                        }
                        else if(board[d][z].equals("p") || board[d][z].equals("n") || board[d][z].equals("b") || board[d][z].equals("r") || board[d][z].equals("q")){ //square contains friendly piece
                            d = -1;
                        }
                    }
                }
                else if(count % 2 != 0){
                    //direction DOWN (approaching row 7)
                    for(int d = (getKingRow(board,turn) + 1); d < board.length; d++) { // square by square from the king and out in the current direction (square in direction)
                        if(board[d][z].equals("R") || board[d][z].equals("Q")){ //square contains opponent rook or queen
                            return true;
                        }
                        else if(board[d][z].equals("p") || board[d][z].equals("n") || board[d][z].equals("b") || board[d][z].equals("r") || board[d][z].equals("q")){ //square contains friendly piece
                            d = 8;
                        }
                    }
                }
                count += 1;
            }


            count = 0;
            int work = getKingColumn(board,turn);
            // Check diagonals
            for(int e = (getKingColumn(board,turn) + 1); e < board.length; e++){ // RIGHT-UP and RIGHT-DOWN
                work = getKingColumn(board,turn);
                if(count % 2 == 0){
                    for(int f = (getKingRow(board,turn) - 1); f >= 0; f--){ // square by square from the king and out in the current direction
                        work += 1;
                        if(work > 7){
                            break;
                        }

                        if(board[f][work].equals("B") || board[f][work].equals("Q")){ //square contains opponent bishop or queen
                            return true;
                        }
                        else if(board[f][work].equals("p") || board[f][work].equals("n") || board[f][work].equals("b") || board[f][work].equals("r") || board[f][work].equals("q")){ //square contains friendly piece
                            f = -1;
                        }
                    }
                    e = e -1;
                }
                else if(count % 2 != 0){
                    for(int f = (getKingRow(board,turn) + 1); f < board.length; f++){ // square by square from the king and out in the current direction
                        work += 1;
                        if(work > 7){
                            break;
                        }

                        if(board[f][work].equals("B") || board[f][work].equals("Q")){ //square contains opponent bishop or queen
                            return true;
                        }
                        else if(board[f][work].equals("p") || board[f][work].equals("n") || board[f][work].equals("b") || board[f][work].equals("r") || board[f][work].equals("q")){ //square contains friendly piece
                            f = 9;
                        }
                    }
                }
                count += 1;
            }

            count = 0;
            for(int g = (getKingColumn(board,turn) - 1); g >= 0; g--){ // LEFT-UP and LEFT-DOWN
                work = getKingColumn(board,turn);
                if(count % 2 == 0){
                    for(int h = (getKingRow(board,turn) - 1); h >= 0; h--){ // square by square from the king and out in the current direction
                        work -= 1;
                        if(work < 0){
                            break;
                        }

                        if(board[h][work].equals("B") || board[h][work].equals("Q")){ //square contains opponent bishop or queen
                            return true;
                        }
                        else if(board[h][work].equals("p") || board[h][work].equals("n") || board[h][work].equals("b") || board[h][work].equals("r") || board[h][work].equals("q")){ //square contains friendly piece
                            h = -1;
                        }
                    }
                }
                else if(count % 2 != 0){
                    for(int h = (getKingRow(board,turn) + 1); h < board.length; h++){ // square by square from the king and out in the current direction
                        work -= 1;
                        if(work < 0){
                            break;
                        }

                        if(board[h][work].equals("B") || board[h][work].equals("Q")){ //square contains opponent bishop or queen
                            return true;
                        }
                        else if(board[h][work].equals("p") || board[h][work].equals("n") || board[h][work].equals("b") || board[h][work].equals("r") || board[h][work].equals("q")){ //square contains friendly piece
                            h = 9;
                        }
                    }
                }
                count += 1;
            }
            count = 0;

            // Check pawns
            if(board[getKingRow(board,turn) + 1][getKingColumn(board,turn) + 1].equals("P") || board[getKingRow(board,turn) + 1][getKingColumn(board,turn) - 1].equals("P")){ //squares where pawns would threaten the king contains pawns
                return true;
            }

            // Check king, this is to find if a square is legal to move to only
        /*if(){//squares where a king would threaten the king constains a king
            return true;
        }*/

            // Check knights
        /*if(board[row2][col2].equals("n") && (row2 == row1 + 1 && (col2 == col1 - 2 || col2 == col1 + 2)).equals("K") || (row2 == row1 - 1 && (col2 == col1 - 2 || col2 == col1 + 2)).equals("K") || (row2 == row1 + 2 && (col2 == col1 - 1 || col2 == col1 + 1)).equals("K") || (row2 == row1 - 2 && (col2 == col1 - 1 || col2 == col1 + 1)).equals("K")){ //squares where knights would threaten the king contains knights
            return true;
        }*/

        }

        return false;
    }

    public static int getKingRow(String[][] board, int turn){ //returns the row location of the king depending on who's turn it is
        if(turn % 2 == 0){
            for(int x = 0; x < board.length; x++){
                for(int y = 0; y < board[x].length; y++){
                    if(board[x][y].equals("K")){
                        return x;
                    }
                }
            }
        }
        else{
            for(int x = 0; x < board.length; x++){
                for(int y = 0; y < board[x].length; y++){
                    if(board[x][y].equals("k")){
                        return x;
                    }
                }
            }
        }

        return 0;
    }
    public static int getKingColumn(String[][] board, int turn){ //returns the column location of the king depending on who's turn it is
        if(turn % 2 == 0){
            for(int x = 0; x < board.length; x++){
                for(int y = 0; y < board[x].length; y++){
                    if(board[x][y].equals("K")){
                        return y;
                    }
                }
            }
        }
        else{
            for(int x = 0; x < board.length; x++){
                for(int y = 0; y < board[x].length; y++){
                    if(board[x][y].equals("k")){
                        return y;
                    }
                }
            }
        }

        return 0;
    }

    public static void playChess(String[][] board, int turn, boolean gameEnd, int row1, int col1, int row2, int col2){ //runs through the multiple methods to play the game of chess
        gameEnd = false;
        printBoard(board);
        while(gameEnd == false){
            System.out.println("");

            //accept and check user's selected piece
            if(turn % 2 == 0){ //player 1
                if(isKingCheck(board,turn,row2,col2) == true){
                    System.out.println("Player 1, your king is in check.");
                }
                System.out.println("Player 1, insert the piece you want to move.");
                System.out.print("Row: ");
                row1 = kb.nextInt();
                System.out.print("Column: ");
                col1 = kb.nextInt();
                checkPieceSelection(board,turn,row1,col1,row2,col2);
            }
            else{ //player 2
                if(isKingCheck(board,turn,row2,col2) == true){
                    System.out.println("Player 2, your king is in check.");
                }
                System.out.println("Player 2, insert the piece you want to move.");
                System.out.print("Row: ");
                row1 = kb.nextInt();
                System.out.print("Column: ");
                col1 = kb.nextInt();
                checkPieceSelection(board,turn,row1,col1,row2,col2);
            }

            System.out.println("");

            //accept and check user's input for the space to move to
            if(turn % 2 == 0){ //player 1
                System.out.println("Player 1, insert the space you want to move to.");
                System.out.print("Row: ");
                row2 = kb.nextInt();
                System.out.print("Column: ");
                col2 = kb.nextInt();
                checkMoveSelection(board,turn,row2,col2,row1,col1);
            }
            else{ //player 2
                System.out.println("Player 2, insert the space you want to move to.");
                System.out.print("Row: ");
                row2 = kb.nextInt();
                System.out.print("Column: ");
                col2 = kb.nextInt();
                checkMoveSelection(board,turn,row2,col2,row1,col1);
            }

            System.out.println("");
            System.out.println("");
            printBoard(board);
            turn += 1;
            System.out.println("");
            //gameEnd = true; //temporary, while coding is in progress

        }

    }

    public static void move(String[][] board, int row1, int col1, int row2, int col2){
        board[row2][col2] = board[row1][col1];
        board[row1][col1] = " ";
    }

    public static void checkMoveSelection(String[][] board, int turn, int row2, int col2, int row1, int col1){
        if(turn % 2 == 0){
            if(board[row2][col2].equals("P") || board[row2][col2].equals("R") || board[row2][col2].equals("N") || board[row2][col2].equals("B") || board[row2][col2].equals("Q") || board[row2][col2].equals("K") || board[row2][col2].equals(" ") && pieceMovement(board,row2,col2,row1,col1) == false){
                System.out.println("");
                System.out.println("Invalid selection, please re-enter values.");
                System.out.print("Row: ");
                row2 = kb.nextInt();
                System.out.print("Column: ");
                col2 = kb.nextInt();
                checkMoveSelection(board,turn,row2,col2,row1,col1);
            }
            else{
                move(board,row1,col1,row2,col2);
                if(isKingCheck(board,turn,row2,col2) == true){
                    board[row1][col1] = board[row2][col2];
                    board[row2][col2] = " ";
                    System.out.println("");
                    System.out.println("Invalid, your king is still in check.");
                    System.out.println("");
                    playChess(board,turn,gameEnd,row1,col1,row2,col2);
                }
            }

        }
        else{
            if(board[row2][col2].equals("p") || board[row2][col2].equals("r") || board[row2][col2].equals("n") || board[row2][col2].equals("b") || board[row2][col2].equals("q") || board[row2][col2].equals("k") || board[row2][col2].equals(" ") && pieceMovement(board,row2,col2,row1,col1) == false){
                System.out.println("");
                System.out.println("Invalid selection, please re-enter values.");
                System.out.print("Row: ");
                row2 = kb.nextInt();
                System.out.print("Column: ");
                col2 = kb.nextInt();
                checkMoveSelection(board,turn,row2,col2,row1,col1);
            }
            else{
                move(board,row1,col1,row2,col2);
                if(isKingCheck(board,turn,row2,col2) == true){
                    board[row1][col1] = board[row2][col2];
                    board[row2][col2] = " ";
                    System.out.println("");
                    System.out.println("Invalid, your king is still in check.");
                    System.out.println("");
                    playChess(board,turn,gameEnd,row1,col1,row2,col2);
                }
            }

        }

    }

    public static boolean pieceMovement(String[][] board, int row2, int col2, int row1, int col1){
        if(board[row1][col1].equals("p") || board[row1][col1].equals("P")){
            if(board[row1][col1].equals("p")){
                if(row2 == row1 + 3 || row2 == row1 + 4 || row2 == row1 + 5){
                    return false;
                }

                if((row2 == row1 + 1 || row2 == row1 + 2) && row1 == 1 && board[row2][col2].equals(" ")){
                    return true;
                }
                else if(row2 == row1 + 1 && col1 == col2 && board[row2][col2].equals(" ")){
                    return true;
                }
                else if(row2 == row1  + 1 && (col2 == col1 + 1 || col2 == col1 - 1)){
                    return true;
                }
            }
            else if(board[row1][col1].equals("P")){
                if(row2 == row1 - 3 || row2 == row1 - 4 || row2 == row1 - 5){
                    return false;
                }

                if((row2 == row1 - 1 || row2 == row1 - 2) && row1 == 6 && board[row2][col2].equals(" ")){
                    return true;
                }
                else if(row2 == row1 - 1 && col1 == col2 && board[row2][col2].equals(" ")){
                    return true;
                }
                else if(row2 == row1  - 1 && (col2 == col1 - 1 || col2 == col1 + 1)){
                    return true;
                }
            }
        }
        else if(board[row1][col1].equals("r") || board[row1][col1].equals("R")){
            if(board[row1][col1].equals("r")){
                if(row2 == row1 && col2 == col1){
                    return false;
                }
                else if(col2 > 7 || col2 < 0 || row2 < 0 || row2 > 7){
                    return false;
                }
                else if((row2 == row1 && col2 != col1) && (col2 > col1 || col2 < col1)){
                    return true;
                }
                else if((row2 != row1 && col2 == col1) && (row2 > row1 || row2 < row1)){
                    return true;
                }
            }
            else if(board[row1][col1].equals("R")){
                if(row2 == row1 && col2 == col1){
                    return false;
                }
                else if(col2 > 7 || col2 < 0 || row2 < 0 || row2 > 7){
                    return false;
                }
                else if((row2 == row1 && col2 != col1) && (col2 > col1 || col2 < col1)){
                    return true;
                }
                else if((row2 != row1 && col2 == col1) && (row2 > row1 || row2 < row1)){
                    return true;
                }
            }
        }
        else if(board[row1][col1].equals("n") || board[row1][col1].equals("N")){
            if(board[row1][col1].equals("n")){
                if((row2 == row1 + 1 && (col2 == col1 - 2 || col2 == col1 + 2))){
                    return true;
                }
                else if((row2 == row1 - 1 && (col2 == col1 - 2 || col2 == col1 + 2))){
                    return true;
                }
                else if((row2 == row1 + 2 && (col2 == col1 - 1 || col2 == col1 + 1))){
                    return true;
                }
                else if((row2 == row1 - 2 && (col2 == col1 - 1 || col2 == col1 + 1))){
                    return true;
                }
            }
            else if(board[row1][col1].equals("N")){
                if((row2 == row1 + 1 && (col2 == col1 - 2 || col2 == col1 + 2))){
                    return true;
                }
                else if((row2 == row1 - 1 && (col2 == col1 - 2 || col2 == col1 + 2))){
                    return true;
                }
                else if((row2 == row1 + 2 && (col2 == col1 - 1 || col2 == col1 + 1))){
                    return true;
                }
                else if((row2 == row1 - 2 && (col2 == col1 - 1 || col2 == col1 + 1))){
                    return true;
                }
            }
        }
        else if(board[row1][col1].equals("b") || board[row1][col1].equals("B")){
            if(board[row1][col1].equals("b")){
                if((row2 == row1 + 1 && col2 == col1 + 1) || (row2 == row1 - 1 && col2 == col1 - 1) || (row2 == row1 + 1 && col2 == col1 - 1) || (row2 == row1 - 1 && col2 == col1 + 1)){
                    return true;
                }
                else if((row2 == row1 + 2 && col2 == col1 + 2) || (row2 == row1 - 2 && col2 == col1 - 2) || (row2 == row1 + 2 && col2 == col1 - 2) || (row2 == row1 - 2 && col2 == col1 + 2)){
                    return true;
                }
                else if((row2 == row1 + 3 && col2 == col1 + 3) || (row2 == row1 - 3 && col2 == col1 - 3) || (row2 == row1 + 3 && col2 == col1 - 3) || (row2 == row1 - 3 && col2 == col1 + 3)){
                    return true;
                }
                else if((row2 == row1 + 4 && col2 == col1 + 4) || (row2 == row1 - 4 && col2 == col1 - 4) || (row2 == row1 + 4 && col2 == col1 - 4) || (row2 == row1 - 4 && col2 == col1 + 4)){
                    return true;
                }
                else if((row2 == row1 + 5 && col2 == col1 + 5) || (row2 == row1 - 5 && col2 == col1 - 5) || (row2 == row1 + 5 && col2 == col1 - 5) || (row2 == row1 - 5 && col2 == col1 + 5)){
                    return true;
                }
                else if((row2 == row1 + 6 && col2 == col1 + 6) || (row2 == row1 - 6 && col2 == col1 - 6) || (row2 == row1 + 6 && col2 == col1 - 6) || (row2 == row1 - 6 && col2 == col1 + 6)){
                    return true;
                }
                else if((row2 == row1 + 7 && col2 == col1 + 7) || (row2 == row1 - 7 && col2 == col1 - 7) || (row2 == row1 + 7 && col2 == col1 - 7) || (row2 == row1 - 7 && col2 == col1 + 7)){
                    return true;
                }
            }
            else if(board[row1][col1].equals("B")){
                if((row2 == row1 + 1 && col2 == col1 + 1) || (row2 == row1 - 1 && col2 == col1 - 1) || (row2 == row1 + 1 && col2 == col1 - 1) || (row2 == row1 - 1 && col2 == col1 + 1)){
                    return true;
                }
                else if((row2 == row1 + 2 && col2 == col1 + 2) || (row2 == row1 - 2 && col2 == col1 - 2) || (row2 == row1 + 2 && col2 == col1 - 2) || (row2 == row1 - 2 && col2 == col1 + 2)){
                    return true;
                }
                else if((row2 == row1 + 3 && col2 == col1 + 3) || (row2 == row1 - 3 && col2 == col1 - 3) || (row2 == row1 + 3 && col2 == col1 - 3) || (row2 == row1 - 3 && col2 == col1 + 3)){
                    return true;
                }
                else if((row2 == row1 + 4 && col2 == col1 + 4) || (row2 == row1 - 4 && col2 == col1 - 4) || (row2 == row1 + 4 && col2 == col1 - 4) || (row2 == row1 - 4 && col2 == col1 + 4)){
                    return true;
                }
                else if((row2 == row1 + 5 && col2 == col1 + 5) || (row2 == row1 - 5 && col2 == col1 - 5) || (row2 == row1 + 5 && col2 == col1 - 5) || (row2 == row1 - 5 && col2 == col1 + 5)){
                    return true;
                }
                else if((row2 == row1 + 6 && col2 == col1 + 6) || (row2 == row1 - 6 && col2 == col1 - 6) || (row2 == row1 + 6 && col2 == col1 - 6) || (row2 == row1 - 6 && col2 == col1 + 6)){
                    return true;
                }
                else if((row2 == row1 + 7 && col2 == col1 + 7) || (row2 == row1 - 7 && col2 == col1 - 7) || (row2 == row1 + 7 && col2 == col1 - 7) || (row2 == row1 - 7 && col2 == col1 + 7)){
                    return true;
                }
            }
        }
        else if(board[row1][col1].equals("q") || board[row1][col1].equals("Q")){
            if(board[row1][col1].equals("q")){
                if(row2 == row1 && col2 == col1){
                    return false;
                }
                else if(col2 > 7 || col2 < 0 || row2 < 0 || row2 > 7){
                    return false;
                }
                else if((row2 == row1 && col2 != col1) && (col2 > col1 || col2 < col1)){
                    return true;
                }
                else if((row2 != row1 && col2 == col1) && (row2 > row1 || row2 < row1)){
                    return true;
                }
                else if((row2 == row1 + 1 && col2 == col1 + 1) || (row2 == row1 - 1 && col2 == col1 - 1) || (row2 == row1 + 1 && col2 == col1 - 1) || (row2 == row1 - 1 && col2 == col1 + 1)){
                    return true;
                }
                else if((row2 == row1 + 2 && col2 == col1 + 2) || (row2 == row1 - 2 && col2 == col1 - 2) || (row2 == row1 + 2 && col2 == col1 - 2) || (row2 == row1 - 2 && col2 == col1 + 2)){
                    return true;
                }
                else if((row2 == row1 + 3 && col2 == col1 + 3) || (row2 == row1 - 3 && col2 == col1 - 3) || (row2 == row1 + 3 && col2 == col1 - 3) || (row2 == row1 - 3 && col2 == col1 + 3)){
                    return true;
                }
                else if((row2 == row1 + 4 && col2 == col1 + 4) || (row2 == row1 - 4 && col2 == col1 - 4) || (row2 == row1 + 4 && col2 == col1 - 4) || (row2 == row1 - 4 && col2 == col1 + 4)){
                    return true;
                }
                else if((row2 == row1 + 5 && col2 == col1 + 5) || (row2 == row1 - 5 && col2 == col1 - 5) || (row2 == row1 + 5 && col2 == col1 - 5) || (row2 == row1 - 5 && col2 == col1 + 5)){
                    return true;
                }
                else if((row2 == row1 + 6 && col2 == col1 + 6) || (row2 == row1 - 6 && col2 == col1 - 6) || (row2 == row1 + 6 && col2 == col1 - 6) || (row2 == row1 - 6 && col2 == col1 + 6)){
                    return true;
                }
                else if((row2 == row1 + 7 && col2 == col1 + 7) || (row2 == row1 - 7 && col2 == col1 - 7) || (row2 == row1 + 7 && col2 == col1 - 7) || (row2 == row1 - 7 && col2 == col1 + 7)){
                    return true;
                }
            }
            else if(board[row1][col1].equals("Q")){
                if(row2 == row1 && col2 == col1){
                    return false;
                }
                else if(col2 > 7 || col2 < 0 || row2 < 0 || row2 > 7){
                    return false;
                }
                else if((row2 == row1 && col2 != col1) && (col2 > col1 || col2 < col1)){
                    return true;
                }
                else if((row2 != row1 && col2 == col1) && (row2 > row1 || row2 < row1)){
                    return true;
                }
                else if((row2 == row1 + 1 && col2 == col1 + 1) || (row2 == row1 - 1 && col2 == col1 - 1) || (row2 == row1 + 1 && col2 == col1 - 1) || (row2 == row1 - 1 && col2 == col1 + 1)){
                    return true;
                }
                else if((row2 == row1 + 2 && col2 == col1 + 2) || (row2 == row1 - 2 && col2 == col1 - 2) || (row2 == row1 + 2 && col2 == col1 - 2) || (row2 == row1 - 2 && col2 == col1 + 2)){
                    return true;
                }
                else if((row2 == row1 + 3 && col2 == col1 + 3) || (row2 == row1 - 3 && col2 == col1 - 3) || (row2 == row1 + 3 && col2 == col1 - 3) || (row2 == row1 - 3 && col2 == col1 + 3)){
                    return true;
                }
                else if((row2 == row1 + 4 && col2 == col1 + 4) || (row2 == row1 - 4 && col2 == col1 - 4) || (row2 == row1 + 4 && col2 == col1 - 4) || (row2 == row1 - 4 && col2 == col1 + 4)){
                    return true;
                }
                else if((row2 == row1 + 5 && col2 == col1 + 5) || (row2 == row1 - 5 && col2 == col1 - 5) || (row2 == row1 + 5 && col2 == col1 - 5) || (row2 == row1 - 5 && col2 == col1 + 5)){
                    return true;
                }
                else if((row2 == row1 + 6 && col2 == col1 + 6) || (row2 == row1 - 6 && col2 == col1 - 6) || (row2 == row1 + 6 && col2 == col1 - 6) || (row2 == row1 - 6 && col2 == col1 + 6)){
                    return true;
                }
                else if((row2 == row1 + 7 && col2 == col1 + 7) || (row2 == row1 - 7 && col2 == col1 - 7) || (row2 == row1 + 7 && col2 == col1 - 7) || (row2 == row1 - 7 && col2 == col1 + 7)){
                    return true;
                }
            }
        }
        else if(board[row1][col1].equals("k") || board[row1][col1].equals("K")){
            if(board[row1][col1].equals("k")){
                if((row2 == row1 + 1 && col2 == col1 + 1) || (row2 == row1 - 1 && col2 == col1 - 1)){
                    return true;
                }
                else if((row2 == row1 - 1 && col2 == col1 + 1) || (row2 == row1 + 1 && col2 == col1 - 1)){
                    return true;
                }
                else if((row2 == row1 && col2 == col1 - 1) || (row2 == row1 && col2 == col1 + 1)){
                    return true;
                }
                else if((row2 == row1 - 1 && col2 == col1) || (row2 == row1 + 1 && col2 == col1)){
                    return true;
                }
                else if((board[0][4].equals("k")) && (board[0][5].equals(" ")) && (board[0][6].equals(" ")) && (board[0][7].equals("r")) && (col2 == col1 + 2) && (row2 == row1)){
                    board[0][5] = "r";
                    board[0][7] = " ";
                    return true;
                }
                else if((board[0][4].equals("k")) && (board[0][3].equals(" ")) && (board[0][2].equals(" ")) && (board[0][1].equals(" ")) && (board[0][0].equals("r")) && (col2 == col1 - 2) && (row2 == row1)){
                    board[0][3] = "r";
                    board[0][0] = " ";
                    return true;
                }
            }
            else if(board[row1][col1].equals("K")){
                if((row2 == row1 + 1 && col2 == col1 + 1) || (row2 == row1 - 1 && col2 == col1 - 1)){
                    return true;
                }
                else if((row2 == row1 - 1 && col2 == col1 + 1) || (row2 == row1 + 1 && col2 == col1 - 1)){
                    return true;
                }
                else if((row2 == row1 && col2 == col1 - 1) || (row2 == row1 && col2 == col1 + 1)){
                    return true;
                }
                else if((row2 == row1 - 1 && col2 == col1) || (row2 == row1 + 1 && col2 == col1)){
                    return true;
                }
                else if((board[7][4].equals("K")) && (board[7][5].equals(" ")) && (board[7][6].equals(" ")) && (board[7][7].equals("R")) && (col2 == col1 + 2) && (row2 == row1)){
                    board[7][5] = "R";
                    board[7][7] = " ";
                    return true;
                }
                else if((board[7][4].equals("K")) && (board[7][3].equals(" ")) && (board[7][2].equals(" ")) && (board[7][1].equals(" ")) && (board[7][0].equals("R")) && (col2 == col1 - 2) && (row2 == row1)){
                    board[7][3] = "R";
                    board[7][0] = " ";
                    return true;
                }
            }
        }

        return false;
    }

    public static void checkPieceSelection(String[][] board, int turn, int row1, int col1, int row2, int col2) {
        // Define the valid pieces based on the turn
        String validPieces = turn % 2 == 0 ? "prnbqk" : "PRNBQK";

        // Check if the selected piece is valid
        if (validPieces.contains(board[row1][col1]) || board[row1][col1].equals(" ")) {
            System.out.println("Invalid selection, please re-enter values.");
            System.out.print("Row: ");
            row1 = kb.nextInt();
            System.out.print("Column: ");
            col1 = kb.nextInt();
            checkPieceSelection(board, turn, row1, col1, row2, col2);
        }
    }


    public static void printBoard(String[][] board){ //prints the chess board's current state
        int iteration = 0;
        System.out.println("    0   1   2   3   4   5   6   7");
        System.out.println("  ---------------------------------");
        for(int x = 0; x < board.length; x++){
            iteration = 0;
            for(int y = 0; y < board[x].length; y++){
                if(iteration == 0){
                    System.out.print(x+" | "+ board[x][y] +" | ");
                }
                else{
                    System.out.print(board[x][y] +" | ");
                }
                iteration += 1;
            }
            System.out.println("");
            System.out.println("  ---------------------------------");
        }
        System.out.println("");

    }

}
