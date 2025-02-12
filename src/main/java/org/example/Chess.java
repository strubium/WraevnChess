package org.example;
import java.util.*;


public class Chess {
    public static Scanner kb = new Scanner(System.in); //collects user input from the console
    public static String[][] board = {
            {"r","n","b","q","k","b","n","r"},
            {"p","p","p","p","p","p","p","p"},
            {" "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "},
            {"P","P","P","P","P","P","P","P"},
            {"R","N","B","Q","K","B","N","R"}};
    public static int turn = 0; //Keeps track whose turn it is
    public static int row1; //Player's row selection for the piece to move
    public static int col1; //Player's column selection for the piece to move
    public static int row2; //Player's row selection for the location of the selected piece to move
    public static int col2; //Player's column selection for the location of the selected piece to move
    public static boolean gameEnd = false; //Keeps track of when the game is over or not

    //main method to run program
    public static void main(String[] args) {
        System.out.println("---------- Chess ----------");
        System.out.println();
        System.out.println("White is capitalized pieces, black is lowercase pieces.");
        System.out.println("Player 1 is uppercase, Player 2 is lowercase");
        System.out.println();
        System.out.println();
        playChess(board,turn,gameEnd,row1,col1,row2,col2);


    }

//methods used to run the overall program

    public static boolean isKingCheck(String[][] board, int turn) {
        int kingRow = getKingRow(board, turn);
        int kingCol = getKingColumn(board, turn);
        boolean isWhite = (turn % 2 == 0);

        // Check straight lines (Rooks and Queens)
        if (isThreatenedByLinear(board, kingRow, kingCol, isWhite)) {
            return true;
        }

        // Check diagonals (Bishops and Queens)
        if (isThreatenedByDiagonal(board, kingRow, kingCol, isWhite)) {
            return true;
        }

        // Check knights
        if (isThreatenedByKnights(board, kingRow, kingCol, isWhite)) {
            return true;
        }

        // Check pawns
        if (isThreatenedByPawns(board, kingRow, kingCol, isWhite)) {
            return true;
        }

        return false;
    }

    // Helper method to check threats from rooks and queens (straight-line attacks)
    private static boolean isThreatenedByLinear(String[][] board, int row, int col, boolean isWhite) {
        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}}; // Right, Left, Down, Up
        char enemyRook = isWhite ? 'r' : 'R';
        char enemyQueen = isWhite ? 'q' : 'Q';

