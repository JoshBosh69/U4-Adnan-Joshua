package Model.PlayingField.Mysteries;

import Controller.GameController;
import Model.Player.Player;

public class TimeJump implements MysteryTile{
    private boolean isActive;

    public TimeJump() {
        isActive = true;
    }


    @Override
    public boolean activateMystery(Player currentPlayer, Player otherPlayer) {
        return false;
    }

    public boolean isActive() {
        return isActive;
    }

    @Override
    public void setIsActive(boolean status) {
        isActive = status;
    }
}
