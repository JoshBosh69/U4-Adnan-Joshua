package View;

import Controller.GameController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.*;

public class GameView {
    private JFrame frame;
    private JPanel boardPanel;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JPanel winnerPanel;
    private JButton[][] boardButtons;
    private JButton submitButton;
    private JButton resetButton;
    private JLabel statusLabel;
    private JLabel infoLabel;
    private JLabel winnerLabel;
    private JTextField textField;
    GameController gameController;

    public GameView(GameController gameController) {
        this.gameController = gameController;
        frame = new JFrame("The Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 650); 
        frame.setLayout(new BorderLayout());

        topPanel = new JPanel(new GridLayout(2,1));

        bottomPanel = new JPanel();
        resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> gameController.resetGame());
        bottomPanel.add(resetButton);


        infoLabel = new JLabel("Pick square",SwingConstants.CENTER);
        infoLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.add(infoLabel);

        statusLabel = new JLabel("Current Player: Player 1", SwingConstants.CENTER);
        statusLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.add(statusLabel);

        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(10, 10, 2, 2));
        boardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        boardButtons = new JButton[10][10];


        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                int i  = row;
                int j = col;

                JButton button = new JButton();
                button.setBackground(Color.WHITE);
                
                button.setActionCommand(row + "," + col);
                
                boardButtons[row][col] = button;
                boardPanel.add(button);

                gameController.registerTile(row, col);

                button.addActionListener(e -> {
                    gameController.buttonPressed(i, j);
                    //System.out.println(i + " " +j);
                });
            }
        }

        frame.add(topPanel,BorderLayout.NORTH);
        frame.add(boardPanel, BorderLayout.CENTER);
        frame.add(bottomPanel,BorderLayout.SOUTH);
        frame.setLocationRelativeTo(null); 
        frame.setVisible(true);
    }

    public JButton[][] getBoardButtons() {
        return boardButtons;
    }

    public void updateCurrentPlayer(String currentPlayer) {
        statusLabel.setText("Current Player: " + currentPlayer);
    }

    public void updateInfoText(String infoText) {
        infoLabel.setText(infoText);
    }

    public void winnerName() {
    JFrame winnerFrame = new JFrame("Winner");
    winnerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    winnerFrame.setSize(300, 150);
    winnerFrame.setLocationRelativeTo(null);

    winnerLabel = new JLabel("Enter your name here", SwingConstants.CENTER);
    submitButton = new JButton("Submit");
    textField = new JTextField(20);

    winnerPanel = new JPanel();

    winnerPanel.add(textField);
    winnerPanel.add(submitButton);
    winnerPanel.add(winnerLabel);
    winnerFrame.add(winnerPanel);

    submitButton.addActionListener(e -> {
        System.out.println("Submit button clicked");
        if (textField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(winnerFrame, "Name cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
        }else {
            String name = textField.getText();
            gameController.winnerName(name);
            System.out.println("Winner name set to: " + name);
            System.exit(0);
        }
    });

    winnerFrame.setVisible(true);
}

    public void markTile(int row, int col, String mark) {
        boardButtons[row][col].setText(mark);
    }

    public void disableBoard() {
        for (int row = 0; row < boardButtons.length; row++) {
            for (int col = 0; col < boardButtons[row].length; col++) {
                boardButtons[row][col].setEnabled(false);
            }
        }
    }

    public void enableBoard() {
        for (int row = 0; row < boardButtons.length; row++) {
            for (int col = 0; col < boardButtons[row].length; col++) {
                boardButtons[row][col].setEnabled(true);
            }
        }
    }
}
