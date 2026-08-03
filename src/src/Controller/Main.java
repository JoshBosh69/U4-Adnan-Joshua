package Controller;

/**
 * Startklass för applikationen. Innehåller programmets main-metod som
 * startar spelet genom att skapa en ny instans av GameController.
 *
 * @author Adnan
 */
public class Main {

    /**
     * Startar applikationen genom att skapa en ny GameController, som
     * i sin tur sätter upp spelplanen, det grafiska gränssnittet och
     * placerar ut Mysterium.
     *
     * @param args kommandoradsargument (används inte)
     * @author Adnan
     * @author Joshua
     */
    public static void main(String[] args) {
        GameController gameController = new GameController();
    }
}