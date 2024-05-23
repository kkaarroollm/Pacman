package controllers;

import models.*;
import threads.*;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

public class Game extends JFrame {
    final Object lock = new Object();
    private final GameLoopThread gameLoopThread;
    private final AnimationThread animationThread;
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
//        CollisionDetector collisionDetector = new CollisionDetector(board.getWalls(), board.getCoins(), BLOCK_SIZE);

        // Movable objects needs to contain a WallDetector object
        List<Ghost> ghosts = Arrays.asList(new Ghost(board), new Ghost(board), new Ghost(board));
        Pacman pacman = new Pacman(board);

        GamePanel gamePanel = new GamePanel(this, pacman, ghosts, board);
        getContentPane().add(gamePanel);


        animationThread = new AnimationThread(pacman, ghosts, board.getCoins(), lock);
        animationThread.start();


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
        animationThread.stopAnimation();

        gameTimerThread.stopTimer();
        scoreUpdateThread.stopScoreUpdate();
        lifeCounterThread.stopLifeCounter();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Game::new);
    }
}
