package Model.PlayingField;

import Model.Player.Player;
import Model.PlayingField.Mysteries.MysteryTile;

/**
 * Modellklass som representerar en enskild ruta på spelplanen. Håller
 * reda på rutans position, om den är ockuperad och i så fall av vilken
 * spelare, samt om rutan utgör ett Mysterium och i så fall vilken typ
 * av Mysterium det rör sig om.
 *
 * @author Adnan
 */
public class Tile {
    private int xcor;
    private int ycor;
    private boolean isOccupied = false;
    private boolean isMystery = false;
    private MysteryTile mystery;
    private Player player;

    /**
     * Skapar en ny, tom ruta på angiven position.
     *
     * @param xcor radens position för rutan
     * @param ycor kolumnens position för rutan
     * @author Adnan
     */
    public Tile(int xcor, int ycor) {
        this.xcor = xcor;
        this.ycor = ycor;
    }

    /**
     * Sätter rutans radposition.
     *
     * @param xcor den nya radpositionen för rutan
     * @author Adnan
     */
    public void setXcor(int xcor) {
        this.xcor = xcor;
    }

    /**
     * Hämtar rutans radposition.
     *
     * @return rutans radposition
     * @author Adnan
     */
    public int getXcor() {
        return xcor;
    }

    /**
     * Sätter rutans kolumnposition.
     *
     * @param ycor den nya kolumnpositionen för rutan
     * @author Adnan
     */
    public void setYcor(int ycor) {
        this.ycor = ycor;
    }

    /**
     * Hämtar rutans kolumnposition.
     *
     * @return rutans kolumnposition
     * @author Adnan
     */
    public int getYcor() {
        return ycor;
    }

    /**
     * Kontrollerar om rutan är ockuperad av en pjäs.
     *
     * @return true om rutan är ockuperad, annars false
     * @author Adnan
     */
    public boolean isOccupied() {
        return isOccupied;
    }

    /**
     * Anger om rutan ska vara ockuperad av en pjäs.
     *
     * @param occupied true om rutan ska vara ockuperad, annars false
     * @author Adnan
     */
    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    /**
     * Hämtar den spelare som äger rutan.
     *
     * @return spelaren som äger rutan, eller null om ingen äger den
     * @author Adnan
     */
    public Player getOwner() {
        return player;
    }

    /**
     * Sätter vilken spelare som äger rutan.
     *
     * @param player spelaren som ska äga rutan
     * @author Adnan
     */
    public void setOwner(Player player) {
        this.player = player;
    }

    /**
     * Kontrollerar om rutan utgör ett Mysterium.
     *
     * @return true om rutan är ett Mysterium, annars false
     * @author Adnan
     */
    public boolean isMystery() {
        return isMystery;
    }

    /**
     * Anger om rutan ska utgöra ett Mysterium.
     *
     * @param mystery true om rutan ska vara ett Mysterium, annars false
     * @author Adnan
     */
    public void setIsMystery(boolean mystery) {
        isMystery = mystery;
    }

    /**
     * Hämtar det Mysterium-objekt som är kopplat till rutan.
     *
     * @return rutans Mysterium-typ, eller null om rutan inte är ett
     *         Mysterium
     * @author Adnan
     */
    public MysteryTile getMysteryType() {
        return mystery;
    }

    /**
     * Kopplar ett Mysterium-objekt till rutan.
     *
     * @param mysteryType det Mysterium som rutan ska tilldelas
     * @author Adnan
     */
    public void setMysteryType(MysteryTile mysteryType) {
        this.mystery = mysteryType;
    }

    /**
     * Kontrollerar om rutans Mysterium är aktiverat, det vill säga om
     * rutan har ett Mysterium och detta redan har aktiverats.
     *
     * @return true om rutans Mysterium är aktiverat, annars false
     * @author Adnan
     */
    public boolean isMysteryActive() {
        return mystery != null && mystery.isActive();
    }
}