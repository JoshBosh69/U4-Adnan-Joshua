package Model.PlayingField;

import Model.Player.Player;

public class Tile {
    private int xcor;
    private int ycor;
    private boolean isOccupied = false;
    Player player;

    public Tile(int xcor, int ycor) {
        this.xcor = xcor;
        this.ycor = ycor;
    }

    public void setXcor(int xcor) {
        this.xcor = xcor;
    }

    public int getXcor() {
        return xcor;
    }

    public void setYcor(int ycor) {
        this.ycor = ycor;
    }

    public int getYcor() {
        return ycor;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public Player getOwner() {
        return player;
    }

    public void setOwner(Player player) {
        this.player = player;
    }


}
