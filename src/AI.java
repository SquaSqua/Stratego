import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class AI{
    Window window;
    Game game;
    int size;
    ArrayList<Point> possibleMoves;

    public AI(Window window, Game game) {
        super();
        this.window = window;
        this.game = game;
        size = window.size;
    }

    public Point play(int[][] gameState) {
        Point move;
        if(size * size - game.movesCounter > 9) {
            Object best = chooseBestClosing(gameState);
            if(best == null) {
                ArrayList<Point> safe = possibleSafe(gameState);
                if(safe == null) {
                    Object randomMove = chooseRandom(gameState);
                    move = (Point) randomMove;
                }
                else {
                    move = (Point)chooseRandom(safe);
                }
            }
            else {
                move = (Point)best;
            }
        }
        else {
            move = minimax(gameState);
        }
        return move;
    }
//
//    public Point findBestMove() {
//
//    }
//
//    public int rateMove() {
//
//    }
    public Object chooseRandom(int[][] state) {
        possibleMoves = checkPossible(state);//aktualizacja possibleMoves
        return chooseRandom(possibleMoves);
    }

    public Object chooseRandom(ArrayList<Point> possible) {
        Random random = new Random();
        int possibleMovesSize = possible.size();
        return possibleMovesSize != 0 ?  possible.get(random.nextInt(possibleMovesSize)) : null;
    }

    private ArrayList<Point> checkPossible(int[][] board) {
        ArrayList<Point> result = new ArrayList<>();
        for(int x  = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if(board[x][y] == 0){
                    result.add(new Point(x, y));
                }
            }
        }
        return result;
    }

    public Point minimax(int[][] gameState) {//player = {1 lub 2}
        boolean player = true;
        int highestScoreDifference = Integer.MIN_VALUE;
        //highestScoreDifference = player1Score - player2Score;

        //czy tu nie ma problemu, że przypisuję nulla do Pointa? o.O

        Point bestMove = null;//best move returns highest difference (above 0)
        ArrayList<Point> possible = checkPossible(gameState);//all possible moves for NEW STATE
        int score;
        for(Point move : possible) {
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
        ArrayList<Point> possible = checkPossible(gameState);
        if(possible.size() == 0) {
            return 0;
        }
        int lowestScore = Integer.MAX_VALUE;
        int highestScore = Integer.MIN_VALUE;
        int score = 0;
        for(Point move : possible) {
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

    public Object chooseBestClosing(int[][] gameState) {
        possibleMoves = checkPossible(gameState);
        Point bestMove = null;
        int maxWage = 0;
        for(Point move : possibleMoves) {
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

    public ArrayList<Point> possibleSafe(int[][] gameState) {//returns all not danger possible moves;
        possibleMoves = checkPossible(gameState);
        ArrayList<Point> possibleSafe = new ArrayList<>(possibleMoves);
        ArrayList<Point> dangerMoves = new ArrayList<>();
        for(Point move : possibleMoves) {
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
