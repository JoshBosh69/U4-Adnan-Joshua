package Model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Highscore {
    private List<HighscoreEntry> entries = new ArrayList<>();


    public void saveToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (HighscoreEntry entry : entries) {
                writer.println(entry.getName() + "," + entry.getScore());
            }
        } catch (IOException e) {
        }
    }

    public void loadFromFile(String filename) {
        entries.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String name = parts[0];
                    int score = Integer.parseInt(parts[1]);
                    entries.add(new HighscoreEntry(name, score));
                }
            }
        } catch (IOException e) {

        }
        sortEntries();
    }

    public void addEntry(String name, int score) {
        entries.add(new HighscoreEntry(name, score));
        sortEntries();

        if (entries.size() > 10) {
            entries.remove(entries.size() - 1); // ta bort den sämsta, som nu hamnat sist
        }
    }

    private void sortEntries() {
        for (int i = 0; i < entries.size() - 1; i++) {
            for (int j = 0; j < entries.size() - 1 - i; j++) {
                if (entries.get(j).getScore() < entries.get(j + 1).getScore()) {
                    // fel ordning — byt plats
                    HighscoreEntry temp = entries.get(j);
                    entries.set(j, entries.get(j + 1));
                    entries.set(j + 1, temp);
                }
            }
        }
    }

    public boolean qualifies(int score) {
        if (entries.size() < 10) {
            return true;
        }

        int lowestScore = entries.get(entries.size() - 1).getScore();
        return score > lowestScore;
    }

    public List<HighscoreEntry> getEntries() {
        return entries;
    }

    public String getFormattedHighscore() {
        String highScore = "";
        int rank = 1;

        for (HighscoreEntry highscoreEntry : entries) {
            String name = highscoreEntry.getName();
            int score = highscoreEntry.getScore();

            highScore += rank + ". " + name + " - " + score + "\n";
            rank++;
        }

        return highScore;
    }
}
