package threads;

import controllers.GamePanel;

import javax.swing.*;

public class GameTimerThread extends Thread {
    private GamePanel gamePanel;
    private int time;
    private boolean running;

    public GameTimerThread(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.time = 0;
        this.running = true;
        setName("GameTimerThread");
    }

    public void stopTimer() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            time++;
            SwingUtilities.invokeLater(() -> gamePanel.updateTime(time));
        }
    }
}
