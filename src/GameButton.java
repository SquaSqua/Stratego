import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameButton extends JButton implements ActionListener{
    ImageIcon allay, enemy;
//    static int turn;
    private boolean set = false;
    private Point coordinates;
    public GameButton(int boardSize, Point coordinates) {
//        setTurn();
//        allay = new ImageIcon("src/img/catBG.png");
//        enemy = new ImageIcon("src/img/dogBG.png");
        this.coordinates = coordinates;
        allay = new ImageIcon("src/img/batman.png");
        enemy = new ImageIcon("src/img/joker.png");
        this.setBackground(Color.black);
        if(boardSize <= 15 && boardSize >= 0) {
            this.setMinimumSize(new Dimension(10, 10));
            this.setPreferredSize(new Dimension(50, 50));
            this.setMaximumSize(new Dimension(200, 200));
        } else if(boardSize > 15 && boardSize <= 30) {
            this.setPreferredSize(new Dimension(25, 25));
        } else {
            this.setPreferredSize(new Dimension(15, 15));
        }
        this.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        setChosen();
    }

    public void setChosen() {
        if(!set) {
            switch (OnePlayerGame.movesCounter%2) {
                case 0:
                    setIcon(allay);
                    set = true;
                    break;
                case 1:
                    setIcon(enemy);
                    set = true;
                    break;
            }
        }
    }
    public boolean isSet() {
        return set;
    }

    public void seted(){
        set = true;
    }
    public Point getCoordinates() {
        return coordinates;
    }

//    public static void setTurn() {
//        turn = 0;
//    }
}
