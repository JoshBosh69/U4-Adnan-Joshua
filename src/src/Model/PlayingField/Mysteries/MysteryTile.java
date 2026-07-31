package Model.PlayingField.Mysteries;

import Controller.GameController;
import Model.Player.Player;
import Model.PlayingField.Tile;

import java.util.List;

public interface MysteryTile {

    public boolean activateMystery(Player currentPlayer, Player otherPlayer, int row, int col, Tile[][] tiles, int[][] directions, List<Tile> affectedTiles);

    public boolean isActive();

    public void setIsActive(boolean status);
}
