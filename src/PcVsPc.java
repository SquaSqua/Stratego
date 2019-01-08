import java.awt.*;

public class PcVsPc extends Game {
    AI player1;
    AI player2;
    int depthSize;
    boolean isMiniMax;
    boolean isSorted;
    int depthSize2;
    boolean isMiniMax2;
    boolean isSorted2;
    PcVsPc(int size, Window w, int depthSize, boolean isMiniMax, boolean isSorted, int depthSize2, boolean isMiniMax2, boolean isSorted2) {
        super(size, w);
        this.depthSize = depthSize;
        this.isMiniMax = isMiniMax;
        this.isSorted = isSorted;
        this.depthSize2 = depthSize2;
        this.isMiniMax2 = isMiniMax2;
        this.isSorted2 = isSorted2;
        player1Score = 0;
        player2Score = 0;
    }

    void player1Turn() {
        Object o = player1.play(gameState);
        if(o != null) {
            Point move = (Point) o;
//            System.out.println("player1:");
            setGameStatus(move);
            window.currentButton = move;
            player1Score += countScoreAdded(window.currentButton, gameState, 1);
            if (isGameEnded()) {
                window.endGame();
            }
            movesCounter += 1;
            if (isGameEnded()) {
                window.endGame();
            }
        }
    }

    void player2Turn() {
        Object o = player2.play(gameState);
        if(o != null) {
            Point move = (Point) o;
//            System.out.println("player2:");
            setGameStatus(move);
            window.currentButton = move;
            player2Score += countScoreAdded(window.currentButton, gameState, 2);
            if (isGameEnded()) {
                window.endGame();
            }
            movesCounter += 1;
            if (isGameEnded()) {
                window.endGame();
            }
        }
    }

    public void playTheGame() {
        while (!isGameEnded()){
            player1Turn();
            if(!isGameEnded()) {
                player2Turn();
            }
        }

    }

    public void displayScore() {
        if(player1Score == player2Score) {
            System.out.println("Remis");
        }
        else {
            System.out.print("Wygrywa gracz nr ");
            if(player1Score > player2Score) {
                System.out.println("1!");
                System.out.println("Zdobyte punkty: " + player1Score);
            }
            else {
                System.out.println("2!");
                System.out.println("Zdobyte punkty: " + player2Score);
            }
        }
    }

    public void showBoard(int[][] gameState) {
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                int player = gameState[i][j];
                GameButton button = window.buttons[i][j];
                if(player == 1) {
                    button.setIcon(button.allay);
                    button.seted();
                }
                else if(player == 2) {
                    button.setIcon(button.enemy);
                    button.seted();
                }
            }
        }
    }

    public void initializeAI() {
        this.player1 = new AI(window, window.currentGame, depthSize, isMiniMax, isSorted);
        this.player2 = new AI(window, window.currentGame, depthSize2, isMiniMax2, isSorted2);
    }
}
