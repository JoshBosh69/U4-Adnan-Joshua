package Model.Player;

/**
 * Modellklass som representerar en spelare i spelet. Håller reda på
 * spelarens namn, vilket märke (symbol) spelaren spelar med, hur många
 * rutor spelaren för närvarande kontrollerar samt om spelaren ska
 * hoppa över sin nästa tur på grund av ett Narcissus-Mysterium.
 *
 * @author Adnan
 * @author Joshua
 */
public class Player {
    private String name;
    private String mark;
    private int amountOfTilesOccupied;
    private boolean skipNextTurn = false;

    /**
     * Skapar en ny spelare med angivet namn och märke.
     *
     * @param name namnet på spelaren
     * @param mark symbolen som spelarens pjäser ska markeras med
     * @author Adnan
     * @author Joshua
     */
    public Player(String name, String mark) {
        this.name = name;
        this.mark = mark;
    }

    /**
     * Hämtar spelarens namn.
     *
     * @return spelarens namn
     * @author Adnan
     * @author Joshua
     */
    public String getName() {
        return name;
    }

    /**
     * Hämtar spelarens märke (symbol).
     *
     * @return spelarens märke
     * @author Adnan
     * @author Joshua
     */
    public String getMark() {
        return mark;
    }

    /**
     * Hämtar antalet rutor spelaren för närvarande kontrollerar på
     * spelplanen.
     *
     * @return antalet rutor spelaren kontrollerar
     * @author Adnan
     * @author Joshua
     */
    public int getAmountOfTilesOccupied() {
        return amountOfTilesOccupied;
    }

    /**
     * Räknar upp antalet rutor spelaren kontrollerar med ett, används
     * när spelaren placerar en ny pjäs på spelplanen.
     *
     * @author Adnan
     * @author Joshua
     */
    public void addOccupiedTile() {
        amountOfTilesOccupied++;
    }

    /**
     * Anger om spelaren ska hoppa över sin nästa tur, exempelvis till
     * följd av att Mysteriet Narcissus har aktiverats.
     *
     * @param skipNextTurn true om spelaren ska hoppa över nästa tur,
     *                      annars false
     * @author Adnan
     * @author Joshua
     */
    public void setSkipNextTurn(boolean skipNextTurn) {
        this.skipNextTurn = skipNextTurn;
    }

    /**
     * Kontrollerar om spelaren ska hoppa över sin nästa tur.
     *
     * @return true om spelaren ska hoppa över nästa tur, annars false
     * @author Adnan
     * @author Joshua
     */
    public boolean getSkipNextTurn() {
        return skipNextTurn;
    }

    /**
     * Sätter antalet rutor spelaren kontrollerar till ett angivet
     * värde. Används bland annat för att nollställa poängen när ett
     * nytt spel startas.
     *
     * @param amount antalet rutor spelaren ska kontrollera
     * @author Adnan
     * @author Joshua
     */
    public void setAmountOfOccupiedTiles(int amount) {
        amountOfTilesOccupied = amount;
    }
}