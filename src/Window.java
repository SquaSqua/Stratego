import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window extends JFrame implements ActionListener{

    int size;
    JPanel panel;
    JTextField boardSizeInput;
    JTextField sizeMessage;
    GameButton[] buttons;
    JButton ok;
    JMenuBar menuBar;
    JMenu gameType;
    JMenuItem onePlayer, twoPlayers, pcVsPc;

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

        twoPlayers = new JMenuItem("Dwóch graczy");

        pcVsPc = new JMenuItem("PC vs PC");

        gameType.add(onePlayer);
        gameType.add(twoPlayers);
        gameType.add(pcVsPc);

        menuBar.add(gameType);
        menuBar.add(sizeMessage);
        menuBar.add(boardSizeInput);
        menuBar.add(ok);

        panel = new JPanel();
        buttons = new GameButton[size*size];
        panel.setLayout(new GridLayout(size, size));
        for(int i = 0; i < size * size; i++) {
            buttons[i] = new GameButton(size);
            panel.add(buttons[i]);
        }

        add(panel, BorderLayout.SOUTH);

        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    public void actionPerformed(ActionEvent e) {

        try{
            if(e.getActionCommand().equals("OK")) {
                Window w = new Window(Integer.parseInt(boardSizeInput.getText()));
                w.setLocation(this.getLocation());
                w.pack();
                dispose();
            }
        }catch (Exception ex) {
            System.out.println("Brak podanego wymiaru");
        }
    }
    public class OnePlayerAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equals("Jeden gracz")) {
                Window w = new Window(Integer.parseInt(boardSizeInput.getText()));
                w.setLocationRelativeTo(null);
                w.pack();
                dispose();
            }
        }
    }
}
