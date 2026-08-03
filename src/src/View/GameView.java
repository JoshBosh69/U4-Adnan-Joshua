package View;

import Controller.GameController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.*;

/**
 * Vygränssnittsklass som ansvarar för spelets grafiska gränssnitt.
 * Bygger upp spelplanens knappar, informations- och statuspaneler samt
 * popup-fönster för vinnarnamn och highscore. Tar emot uppdateringar
 * från GameController och vidarebefordrar användarens knapptryckningar
 * till kontrollklassen.
 *
 * @author Adnan
 * @author Joshua
 */
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
    private GameController gameController;

    // ------------------------------------------------------------
    // Init
    // ------------------------------------------------------------

    /**
     * Skapar och visar spelets huvudfönster. Bygger upp den övre panelen,
     * den nedre panelen och spelplanen samt kopplar samman dessa i
     * huvudfönstret.
     *
     * @param gameController kontrollklassen som vyn ska kommunicera med
     * @author Adnan
     * @author Joshua
     */
    public GameView(GameController gameController) {
        this.gameController = gameController;
        frame = new JFrame("The Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 650);
        frame.setLayout(new BorderLayout());

        buildTopPanel();
        buildBottomPanel();
        buildBoard();

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(boardPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Bygger upp den övre panelen som visar informationstext till
     * spelaren samt vilken spelare som har turen.
     *
     * @author Adnan
     * @author Joshua
     */
    private void buildTopPanel() {
        topPanel = new JPanel(new GridLayout(2, 1));

        infoLabel = new JLabel("Pick square", SwingConstants.CENTER);
        infoLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.add(infoLabel);

        statusLabel = new JLabel("Current Player: Player 1", SwingConstants.CENTER);
        statusLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.add(statusLabel);
    }

    /**
     * Bygger upp den nedre panelen som innehåller knappen för att
     * starta om spelet. Knappen kopplas till GameControllers metod
     * för att återställa spelet.
     *
     * @author Adnan
     * @author Joshua
     */
    private void buildBottomPanel() {
        bottomPanel = new JPanel();
        resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> gameController.resetGame());
        bottomPanel.add(resetButton);
    }

    /**
     * Bygger upp spelplanen som en 10×10-matris av knappar. Varje knapp
     * registreras som en ruta hos GameController och tilldelas en
     * lyssnare som meddelar kontrollklassen om vilken position
     * spelaren har tryckt på.
     *
     * @author Adnan
     * @author Joshua
     */
    private void buildBoard() {
        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(10, 10, 2, 2));
        boardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        boardButtons = new JButton[10][10];

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                int i = row;
                int j = col;

                JButton button = new JButton();
                button.setBackground(Color.WHITE);

                boardButtons[row][col] = button;
                boardPanel.add(button);

                gameController.registerTile(row, col);

                button.addActionListener(e -> gameController.buttonPressed(i, j));
            }
        }
    }

    // ------------------------------------------------------------
    // Status Updates
    // ------------------------------------------------------------

    /**
     * Uppdaterar texten som visar vilken spelare som för närvarande
     * har turen.
     *
     * @param currentPlayer namnet på den spelare vars tur det är
     * @author Adnan
     * @author Joshua
     */
    public void updateCurrentPlayer(String currentPlayer) {
        statusLabel.setText("Current Player: " + currentPlayer);
    }

    /**
     * Uppdaterar informationstexten som visas för spelaren, exempelvis
     * felmeddelanden eller instruktioner om nästa drag.
     *
     * @param infoText texten som ska visas i informationsfältet
     * @author Adnan
     * @author Joshua
     */
    public void updateInfoText(String infoText) {
        infoLabel.setText(infoText);
    }

    /**
     * Sätter texten (märket) på en given ruta på spelplanen, exempelvis
     * en spelares symbol eller en bokstav som indikerar ett Mysterium.
     *
     * @param row  raden för den ruta som ska märkas
     * @param col  kolumnen för den ruta som ska märkas
     * @param mark texten som ska visas på rutan
     * @author Adnan
     * @author Joshua
     */
    public void markTile(int row, int col, String mark) {
        boardButtons[row][col].setText(mark);
    }

    // ------------------------------------------------------------
    // Board state
    // ------------------------------------------------------------

    /**
     * Inaktiverar samtliga knappar på spelplanen, så att spelaren inte
     * längre kan göra några drag. Används när spelet är slut.
     *
     * @author Adnan
     * @author Joshua
     */
    public void disableBoard() {
        for (int row = 0; row < boardButtons.length; row++) {
            for (int col = 0; col < boardButtons[row].length; col++) {
                boardButtons[row][col].setEnabled(false);
            }
        }
    }

    /**
     * Aktiverar samtliga knappar på spelplanen igen, så att spelaren
     * kan göra drag. Används när ett nytt spel startas.
     *
     * @author Adnan
     * @author Joshua
     */
    public void enableBoard() {
        for (int row = 0; row < boardButtons.length; row++) {
            for (int col = 0; col < boardButtons[row].length; col++) {
                boardButtons[row][col].setEnabled(true);
            }
        }
    }

    // ------------------------------------------------------------
    // Popups
    // ------------------------------------------------------------

    /**
     * Visar ett popup-fönster där vinnaren kan ange sitt namn för att
     * registreras i highscore-listan. Om fältet lämnas tomt visas ett
     * felmeddelande. Vid godkänd inmatning skickas namnet till
     * GameController och highscore-listan visas därefter.
     *
     * @author Adnan
     * @author Joshua
     */
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
            if (textField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(winnerFrame, "Name cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                String name = textField.getText();
                gameController.winnerName(name);
                winnerFrame.dispose();
                showHighscore(gameController.getHighscore());
            }
        });

        winnerFrame.setVisible(true);
    }

    /**
     * Visar highscore-listan i ett popup-fönster.
     *
     * @param formattedHighscore den färdigformaterade texten som
     *                            innehåller highscore-listan
     * @author Adnan
     * @author Joshua
     */
    public void showHighscore(String formattedHighscore) {
        JOptionPane.showMessageDialog(frame, formattedHighscore, "Highscore", JOptionPane.INFORMATION_MESSAGE);
    }
}