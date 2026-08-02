package Model.PlayingField.Mysteries;

import Model.Player.Player;
import Model.PlayingField.Tile;

import java.util.List;

public class Narcissus implements MysteryTile{
    private boolean isActive;

    public Narcissus() {
        isActive = true;
    }

    @Override
    public boolean activateMystery(Player currentPlayer, Player otherPlayer, int row, int col, Tile[][] tiles, int[][] directions, List<Tile> affectedTiles) {
        currentPlayer.setSkipNextTurn(true);
        return true;
    }

    public boolean isActive() {
        return isActive;
    }

    @Override
    public void setIsActive(boolean status) {
        isActive = status;
    }

}
