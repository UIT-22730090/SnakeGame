package SnakeGame;
import javax.swing.*;

public class GameFrame extends JFrame {
    private final Difficulty difficulty;

    GameFrame(Difficulty difficulty) {
        this.difficulty = difficulty;
        setTitle("Snake Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        add(new GamePanel(difficulty));  // Use "GamePanel", not "GamePannel"
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    public Difficulty getDifficulty() {
        return difficulty;
    }

    public static void main(String[] args) {
        // Start the game by showing the menu
        SwingUtilities.invokeLater(() -> {
            new MenuFrame();
        });
    }
}