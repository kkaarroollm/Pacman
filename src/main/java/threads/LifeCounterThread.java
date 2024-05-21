package threads;

import controllers.GamePanel;

import javax.swing.*;

public class LifeCounterThread extends Thread {
    private GamePanel gamePanel;
    private int lives;
    private final Object lock;
    private boolean running;

    public LifeCounterThread(GamePanel gamePanel, Object lock) {
        this.gamePanel = gamePanel;
        this.lock = lock;
        this.lives = 3;
        this.running = true;
        setName("LifeCounterThread");
    }

    public void stopLifeCounter() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            synchronized (lock) {
                SwingUtilities.invokeLater(() -> gamePanel.updateLives(lives));
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void decreaseLives() {
        synchronized (lock) {
            lives--;
        }
    }
}
