package Controller;

import Model.Player.Player;
import Model.PlayingField.Tile;
import View.GameView;

public class GameController {
    private GameView gameView;
    private Player player1;
    private Player player2;
    private Tile[][] tiles = new Tile[10][10];

    public GameController() {
        this.player1 = new Player();
        this.player2 = new Player();
        player1.setIsCurrentlyPlaying(true);
        this.gameView = new GameView(this);
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
}
