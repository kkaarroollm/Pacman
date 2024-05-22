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
    public static final int NUM_COLUMNS = 20;
    private static final int NUM_ROWS = 20;
    public static final int SCREEN_WIDTH = BLOCK_SIZE * NUM_COLUMNS;
    public static final int SCREEN_HEIGHT = BLOCK_SIZE * NUM_ROWS;

    public Game() {
        setTitle("Pacman");
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        List<Ghost> ghosts = Arrays.asList(new Ghost(), new Ghost(), new Ghost());
        Board board = new Board(BLOCK_SIZE);
        WallDetector wallDetector = new WallDetector(board.getWalls());
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
