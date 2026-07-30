package Model.PlayingField.Mysteries;

import Controller.GameController;
import Model.Player.Player;

public interface MysteryTile {

    public boolean activateMystery(Player currentPlayer, Player otherPlayer);

    public boolean isActive();

    public void setIsActive(boolean status);
}
