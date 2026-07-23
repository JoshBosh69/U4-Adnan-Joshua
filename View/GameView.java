package View;

import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameView {
    public GameView() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        JFrame frame = new JFrame("Game View");
        frame.add(panel, GroupLayout.Alignment.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setVisible(true);

        
    }
    public static void main(String[] args) {
        new GameView();
    }
}
