package threads;

import java.util.List;
import java.util.Random;

import constants.Direction;
import models.Ghost;

public class GhostMovementThread extends Thread {
    private List<Ghost> ghosts;
    private final Object lock;
    private boolean running;

    public GhostMovementThread(List<Ghost> ghosts, Object lock) {
        this.ghosts = ghosts;
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
                    for (Ghost ghost : ghosts) {
                        ghost.move();
                    }
            }
            try {
                Thread.sleep(65);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
