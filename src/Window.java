import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Window extends JFrame implements ActionListener{

    int size;
    JPanel panel;
    JTextField boardSizeInput;
    JLabel sizeMessage;
    GameButton[][] buttons;
    JButton ok;
    JMenuBar menuBar;
    JLabel pointsField;
    JMenu gameType;
    JMenuItem onePlayer, pcVsPc;
    Point currentButton;
    Game currentGame;
    String points1 = "Gracz nr 1    ";
    String points2 = "    Gracz nr 2";
    String score1 = "0";
    String score2 = "0";

    JPanel pointsPanel;
    JPanel leftSubpanel;
    JPanel rightSubpanel;

    //rightPanel
    JLabel PC1;
    JLabel depthLabel;
    JTextField depthInput;
    JPanel depthField;
    JLabel heuristicLabel;
    ButtonGroup heuristicsMenu;
    JRadioButton minimax;
    JRadioButton alpha_beta_pruning;
    ButtonGroup checkingMenu;
    JRadioButton randomChecking;
    JRadioButton sortedChecking;

    JLabel PC2;
    JLabel depthLabel2;
    JTextField depthInput2;
    JPanel depthField2;
    JLabel heuristicLabel2;
    ButtonGroup heuristicsMenu2;
    JRadioButton minimax2;
    JRadioButton alpha_beta_pruning2;
    ButtonGroup checkingMenu2;
    JRadioButton randomChecking2;
    JRadioButton sortedChecking2;

    public Window(int size) {
        super("Stratego");

        this.size = size;
        setLocationRelativeTo(null);

        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        pointsField = new JLabel(points1 + score1 + "  :  " + score2 + points2 );

        boardSizeInput = new JTextField();
        boardSizeInput.setPreferredSize(new Dimension(50, 20));
        sizeMessage = new JLabel("Rozmiar planszy: ");
        sizeMessage.getVisibleRect();

        ok = new JButton("OK");
        ok.addActionListener(this);

        gameType = new JMenu("Rodzaj gry");

        onePlayer = new JMenuItem("Jeden gracz");
        onePlayer.addActionListener(new OnePlayerAction());

        pcVsPc = new JMenuItem("PC vs PC");
        pcVsPc.addActionListener(new PcVsPcAction());

        pointsPanel = new JPanel();
        panel = new JPanel();

        leftSubpanel = new JPanel();
        rightSubpanel = new JPanel();

        gameType.add(onePlayer);
        gameType.add(pcVsPc);

        menuBar.add(gameType);
        menuBar.add(sizeMessage);
        menuBar.add(boardSizeInput);
        menuBar.add(ok);

        //rightSubpanel
        depthLabel = new JLabel("Głębokość: ");
        depthInput = new JTextField();
        depthInput.setText("3");
        depthField = new JPanel();
        heuristicLabel = new JLabel("Heurystyka: ");
        PC1 = new JLabel("Komputer1");
        PC2 = new JLabel("Komputer2");
        heuristicsMenu = new ButtonGroup();
        minimax = new JRadioButton("MiniMax");
        minimax.setSelected(true);
        alpha_beta_pruning = new JRadioButton("alfa-beta cięcie");
        checkingMenu = new ButtonGroup();
        randomChecking = new JRadioButton("RandomChecking");
        sortedChecking = new JRadioButton("SortedChecking");
        sortedChecking.setSelected(true);
        checkingMenu.add(randomChecking);
        checkingMenu.add(sortedChecking);
        heuristicsMenu.add(minimax);
        heuristicsMenu.add(alpha_beta_pruning);

        depthLabel2 = new JLabel("Głębokość: ");
        depthInput2 = new JTextField();
        depthInput2.setText("3");
        depthField2 = new JPanel();
        heuristicLabel2 = new JLabel("Heurystyka: ");
        heuristicsMenu2 = new ButtonGroup();
        minimax2 = new JRadioButton("MiniMax");
        alpha_beta_pruning2 = new JRadioButton("alfa-beta cięcie");
        alpha_beta_pruning2.setSelected(true);
        checkingMenu2 = new ButtonGroup();
        randomChecking2 = new JRadioButton("RandomChecking");
        sortedChecking2 = new JRadioButton("SortedChecking");
        sortedChecking2.setSelected(true);
        checkingMenu2.add(randomChecking2);
        checkingMenu2.add(sortedChecking2);
        heuristicsMenu2.add(minimax2);
        heuristicsMenu2.add(alpha_beta_pruning2);

        depthField.setLayout(new BoxLayout(depthField, BoxLayout.LINE_AXIS));
        depthField2.setLayout(new BoxLayout(depthField2, BoxLayout.LINE_AXIS));
        depthField.add(depthLabel);
        depthField.add(depthInput);
        depthField2.add(depthLabel2);
        depthField2.add(depthInput2);

        rightSubpanel.setLayout(new BoxLayout(rightSubpanel, BoxLayout.PAGE_AXIS));

        rightSubpanel.add(PC1);
        rightSubpanel.add(heuristicLabel);
        rightSubpanel.add(minimax);
        rightSubpanel.add(alpha_beta_pruning);
        rightSubpanel.add(depthField);
        rightSubpanel.add(randomChecking);
        rightSubpanel.add(sortedChecking);

        rightSubpanel.add(PC2);
        rightSubpanel.add(heuristicLabel2);
        rightSubpanel.add(minimax2);
        rightSubpanel.add(alpha_beta_pruning2);
        rightSubpanel.add(depthField2);
        rightSubpanel.add(randomChecking2);
        rightSubpanel.add(sortedChecking2);

        buttons = new GameButton[size][size];
        panel.setLayout(new GridLayout(size, size));
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                buttons[i][j] = new GameButton(size, new Point(i, j));
                panel.add(buttons[i][j]);
                buttons[i][j].addActionListener(this);
            }
        }

        pointsPanel.add(pointsField);
        pointsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));


        leftSubpanel.setLayout(new BoxLayout(leftSubpanel, BoxLayout.PAGE_AXIS));
        leftSubpanel.add(pointsPanel);
        leftSubpanel.add(panel);




        add(rightSubpanel, BorderLayout.EAST);
        add(leftSubpanel, BorderLayout.WEST);


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
                int depth = Integer.parseInt(depthInput.getText());
                boolean isMiniMaxSelected = minimax.isSelected();
                boolean isSorted = sortedChecking.isSelected();
                w.currentGame = new OnePlayerGame(size, w, depth, isMiniMaxSelected, isSorted);
                ((OnePlayerGame)w.currentGame).initializeAI();
                OnePlayerGame.movesCounter = 0;
                dispose();
            }
        }
    }

    public class PcVsPcAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equals("PC vs PC")) {
                Window w = new Window(size);
                w.setLocationRelativeTo(null);
                w.pack();
                int depth = Integer.parseInt(depthInput.getText());
                boolean isMiniMaxSelected = minimax.isSelected();
                boolean isSorted = sortedChecking.isSelected();
                int depth2 = Integer.parseInt(depthInput2.getText());
                boolean isMiniMaxSelected2 = minimax2.isSelected();
                boolean isSorted2 = sortedChecking2.isSelected();
                w.currentGame = new PcVsPc(size, w, depth, isMiniMaxSelected, isSorted, depth2, isMiniMaxSelected2, isSorted2);
                ((PcVsPc)w.currentGame).initializeAI();
                PcVsPc.movesCounter = 0;
                dispose();
                ((PcVsPc)w.currentGame).playTheGame();
            }
        }
    }

    public void endGame() {
//        for(int i = 0; i < size; i++) {
//            for(GameButton button : buttons[i]) {
//                button.setEnabled(false);
//            }
//        }
        score1 = currentGame.player1Score + "";
        score2 = currentGame.player2Score + "";
        pointsField.setText(points1 + score1 + "  :  " + score2 + points2);
        if(currentGame.getClass().equals(OnePlayerGame.class)){
            ((OnePlayerGame)currentGame).displayScore();
        }
        else {
            ((PcVsPc)currentGame).showBoard(currentGame.gameState);
        }
        ArrayList<Long> times = currentGame.times;
        ArrayList<Integer> nodes = currentGame.nodes;
        String results = "data.csv";
        try
        {
            PrintWriter out = new PrintWriter(results);
            out.println("player1, player2");
            out.println(dataGenerator(times, nodes));
            out.close();
        }
        catch (FileNotFoundException ex)
        {
            System.out.println("Nie da się utworzyć pliku!");
        }
    }

    public String dataGenerator(ArrayList<Long> times, ArrayList<Integer> nodes) {
        String result = "";
        StringBuilder sB = new StringBuilder(result);
        if(times.size()%2 == 0){
            for(int i = 0; i < times.size(); i++) {
                sB.append(times.get(i) + "," + times.get(++i));
                sB.append("\n");
            }
        }
        else {
            for(int i = 0; i < times.size() - 1; i++) {
                sB.append(times.get(i) + "," + times.get(++i));
                sB.append("\n");
            }
            sB.append(times.get(times.size()-1));
        }
        sB.append("\n");
        if(nodes.size()%2 == 0){
            for(int i = 0; i < nodes.size(); i++) {
                sB.append(nodes.get(i) + "," + nodes.get(++i));
                sB.append("\n");
            }
        }
        else {
            for(int i = 0; i < nodes.size() - 1; i++) {
                sB.append(nodes.get(i) + "," + nodes.get(++i));
                sB.append("\n");
            }
            sB.append(nodes.get(nodes.size()-1));
        }
        result = sB.toString();
        return result;
    }
}
