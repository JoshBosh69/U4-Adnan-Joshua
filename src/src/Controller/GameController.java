package Controller;

import Model.Player.Player;
import View.GameView;

public class GameController {
    private GameView gameView;
    private int counter = 0;
    private Player player1;
    private Player player2;

    public GameController() {
        this.player1 = new Player();
        this.player2 = new Player();
        player1.setIsCurrentlyPlaying(true);

        this.gameView = new GameView(this);

    }

    public void buttonPressed() {
        if (player1.getIsCurrentlyPlaying()) {
            player1.setIsCurrentlyPlaying(false);
            player2.setIsCurrentlyPlaying(true);
            gameView.updateCurrentPlayer("Spelare 2");
        } else {
            player2.setIsCurrentlyPlaying(false);
            player1.setIsCurrentlyPlaying(true);
            gameView.updateCurrentPlayer("Spelare 1");
        }

    }
}
