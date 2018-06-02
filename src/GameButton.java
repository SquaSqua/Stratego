import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameButton extends JButton implements ActionListener{
    ImageIcon allay, enemy;
    static int turn = 0;
    private boolean set = false;
    public GameButton(int boardSize) {
//        allay = new ImageIcon("src/img/catBG.png");
//        enemy = new ImageIcon("src/img/dogBG.png");
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

        if(!set) {
            turn++;
            switch (turn%2) {
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
}
