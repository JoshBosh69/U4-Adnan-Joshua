package Controller;

import Model.Highscore.Highscore;
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

/**
 * Kontrollklass som styr spelet Omvälvning. Ansvarar för att sätta upp
 * spelplanen och Mysterium, hantera spelarnas drag, avgöra överraskningar
 * och Mysterium-aktiveringar, kontrollera turordning, avgöra när spelet
 * är slut samt koppla samman spelets tillstånd med highscore-listan och
 * det grafiska gränssnittet.
 *
 * @author Adnan
 * @author Joshua
 */
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

    /**
     * Skapar en ny spelomgång. Initierar de två spelarna, läser in
     * befintlig highscore-lista från fil, skapar det grafiska
     * gränssnittet och placerar ut Mysterium på spelplanen.
     *
     * @author Adnan
     * @author Joshua
     */
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

    /**
     * Skapar och registrerar en ny, tom ruta på angiven position i
     * spelplanens array.
     *
     * @param row raden rutan ska registreras på
     * @param col kolumnen rutan ska registreras på
     * @author Adnan
     * @author Joshua
     */
    public void registerTile(int row, int col) {
        tiles[row][col] = new Tile(row, col);
    }

    /**
     * Placerar slumpmässigt ut ett antal Mysterium på spelplanen. Varje
     * Mysterium tilldelas slumpmässigt en av de tre implementerade
     * typerna (Tidshopp, Avgrundsvrål, Narcissus). Mysterium får inte
     * placeras i något av brädets fyra hörn, och får inte placeras
     * angränsande till ett annat Mysterium.
     *
     * @author Adnan
     * @author Joshua
     */
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

            gameView.markTile(randomRow, randomCol, "M");
            placedMysteries++;
        }
    }

    /**
     * Kontrollerar om någon av de åtta angränsande rutorna runt en
     * given position redan innehåller ett Mysterium.
     *
     * @param row raden som ska kontrolleras
     * @param col kolumnen som ska kontrolleras
     * @return true om minst en angränsande ruta innehåller ett
     *         Mysterium, annars false
     * @author Adnan
     * @author Joshua
     */
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

    /**
     * Hanterar ett spelardrag på angiven position. Om rutan är ledig
     * och placeringen är giltig (spelarens första drag i spelomgången,
     * eller en position som angränsar till en redan placerad pjäs
     * eller ett Mysterium) placeras den aktuella spelarens pjäs där,
     * överraskningar i alla åtta riktningar kontrolleras, och om
     * spelet därefter är slut avslutas det. Annars uppdateras
     * gränssnittet och turen växlas till nästa spelare.
     *
     * @param row raden på den ruta spelaren valde
     * @param col kolumnen på den ruta spelaren valde
     * @author Adnan
     * @author Joshua
     */
    public void buttonPressed(int row, int col) {
        allowSwitchTurn = true;

        if (tiles[row][col].isOccupied()) {
            gameView.updateInfoText("Square is occupied. Pick another square.");
            return;
        }

        if (currentPlayer.hasPlacedFirstTile() && !isAdjacentToPieceOrMystery(row, col)) {
            gameView.updateInfoText("You need to place your piece next to another piece or next to a Mystery.");
            return;
        }

        tiles[row][col].setOccupied(true);
        tiles[row][col].setOwner(currentPlayer);
        currentPlayer.setHasPlacedFirstTile(true);

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

    /**
     * Går igenom rutorna i en given riktning, med start en ruta bort
     * från den nyligen placerade pjäsen, och samlar upp motståndarens
     * pjäser samt eventuella oaktiverade Mysterium i en kedja. Om
     * kedjan avslutas med spelarens egen pjäs byter alla insamlade
     * rutor ägare, och eventuella Mysterium i kedjan aktiveras.
     *
     * @param row rad för den ruta spelaren just placerade sin pjäs på
     * @param col kolumn för den ruta spelaren just placerade sin pjäs på
     * @param dRow radförflyttning per steg i den riktning som undersöks
     * @param dCol kolumnförflyttning per steg i den riktning som undersöks
     * @param currentPlayer spelaren som just gjorde draget
     * @param otherPlayer motståndaren till den spelare som gjorde draget
     * @author Adnan
     * @author Joshua
     */
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

    /**
     * Växlar aktiv spelare, förutsatt att inget Mysterium (t.ex.
     * Tidshopp) har förhindrat detta för det aktuella draget. Efter
     * ett normalt byte kontrolleras om den nya aktiva spelaren ska
     * hoppa över sin tur på grund av ett tidigare Narcissus-Mysterium,
     * och byter i så fall tillbaka igen.
     *
     * @author Adnan
     * @author Joshua
     */
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

    /**
     * Kontrollerar om någon av de åtta angränsande rutorna runt en
     * given position innehåller en placerad pjäs eller ett Mysterium.
     * Används för att avgöra om en spelare får placera en ny pjäs på
     * angiven position, förutom vid spelarens första drag i
     * spelomgången då detta krav inte gäller.
     *
     * @param row raden som ska kontrolleras
     * @param col kolumnen som ska kontrolleras
     * @return true om minst en angränsande ruta är ockuperad eller
     *         innehåller ett Mysterium, annars false
     * @author Adnan
     * @author Joshua
     */
    private boolean isAdjacentToPieceOrMystery(int row, int col) {
        for (int[] dir : directions) {
            int r = row + dir[0];
            int c = col + dir[1];
            if (r >= 0 && r < tiles.length && c >= 0 && c < tiles[0].length) {
                Tile neighbor = tiles[r][c];
                if (neighbor.isOccupied() || neighbor.isMystery()) {
                    return true;
                }
            }
        }
        return false;
    }

    // ------------------------------------------------------------
    // Game Ending
    // ------------------------------------------------------------

    /**
     * Hanterar avslutningen av ett spel. Inaktiverar spelplanen,
     * avgör vinnaren och visar antingen en namnfråga för highscore-
     * listan (om poängen kvalar in), ett meddelande om att poängen
     * inte kvalade in, eller ett meddelande om oavgjort resultat.
     *
     * @author Adnan
     * @author Joshua
     */
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

    /**
     * Kontrollerar om spelet är slut, det vill säga om spelplanen är
     * full eller om alla utplacerade Mysterium har aktiverats.
     *
     * @return true om spelet är slut, annars false
     * @author Adnan
     * @author Joshua
     */
    private boolean checkGameOver() {
        return isBoardFull() || allMysteriesActivated();
    }

    /**
     * Kontrollerar om samtliga rutor på spelplanen är ockuperade.
     *
     * @return true om spelplanen är full, annars false
     * @author Adnan
     * @author Joshua
     */
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

    /**
     * Räknar antalet utplacerade Mysterium som redan har aktiverats
     * och kontrollerar om samtliga utplacerade Mysterium är aktiverade.
     *
     * @return true om alla utplacerade Mysterium har aktiverats,
     *         annars false
     * @author Adnan
     * @author Joshua
     */
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

    /**
     * Avgör vinnaren av matchen genom att jämföra hur många rutor
     * respektive spelare kontrollerar på spelplanen.
     *
     * @return den spelare som kontrollerar flest rutor, eller null
     *         om båda spelarna kontrollerar lika många rutor
     * @author Adnan
     * @author Joshua
     */
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

    /**
     * Registrerar den nuvarande spelarens resultat i highscore-listan
     * under det angivna namnet, och sparar den uppdaterade listan till
     * fil.
     *
     * @param name namnet vinnaren angav för highscore-listan
     * @return det angivna namnet
     * @author Adnan
     * @author Joshua
     */
    public String winnerName(String name) {
        if (name != null) {
            int score = currentPlayer.getAmountOfTilesOccupied();
            highscore.addEntry(name, score);
            highscore.saveToFile("highscore.txt");
        }
        return name;
    }

    /**
     * Hämtar highscore-listan formaterad som en läsbar textsträng.
     *
     * @return den formaterade highscore-listan
     * @author Adnan
     * @author Joshua
     */
    public String getHighscore() {
        return highscore.getFormattedHighscore();
    }

    // ------------------------------------------------------------
    // Game reset logic
    // ------------------------------------------------------------

    /**
     * Startar om spelet. Skapar en ny, tom spelplan med nya
     * slumpmässigt utplacerade Mysterium, nollställer båda spelarnas
     * poäng, turordningsrelaterade tillstånd samt information om
     * huruvida spelaren gjort sitt första drag, samt återställer det
     * grafiska gränssnittet till startläge.
     *
     * @author Adnan
     * @author Joshua
     */
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
            p.setHasPlacedFirstTile(false);
        }

        currentPlayer = players.getFirst();
        otherPlayer = players.getLast();

        gameView.enableBoard();
        gameView.updateCurrentPlayer(currentPlayer.getName());
        gameView.updateInfoText("New Game. Pick a square");
    }
}