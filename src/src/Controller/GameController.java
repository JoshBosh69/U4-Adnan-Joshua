package Controller;

import Model.Player.Player;
import Model.PlayingField.Tile;
import View.GameView;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameController {
    // For Logging
    private Logger logger = Logger.getLogger(GameController.class.getName());
    private FileWriter fileWriter;
    private PrintWriter printWriter;

    private GameView gameView;
    private Player player;
    private Player currentPlayer;
    private Player otherPlayer;
    private List<Player> players;
    private Tile[][] tiles = new Tile[10][10];
    private int directions[][] = {
            {-1, -1}, {-1, 0}, {-1, 1},
            { 0, -1},          { 0, 1},
            { 1, -1}, { 1, 0}, { 1, 1}
    };

    public GameController() {
        players = new ArrayList<>();
        players.add(new Player("player1", "x"));
        players.add(new Player("player2", "o"));
        currentPlayer = players.getFirst();
        otherPlayer = players.getLast();

        this.gameView = new GameView(this);

        // For testing:
        //this.player = new Player("player1", "o");

    }

    public void buttonPressed(int row, int col) {
        //check if tile is already occupied
        if (!tiles[row][col].isOccupied()) {
            // if not then set to occupied and register the player to that tile
            tiles[row][col].setOccupied(true);
            tiles[row][col].setOwner(currentPlayer);

            // Check if surprise
            for (int[] dir : directions) {
                checkDirection(row, col, dir[0], dir[1], currentPlayer, otherPlayer);
            }
            // update the players counter for the amount of tiles he is occupying
            currentPlayer.addOccupiedTile();

            // mark the tile with the players mark
            gameView.markTile(row, col, currentPlayer.getMark());

            // switch turns
            Player tempPlayer = currentPlayer;
            currentPlayer = otherPlayer;
            otherPlayer = tempPlayer;

            // update info on gui
            gameView.updateCurrentPlayer(currentPlayer.getName());
            gameView.updateInfoText(currentPlayer.getName() + " tur att välja");

            //debug
            System.out.println(tiles[row][col]);
            System.out.println(tiles[row][col].getOwner());
            System.out.println(currentPlayer.getAmountOfTilesOccupied());

        } else {
            gameView.updateInfoText("Denna ruta är redan upptagen. Välj en annan ruta.");
        }
    }

    private void checkDirection(int row, int col, int dRow, int dCol, Player currentPlayer, Player otherPlayer) {
        List<Tile> tilesToChange = new ArrayList<>();

        int r = row + dRow;
        int c = col + dCol;

        // walk outward one step at a time in this direction
        while (r >= 0 && r < tiles.length && c >= 0 && c < tiles[0].length) {
            Tile currentTile = tiles[r][c];

            if (currentTile.getOwner() == otherPlayer) {
                // enemy tile — could be part of a pinch, keep collecting
                tilesToChange.add(currentTile);
            } else if (currentTile.getOwner() == currentPlayer) {
                // found my own tile — valid capture if we collected at least one enemy tile
                if (!tilesToChange.isEmpty()) {
                    for (Tile t : tilesToChange) {
                        t.setOwner(currentPlayer);
                        gameView.markTile(t.getXcor(), t.getYcor(), currentPlayer.getMark());
                    }
                }
                return; // done with this direction either way
            } else {
                // empty tile — chain is broken, no capture in this direction
                return;
            }

            r += dRow;
            c += dCol;
        }
        // walked off the board without finding my own tile — no capture
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

    private void switchCurrentPlayer() {
        player.setIsCurrentlyPlaying(false);
    }
}
