package Controller;

import Model.PlayingField.Highscore.Highscore;
import Model.Player.Player;
import Model.PlayingField.Mysteries.AbyssalRoar;
import Model.PlayingField.Mysteries.MysteryTile;
import Model.PlayingField.Mysteries.Narcissus;
import Model.PlayingField.Mysteries.TimeJump;
import Model.PlayingField.Tile;
import View.GameView;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameController {

    private GameView gameView;
    private Player currentPlayer;
    private Player otherPlayer;
    private List<Player> players;
    private Tile[][] tiles = new Tile[10][10];
    private final int[][] directions = {
            {-1, -1}, {-1, 0}, {-1, 1},
            { 0, -1},          { 0, 1},
            { 1, -1}, { 1, 0}, { 1, 1}
    };
    private boolean allowSwitchTurn = true;
    private final int mysteries = 5;
    private int placedMysteries = 0;
    private Highscore highscore;

    // ------------------------------------------------------------
    // Init
    // ------------------------------------------------------------

    public GameController() {
        players = new ArrayList<>();
        players.add(new Player("player1", "x"));
        players.add(new Player("player2", "o"));
        currentPlayer = players.getFirst();
        otherPlayer = players.getLast();
        highscore = new Highscore();
        highscore.loadFromFile("highscore.txt");

        this.gameView = new GameView(this);
        placeMysteries();
    }

    public void registerTile(int row, int col) {
        tiles[row][col] = new Tile(row, col);
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

    // ------------------------------------------------------------
    // Core game logic
    // ------------------------------------------------------------

    public void buttonPressed(int row, int col) {
        allowSwitchTurn = true;

        if (tiles[row][col].isOccupied()) {
            gameView.updateInfoText("Square is occupied. Pick another square.");
            return;
        }

        tiles[row][col].setOccupied(true);
        tiles[row][col].setOwner(currentPlayer);

        for (int[] dir : directions) {
            checkDirection(row, col, dir[0], dir[1], currentPlayer, otherPlayer);
        }
        currentPlayer.addOccupiedTile();

        if (checkGameOver()) {
            handleGameOver();
            return;
        }

        gameView.markTile(row, col, currentPlayer.getMark());

        switchTurns();

        gameView.updateCurrentPlayer(currentPlayer.getName());
        gameView.updateInfoText(currentPlayer.getName() + " Turn to pick a square.");
    }

    private void checkDirection(int row, int col, int dRow, int dCol, Player currentPlayer, Player otherPlayer) {
        List<Tile> tilesToChange = new ArrayList<>();

        int r = row + dRow;
        int c = col + dCol;

        while (r >= 0 && r < tiles.length && c >= 0 && c < tiles[0].length) {
            Tile currentTile = tiles[r][c];

            if (currentTile.getOwner() == otherPlayer) {
                tilesToChange.add(currentTile);
            } else if (currentTile.isMystery() && currentTile.isMysteryActive()) {
                tilesToChange.add(currentTile);
            } else if (currentTile.getOwner() == currentPlayer) {
                if (!tilesToChange.isEmpty()) {
                    for (Tile t : tilesToChange) {
                        t.setOwner(currentPlayer);

                        if (t.isMystery() && t.isMysteryActive()) {
                            MysteryTile mystery = t.getMysteryType();
                            List<Tile> affectedTiles = new ArrayList<>();

                            allowSwitchTurn = mystery.activateMystery(currentPlayer, otherPlayer, t.getXcor(), t.getYcor(), tiles, directions, affectedTiles);
                            mystery.setIsActive(false);

                            for (Tile affected : affectedTiles) {
                                gameView.markTile(affected.getXcor(), affected.getYcor(), "");
                            }
                        }

                        gameView.markTile(t.getXcor(), t.getYcor(), currentPlayer.getMark());
                    }
                }
                return;
            } else {
                return;
            }

            r += dRow;
            c += dCol;
        }
    }

    public void switchTurns() {
        if (allowSwitchTurn) {
            Player tempPlayer = currentPlayer;
            this.currentPlayer = otherPlayer;
            this.otherPlayer = tempPlayer;

            if (currentPlayer.getSkipNextTurn()) {
                currentPlayer.setSkipNextTurn(false);
                gameView.updateInfoText(currentPlayer.getName() + " hoppar över sin tur (Narcissus)!");

                Player tempPlayer2 = currentPlayer;
                this.currentPlayer = otherPlayer;
                this.otherPlayer = tempPlayer2;
            }
        } else {
            gameView.updateInfoText("TimeJump! " + currentPlayer.getName() + " get to play again");
        }
    }

    // ------------------------------------------------------------
    // Game Ending
    // ------------------------------------------------------------

    private void handleGameOver() {
        gameView.disableBoard();
        Player winner = getMatchWinner();

        if (winner != null) {
            int score = winner.getAmountOfTilesOccupied();
            if (highscore.qualifies(score)) {
                gameView.updateInfoText("GAME FINISHED - WINNER IS: " + winner.getName());
                gameView.winnerName();
            } else {
                gameView.updateInfoText("GAME FINISHED - " + winner.getName() + " won, but didn't qualify for highscore.");
                gameView.showHighscore(highscore.getFormattedHighscore());
            }
        } else {
            gameView.updateInfoText("GAME ENDED IN A DRAW");
            gameView.showHighscore(highscore.getFormattedHighscore());
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
                    activatedMysteries++;
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
            return null;
        }
    }

    // ------------------------------------------------------------
    // Highscore logic
    // ------------------------------------------------------------

    public String winnerName(String name) {
        if (name != null) {
            int score = currentPlayer.getAmountOfTilesOccupied();
            highscore.addEntry(name, score);
            highscore.saveToFile("highscore.txt");
        }
        return name;
    }

    public String getHighscore() {
        return highscore.getFormattedHighscore();
    }

    // ------------------------------------------------------------
    // Game reset logic
    // ------------------------------------------------------------

    public void resetGame() {
        tiles = new Tile[10][10];
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles[0].length; col++) {
                registerTile(row, col);
                gameView.markTile(row, col, "");
            }
        }

        placedMysteries = 0;
        placeMysteries();

        for (Player p : players) {
            p.setAmountOfOccupiedTiles(0);
            p.setSkipNextTurn(false);
        }

        currentPlayer = players.getFirst();
        otherPlayer = players.getLast();

        gameView.enableBoard();
        gameView.updateCurrentPlayer(currentPlayer.getName());
        gameView.updateInfoText("New Game. Pick a square");
    }
}