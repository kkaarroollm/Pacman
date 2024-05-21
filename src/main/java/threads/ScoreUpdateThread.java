package threads;

import controllers.GamePanel;

import javax.swing.*;

public class ScoreUpdateThread extends Thread {
    private final GamePanel gamePanel;
    private int score;
    private final Object lock;
    private boolean running;

    public ScoreUpdateThread(GamePanel gamePanel, Object lock) {
        this.gamePanel = gamePanel;
        this.lock = lock;
        this.score = 0;
        this.running = true;
    }

    public void stopScoreUpdate() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            synchronized (lock) {
                SwingUtilities.invokeLater(() -> gamePanel.updateScore(score));
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void increaseScore(int amount) {
        synchronized (lock) {
            score += amount;
        }
    }
}
