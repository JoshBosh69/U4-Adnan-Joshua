package Model.Player;

public class Player {
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

    public void setWinnerrName(String name) {
        if(name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }else {
            this.name = name;
        }
    }

    public int  getAmountOfTilesOccupied() {
        return amountOfTilesOccupied;
    }

    public void addOccupiedTile() {
        amountOfTilesOccupied++;
    }
}
