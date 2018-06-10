import java.awt.*;

public class OnePlayerGame extends Game {

    int[][] gameStatus;//0 - unset, 1 - player1, 2 - player2
    int size;
    int userScore;
    Window window;
    AI ai;
    int player1Score;
    int player2Score;
    private int movesCounter = 0;

    OnePlayerGame(int size, Window w) {
        this.size = size;
        window = w;
        ai = new AI(window, this);
        gameStatus = new int[size][size];
        window = w;
        userScore = 0;
        player1Score = 0;
        player2Score = 0;
    }

    void player1Turn() {
        player1Score += countScoreAdded(window.currentButton);
        System.out.println("player1 score : " + player1Score);
        if(isGameEnded()) {
            window.endGame();
        }
    }

    void player2Turn() {
        movesCounter += 1;
        Point move = ai.chooseRandom();
        System.out.println(move);
        setGameStatus(move);
        GameButton chosenButton = window.buttons[move.x][move.y];
        chosenButton.setChosen();
        window.currentButton = move;
        player2Score += countScoreAdded(window.currentButton);
        System.out.println("player2 score : " + player2Score);
        if(isGameEnded()) {
            window.endGame();
        }
    }

    public int countScoreAdded(Point lastMove) {
        int score = 0;
        score += checkHorizontal(lastMove);
        score += checkVertical(lastMove);
        score += checkLeftDiagonal(lastMove);
        score += checkRightDiagonal(lastMove);
        return score;
    }

    public int checkVertical(Point lastMove) {
        int score = 0;
        boolean unset = false;
        for(int i = 0; i < size && !unset; i++) {
            if(gameStatus[lastMove.x][i] == 0) {
                unset = true;
            }
            else {
                score++;
            }
        }
        return (unset || (score == 1)) ? 0 : score;
    }

    public int checkHorizontal(Point lastMove) {
        int score = 0;
        boolean unset = false;
        for(int i = 0; i < size && !unset; i++) {
            if(gameStatus[i][lastMove.y] == 0) {
                unset = true;
            }
            else {
                score++;
            }
        }
        return (unset || (score == 1)) ? 0 : score;
    }

    public int checkLeftDiagonal(Point lastMove) {
        int score = 0;
        Point temp = new Point(lastMove.x, lastMove.y);
        boolean unset = false;
        while(temp.x >= 0 && temp.y < size && !unset) {
            if(gameStatus[temp.x][temp.y] == 0) {
                unset = true;
            }
            else {
                score++;
            }
            temp.x--;
            temp.y++;
        }
        temp.x = lastMove.x + 1;
        temp.y = lastMove.y - 1;
        while(temp.y >= 0 && temp.x < size && !unset) {
            if(gameStatus[temp.x][temp.y] == 0) {
                unset = true;
            }
            else {
                score++;
            }
            temp.x++;
            temp.y--;
        }
        return (unset || (score == 1)) ? 0 : score;
    }

    public int checkRightDiagonal(Point lastMove) {
        int score = 0;
        Point temp = new Point(lastMove.x, lastMove.y);
        boolean unset = false;
        while(temp.x >= 0 && temp.y >= 0 && !unset) {
            if(gameStatus[temp.x][temp.y] == 0) {
                unset = true;
            }
            else {
                score++;
            }
            temp.x--;
            temp.y--;
        }
        temp.x = lastMove.x + 1;
        temp.y = lastMove.y + 1;
        while(temp.y < size && temp.x < size && !unset) {
            if(gameStatus[temp.x][temp.y] == 0) {
                unset = true;
            }
            else {
                score++;
            }
            temp.x++;
            temp.y++;
        }
        return (unset || (score == 1)) ? 0 : score;
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

    public boolean isGameEnded() {
        return movesCounter == size * size;
    }

    public void setMovesCounter(int movesCounter) {
        this.movesCounter = movesCounter;
    }

    public void addMovesCounter() { this.movesCounter += 1; }

    public void setGameStatus(Point lastMove) {
        int playerSign = movesCounter%2 == 1 ? 1 : 2;
        gameStatus[lastMove.x][lastMove.y] = playerSign;
        for(int i = 0; i < gameStatus.length; i++) {
            for(int j = 0; j < gameStatus[0].length; j++) {
                System.out.print(gameStatus[i][j] + ", ");
            }
            System.out.println();
        }
    }
}
