package Model.PlayingField.Mysteries;

import Model.Player.Player;
import Model.PlayingField.Tile;

import java.util.List;

public class AbyssalRoar implements MysteryTile{
    private boolean isActive;

    public AbyssalRoar() {
        isActive = true;
    }

    @Override
    public boolean activateMystery(Player currentPlayer, Player otherPlayer, int row, int col, Tile[][] tiles, int[][] directions, List<Tile> affectedTiles) {

        for (int[] dir : directions) {
            int r = row + dir[0];
            int c = col + dir[1];

            if (r >= 0 && r < tiles.length && c >= 0 && c < tiles[0].length) {
                Tile neighbor = tiles[r][c];
                neighbor.setOwner(null);
                neighbor.setOccupied(false);
                affectedTiles.add(neighbor);
            }
        }

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
