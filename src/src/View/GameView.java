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
    private JButton[][] boardButtons;
    private JLabel statusLabel;
    private JLabel infoLabel;
    GameController gameController;

    public GameView(GameController gameController) {
        this.gameController = gameController;
        frame = new JFrame("2-Player Game Board");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 650); 
        frame.setLayout(new BorderLayout());

        topPanel = new JPanel(new GridLayout(2,1));

        infoLabel = new JLabel("Välkommen till Omvälvning. Välj och tryck på en ruta för att placera din första pjäs",SwingConstants.CENTER);
        infoLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.add(infoLabel);

        statusLabel = new JLabel("Nuvarande Spelare: Spelare 1", SwingConstants.CENTER);
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
        frame.setLocationRelativeTo(null); 
        frame.setVisible(true);

    }

    public JButton[][] getBoardButtons() {
        return boardButtons;
    }

    public void updateCurrentPlayer(String currentPlayer) {
        statusLabel.setText("Nuvarande Spelare: " + currentPlayer);
    }

    public void updateInfoText(String infoText) {
        infoLabel.setText(infoText);
    }


    public void markTile(int row, int col, String mark) {
        boardButtons[row][col].setText(mark);
    }
}
