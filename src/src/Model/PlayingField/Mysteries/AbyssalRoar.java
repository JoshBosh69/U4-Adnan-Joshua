package Model.PlayingField.Mysteries;

import Model.Player.Player;
import Model.PlayingField.Tile;
import java.util.List;

/**
 * Representerar Mysteriet Avgrundsvrål. När detta Mysterium aktiveras
 * plockas samtliga pjäser bort från de rutor som angränsar till
 * Mysteriet, det vill säga rutorna töms på både ägare och
 * ockuperingsstatus.
 *
 * @author Adnan
 * @author Joshua
 */
public class AbyssalRoar implements MysteryTile {
    private boolean isActive;

    /**
     * Skapar ett nytt Avgrundsvrål-Mysterium, som från början är aktivt
     * (ej aktiverat).
     *
     * @author Adnan
     * @author Joshua
     */
    public AbyssalRoar() {
        isActive = true;
    }

    /**
     * Aktiverar Avgrundsvrålets effekt. Går igenom samtliga åtta
     * angränsande rutor runt Mysteriet och tar bort eventuella pjäser
     * på dessa genom att nollställa ägare och ockuperingsstatus. De
     * rutor som påverkas läggs till i affectedTiles så att vyn kan
     * uppdateras.
     *
     * @param currentPlayer  spelaren som aktiverade Mysteriet
     * @param otherPlayer    motståndaren till den spelare som
     *                       aktiverade Mysteriet
     * @param row            raden för den ruta Mysteriet ligger på
     * @param col            kolumnen för den ruta Mysteriet ligger på
     * @param tiles          spelplanens samtliga rutor
     * @param directions     de åtta riktningar som undersöks runt
     *                       Mysteriet
     * @param affectedTiles  lista som fylls med de rutor som påverkas
     *                       av aktiveringen
     * @return true, eftersom aktuell spelare ska fortsätta ha sin tur
     *         som vanligt efter aktiveringen
     * @author Adnan
     * @author Joshua
     */
    @Override
    public boolean activateMystery(Player currentPlayer, Player otherPlayer, int row, int col, Tile[][] tiles, int[][] directions, List<Tile> affectedTiles) {

        for (int[] dir : directions) {
            int r = row + dir[0];
            int c = col + dir[1];

            if (r >= 0 && r < tiles.length && c >= 0 && c < tiles[0].length) {
                Tile neighbor = tiles[r][c];
                neighbor.setOwner(null);
                neighbor.setOccupied(false);
                affectedTiles.add(neighbor);
            }
        }

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
        return "AbyssalRoar";
    }

}