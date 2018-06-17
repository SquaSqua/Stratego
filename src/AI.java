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
                if(safe.size() == 0) {
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

    public Point minimax(int[][] gameState) {//player - true = MAX
        boolean player = true;
        int highestScoreDifference = Integer.MIN_VALUE;
        //highestScoreDifference = player1Score - player2Score;
        Point bestMove = null;//best move returns highest difference (above 0)
        ArrayList<Point> possible = checkPossible(gameState);//all possible moves for NEW STATE
//tu był score, gdyby coś przestało działać
        for(Point move : possible) {
            int score;
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
//tu był score, gdyby coś przestało działać
        for(Point move : possible) {
            int score = 0;
            int[][] state = copyTab(gameState);
            state[move.x][move.y] = player ? 2 : 1;
            //tu był wcześniej zmeiniany newPlayer
            if(player){//tu był wcześniej newPlayer
                score += game.countScoreAdded(move, state);//tu wcześniej był gameState
            }
            else {
                score -= game.countScoreAdded(move, state);//tu wcześniej był gameState
            }
            boolean newPlayer = !player;
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

//    private Object alpha_beta_pruning(int[][] gameState) {
//        boolean player = true;
//        int highestScoreDifference = Integer.MIN_VALUE;
//        Integer alpha = Integer.MIN_VALUE;
//        Integer beta = Integer.MAX_VALUE;
//        //highestScoreDifference = player1Score - player2Score;
//        Point bestMove = null;//best move returns highest difference (above 0)
//        ArrayList<Point> possible = checkPossible(gameState);//all possible moves for NEW STATE
//        int score;
//        for(Point move : possible) {
//            int[][] state = copyTab(gameState);
//            state[move.x][move.y] = player ? 2 : 1;//new state
//            score = game.countScoreAdded(move, state);
//            player = false;
//            score += alpha_beta_pruningRec(player, state, alpha, beta);
//            if(score > highestScoreDifference) {
//                highestScoreDifference = score;
//                bestMove = move;
//            }
//        }
//        return bestMove;
//    }

//    private Integer alpha_beta_pruningRec(boolean player, int[][] board, Integer a, Integer b) {
//        ArrayList<Point> possible = checkPossible(board);
//        if(possible.size() == 0) {//check if leaf
//            return 0;
//        }
//        Integer alpha = a;//maximum węzłów nimimalnych
//        Integer beta = b;//minimum węzłów maxymalnych
//        int score = 0;
//        for(Point move : possible) {
//            int[][] state = copyTab(board);
//            state[move.x][move.y] = player ? 2 : 1;
//            boolean newPlayer = !player;
//            if(newPlayer){
//                score += game.countScoreAdded(move, state);
//            }
//            else {
//                score -= game.countScoreAdded(move, state);
//            }
//            score += alpha_beta_pruningRec(newPlayer, state);
//            if(score < lowestScore) {
//                lowestScore = score;
//            }
//            if(score > highestScore) {
//                highestScore = score;
//            }
//        }
//
//        return move;
//    }

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

    private int[][] copyTab(int[][] tab) {
        int[][] copy = new int[tab.length][tab[0].length];
        for(int i = 0; i < tab.length; i++) {
            for(int j = 0; j < tab[i].length; j++) {
                copy[i][j] = tab[i][j];
            }
        }
        return copy;
    }
}
