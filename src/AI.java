import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class AI{
    Window window;
    Game game;
    int size;
    ArrayList<AIPoint> possibleMoves;

    public AI(Window window, Game game) {
        super();
        this.window = window;
        this.game = game;
        size = window.size;
    }

    public AIPoint play(int[][] gameState) {
        AIPoint move;
        if(size * size - game.movesCounter > 9) {
            move = chooseBestClosing(gameState);
            if(move == null) {
                ArrayList<AIPoint> safe = possibleSafe(gameState);
                if(safe == null) {
                    move = chooseRandom(gameState);
                }
                else {
                    move = chooseRandom(gameState, safe);
                }
            }
        }
        else {
            move = minimax(gameState);
        }
        return move;
    }
//
//    public AIPoint findBestMove() {
//
//    }
//
//    public int rateMove() {
//
//    }
    public AIPoint chooseRandom(int[][] state) {
        return chooseRandom(state, possibleMoves);
    }

    public AIPoint chooseRandom(int[][] state, ArrayList<AIPoint> possible) {
        possible = checkPossible(state);
        Random random = new Random();
        int possibleMovesSize = possible.size();
        return possibleMovesSize != 0 ?  possible.get(random.nextInt(possibleMovesSize)) : null;
    }

    private ArrayList<AIPoint> checkPossible(int[][] board) {
        ArrayList<AIPoint> result = new ArrayList<>();
        for(int x  = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if(board[x][y] == 0){
                    result.add(new AIPoint(x, y));
                }
            }
        }
        return result;
    }

    public AIPoint minimax(int[][] gameState) {//player = {1 lub 2}
        boolean player = true;
        int highestScoreDifference = Integer.MIN_VALUE;
        //highestScoreDifference = player1Score - player2Score;
        AIPoint bestMove = null;//best move returns highest difference (above 0)
        ArrayList<AIPoint> possible = checkPossible(gameState);//all possible moves for NEW STATE
        int score;
        for(AIPoint move : possible) {
            int[][] state = copyTab(gameState);
            state[move.x][move.y] = player ? 2 : 1;//the new state
            score = game.countScoreAdded(move, state);
            player = false;
            score += minimaxRec(player, state);
            if(score > highestScoreDifference) {
                highestScoreDifference = score;
                bestMove = move;
            }
        }
        return bestMove;
    }

    private int minimaxRec(boolean player, int[][] gameState) {
        ArrayList<AIPoint> possible = checkPossible(gameState);
        if(possible.size() == 0) {
            return 0;
        }
        int lowestScore = Integer.MAX_VALUE;
        int highestScore = Integer.MIN_VALUE;
        int score = 0;
        for(AIPoint move : possible) {
            int[][] state = copyTab(gameState);
            state[move.x][move.y] = player ? 2 : 1;

            boolean newPlayer = !player;
            if(newPlayer){
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
        return player ? highestScore : lowestScore;
    }

    private int[][] copyTab(int[][] tab) {
        int[][] copy = new int[tab.length][tab[0].length];
        for(int i = 0; i < tab.length; i++) {
            for(int j = 0; j < tab[i].length; j++) {
                copy[i][j] = tab[i][j];
            }
        }
        return copy;
    }

    public AIPoint chooseBestClosing(int[][] gameState) {
        checkPossible(gameState);
        AIPoint bestMove = new AIPoint();
        int maxWage = 0;
        for(AIPoint move : possibleMoves) {
            int[][] board = copyTab(gameState);
            board[move.x][move.y] = 1;
            int score = game.countScoreAdded(move, board);
            if(score > maxWage) {
                maxWage = score;
                bestMove = move;
            }
        }
        return bestMove;
    }

    public ArrayList<AIPoint> possibleSafe(int[][] gameState) {//returns all not danger possible moves;
        checkPossible(gameState);
        ArrayList<AIPoint> possibleSafe = new ArrayList<>(possibleMoves);
        ArrayList<AIPoint> dangerMoves = new ArrayList<>();
        for(AIPoint move : possibleMoves) {
            if(game.countZerosHorizontal(move, gameState) == 2){
                dangerMoves.add(move);
            }
            else if(game.countZerosVertical(move, gameState) == 2) {
                dangerMoves.add(move);
            }else if(game.countZerosLeftDiagonal(move, gameState) == 2) {
                dangerMoves.add(move);
            }else if(game.countZerosRightDiagonal(move, gameState) == 2) {
                dangerMoves.add(move);
            }
        }
        possibleSafe.removeAll(dangerMoves);
        return possibleSafe;
    }
}
