package controllers;

import constants.Direction;
import models.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class GamePanel extends JPanel {
    final Game game;
    private final Pacman pacman;
    private final Board board;
    private final List<Ghost> ghosts;
    private int score;
    private int lives;
    private long gameTime;


    public GamePanel(Game game, Pacman pacman, List<Ghost> ghosts, Board board) {
        this.game = game;
        this.pacman = pacman;
        this.ghosts = ghosts;
        this.score = 0;
        this.lives = 3;
        this.gameTime = 0;
        this.board = board;


        setFocusable(true);

        requestFocusInWindow();


        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                keyInputPressed(e);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        drawBoard(g);
        drawPacman(g);
        drawGhosts(g);
        drawHUD(g);
    }

    private void drawPacman(Graphics g) {
        pacman.draw(g);
    }

    private void drawGhosts(Graphics g) {
        for (Ghost ghost : ghosts) {
            ghost.draw(g);
        }
    }

    private void drawHUD(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 14));

        g.drawString("Score: " + score, 10, 20);
        g.drawString("Lives: " + lives, 10, 40);
        g.drawString("Time: " + this.gameTime + "s", 10, 60);
    }

    private void drawBoard(Graphics g){
        board.draw(g);
    }

    public void updateScore(int newScore) {
        this.score = newScore;
        repaint();
    }

    public void updateLives(int newLives) {
        this.lives = newLives;
        repaint();
    }

    public void updateTime(long newGameTime) {
        this.gameTime = newGameTime;
    }

    private void keyInputPressed(KeyEvent e) {
        int key = e.getKeyCode();

        switch (key) {
            case KeyEvent.VK_UP:
                pacman.setCurrentDirection(Direction.UP);
                break;
            case KeyEvent.VK_DOWN:
                pacman.setCurrentDirection(Direction.DOWN);
                break;
            case KeyEvent.VK_LEFT:
                pacman.setCurrentDirection(Direction.LEFT);
                break;
            case KeyEvent.VK_RIGHT:
                pacman.setCurrentDirection(Direction.RIGHT);
                break;
        }

        pacman.move();
        repaint();
    }
}