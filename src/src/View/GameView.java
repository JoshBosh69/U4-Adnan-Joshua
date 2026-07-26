package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class GameView {
    private JFrame frame;
    private JPanel boardPanel;
    private JButton[][] boardButtons;
    private JLabel statusLabel;

    public GameView() {
        frame = new JFrame("2-Player Game Board");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 650); 
        frame.setLayout(new BorderLayout());

        statusLabel = new JLabel("Spelare 1:s tur", SwingConstants.CENTER);
        statusLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.add(statusLabel, BorderLayout.NORTH);

        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(10, 10, 2, 2));
        boardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        boardButtons = new JButton[10][10];

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                JButton button = new JButton();
                button.setBackground(Color.WHITE);
                
                button.setActionCommand(row + "," + col);
                
                boardButtons[row][col] = button;
                boardPanel.add(button);
            }
        }

        frame.add(boardPanel, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null); 
        frame.setVisible(true);
    }

    public JButton[][] getBoardButtons() {
        return boardButtons;
    }

    public void setStatusText(String text) {
        statusLabel.setText(text);
    }
}
