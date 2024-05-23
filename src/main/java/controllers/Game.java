package controllers;

import models.*;
import threads.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Game extends JFrame {
    final Object lock = new Object();
    private final GameLoopThread gameLoopThread;
    private final PacmanAnimationThread pacmanAnimationThread;
    private final List<GhostAnimationThread> ghostAnimationThreads;
    private final GameTimerThread gameTimerThread;
    private final ScoreUpdateThread scoreUpdateThread;
    private final LifeCounterThread lifeCounterThread;

    public static final int BLOCK_SIZE = 24;

    public Game() {
        setTitle("Pacman");
        setSize(600, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);

        Board board = new Board();
        WallDetector wallDetector = new WallDetector(board.getWalls(), BLOCK_SIZE);
        List<Ghost> ghosts = Arrays.asList(new Ghost(wallDetector), new Ghost(wallDetector), new Ghost(wallDetector));
        Pacman pacman = new Pacman(wallDetector);


        GamePanel gamePanel = new GamePanel(this, pacman, ghosts, board);
        getContentPane().add(gamePanel);


        pacmanAnimationThread = new PacmanAnimationThread(pacman, lock);
        pacmanAnimationThread.start();

        ghostAnimationThreads = new ArrayList<>();
        for (Ghost ghost : ghosts) {
            GhostAnimationThread ghostAnimationThread = new GhostAnimationThread(ghost, lock);
            ghostAnimationThread.start();
            ghostAnimationThreads.add(ghostAnimationThread);
        }

        gameLoopThread = new GameLoopThread(gamePanel, pacman, ghosts, lock);
        gameLoopThread.start();

        gameTimerThread = new GameTimerThread(gamePanel);
        gameTimerThread.start();

        scoreUpdateThread = new ScoreUpdateThread(gamePanel, lock);
        scoreUpdateThread.start();

        lifeCounterThread = new LifeCounterThread(gamePanel, lock);
        lifeCounterThread.start();

        setVisible(true);
        System.out.println("Game started");
        Thread[] threads = new Thread[Thread.activeCount()];
        Thread.enumerate(threads);
        for (Thread thread : threads) {
            System.out.println(thread);
        }
    }

    public void stopGame() {
        gameLoopThread.stopGame();
        pacmanAnimationThread.stopAnimation();
        for (GhostAnimationThread ghostAnimationThread : ghostAnimationThreads) {
            ghostAnimationThread.stopAnimation();
        }
        gameTimerThread.stopTimer();
        scoreUpdateThread.stopScoreUpdate();
        lifeCounterThread.stopLifeCounter();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Game::new);
    }
}
