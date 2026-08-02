package Model.Player;

public class Player {
    private String name;
    private String mark;
    private int amountOfTilesOccupied;
    private boolean skipNextTurn = false;

    public Player(String name, String mark) {
        this.name = name;
        this.mark = mark;
    }
    public String getName() {
        return name;
    }

    public String getMark() {
        return mark;
    }

    public int getAmountOfTilesOccupied() {
        return amountOfTilesOccupied;
    }

    public void addOccupiedTile() {
        amountOfTilesOccupied++;
    }

    public void setSkipNextTurn (boolean skipNextTurn) {
        this.skipNextTurn = skipNextTurn;
    }

    public boolean getSkipNextTurn() {
        return skipNextTurn;
    }

    public void setAmountOfOccupiedTiles(int amount) {
        amountOfTilesOccupied = amount;
    }
}
