package tictoc.model;

public class Main {
    public static void main(String[] args) {
        TickTocGame tickTocGame = new TickTocGame();
        tickTocGame.initializeGame();
        System.out.println("Winner -->"+tickTocGame.startGame());

    }
}