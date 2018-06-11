import java.awt.*;

public class OnePlayerGame extends Game {

    int userScore;
    AI ai;
    int player1Score;
    int player2Score;


    OnePlayerGame(int size, Window w) {
        super(size, w);
        ai = new AI(window, this);
        userScore = 0;
        player1Score = 0;
        player2Score = 0;
    }

    void player1Turn() {
        player1Score += countScoreAdded(window.currentButton, gameState);
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
//        Point move = ai.chooseRandom();
        Point move = ai.minimax(gameState);
        setGameStatus(move);
        GameButton chosenButton = window.buttons[move.x][move.y];
        chosenButton.setChosen();
        window.currentButton = move;
        player2Score += countScoreAdded(window.currentButton, gameState);
        System.out.println("player2 score : " + player2Score);
        if (isGameEnded()) {
            window.endGame();
        }
        movesCounter += 1;
        if (isGameEnded()) {
            window.endGame();
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

