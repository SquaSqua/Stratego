import java.awt.*;

public class TwoPlayersGame extends Game{

    int[][] gameStatus;//0 - unset, 1 - player1, 2 - player2
    int size;
    int userScore;
    Window window;

    private static int movesCounter = 0;

    TwoPlayersGame(int size, Window w) {
        this.size = size;
        window = w;
        gameStatus = new int[size][size];
        window = w;
        userScore = 0;
    }

    public void playTheGame() {
        synchronized (window) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        endGame();
    }

    void player1Turn() {

        Point p;
        p = window.currentButton;
        //do score Usera dodaje punkty z tury
        gameStatus[p.x][p.y] = 1;
        movesCounter += 1;
        System.out.println("Teraz gra gracz1");
    }

    void player2Turn() {
        Point p;
        p = window.currentButton;
//                p = ai.play(gameStatus);
        gameStatus[p.x][p.y] = 2;
        //wpisz p do GUI
        movesCounter += 1;
        System.out.println("Teraz gra gracz2");
    }

    public boolean checkIfScored() {
        boolean scored = false;
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
//                window.buttons//coś się wymyśli
            }
        }
        return scored;
    }

    private void endGame() {
        //wyświetl punkty i zwycięzcę
    }

    private boolean isGameNotEnded() {
        return movesCounter < size * size;
    }

    public static void setMovesCounter(int movesCounter) {
        TwoPlayersGame.movesCounter = movesCounter;
    }

    public void addTurn() { TwoPlayersGame.movesCounter += 1; }
}
