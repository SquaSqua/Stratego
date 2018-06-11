import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class AI{
    Window window;
    Game game;
    int[][] gameState;
    int size;
    ArrayList<Point> possibleMoves;

    public AI(Window window, Game game) {
        super();
        this.window = window;
        this.game = game;
        size = window.size;
    }

//    public Point play(int[][] gameStatus) {
//
//    }
//
//    public Point findBestMove() {
//
//    }
//
//    public int rateMove() {
//
//    }
    public Point chooseRandom() {
        possibleMoves = checkPossible(gameState);
        Random random = new Random();
        int possibleMovesSize = possibleMoves.size();
        return possibleMovesSize != 0 ?  possibleMoves.get(random.nextInt(possibleMovesSize)) : null;
    }

    private ArrayList<Point> checkPossible(int[][] gameStatus) {
        ArrayList<Point> result = new ArrayList<>();
        gameStatus = ((OnePlayerGame)game).gameState;
        for(int x  = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if(gameStatus[x][y] == 0){
                    result.add(new Point(x, y));
                }
            }
        }
        return result;
    }

    public Point minimax(int[][] gameState) {//player = {1 lub 2}
        int player = 1;
        int highestScoreDifference = Integer.MIN_VALUE;
        //highestScoreDifference = player1Score - player2Score;
        Point bestMove = null;//best move returns higest difference (above 0)
        ArrayList<Point> possible = checkPossible(gameState);//all possible moves for NEW STATE
        int score;
        for(Point move : possible) {
            int[][] state = gameState.clone();
            state[move.x][move.y] = player;//the new state
            score = game.countScoreAdded(move, state);
            int newPlayer = (player+1)%2 == 0 ? 1 : 2;
            score += minimaxRec(newPlayer, state);
            if(score > highestScoreDifference) {
                highestScoreDifference = score;
                bestMove = move;
            }
        }
        return bestMove;
    }

    private int minimaxRec(int player, int[][] gameState) {
        ArrayList<Point> possible = checkPossible(gameState);
        if(possible.size() == 0) {
            return 0;
        }
        int lowestScore = Integer.MAX_VALUE;
        int highestScore = Integer.MIN_VALUE;
        int score = 0;
        for(Point move : possible) {
            int[][] state = gameState.clone();
            state[move.x][move.y] = player;

            int newPlayer = (player+1)%2 == 0 ? 1 : 2;
            if(newPlayer == 1){
                score += game.countScoreAdded(move, gameState);
            }
            else {
                score -= game.countScoreAdded(move, gameState);
            }
            score += minimaxRec(newPlayer, state);
            if(score < lowestScore) {
                lowestScore = score;
            }
            if(score > highestScore) {
                highestScore = score;
            }
        }
        return player == 1 ? highestScore : lowestScore;
    }
//    public Point chooseClosing() {
//
//    }
}
