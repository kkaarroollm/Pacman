package controllers;

import models.*;
import threads.*;
import utils.BoardReaderUtils;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

public class Game extends JFrame {
    final Object lock = new Object();
    private final GameLoopThread gameLoopThread;
    private final AnimationThread animationThread;
    private final GhostMovementThread ghostMovementThread;
    private final GameTimerThread gameTimerThread;
    public static final int BLOCK_SIZE = 24;

    public Game() {
        setTitle("Pacman");
        setSize(600, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);

        Board board = new Board();

        // Movable objects needs to contain a WallDetector object
        List<Ghost> ghosts = Arrays.asList(new Ghost(board, 240, 240), new Ghost(board, 120, 120), new Ghost(board, 120, 60));
        Pacman pacman = new Pacman(board);

        GamePanel gamePanel = new GamePanel(this, pacman, ghosts, board);
        getContentPane().add(gamePanel);


        animationThread = new AnimationThread(pacman, ghosts, board.getCoins(), lock);
        animationThread.start();

        ghostMovementThread = new GhostMovementThread(ghosts, lock);
        ghostMovementThread.start();

        gameLoopThread = new GameLoopThread(gamePanel, pacman, lock);
        gameLoopThread.start();

        gameTimerThread = new GameTimerThread(gamePanel);
        gameTimerThread.start();

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
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Game::new);
    }
}
