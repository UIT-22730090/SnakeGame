package SnakeGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    private static final int SCREEN_WIDTH = 600;
    private static final int SCREEN_HEIGHT = 600;
    private static final int UNIT_SIZE = 25;
    private static int DELAY;

    private final ArrayList<Integer> snakeX = new ArrayList<>();
    private final ArrayList<Integer> snakeY = new ArrayList<>();
    private int appleX;
    private int appleY;
    private char direction = 'R';
    private boolean running = false;
    private boolean gameOver = false; // Flag to indicate game over
    private Timer timer;
    private final Random random = new Random();
    private int score = 0;

    public GamePanel(Difficulty difficulty) {
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setBackground(Color.black);
        setFocusable(true);
        addKeyListener(new MyKeyAdapter());
        // Set the delay based on the difficulty level
        DELAY = switch (difficulty) {
            case EASY ->
                150;
            case MEDIUM ->
                85;
            case HARD ->
                50;
            default ->
                75;
        }; // Adjust this value for the desired speed
        // Adjust this value for the desired speed
        // Adjust this value for the desired speed
        // Default to medium speed

        timer = new Timer(DELAY, this);
        startGame();
    }
//Hàm bắt đàu game
    private void startGame() {
        newApple();
        snakeX.clear();
        snakeY.clear();
        snakeX.add(SCREEN_WIDTH / 2);
        snakeY.add(SCREEN_HEIGHT / 2);
        running = true;
        gameOver = false; // Reset the game over flag
        timer = new Timer(DELAY, this);
        timer.start();
    }
    // thực hiện hành đông
    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkApple();
            checkCollisions();
            repaint();
        }
    }

    private void move() {
        // Move the snake's body
        for (int i = snakeX.size() - 1; i > 0; i--) {
            snakeX.set(i, snakeX.get(i - 1));
            snakeY.set(i, snakeY.get(i - 1));
        }

        // Move the snake's head based on the direction
        switch (direction) {
            case 'U' ->
                snakeY.set(0, snakeY.get(0) - UNIT_SIZE);
            case 'D' ->
                snakeY.set(0, snakeY.get(0) + UNIT_SIZE);
            case 'L' ->
                snakeX.set(0, snakeX.get(0) - UNIT_SIZE);
            case 'R' ->
                snakeX.set(0, snakeX.get(0) + UNIT_SIZE);
        }
    }

    private void checkApple() {
        if (snakeX.get(0) == appleX && snakeY.get(0) == appleY) {
            score++;
            int TailX = snakeX.get(snakeX.size() - 1);
            int TailY = snakeY.get(snakeY.size() - 1);
            snakeX.add(TailX);
            snakeY.add(TailY);
            newApple();
        }
    }

    private void checkCollisions() {

        for (int i = 2; i < snakeX.size(); i++) {
            if (snakeX.get(0).equals(snakeX.get(i)) && snakeY.get(0).equals(snakeY.get(i))) {
                gameOver = true;
                running = false;
                break;
            }
        }

        if (snakeX.get(0) < 0 || snakeX.get(0) >= SCREEN_WIDTH || snakeY.get(0) < 0 || snakeY.get(0) >= SCREEN_HEIGHT) {
            gameOver = true;  // Set the gameOver flag to true
            running = false;
        }

        /*if (!running) {
            timer.stop();
        }*/
    }
    //Xuất hiện táo ngẫu nhiên
    private void newApple() {
        appleX = random.nextInt((SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        appleY = random.nextInt((SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g) {
        if (running) {
            g.setColor(Color.white);
            g.setFont(new Font("Ink Free", Font.BOLD, 30));
            g.drawString("Score: " + score, 10, 30);
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < snakeX.size(); i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                } else {
                    g.setColor(Color.orange);
                }
                g.fillRect(snakeX.get(i), snakeY.get(i), UNIT_SIZE, UNIT_SIZE);
            }
        } else {
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 75));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);

            // Draw the "Play Again" button
            if (gameOver) {
                JButton playAgainButton = new JButton("Play Again");
                playAgainButton.setBounds((SCREEN_WIDTH - 100) / 2, (SCREEN_HEIGHT + metrics.getHeight()) / 2, 100, 40);
                playAgainButton.addActionListener((ActionEvent e) -> {
                    // Restart the game
                    restartGame();
                });
                add(playAgainButton);
            }
        }
    }

    private void restartGame() {
        // Remove the "Play Again" button
        removePlayAgainButton();

        // Reset all necessary game variables
        newApple();
        snakeX.clear();
        snakeY.clear();
        snakeX.add(SCREEN_WIDTH / 2);
        snakeY.add(SCREEN_HEIGHT / 2);
        running = true;
        gameOver = false;
        score = 0; // Reset the score
        timer.start();

        // Stop the current timer and repaint the panel
        //timer.stop();
        setFocusable(true); // Make sure the panel is focusable
        addKeyListener(new MyKeyAdapter()); // Add the key listener
        repaint();

    }

    private void removePlayAgainButton() {
        // Remove the "Play Again" button if it exists
        Component[] components = getComponents();
        for (Component component : components) {
            if (component instanceof JButton) {
                remove(component);
            }
        }
    }

    private class MyKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if ((key == KeyEvent.VK_LEFT) && (direction != 'R')) {
                direction = 'L';
            } else if ((key == KeyEvent.VK_RIGHT) && (direction != 'L')) {
                direction = 'R';
            } else if ((key == KeyEvent.VK_UP) && (direction != 'D')) {
                direction = 'U';
            } else if ((key == KeyEvent.VK_DOWN) && (direction != 'U')) {
                direction = 'D';
            }
        }
    }
}
