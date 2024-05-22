package threads;

import models.Pacman;

public class PacmanAnimationThread extends Thread {
    private final Pacman pacman;
    private final Object lock;
    private boolean running;

    public PacmanAnimationThread(Pacman pacman, Object lock) {
        this.pacman = pacman;
        this.lock = lock;
        this.running = true;
        setName("PacmanAnimationThread");
    }

    public void stopAnimation() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            synchronized (lock) {
                pacman.updateAnimationFrame();
            }
            try {
                Thread.sleep(275);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
