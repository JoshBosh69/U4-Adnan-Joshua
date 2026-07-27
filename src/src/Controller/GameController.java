package Controller;

import Model.Player.Player;
import Model.PlayingField.Board;
import View.GameView;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameController {
    // For Logging
    private Logger logger = Logger.getLogger(GameController.class.getName());
    private FileWriter fileWriter;
    private PrintWriter printWriter;
    private IOException ioException;

    private GameView gameView;
    private Board board;
    private Player player;
    private int counter = 0;
    private Player player1;
    private Player player2;

    public GameController() {
        this.player1 = new Player();
        this.player2 = new Player();
        player1.setIsCurrentlyPlaying(true);

        this.gameView = new GameView(this);
        // For testing:
        this.player = new Player();


    }

    public void buttonPressed() {
        if (player1.getIsCurrentlyPlaying()) {
            player1.setIsCurrentlyPlaying(false);
            player2.setIsCurrentlyPlaying(true);
            gameView.updateCurrentPlayer("Spelare 2");
        } else {
            player2.setIsCurrentlyPlaying(false);
            player1.setIsCurrentlyPlaying(true);
            gameView.updateCurrentPlayer("Spelare 1");
        }

    }

    public boolean gameEnded() {
        gameView.winnerName();
        // if Board is full, return true
        return false;
    } 
    public String winnerName(String name){
        if(name != null){
            player.setWinnerrName(name);
            winnerLogger(name);

        }
        return name;
    }

    public String winnerLogger(String name) {
        try {
            fileWriter = new FileWriter("winner_log.txt", true);
            printWriter = new PrintWriter(fileWriter);
            printWriter.println(name);
            printWriter.close();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error writing to log file", e);
        }
        return name;
    }
}
