package Model.PlayingField.Mysteries;

import Model.Player.Player;
import Model.PlayingField.Tile;
import java.util.List;

/**
 * Representerar Mysteriet Narcissus. När detta Mysterium aktiveras
 * blir den spelare som aktiverade det tvungen att hoppa över sin
 * nästa tur.
 *
 * @author Adnan
 * @author Joshua
 */
public class Narcissus implements MysteryTile {
    private boolean isActive;

    /**
     * Skapar ett nytt Narcissus-Mysterium, som från början är aktivt
     * (ej aktiverat).
     *
     * @author Adnan
     * @author Joshua
     */
    public Narcissus() {
        isActive = true;
    }

    /**
     * Aktiverar Narcissus-effekten genom att markera att den aktuella
     * spelaren ska hoppa över sin nästa tur.
     *
     * @param currentPlayer  spelaren som aktiverade Mysteriet
     * @param otherPlayer    motståndaren till den spelare som
     *                       aktiverade Mysteriet
     * @param row            raden för den ruta Mysteriet ligger på
     * @param col            kolumnen för den ruta Mysteriet ligger på
     * @param tiles          spelplanens samtliga rutor
     * @param directions     de åtta riktningar som kan undersökas runt
     *                       en ruta
     * @param affectedTiles  lista som fylls med de rutor som påverkas
     *                       av aktiveringen
     * @return true, eftersom aktuell spelare ska fortsätta ha sin tur
     *         som vanligt efter aktiveringen (själva hoppet över
     *         turen sker först nästa gång turen skulle växla till
     *         spelaren)
     * @author Adnan
     * @author Joshua
     */
    @Override
    public boolean activateMystery(Player currentPlayer, Player otherPlayer, int row, int col, Tile[][] tiles, int[][] directions, List<Tile> affectedTiles) {
        currentPlayer.setSkipNextTurn(true);
        return true;
    }

    /**
     * Kontrollerar om Mysteriet är aktivt, det vill säga om det ännu
     * inte har aktiverats.
     *
     * @return true om Mysteriet är aktivt, annars false
     * @author Adnan
     * @author Joshua
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Anger Mysteriets aktiveringsstatus.
     *
     * @param status true om Mysteriet ska vara aktivt, annars false
     * @author Adnan
     * @author Joshua
     */
    @Override
    public void setIsActive(boolean status) {
        isActive = status;
    }

    /**
     * Hämtar Mysteriets namn, för visning för spelaren när
     * Mysteriet aktiveras.
     *
     * @return Mysteriets namn
     * @author Adnan
     * @author Joshua
     */
    @Override
    public String getName() {
        return "Narcissus";
    }

}