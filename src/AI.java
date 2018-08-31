import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Random;

public class AI{
    Window window;
    Game game;
    int size;
    int depthSize;
    ArrayList<Point> possibleMoves;
    boolean isMiniMax;
    boolean isSorted;
    int minimaxNodes;
    int aBNodes;

    public AI(Window window, Game game, int depthSize, boolean isMiniMax, boolean isSorted) {
        super();
        this.window = window;
        this.game = game;
        size = window.size;
        this.depthSize = depthSize;
        this.isMiniMax = isMiniMax;
        this.isSorted = isSorted;
        minimaxNodes = 0;
        aBNodes = 0;
    }

    public Object play(int[][] gameState) {
        Point move;
        if(isMiniMax){
            move = minimax(gameState);
        }
        else {
            move = (Point)alpha_beta_pruning(gameState);
        }
        return move;
    }

//    public Point play(int[][] gameState) {
//        Point move;
//        if(size * size - game.movesCounter > 9) {
//            Object best = chooseBestClosing(gameState);
//            if(best == null) {
//                ArrayList<Point> safe = possibleSafe(gameState);
//                if(safe.size() == 0) {
//                    Object randomMove = chooseRandom(gameState);
//                    move = (Point) randomMove;
//                }
//                else {
//                    move = (Point)chooseRandom(safe);
//                }
//            }
//            else {
//                move = (Point)best;
//            }
//        }
//        else {
//            move = minimax(gameState);
//        }
//        return move;
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

    public Point minimax(int[][] gameState) {
        minimaxNodes = 0;
        long start=System.currentTimeMillis();
        Point bestMove = null;
        int highestScoreDifference = Integer.MIN_VALUE;
        possibleMoves = checkPossible(gameState);
        if(isSorted) {
//            possibleMoves = sortPossibleMoves(possibleMoves, gameState);
//            Collections.shuffle(possibleMoves);
        }
        else {
            Collections.shuffle(possibleMoves);
        }
        for(Point move : possibleMoves) {
            gameState[move.x][move.y] = 1;
            minimaxNodes++;
            int score = game.countScoreAdded(move, gameState);
            score += minimaxRec(false, gameState, 1);
            if(highestScoreDifference < score) {
                highestScoreDifference = score;
                bestMove = move;
            }
            gameState[move.x][move.y] = 0;
        }
        long finish=System.currentTimeMillis() - start;
        game.times.add(finish);
        game.nodes.add(minimaxNodes);
        return bestMove;
    }

    private int minimaxRec(boolean isMax, int[][] gameState, int depth) {
        ArrayList<Point> possible = checkPossible(gameState);
        if(possible.size() == 0 || depth == depthSize) {
            return 0;
        }
        int lowestScore = Integer.MAX_VALUE;
        int highestScore = Integer.MIN_VALUE;

        if(isSorted) {
//            possibleMoves = sortPossibleMoves(possibleMoves, gameState);
//            Collections.shuffle(possibleMoves);
        }
        else {
            Collections.shuffle(possibleMoves);
        }
        for(Point move : possible) {
            int score = 0;
            gameState[move.x][move.y] = isMax ? 2 : 1;
            minimaxNodes++;
            if(isMax){
                score += game.countScoreAdded(move, gameState);
            }
            else {
                score -= game.countScoreAdded(move, gameState);
            }
            boolean newPlayer = !isMax;
            score += minimaxRec(newPlayer, gameState, depth + 1);
            gameState[move.x][move.y] = 0;
            if(score < lowestScore) {
                lowestScore = score;
            }
            if(score > highestScore) {
                highestScore = score;
            }
        }
        return isMax ? highestScore : lowestScore;
    }

    public Object alpha_beta_pruning(int[][] gameState) {
        aBNodes = 0;
        long start=System.currentTimeMillis();
        Point bestMove = null;
        Integer alpha = Integer.MIN_VALUE;
        Integer beta = Integer.MAX_VALUE;
        ArrayList<Point> possible = checkPossible(gameState);
        if(isSorted) {
//            possibleMoves = sortPossibleMoves(possibleMoves, gameState);
//            Collections.shuffle(possibleMoves);
        }
        else {
            Collections.shuffle(possibleMoves);
        }
        for(Point move : possible) {
            gameState[move.x][move.y] = 2;
            aBNodes++;
            int score = game.countScoreAdded(move, gameState);
            score += alpha_beta_pruningRec(false, gameState, alpha, beta, 1);
            if(alpha < score) {
                alpha = score;
                bestMove = move;
            }
            gameState[move.x][move.y] = 0;
        }
        long finish=System.currentTimeMillis() - start;
        game.times.add(finish);
        game.nodes.add(aBNodes);
        return bestMove;
    }

    private Integer alpha_beta_pruningRec(boolean isMax, int[][] board, Integer a, Integer b, int depth) {
        ArrayList<Point> possible = checkPossible(board);
        if(possible.size() == 0 || depth == depthSize) {
            return 0;
        }
        Integer alpha = a;
        Integer beta = b;
        if(isSorted) {
//            possibleMoves = sortPossibleMoves(possibleMoves, gameState);
//            Collections.shuffle(possibleMoves);
        }
        else {
            Collections.shuffle(possibleMoves);
        }
        if(isMax) {
            for(Point move : possible) {
                int score;
                board[move.x][move.y] = 2;
                aBNodes++;
                score = game.countScoreAdded(move, board);
                score += alpha_beta_pruningRec(!isMax, board, alpha, beta,depth + 1);
                board[move.x][move.y] = 0;
                if(score > alpha) {
                    alpha = score;
                }
                if(alpha >= beta) {
                    return alpha;
                }
            }
        }
        else {
            for(Point move : possible) {
                int score;
                board[move.x][move.y] = 1;
                aBNodes++;
                score = -game.countScoreAdded(move, board);//można tak?
                score += alpha_beta_pruningRec(!isMax, board, alpha, beta, depth + 1);
                board[move.x][move.y] = 0;
                if(score < beta) {
                    beta = score;
                }
                if(alpha >= beta) {
                    return beta;
                }
            }
        }
        return isMax ? alpha : beta;
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

    public ArrayList<Point> sortPossibleMoves(ArrayList<Point> possibleMoves, int[][] gameState) {
        ArrayList<Point> sortedMoves = new ArrayList<>();
        for(Point move : possibleMoves) {
            gameState[move.x][move.y] = 1;
            if(window.currentGame.countScoreAdded(move, gameState) > 0)
                sortedMoves.add(move);
            gameState[move.x][move.y] = 0;
        }
        possibleMoves.removeAll(sortedMoves);
        ArrayList<Point> subsetSafe = possibleSafe(gameState);
        sortedMoves.addAll(subsetSafe);
        possibleMoves.removeAll(subsetSafe);
        sortedMoves.addAll(possibleMoves);
        return sortedMoves;
    }

//    public ArrayList<Point> sortPossibleMoves2(ArrayList<Point> possibleMoves, int[][] gameState) {
//        ArrayList<Point> sortedMoves = new ArrayList<>();
//        Object[] subarray = new Object[possibleMoves.size()];
//        subarray = possibleMoves.toArray();
//        for(int lev = (size - 1)/2; lev < size - 1; lev++) {
//            for (int i = 0; i < subarray.length; i++) {
//                if (subarray[i] != null) {
//                    Point move = (Point)subarray[i];
//                    if(move.x == lev || move.y == lev){
//                        sortedMoves.add(move);
//                        subarray[i] = null;
//                    }
//                }
//            }
//        }
//        return sortedMoves;
//    }

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
