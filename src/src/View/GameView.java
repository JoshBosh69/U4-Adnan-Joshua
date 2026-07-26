package View;

import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameView {
    public GameView() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        
        JFrame frame = new JFrame("Game View");
        
        // FIX: Removed "GroupLayout.Alignment.CENTER" which was causing the crash.
        // Adding the panel directly defaults it to the center of the BorderLayout.
        frame.add(panel); 
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
        new GameView();
    }
}