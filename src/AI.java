import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class AI{
    Window window;
    Game game;
    int[][] gameStatus;
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
        checkPossible();
        Random random = new Random();
        return possibleMoves.get(random.nextInt(possibleMoves.size()));
    }

    private void checkPossible() {
        possibleMoves = new ArrayList<>();
        gameStatus = ((OnePlayerGame)game).gameStatus;
        for(int x  = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if(gameStatus[x][y] == 0){
                    possibleMoves.add(new Point(x, y));
                }
            }
        }
    }

//    public Point chooseClosing() {
//
//    }
}
