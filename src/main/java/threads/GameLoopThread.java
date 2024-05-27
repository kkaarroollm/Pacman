package threads;

import controllers.GamePanel;
import models.Ghost;
import models.Pacman;

import java.util.List;

public class GameLoopThread extends Thread {
    private final GamePanel gamePanel;
    private final Pacman pacman;
    private final Object lock;
    private boolean running;

    public GameLoopThread(GamePanel gamePanel, Pacman pacman, Object lock) {
        this.gamePanel = gamePanel;
        this.pacman = pacman;
        this.lock = lock;
        this.running = true;
        setName("GameLoopThread");
    }

    public void stopGame() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            synchronized (lock) {
                updateGame();
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateGame() {
        pacman.move();
        gamePanel.repaint();
    }
}
