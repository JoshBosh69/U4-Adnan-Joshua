package Controller;

import Model.Player.Player;
import Model.PlayingField.Mysteries.AbyssalRoar;
import Model.PlayingField.Mysteries.MysteryTile;
import Model.PlayingField.Mysteries.Narcissus;
import Model.PlayingField.Mysteries.TimeJump;
import Model.PlayingField.Tile;
import View.GameView;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
    private boolean switchTurns = true;
    private final int mysteries = 5;
    private int placedMysteries = 0;

    public GameController() {
        players = new ArrayList<>();
        players.add(new Player("player1", "x"));
        players.add(new Player("player2", "o"));
        currentPlayer = players.getFirst();
        otherPlayer = players.getLast();

        this.gameView = new GameView(this);
        placeMysteries();

        // For testing:
        //this.player = new Player("player1", "o");

    }

    private void placeMysteries() {
        Random random = new Random();

        while (placedMysteries < mysteries) {
            int mysteryIndex = random.nextInt(3);
            int randomRow = random.nextInt(tiles.length);
            int randomCol = random.nextInt(tiles[0].length);

            if ((randomRow == 0 || randomRow == tiles.length - 1) && (randomCol == tiles[0].length - 1 || randomCol == 0)) {
                continue;
            }

            if (hasAdjacentMystery(randomRow, randomCol)) {
                continue;
            }

            switch (mysteryIndex) {
                case 0:
                    tiles[randomRow][randomCol].setMysteryType(new TimeJump());
                    break;
                case 1:
                    tiles[randomRow][randomCol].setMysteryType(new AbyssalRoar());
                    break;
                case 2:
                    tiles[randomRow][randomCol].setMysteryType(new Narcissus());
                    break;
                default:
                    break;
            }
            tiles[randomRow][randomCol].setIsMystery(true);

            // debug
            //gameView.markTile(randomRow, randomCol, "?");
            if (mysteryIndex == 0) {
                gameView.markTile(randomRow, randomCol, "T");
            } else if (mysteryIndex == 2) {
                gameView.markTile(randomRow, randomCol, "N");
            } else {
                gameView.markTile(randomRow, randomCol, "A");
            }
            placedMysteries++;
        }

    }

    private boolean hasAdjacentMystery(int row, int col) {
        for (int[] dir : directions) {
            int r = row + dir[0];
            int c = col + dir[1];
            if (r >= 0 && r < tiles.length && c >= 0 && c < tiles[0].length) {
                if (tiles[r][c].isMystery()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void buttonPressed(int row, int col) {
        // by default switching between players is enabled
        switchTurns = true;
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

            if (checkGameOver()) {
                gameView.disableBoard();
                Player winner = getMatchWinner();

                if (winner != null) {
                    gameView.updateInfoText("GAME FINISHED - WINNER IS: " + winner.getName());
                    gameView.winnerName();
                } else
                    gameView.updateInfoText("GAME ENDED IN A DRAW");
                return;
            }

            /*
                if(){
                isGameOver = true;
                gameView.updateInfoText(currentPlayer.getName() + " has won the game!");
                gameView.disableBoard();
                gameView.updateCurrentPlayer(currentPlayer.getName() + " has won the game!");
                gameView.winnerName();
                return;
            }
             */

            // mark the tile with the players mark
            gameView.markTile(row, col, currentPlayer.getMark());

            // switch turns
            switchTurns();

            // update info on gui
            gameView.updateCurrentPlayer(currentPlayer.getName());
            gameView.updateInfoText(currentPlayer.getName() + " Turn to pick a square.");

            //debug
            //System.out.println(tiles[row][col]);
            //System.out.println(tiles[row][col].getOwner().getName());
            //System.out.println(currentPlayer.getAmountOfTilesOccupied());

        } else {
            gameView.updateInfoText("Square is occupied. Pick another square.");
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
            } else if (currentTile.isMystery() && currentTile.isMysteryActive()) {
                tilesToChange.add(currentTile);
            } else if (currentTile.getOwner() == currentPlayer) {
                // found my own tile — valid capture if we collected at least one enemy tile
                if (!tilesToChange.isEmpty()) {
                    for (Tile t : tilesToChange) {
                        t.setOwner(currentPlayer);

                        if (t.isMystery() && t.isMysteryActive()) {
                            MysteryTile mystery = t.getMysteryType();
                            List<Tile> affectedTiles = new ArrayList<>();

                            switchTurns = mystery.activateMystery(currentPlayer, otherPlayer, t.getXcor(), t.getYcor(), tiles, directions, affectedTiles);
                            mystery.setIsActive(false);

                            for (Tile affected : affectedTiles) {
                                gameView.markTile(affected.getXcor(), affected.getYcor(), ""); // tom sträng = rutan är nu tom
                            }
                        }

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

    public String winnerName(String name){
        if(name != null){
            this.currentPlayer.setWinnerrName(name);
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

    public void startGame() {
        gameView.updateCurrentPlayer(currentPlayer.getName());
    }

    public void switchTurns() {
        if (switchTurns) {
            Player tempPlayer = currentPlayer;
            this.currentPlayer = otherPlayer;
            this.otherPlayer = tempPlayer;

            // efter det vanliga bytet, kolla om den nya spelaren ska hoppas över
            if (currentPlayer.getSkipNextTurn()) {
                currentPlayer.setSkipNextTurn(false);
                gameView.updateInfoText(currentPlayer.getName() + " hoppar över sin tur (Narcissus)!");

                // den överhoppade spelarens motståndare spelar istället
                Player tempPlayer2 = currentPlayer;
                this.currentPlayer = otherPlayer;
                this.otherPlayer = tempPlayer2;
            }
        } else {
            gameView.updateInfoText("TimeJump! " + currentPlayer.getName() + " get to play again");
        }
    }

    private boolean checkGameOver() {
        return isBoardFull() || allMysteriesActivated();
    }

    private boolean isBoardFull() {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                if (!tiles[i][j].isOccupied()) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean allMysteriesActivated() {
        int activatedMysteries = 0;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                if (tiles[i][j].isMystery() && !tiles[i][j].isMysteryActive()) {
                    activatedMysteries ++;
                }
            }
        }
        return activatedMysteries == mysteries;
    }

    public Player getMatchWinner() {
        Player p1 = players.getFirst();
        Player p2 = players.getLast();

        if (p1.getAmountOfTilesOccupied() > p2.getAmountOfTilesOccupied()) {
            return p1;
        } else if (p2.getAmountOfTilesOccupied() > p1.getAmountOfTilesOccupied()) {
            return p2;
        } else {
            return null; // might be a tie
        }
    }


}
