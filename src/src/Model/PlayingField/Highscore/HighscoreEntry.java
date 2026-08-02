package Model.PlayingField.Highscore;

public class HighscoreEntry {
    private String name;
    private int score;

    public HighscoreEntry(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
}
