import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window extends JFrame implements ActionListener{

    int size;
    JPanel panel;
    JTextField boardSizeInput;
    JTextField sizeMessage;
    GameButton[][] buttons;
    JButton ok;
    JMenuBar menuBar;
    JMenu gameType;
    JMenuItem onePlayer, pcVsPc;
    Point currentButton;
    Game currentGame;

    public Window(int size) {
        super("Stratego");

        this.size = size;
        setLocationRelativeTo(null);

        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        boardSizeInput = new JTextField();
        boardSizeInput.setPreferredSize(new Dimension(50, 20));
        sizeMessage = new JTextField("Rozmiar planszy");
        sizeMessage.getVisibleRect();

        ok = new JButton("OK");
        ok.addActionListener(this);

        gameType = new JMenu("Rodzaj gry");

        onePlayer = new JMenuItem("Jeden gracz");
        onePlayer.addActionListener(new OnePlayerAction());

        pcVsPc = new JMenuItem("PC vs PC");

        gameType.add(onePlayer);
        gameType.add(pcVsPc);

        menuBar.add(gameType);
        menuBar.add(sizeMessage);
        menuBar.add(boardSizeInput);
        menuBar.add(ok);

        panel = new JPanel();
        buttons = new GameButton[size][size];
        panel.setLayout(new GridLayout(size, size));
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                buttons[i][j] = new GameButton(size, new Point(i, j));
                panel.add(buttons[i][j]);
                buttons[i][j].addActionListener(this);
            }
        }

        add(panel, BorderLayout.SOUTH);

        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    public void actionPerformed(ActionEvent e) {//głównego okna, akcja któregokolwiek z przycisków niebędącego menu

        try{
            if(e.getSource().getClass().equals(GameButton.class))//wykonaj dla pól
            {
                GameButton b = (GameButton) e.getSource();
                if(!b.isSet()) {
                    currentButton = b.getCoordinates();
                    System.out.println("my point: " + currentButton);
                    if(currentGame.getClass().equals(OnePlayerGame.class)) {
                        currentGame.setGameStatus(currentButton);
                        ((OnePlayerGame)currentGame).player1Turn();
                        if(!currentGame.isGameEnded()) {
                            ((OnePlayerGame)currentGame).player2Turn();
                        }

                    }
                }
            }
            else if(e.getActionCommand().equals("OK")) {//wykonaj dla okejki
                Window w = new Window(Integer.parseInt(boardSizeInput.getText()));
                w.setLocation(this.getLocation());
                w.pack();
                dispose();
            }
        }catch (Exception ex) {
            System.out.println("Podaj rozmiar i zatwierdź, a następnie wybierz rodzaj gry");
        }
    }

    public class OnePlayerAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equals("Jeden gracz")) {
                Window w = new Window(size);
                w.setLocationRelativeTo(null);
                w.pack();
                w.currentGame = new OnePlayerGame(size, w);
                OnePlayerGame.movesCounter = 0;
                dispose();
            }
        }
    }

    public void endGame() {
//        for(int i = 0; i < size; i++) {
//            for(GameButton button : buttons[i]) {
//                button.setEnabled(false);
//            }
//        }
        ((OnePlayerGame)currentGame).displayScore();
    }
}
