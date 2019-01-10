import java.awt.*;

public class OnePlayerGame extends Game {
    AI ai;
    int depthSize;
    boolean isMiniMax;
    boolean isSorted;

    OnePlayerGame(int size, Window w, int depthSize, boolean isMiniMax, boolean isSorted) {
        super(size, w);
        this.depthSize = depthSize;
        this.isMiniMax = isMiniMax;
        this.isSorted = isSorted;
        player1Score = 0;
        player2Score = 0;
    }

    public void initializeAI(){
        ai = new AI(window, window.currentGame, depthSize, isMiniMax, isSorted);
    }

    void player1Turn() {
        player1Score += countScoreAdded(window.currentButton, gameState, 1);
        window.pointsField.setText("Gracz nr 1    " + player1Score + "  :  " + player2Score + "    Gracz nr 2");
        System.out.println("player1 score : " + player1Score);
        if (isGameEnded()) {
            window.endGame();
        }
        movesCounter += 1;
        if (isGameEnded()) {
            window.endGame();
        }
    }

    void player2Turn() {
//        Point move = ai.play(gameState);
//        Object o = ai.alpha_beta_pruning(gameState);
        Object o = ai.playAgainstHuman(gameState);
        if(o != null) {
            Point move = (Point) o;

            setGameStatus(move);
            GameButton chosenButton = window.buttons[move.x][move.y];
            chosenButton.setChosen();
            window.currentButton = move;
            player2Score += countScoreAdded(window.currentButton, gameState, 2);
            window.pointsField.setText("Gracz nr 1    " + player1Score + "  :  " + player2Score + "    Gracz nr 2");
            window.pointsField.setText(window.pointsField.getText());
            System.out.println("player2 score : " + player2Score);
            if (isGameEnded()) {
                window.endGame();
            }
            movesCounter += 1;
            if (isGameEnded()) {
                window.endGame();
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
}