        return isThreatened(board, row, col, directions, enemyRook, enemyQueen);
    }

    // Helper method to check threats from bishops and queens (diagonal attacks)
    private static boolean isThreatenedByDiagonal(String[][] board, int row, int col, boolean isWhite) {
        int[][] directions = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}}; // Diagonal directions
        char enemyBishop = isWhite ? 'b' : 'B';
        char enemyQueen = isWhite ? 'q' : 'Q';

        return isThreatened(board, row, col, directions, enemyBishop, enemyQueen);
    }

    // Generalized function for linear or diagonal threats
    private static boolean isThreatened(String[][] board, int row, int col, int[][] directions, char enemyPiece1, char enemyPiece2) {
        for (int[] dir : directions) {
            int r = row, c = col;
            while (isValid(r += dir[0], c += dir[1])) {
                if (board[r][c].equals(String.valueOf(enemyPiece1)) || board[r][c].equals(String.valueOf(enemyPiece2))) {
                    return true;
                }
                if (!board[r][c].equals(".")) { // Blocked by any piece
                    break;
                }
            }
        }
        return false;
    }

    // Helper method to check knight threats
    private static boolean isThreatenedByKnights(String[][] board, int row, int col, boolean isWhite) {
        int[][] knightMoves = {
                {2, 1}, {2, -1}, {-2, 1}, {-2, -1},
                {1, 2}, {1, -2}, {-1, 2}, {-1, -2}
        };
        char enemyKnight = isWhite ? 'n' : 'N';

        for (int[] move : knightMoves) {
            int r = row + move[0], c = col + move[1];
            if (isValid(r, c) && board[r][c].equals(String.valueOf(enemyKnight))) {
                return true;
            }
        }
        return false;
    }

    // Helper method to check pawn threats
    private static boolean isThreatenedByPawns(String[][] board, int row, int col, boolean isWhite) {
        int dir = isWhite ? -1 : 1; // White pawns move up (-1), black pawns move down (+1)
        char enemyPawn = isWhite ? 'p' : 'P';

        return (isValid(row + dir, col - 1) && board[row + dir][col - 1].equals(String.valueOf(enemyPawn))) ||
                (isValid(row + dir, col + 1) && board[row + dir][col + 1].equals(String.valueOf(enemyPawn)));
    }

    // Helper method to check if a position is within bounds
    private static boolean isValid(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
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
            for (String[] strings : board) {
                for (int y = 0; y < strings.length; y++) {
                    if (strings[y].equals("K")) {
                        return y;
                    }
                }
            }
        }
        else{
            for (String[] strings : board) {
                for (int y = 0; y < strings.length; y++) {
                    if (strings[y].equals("k")) {
                        return y;
                    }
                }
            }
        }

        return 0;
    }

    public static void playChess(String[][] board, int turn, boolean gameEnd, int row1, int col1, int row2, int col2){ //runs through the multiple methods to play the game of chess
        printBoard(board);
        while(!gameEnd){
            System.out.println();

            //accept and check user's selected piece
            if(turn % 2 == 0){ //player 1
                if(isKingCheck(board, turn)){
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
                if(isKingCheck(board, turn)){
                    System.out.println("Player 2, your king is in check.");
                }
                System.out.println("Player 2, insert the piece you want to move.");
                System.out.print("Row: ");
                row1 = kb.nextInt();
                System.out.print("Column: ");
                col1 = kb.nextInt();
                checkPieceSelection(board,turn,row1,col1,row2,col2);
            }

            System.out.println();

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

            System.out.println();
            System.out.println();
            printBoard(board);
            turn += 1;
            System.out.println();
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
                System.out.println();
                System.out.println("Invalid selection, please re-enter values.");
                System.out.print("Row: ");
                row2 = kb.nextInt();
                System.out.print("Column: ");
                col2 = kb.nextInt();
                checkMoveSelection(board,turn,row2,col2,row1,col1);
            }
            else{
                move(board,row1,col1,row2,col2);
                if(isKingCheck(board, turn)){
                    board[row1][col1] = board[row2][col2];
                    board[row2][col2] = " ";
                    System.out.println();
                    System.out.println("Invalid, your king is still in check.");
                    System.out.println();
                    playChess(board,turn,gameEnd,row1,col1,row2,col2);
                }
            }

        }
        else{
            if(board[row2][col2].equals("p") || board[row2][col2].equals("r") || board[row2][col2].equals("n") || board[row2][col2].equals("b") || board[row2][col2].equals("q") || board[row2][col2].equals("k") || board[row2][col2].equals(" ") && pieceMovement(board,row2,col2,row1,col1) == false){
                System.out.println();
                System.out.println("Invalid selection, please re-enter values.");
                System.out.print("Row: ");
                row2 = kb.nextInt();
                System.out.print("Column: ");
                col2 = kb.nextInt();
                checkMoveSelection(board,turn,row2,col2,row1,col1);
            }
            else{
                move(board,row1,col1,row2,col2);
                if(isKingCheck(board, turn)){
                    board[row1][col1] = board[row2][col2];
                    board[row2][col2] = " ";
                    System.out.println();
                    System.out.println("Invalid, your king is still in check.");
                    System.out.println();
                    playChess(board,turn,gameEnd,row1,col1,row2,col2);
                }
            }

        }

    }

    public static boolean pieceMovement(String[][] board, int row2, int col2, int row1, int col1){
        switch (board[row1][col1]) {
            case "p", "P" -> {
                if (board[row1][col1].equals("p")) {
                    if (row2 == row1 + 3 || row2 == row1 + 4 || row2 == row1 + 5) {
                        return false;
                    }

                    if ((row2 == row1 + 1 || row2 == row1 + 2) && row1 == 1 && board[row2][col2].equals(" ")) {
                        return true;
                    } else if (row2 == row1 + 1 && col1 == col2 && board[row2][col2].equals(" ")) {
                        return true;
                    } else if (row2 == row1 + 1 && (col2 == col1 + 1 || col2 == col1 - 1)) {
                        return true;
                    }
                } else if (board[row1][col1].equals("P")) {
                    if (row2 == row1 - 3 || row2 == row1 - 4 || row2 == row1 - 5) {
                        return false;
                    }

                    if ((row2 == row1 - 1 || row2 == row1 - 2) && row1 == 6 && board[row2][col2].equals(" ")) {
                        return true;
                    } else if (row2 == row1 - 1 && col1 == col2 && board[row2][col2].equals(" ")) {
                        return true;
                    } else if (row2 == row1 - 1 && (col2 == col1 - 1 || col2 == col1 + 1)) {
                        return true;
                    }
                }
            }
            case "r", "R" -> {
                if (board[row1][col1].equals("r")) {
                    if (row2 == row1 && col2 == col1) {
                        return false;
                    } else if (col2 > 7 || col2 < 0 || row2 < 0 || row2 > 7) {
                        return false;
                    } else if ((row2 == row1 && col2 != col1) && (col2 > col1 || col2 < col1)) {
                        return true;
                    } else if ((row2 != row1 && col2 == col1) && (row2 > row1 || row2 < row1)) {
                        return true;
                    }
                } else if (board[row1][col1].equals("R")) {
                    if (row2 == row1 && col2 == col1) {
                        return false;
                    } else if (col2 > 7 || col2 < 0 || row2 < 0 || row2 > 7) {
                        return false;
                    } else if ((row2 == row1 && col2 != col1) && (col2 > col1 || col2 < col1)) {
                        return true;
                    } else if ((row2 != row1 && col2 == col1) && (row2 > row1 || row2 < row1)) {
                        return true;
                    }
                }
            }
            case "n", "N" -> {
                if (board[row1][col1].equals("n")) {
                    if ((row2 == row1 + 1 && (col2 == col1 - 2 || col2 == col1 + 2))) {
                        return true;
                    } else if ((row2 == row1 - 1 && (col2 == col1 - 2 || col2 == col1 + 2))) {
                        return true;
                    } else if ((row2 == row1 + 2 && (col2 == col1 - 1 || col2 == col1 + 1))) {
                        return true;
                    } else if ((row2 == row1 - 2 && (col2 == col1 - 1 || col2 == col1 + 1))) {
                        return true;
                    }
                } else if (board[row1][col1].equals("N")) {
                    if ((row2 == row1 + 1 && (col2 == col1 - 2 || col2 == col1 + 2))) {
                        return true;
                    } else if ((row2 == row1 - 1 && (col2 == col1 - 2 || col2 == col1 + 2))) {
                        return true;
                    } else if ((row2 == row1 + 2 && (col2 == col1 - 1 || col2 == col1 + 1))) {
                        return true;
                    } else if ((row2 == row1 - 2 && (col2 == col1 - 1 || col2 == col1 + 1))) {
                        return true;
                    }
                }
            }
            case "b", "B" -> {
                if (board[row1][col1].equals("b")) {
                    if ((row2 == row1 + 1 && col2 == col1 + 1) || (row2 == row1 - 1 && col2 == col1 - 1) || (row2 == row1 + 1 && col2 == col1 - 1) || (row2 == row1 - 1 && col2 == col1 + 1)) {
                        return true;
                    } else if ((row2 == row1 + 2 && col2 == col1 + 2) || (row2 == row1 - 2 && col2 == col1 - 2) || (row2 == row1 + 2 && col2 == col1 - 2) || (row2 == row1 - 2 && col2 == col1 + 2)) {
                        return true;
                    } else if ((row2 == row1 + 3 && col2 == col1 + 3) || (row2 == row1 - 3 && col2 == col1 - 3) || (row2 == row1 + 3 && col2 == col1 - 3) || (row2 == row1 - 3 && col2 == col1 + 3)) {
                        return true;
                    } else if ((row2 == row1 + 4 && col2 == col1 + 4) || (row2 == row1 - 4 && col2 == col1 - 4) || (row2 == row1 + 4 && col2 == col1 - 4) || (row2 == row1 - 4 && col2 == col1 + 4)) {
                        return true;
                    } else if ((row2 == row1 + 5 && col2 == col1 + 5) || (row2 == row1 - 5 && col2 == col1 - 5) || (row2 == row1 + 5 && col2 == col1 - 5) || (row2 == row1 - 5 && col2 == col1 + 5)) {
                        return true;
                    } else if ((row2 == row1 + 6 && col2 == col1 + 6) || (row2 == row1 - 6 && col2 == col1 - 6) || (row2 == row1 + 6 && col2 == col1 - 6) || (row2 == row1 - 6 && col2 == col1 + 6)) {
                        return true;
                    } else if ((row2 == row1 + 7 && col2 == col1 + 7) || (row2 == row1 - 7 && col2 == col1 - 7) || (row2 == row1 + 7 && col2 == col1 - 7) || (row2 == row1 - 7 && col2 == col1 + 7)) {
                        return true;
                    }
                } else if (board[row1][col1].equals("B")) {
                    if ((row2 == row1 + 1 && col2 == col1 + 1) || (row2 == row1 - 1 && col2 == col1 - 1) || (row2 == row1 + 1 && col2 == col1 - 1) || (row2 == row1 - 1 && col2 == col1 + 1)) {
                        return true;
                    } else if ((row2 == row1 + 2 && col2 == col1 + 2) || (row2 == row1 - 2 && col2 == col1 - 2) || (row2 == row1 + 2 && col2 == col1 - 2) || (row2 == row1 - 2 && col2 == col1 + 2)) {
                        return true;
                    } else if ((row2 == row1 + 3 && col2 == col1 + 3) || (row2 == row1 - 3 && col2 == col1 - 3) || (row2 == row1 + 3 && col2 == col1 - 3) || (row2 == row1 - 3 && col2 == col1 + 3)) {
                        return true;
                    } else if ((row2 == row1 + 4 && col2 == col1 + 4) || (row2 == row1 - 4 && col2 == col1 - 4) || (row2 == row1 + 4 && col2 == col1 - 4) || (row2 == row1 - 4 && col2 == col1 + 4)) {
                        return true;
                    } else if ((row2 == row1 + 5 && col2 == col1 + 5) || (row2 == row1 - 5 && col2 == col1 - 5) || (row2 == row1 + 5 && col2 == col1 - 5) || (row2 == row1 - 5 && col2 == col1 + 5)) {
                        return true;
                    } else if ((row2 == row1 + 6 && col2 == col1 + 6) || (row2 == row1 - 6 && col2 == col1 - 6) || (row2 == row1 + 6 && col2 == col1 - 6) || (row2 == row1 - 6 && col2 == col1 + 6)) {
                        return true;
                    } else if ((row2 == row1 + 7 && col2 == col1 + 7) || (row2 == row1 - 7 && col2 == col1 - 7) || (row2 == row1 + 7 && col2 == col1 - 7) || (row2 == row1 - 7 && col2 == col1 + 7)) {
                        return true;
                    }
                }
            }
            case "q", "Q" -> {
                if (board[row1][col1].equals("q")) {
                    if (row2 == row1 && col2 == col1) {
                        return false;
                    } else if (col2 > 7 || col2 < 0 || row2 < 0 || row2 > 7) {
                        return false;
                    } else if ((row2 == row1 && col2 != col1) && (col2 > col1 || col2 < col1)) {
                        return true;
                    } else if ((row2 != row1 && col2 == col1) && (row2 > row1 || row2 < row1)) {
                        return true;
                    } else if ((row2 == row1 + 1 && col2 == col1 + 1) || (row2 == row1 - 1 && col2 == col1 - 1) || (row2 == row1 + 1 && col2 == col1 - 1) || (row2 == row1 - 1 && col2 == col1 + 1)) {
                        return true;
                    } else if ((row2 == row1 + 2 && col2 == col1 + 2) || (row2 == row1 - 2 && col2 == col1 - 2) || (row2 == row1 + 2 && col2 == col1 - 2) || (row2 == row1 - 2 && col2 == col1 + 2)) {
                        return true;
                    } else if ((row2 == row1 + 3 && col2 == col1 + 3) || (row2 == row1 - 3 && col2 == col1 - 3) || (row2 == row1 + 3 && col2 == col1 - 3) || (row2 == row1 - 3 && col2 == col1 + 3)) {
                        return true;
                    } else if ((row2 == row1 + 4 && col2 == col1 + 4) || (row2 == row1 - 4 && col2 == col1 - 4) || (row2 == row1 + 4 && col2 == col1 - 4) || (row2 == row1 - 4 && col2 == col1 + 4)) {
                        return true;
                    } else if ((row2 == row1 + 5 && col2 == col1 + 5) || (row2 == row1 - 5 && col2 == col1 - 5) || (row2 == row1 + 5 && col2 == col1 - 5) || (row2 == row1 - 5 && col2 == col1 + 5)) {
                        return true;
                    } else if ((row2 == row1 + 6 && col2 == col1 + 6) || (row2 == row1 - 6 && col2 == col1 - 6) || (row2 == row1 + 6 && col2 == col1 - 6) || (row2 == row1 - 6 && col2 == col1 + 6)) {
                        return true;
                    } else if ((row2 == row1 + 7 && col2 == col1 + 7) || (row2 == row1 - 7 && col2 == col1 - 7) || (row2 == row1 + 7 && col2 == col1 - 7) || (row2 == row1 - 7 && col2 == col1 + 7)) {
                        return true;
                    }
                } else if (board[row1][col1].equals("Q")) {
                    if (row2 == row1 && col2 == col1) {
                        return false;
                    } else if (col2 > 7 || col2 < 0 || row2 < 0 || row2 > 7) {
                        return false;
                    } else if ((row2 == row1 && col2 != col1) && (col2 > col1 || col2 < col1)) {
                        return true;
                    } else if ((row2 != row1 && col2 == col1) && (row2 > row1 || row2 < row1)) {
                        return true;
                    } else if ((row2 == row1 + 1 && col2 == col1 + 1) || (row2 == row1 - 1 && col2 == col1 - 1) || (row2 == row1 + 1 && col2 == col1 - 1) || (row2 == row1 - 1 && col2 == col1 + 1)) {
                        return true;
                    } else if ((row2 == row1 + 2 && col2 == col1 + 2) || (row2 == row1 - 2 && col2 == col1 - 2) || (row2 == row1 + 2 && col2 == col1 - 2) || (row2 == row1 - 2 && col2 == col1 + 2)) {
                        return true;
                    } else if ((row2 == row1 + 3 && col2 == col1 + 3) || (row2 == row1 - 3 && col2 == col1 - 3) || (row2 == row1 + 3 && col2 == col1 - 3) || (row2 == row1 - 3 && col2 == col1 + 3)) {
                        return true;
                    } else if ((row2 == row1 + 4 && col2 == col1 + 4) || (row2 == row1 - 4 && col2 == col1 - 4) || (row2 == row1 + 4 && col2 == col1 - 4) || (row2 == row1 - 4 && col2 == col1 + 4)) {
                        return true;
                    } else if ((row2 == row1 + 5 && col2 == col1 + 5) || (row2 == row1 - 5 && col2 == col1 - 5) || (row2 == row1 + 5 && col2 == col1 - 5) || (row2 == row1 - 5 && col2 == col1 + 5)) {
                        return true;
                    } else if ((row2 == row1 + 6 && col2 == col1 + 6) || (row2 == row1 - 6 && col2 == col1 - 6) || (row2 == row1 + 6 && col2 == col1 - 6) || (row2 == row1 - 6 && col2 == col1 + 6)) {
                        return true;
                    } else if ((row2 == row1 + 7 && col2 == col1 + 7) || (row2 == row1 - 7 && col2 == col1 - 7) || (row2 == row1 + 7 && col2 == col1 - 7) || (row2 == row1 - 7 && col2 == col1 + 7)) {
                        return true;
                    }
                }
            }
            case "k", "K" -> {
                if (board[row1][col1].equals("k")) {
                    if ((row2 == row1 + 1 && col2 == col1 + 1) || (row2 == row1 - 1 && col2 == col1 - 1)) {
                        return true;
                    } else if ((row2 == row1 - 1 && col2 == col1 + 1) || (row2 == row1 + 1 && col2 == col1 - 1)) {
                        return true;
                    } else if ((row2 == row1 && col2 == col1 - 1) || (row2 == row1 && col2 == col1 + 1)) {
                        return true;
                    } else if ((row2 == row1 - 1 && col2 == col1) || (row2 == row1 + 1 && col2 == col1)) {
                        return true;
                    } else if ((board[0][4].equals("k")) && (board[0][5].equals(" ")) && (board[0][6].equals(" ")) && (board[0][7].equals("r")) && (col2 == col1 + 2) && (row2 == row1)) {
                        board[0][5] = "r";
                        board[0][7] = " ";
                        return true;
                    } else if ((board[0][4].equals("k")) && (board[0][3].equals(" ")) && (board[0][2].equals(" ")) && (board[0][1].equals(" ")) && (board[0][0].equals("r")) && (col2 == col1 - 2) && (row2 == row1)) {
                        board[0][3] = "r";
                        board[0][0] = " ";
                        return true;
                    }
                } else if (board[row1][col1].equals("K")) {
                    if ((row2 == row1 + 1 && col2 == col1 + 1) || (row2 == row1 - 1 && col2 == col1 - 1)) {
                        return true;
                    } else if ((row2 == row1 - 1 && col2 == col1 + 1) || (row2 == row1 + 1 && col2 == col1 - 1)) {
                        return true;
                    } else if ((row2 == row1 && col2 == col1 - 1) || (row2 == row1 && col2 == col1 + 1)) {
                        return true;
                    } else if ((row2 == row1 - 1 && col2 == col1) || (row2 == row1 + 1 && col2 == col1)) {
                        return true;
                    } else if ((board[7][4].equals("K")) && (board[7][5].equals(" ")) && (board[7][6].equals(" ")) && (board[7][7].equals("R")) && (col2 == col1 + 2) && (row2 == row1)) {
                        board[7][5] = "R";
                        board[7][7] = " ";
                        return true;
                    } else if ((board[7][4].equals("K")) && (board[7][3].equals(" ")) && (board[7][2].equals(" ")) && (board[7][1].equals(" ")) && (board[7][0].equals("R")) && (col2 == col1 - 2) && (row2 == row1)) {
                        board[7][3] = "R";
                        board[7][0] = " ";
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public static void checkPieceSelection(String[][] board, int turn, int row1, int col1, int row2, int col2) { //TODO row2 and col2 arent used?
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


    /**
     * Prints the current state of the chessboard.
     *
     * @param board A 2D string array representing the chessboard.
     */
    public static void printBoard(String[][] board){
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
            System.out.println();
            System.out.println("  ---------------------------------");
        }
        System.out.println();

    }

}
