import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class MyKeyAdapter extends KeyAdapter {
    @Override
    public void keyPressed(KeyEvent e) {
        boolean gameOver;
        if (!gameOver) {
            int key = e.getKeyCode();
            char direction = 0;
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