package Model.PlayingField.Mysteries;

import Controller.GameController;
import Model.Player.Player;

public class AbyssalRoar implements MysteryTile{
    private boolean isActive;

    public AbyssalRoar() {
        isActive = true;
    }

    @Override
    public boolean activateMystery(Player currentPlayer, Player otherPlayer) {
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
