package Model.PlayingField;

public class Tile {
    private int xcor;
    private int ycor;
    private boolean isOccupied = false;

    public Tile(int xcor, int ycor) {
        this.xcor = xcor;
        this.ycor = ycor;
    }
}
