package tictoc.model;

import com.sun.tools.javac.util.Pair;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import proxy.Internet;

public class TickTocGame {

    Deque<Player> playerList;
    Board board;

    public void initializeGame() {
        playerList = new LinkedList<>();
        Player player1 = new Player("Gaurav",new PlayingPiece(PieceType.O));
        Player player2 = new Player("Kumar",new PlayingPiece(PieceType.X));

        playerList.add(player1);
        playerList.add(player2);

        board = new Board(3,3);

    }

    public String startGame() {

        boolean isWinning = true;

        System.out.println("Game is Starting ...........");


        while (isWinning) {

            Player player = playerList.removeFirst();

            List<Pair<Integer,Integer>> freeCell = board.getFreeCell();

            board.printMatrix();

            if(freeCell.isEmpty()) {
                System.out.println("No Free Cell Available............");
                isWinning = false;
                continue;
            }

            System.out.println("Enter Position for Player.............." + player.getName());
            Scanner inputScanner = new Scanner(System.in);
            String s = inputScanner.nextLine();
            System.out.println("User Input: " + s); // Add this line to check the input
            String[] values = s.split(",");
            int inputRow = Integer.valueOf(values[0]);
            int inputColumn = Integer.valueOf(values[1]);


            Boolean insertedSafely = board.insertedSafely(inputRow,inputColumn,player);
            if(!insertedSafely) {
                System.out.println("Please enter correct position again..........."+player.getName());
                playerList.addFirst(player);
                continue;
            }
            playerList.addLast(player);

            Boolean isWinner = checkWinner(inputRow,inputColumn,player);

            if(isWinner) {
                return player.getName();
            }



        }

        return "tie";


    }


    private  boolean checkWinner(Integer inputRow, Integer inputColumn, Player player) {

        boolean row = true;
        boolean column = true;
        boolean digonalLeft  = true;
        boolean digonalRight = true;


        for(int i=0;i<board.n;i++) {
            if(board.playingPieces[inputRow][i] == null || board.playingPieces[inputRow][i] != player.getPlayingPiece()) {
                column = false;
                break;
            }
        }

        for(int i=0;i< board.m;i++) {
            if(board.playingPieces[i][inputColumn] == null || board.playingPieces[i][inputColumn] != player.getPlayingPiece()) {
                row = false;
                break;
            }
        }

        for(int i=0,j=0;i< board.m && j< board.n ; i++,j++) {
            if (board.playingPieces[i][j] == null || board.playingPieces[i][j] != player.getPlayingPiece()) {
                digonalLeft = false;
                break;
            }
        }

        for(int i= 0, j= board.n-1;i< board.m && j>=0 ; i++,j--) {
            if (board.playingPieces[i][j] == null || board.playingPieces[i][j] != player.getPlayingPiece()) {
                digonalRight = false;

            }
        }

        return  digonalLeft || digonalRight || row || column;



    }
}
