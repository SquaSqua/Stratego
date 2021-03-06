import java.awt.*;
import java.util.ArrayList;

public class Game {

    int size;
    Window window;
    int[][] gameState;//0 - unset, 1 - player1, 2 - player2
    public static int movesCounter = 0;
    int player1Score;
    int player2Score;
    ArrayList<Long> times = new ArrayList<>();
    ArrayList<Integer> nodes = new ArrayList<>();
    String allay = "#ffb74d";
    String enemy = "#663399";

    public Game(int size, Window w) {
        this.size = size;
        window = w;
        gameState = new int[size][size];
        player1Score = 0;
        player2Score = 0;
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


    public int countScoreAdded(Point lastMove, int[][] gameState, int player) {
        int score = 0;
        int temp = 0;
        temp += checkHorizontal(lastMove, gameState);
        if(temp > 0) {
            score += temp;
            colorHorizontal(lastMove, player);
            temp = 0;
        }
        temp += checkVertical(lastMove, gameState);
        if(temp > 0) {
            score += temp;
            colorVertical(lastMove, player);
            temp = 0;
        }
        temp += checkLeftDiagonal(lastMove, gameState);
        if(temp > 0) {
            score += temp;
            colorLeftDiagonal(lastMove, player);
            temp = 0;
        }
        temp += checkRightDiagonal(lastMove, gameState);
        if(temp > 0) {
            score += temp;
            colorRightDiagonal(lastMove, player);
        }
        return score;
    }

        public void colorVertical(Point lastMove, int player) {
            if(player == 1) {
                for(int i = 0; i < size; i++) {
                    window.buttons[lastMove.x][i].setBackground(Color.decode(allay));//batman
                }
            }
            else {
                for(int i = 0; i < size; i++) {
                    window.buttons[lastMove.x][i].setBackground(Color.decode(enemy));//joker
                }
            }
        }

        public void colorHorizontal(Point lastMove, int player) {
            if(player == 1) {
                for(int i = 0; i < size; i++) {
                    window.buttons[i][lastMove.y].setBackground(Color.decode(allay));//batman
                }
            }
            else {
                for(int i = 0; i < size; i++) {
                    window.buttons[i][lastMove.y].setBackground(Color.decode(enemy));//joker
                }
            }
        }

        public void colorLeftDiagonal(Point lastMove, int player) {

            Point temp = new Point(lastMove.x, lastMove.y);
            if(player == 1) {
                while(temp.x >= 0 && temp.y < size) {
                    window.buttons[temp.x][temp.y].setBackground(Color.decode(allay));//batman
                    temp.x--;
                    temp.y++;
                }
                temp.x = lastMove.x + 1;
                temp.y = lastMove.y - 1;
                while(temp.y >= 0 && temp.x < size) {
                    window.buttons[temp.x][temp.y].setBackground(Color.decode(allay));//batman
                    temp.x++;
                    temp.y--;
                }
            }
            else {
                while(temp.x >= 0 && temp.y < size) {
                    window.buttons[temp.x][temp.y].setBackground(Color.decode(enemy));//joker
                    temp.x--;
                    temp.y++;
                }
                temp.x = lastMove.x + 1;
                temp.y = lastMove.y - 1;
                while(temp.y >= 0 && temp.x < size) {
                    window.buttons[temp.x][temp.y].setBackground(Color.decode(enemy));//joker
                    temp.x++;
                    temp.y--;
                }
            }
        }

        public void colorRightDiagonal(Point lastMove, int player) {
            Point temp = new Point(lastMove.x, lastMove.y);
            if(player == 1) {
                while(temp.x >= 0 && temp.y >= 0) {
                    window.buttons[temp.x][temp.y].setBackground(Color.decode(allay));//batman
                    temp.x--;
                    temp.y--;
                }
                temp.x = lastMove.x + 1;
                temp.y = lastMove.y + 1;
                while(temp.y < size && temp.x < size) {
                    window.buttons[temp.x][temp.y].setBackground(Color.decode(allay));//batman
                    temp.x++;
                    temp.y++;
                }
            }
            else {
                while(temp.x >= 0 && temp.y >= 0) {
                    window.buttons[temp.x][temp.y].setBackground(Color.decode(enemy));//joker
                    temp.x--;
                    temp.y--;
                }
                temp.x = lastMove.x + 1;
                temp.y = lastMove.y + 1;
                while(temp.y < size && temp.x < size) {
                    window.buttons[temp.x][temp.y].setBackground(Color.decode(enemy));//joker
                    temp.x++;
                    temp.y++;
                }
            }
        }


    public int countZerosVertical(Point move, int[][] gameState) {
        int zeros = 0;
        for(int i = 0; i < size; i++) {
            if(gameState[move.x][i] == 0) {
                zeros++;
            }
        }
        return zeros;
    }

    public int countZerosHorizontal(Point move, int[][] gameState) {
        int zeros = 0;
        for(int i = 0; i < size; i++) {
            if(gameState[i][move.y] == 0) {
                zeros++;
            }
        }
        return zeros;
    }

    public int countZerosLeftDiagonal(Point move, int[][] gameState) {
        int zeros = 0;
        Point temp = new Point(move.x, move.y);
        while(temp.x >= 0 && temp.y < size) {
            if(gameState[temp.x][temp.y] == 0) {
                zeros++;
            }
            temp.x--;
            temp.y++;
        }
        temp.x = move.x + 1;
        temp.y = move.y - 1;
        while(temp.y >= 0 && temp.x < size) {
            if(gameState[temp.x][temp.y] == 0) {
                zeros++;
            }
            temp.x++;
            temp.y--;
        }
        return zeros;
    }

    public int countZerosRightDiagonal(Point move, int[][] gameState) {
        int zeros = 0;
        Point temp = new Point(move.x, move.y);
        while(temp.x >= 0 && temp.y >= 0) {
            if(gameState[temp.x][temp.y] == 0) {
                zeros++;
            }
            temp.x--;
            temp.y--;
        }
        temp.x = move.x + 1;
        temp.y = move.y + 1;
        while(temp.y < size && temp.x < size) {
            if(gameState[temp.x][temp.y] == 0) {
               zeros++;
            }
            temp.x++;
            temp.y++;
        }
        return zeros;
    }

    public boolean isGameEnded() {
        return movesCounter == size * size;
    }

    public void setGameStatus(Point lastMove) {
        int playerSign = movesCounter%2 == 1 ? 2 : 1;
        gameState[lastMove.x][lastMove.y] = playerSign;
//        printGameState();
    }
    public void printGameState() {
        System.out.println("_________________");
        for(int i = 0; i < gameState.length; i++) {
            for(int j = 0; j < gameState[0].length; j++) {
                System.out.print(gameState[i][j] + ", ");
            }
            System.out.println();
        }
        System.out.println("_________________");
    }

}
