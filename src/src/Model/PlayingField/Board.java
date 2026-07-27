package Model.PlayingField;

public class Board {
    private int playerSquares;
    private int [][] squares = new int [8][8];


    public void setPlayerSquares(int playerSquares) {
        this.playerSquares = playerSquares;
    }
}
