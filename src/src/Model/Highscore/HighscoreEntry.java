package Model.Highscore;

/**
 * Modellklass som representerar en enskild post i highscore-listan,
 * bestående av ett namn och ett tillhörande poängresultat.
 *
 * @author Adnan
 */
public class HighscoreEntry {
    private String name;
    private int score;

    /**
     * Skapar en ny highscore-post med angivet namn och poäng.
     *
     * @param name  namnet som ska registreras för resultatet
     * @param score poängen som ska registreras
     * @author Adnan
     * @author Joshua
     */
    public HighscoreEntry(String name, int score) {
        this.name = name;
        this.score = score;
    }

    /**
     * Hämtar namnet för denna highscore-post.
     *
     * @return namnet som registrerats för resultatet
     * @author Adnan
     * @author Joshua
     */
    public String getName() {
        return name;
    }

    /**
     * Hämtar poängen för denna highscore-post.
     *
     * @return poängen som registrerats för resultatet
     * @author Adnan
     * @author Joshua
     */
    public int getScore() {
        return score;
    }
}