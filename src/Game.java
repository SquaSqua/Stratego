import java.awt.*;

public class Game {

    int size;
    Window window;
    int[][] gameState;//0 - unset, 1 - player1, 2 - player2
    public static int movesCounter = 0;

    public Game(int size, Window w) {
        this.size = size;
        window = w;
        gameState = new int[size][size];
    }

    public int countScoreAdded(Point lastMove, int[][] gameState) {
        int score = 0;
        score += checkHorizontal(lastMove, gameState);
        score += checkVertical(lastMove, gameState);
        score += checkLeftDiagonal(lastMove, gameState);
        score += checkRightDiagonal(lastMove, gameState);
        return score;
    }

    public int checkVertical(Point lastMove, int[][] gameState) {
        int score = 0;
        boolean unset = false;
        for(int i = 0; i < size && !unset; i++) {
            if(gameState[lastMove.x][i] == 0) {
                unset = true;
            }
            else {
                score++;
            }
        }
        return (unset || (score == 1)) ? 0 : score;
    }

    public int checkHorizontal(Point lastMove, int[][] gameState) {
        int score = 0;
        boolean unset = false;
        for(int i = 0; i < size && !unset; i++) {
            if(gameState[i][lastMove.y] == 0) {
                unset = true;
            }
            else {
                score++;
            }
        }
        return (unset || (score == 1)) ? 0 : score;
    }

    public int checkLeftDiagonal(Point lastMove, int[][] gameState) {
        int score = 0;
        Point temp = new Point(lastMove.x, lastMove.y);
        boolean unset = false;
        while(temp.x >= 0 && temp.y < size && !unset) {
            if(gameState[temp.x][temp.y] == 0) {
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
            if(gameState[temp.x][temp.y] == 0) {
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

    public int checkRightDiagonal(Point lastMove, int[][] gameState) {
        int score = 0;
        Point temp = new Point(lastMove.x, lastMove.y);
        boolean unset = false;
        while(temp.x >= 0 && temp.y >= 0 && !unset) {
            if(gameState[temp.x][temp.y] == 0) {
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
            if(gameState[temp.x][temp.y] == 0) {
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



    public boolean isGameEnded() {
        return movesCounter == size * size;
    }

    public void setMovesCounter(int movesCounter) {
        this.movesCounter = movesCounter;
    }

    public void addMovesCounter() { this.movesCounter += 1; }

    public void setGameStatus(Point lastMove) {
        int playerSign = movesCounter%2 == 1 ? 2 : 1;
        gameState[lastMove.x][lastMove.y] = playerSign;
        for(int i = 0; i < gameState.length; i++) {
            for(int j = 0; j < gameState[0].length; j++) {
                System.out.print(gameState[i][j] + ", ");
            }
            System.out.println();
        }
    }

}
