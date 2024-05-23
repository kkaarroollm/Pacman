package threads;

import models.Ghost;

public class GhostMovementThread extends Thread {
    private Ghost ghost;
    private final Object lock;
    private boolean running;

    public GhostMovementThread(Ghost ghost, Object lock) {
        this.ghost = ghost;
        this.lock = lock;
        this.running = true;
        setName("GhostAnimationThread");
    }

    public void stopAnimation() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            synchronized (lock) {
                ghost.updateAnimationFrame();
                ghost.move();
            }
            try {
                Thread.sleep(275);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
