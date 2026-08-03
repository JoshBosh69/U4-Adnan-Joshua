package Model.Highscore;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Modellklass som representerar och hanterar spelets highscore-lista.
 * Ansvarar för att lägga till nya resultat, hålla listan sorterad i
 * fallande poängordning, avgöra om ett resultat kvalificerar in på
 * topp-10-listan samt läsa och skriva listan till en textfil.
 *
 * @author Adnan
 * @author Joshua
 */
public class Highscore {
    private List<HighscoreEntry> entries = new ArrayList<>();

    /**
     * Skriver samtliga poster i highscore-listan till en textfil, en
     * post per rad i formatet "namn,poäng".
     *
     * @param filename namnet på filen som listan ska sparas till
     * @author Adnan
     * @author Joshua
     */
    public void saveToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (HighscoreEntry entry : entries) {
                writer.println(entry.getName() + "," + entry.getScore());
            }
        } catch (IOException e) {
        }
    }

    /**
     * Läser in highscore-listan från en textfil och ersätter den
     * befintliga listan med de inlästa posterna. Varje rad i filen
     * tolkas som "namn,poäng". Listan sorteras efter inläsning.
     *
     * @param filename namnet på filen som listan ska läsas in från
     * @author Adnan
     * @author Joshua
     */
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

    /**
     * Lägger till en ny post i highscore-listan med angivet namn och
     * poäng, sorterar därefter om listan och tar bort den sämsta posten
     * om listan innehåller fler än 10 poster.
     *
     * @param name  namnet som ska registreras för resultatet
     * @param score poängen som ska registreras
     * @author Adnan
     * @author Joshua
     */
    public void addEntry(String name, int score) {
        entries.add(new HighscoreEntry(name, score));
        sortEntries();

        if (entries.size() > 10) {
            entries.remove(entries.size() - 1); // ta bort den sämsta, som nu hamnat sist
        }
    }

    /**
     * Sorterar highscore-listans poster i fallande ordning efter poäng,
     * med hjälp av en enkel bubbelsortering.
     *
     * @author Adnan
     * @author Joshua
     */
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

    /**
     * Kontrollerar om ett givet poängresultat kvalificerar in på
     * highscore-listan, det vill säga om listan innehåller färre än 10
     * poster eller om resultatet är högre än det lägsta resultatet på
     * listan.
     *
     * @param score poängen som ska kontrolleras
     * @return true om resultatet kvalificerar in på listan, annars false
     * @author Adnan
     * @author Joshua
     */
    public boolean qualifies(int score) {
        if (entries.size() < 10) {
            return true;
        }

        int lowestScore = entries.get(entries.size() - 1).getScore();
        return score > lowestScore;
    }

    /**
     * Hämtar samtliga poster i highscore-listan.
     *
     * @return listan med highscore-poster
     * @author Adnan
     * @author Joshua
     */
    public List<HighscoreEntry> getEntries() {
        return entries;
    }

    /**
     * Bygger en läsbar textrepresentation av highscore-listan, där
     * varje post visas med sin placering, namn och poäng på en egen
     * rad.
     *
     * @return den formaterade highscore-listan som en textsträng
     * @author Adnan
     * @author Joshua
     */
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