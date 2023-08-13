import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuFrame extends JFrame {
    private JButton easyButton;
    private JButton mediumButton;
    private JButton hardButton;

    MenuFrame() {
        setTitle("Snake Game Menu");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 1));

        easyButton = new JButton("Easy");
        mediumButton = new JButton("Medium");
        hardButton = new JButton("Hard");

        easyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startGame(Difficulty.EASY);
            }
        });

        mediumButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startGame(Difficulty.MEDIUM);
            }
        });

        hardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startGame(Difficulty.HARD);
            }
        });

        add(easyButton);
        add(mediumButton);
        add(hardButton);

        setVisible(true);
    }

    private void startGame(Difficulty difficulty) {
        dispose(); // Close the menu frame
        SwingUtilities.invokeLater(() -> {
            new GameFrame(difficulty);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MenuFrame();
        });
    }
}
