package threads;

import controllers.GamePanel;
import models.Ghost;
import models.Pacman;

import java.util.List;

public class GameLoopThread extends Thread {
    private final GamePanel gamePanel;
    private final Pacman pacman;
    private final List<Ghost> ghosts;
    private final Object lock;
    private boolean running;

    public GameLoopThread(GamePanel gamePanel, Pacman pacman, List<Ghost> ghosts, Object lock) {
        this.gamePanel = gamePanel;
        this.pacman = pacman;
        this.ghosts = ghosts;
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
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateGame() {
        pacman.move();
        for (Ghost ghost : ghosts) {
            ghost.move();
        }
        gamePanel.repaint();
    }
}
