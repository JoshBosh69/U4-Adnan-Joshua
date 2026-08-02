package Model.PlayingField.Mysteries;

import Model.Player.Player;
import Model.PlayingField.Tile;

import java.util.List;

public class TimeJump implements MysteryTile{
    private boolean isActive;

    public TimeJump() {
        isActive = true;
    }

    @Override
    public boolean activateMystery(Player currentPlayer, Player otherPlayer, int row, int col, Tile[][] tiles, int[][] directions, List<Tile> affectedTiles) {
        return false;
    }

    public boolean isActive() {
        return isActive;
    }

    @Override
    public void setIsActive(boolean status) {
        isActive = status;
    }
}
