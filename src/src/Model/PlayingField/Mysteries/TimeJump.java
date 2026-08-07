package Model.PlayingField.Mysteries;

import Model.Player.Player;
import Model.PlayingField.Tile;
import java.util.List;

/**
 * Representerar Mysteriet Tidshopp. När detta Mysterium aktiveras får
 * den spelare som aktiverade det spela en gång till, det vill säga
 * turen växlas inte till motståndaren efter draget.
 *
 * @author Adnan
 * @author Joshua
 */
public class TimeJump implements MysteryTile {
    private boolean isActive;

    /**
     * Skapar ett nytt Tidshopp-Mysterium, som från början är aktivt
     * (ej aktiverat).
     *
     * @author Adnan
     * @author Joshua
     */
    public TimeJump() {
        isActive = true;
    }

    /**
     * Aktiverar Tidshopp-effekten. Förhindrar att turen växlas till
     * motståndaren, så att den aktuella spelaren får spela en gång
     * till.
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
     * @return false, eftersom turen inte ska växla till motståndaren
     *         efter aktiveringen
     * @author Adnan
     * @author Joshua
     */
    @Override
    public boolean activateMystery(Player currentPlayer, Player otherPlayer, int row, int col, Tile[][] tiles, int[][] directions, List<Tile> affectedTiles) {
        return false;
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
        return "TimeJump";
    }
}