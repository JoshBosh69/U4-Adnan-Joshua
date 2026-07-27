package Controller;

import Model.Player.Player;
import Model.PlayingField.Board;
import Model.PlayingField.Tile;
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
    private Tile[][] tiles = new Tile[10][10];

    public GameController() {
        this.player1 = new Player();
        this.player2 = new Player();
        player1.setIsCurrentlyPlaying(true);
        this.gameView = new GameView(this);
        // For testing:
        this.player = new Player();


    }

    public void buttonPressed(int row, int col) {
        if (!tiles[row][col].isOccupied()) {
            if (player1.getIsCurrentlyPlaying()) {
                tiles[row][col].setOccupied(true);
                tiles[row][col].setOwner(player1);

                player1.addOccupiedTile();
                player1.setIsCurrentlyPlaying(false);
                player2.setIsCurrentlyPlaying(true);


                gameView.markTile(row, col, "x");
                gameView.updateCurrentPlayer("Spelare 2");
                gameView.updateInfoText("Spelare 2 tur att välja");

                //debug
                System.out.println(tiles[row][col]);
                System.out.println(tiles[row][col].getOwner());
                System.out.println(player1.getAmountOfTilesOccupied());

            } else {
                tiles[row][col].setOccupied(true);
                tiles[row][col].setOwner(player2);

                player2.addOccupiedTile();
                player2.setIsCurrentlyPlaying(false);
                player1.setIsCurrentlyPlaying(true);


                gameView.markTile(row, col, "o");
                gameView.updateCurrentPlayer("Spelare 1");
                gameView.updateInfoText("Spelare 1 tur att välja");

                //debug
                System.out.println(tiles[row][col]);
                System.out.println(tiles[row][col].getOwner());
                System.out.println(player2.getAmountOfTilesOccupied());
            }
        } else {
            gameView.updateInfoText("Denna ruta är redan upptagen. Välj en annan ruta.");
        }
    }

    public void registerTile(int row, int col) {
        tiles[row][col] = new Tile(row, col);
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
