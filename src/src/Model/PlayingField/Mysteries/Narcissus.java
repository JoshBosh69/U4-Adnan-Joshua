package Model.PlayingField.Mysteries;

import Controller.GameController;
import Model.Player.Player;

public class Narcissus implements MysteryTile{
    private boolean isActive;

    public Narcissus() {
        isActive = true;
    }

    @Override
    public boolean activateMystery(Player currentPlayer, Player otherPlayer) {
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
