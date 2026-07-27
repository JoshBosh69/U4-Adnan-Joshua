package Model.Player;

public class Player {
    private int platerTurn; // used in order to determine which player is currently playings
    private String name;
    private boolean isCurrentlyPlaying = false;
    private int amountOfTilesOccupied;

    public Player() {
    }

    public void setIsCurrentlyPlaying(boolean status) {
        isCurrentlyPlaying = status;
    }

    public boolean getIsCurrentlyPlaying() {
        return isCurrentlyPlaying;

    }

    public int  getAmountOfTilesOccupied() {
        return amountOfTilesOccupied;
    }

    public void addOccupiedTile() {
        amountOfTilesOccupied++;
    }
}
