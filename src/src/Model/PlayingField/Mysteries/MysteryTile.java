package Model.PlayingField.Mysteries;

import Model.Player.Player;
import Model.PlayingField.Tile;

import java.util.List;

/**
 * Interface som representerar ett Mysterium på spelplanen. Definierar
 * de metoder som varje typ av Mysterium måste implementera för att
 * kunna aktiveras och hålla reda på sitt aktiveringsstatus, vilket gör
 * det möjligt att hantera olika typer av Mysterium på ett enhetligt
 * sätt.
 *
 * @author Adnan
 */
public interface MysteryTile {

    /**
     * Aktiverar Mysteriets effekt på spelplanen. Vad som sker beror på
     * vilken typ av Mysterium det rör sig om, exempelvis kan pjäser
     * läggas till eller tas bort runt Mysteriet, eller turordningen
     * påverkas.
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
     *                       av Mysteriets aktivering, så att vyn kan
     *                       uppdateras
     * @return true om aktuell spelare ska fortsätta ha sin tur efter
     *         aktiveringen, annars false
     * @author Adnan
     */
    public boolean activateMystery(Player currentPlayer, Player otherPlayer, int row, int col, Tile[][] tiles, int[][] directions, List<Tile> affectedTiles);

    /**
     * Kontrollerar om Mysteriet är aktivt, det vill säga om det ännu
     * inte har aktiverats.
     *
     * @return true om Mysteriet är aktivt, annars false
     * @author Adnan
     */
    public boolean isActive();

    /**
     * Anger Mysteriets aktiveringsstatus.
     *
     * @param status true om Mysteriet ska vara aktivt, annars false
     * @author Adnan
     */
    public void setIsActive(boolean status);
}