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
        frame.setSize(600, 650); // Något högre än bred för att ge plats åt statusfältet
        frame.setLayout(new BorderLayout());

        // Statusfält högst upp (t.ex. för att visa vems tur det är)
        statusLabel = new JLabel("Spelare 1:s tur", SwingConstants.CENTER);
        statusLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.add(statusLabel, BorderLayout.NORTH);

        // Spelplanen med 10x10 GridLayout och 2 pixlars mellanrum mellan rutorna
        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(10, 10, 2, 2));
        boardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Initiera arrayen för knapparna
        boardButtons = new JButton[10][10];

        // Fyll spelplanen med knappar
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                JButton button = new JButton();
                button.setBackground(Color.WHITE);
                
                // Sparar koordinaterna som ActionCommand för att enkelt identifiera rutan vid klick
                button.setActionCommand(row + "," + col);
                
                boardButtons[row][col] = button;
                boardPanel.add(button);
            }
        }

        frame.add(boardPanel, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null); // Centrerar fönstret på skärmen
        frame.setVisible(true);
    }

    // Getter för att kunna nå knapparna från en controller senare
    public JButton[][] getBoardButtons() {
        return boardButtons;
    }

    // Metod för att uppdatera statustexten från kontrollern
    public void setStatusText(String text) {
        statusLabel.setText(text);
    }
}