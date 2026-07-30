package Model.Player;

public class Player {
    private String name;
    private String mark;
    private boolean isCurrentlyPlaying = false;
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

    public int getAmountOfTilesOccupied() {
        System.out.println(amountOfTilesOccupied);
        return amountOfTilesOccupied;
    }

    public void addOccupiedTile() {
        amountOfTilesOccupied++;
        //System.out.println("add function: " + amountOfTilesOccupied);
    }

    public void setSkipNextTurn (boolean skipNextTurn) {
        this.skipNextTurn = skipNextTurn;
    }

    public boolean getSkipNextTurn() {
        return skipNextTurn;
    }
}
