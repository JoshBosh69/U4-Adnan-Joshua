package View;

public class Main {
    public static void main(String[] args) {
        // Starta spelet genom att skapa en instans av GameView
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GameView();
            }
        });
    }
}
