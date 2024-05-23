package threads;

import models.Coin;
import models.Ghost;
import models.Pacman;

import java.util.List;

public class AnimationThread extends Thread {
    private final Pacman pacman;
    private final List<Ghost> ghosts;
    private final List<Coin> coins;
    private final Object lock;
    private boolean running;

    public AnimationThread(Pacman pacman, List<Ghost> ghosts, List<Coin> coins, Object lock) {
        this.pacman = pacman;
        this.ghosts = ghosts;
        this.coins = coins;
        this.lock = lock;
        this.running = true;
        setName("AnimationThread");
    }

    public void stopAnimation() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            synchronized (lock) {
                pacman.updateAnimationFrame();

                for (Ghost ghost : ghosts) {
                    ghost.updateAnimationFrame();
                }

                for (Coin coin : coins) {
                    coin.updateAnimationFrame();
                }

            }
            try {
                Thread.sleep(120);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
