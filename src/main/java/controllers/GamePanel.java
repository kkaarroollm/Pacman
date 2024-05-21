package controllers;

import models.Ghost;
import models.Pacman;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class GamePanel extends JPanel {
    final Game game;
    private final Pacman pacman;
    private final List<Ghost> ghosts;
    private int score;
    private int lives;
    private long gameTime;

    public GamePanel(Game game, Pacman pacman, List<Ghost> ghosts) {
        this.game = game;
        this.pacman = pacman;
        this.ghosts = ghosts;
        this.score = 0;
        this.lives = 3;
        this.gameTime = 0;

        setPreferredSize(new Dimension(800, 600));
        setFocusable(true);
        requestFocusInWindow();

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                keyInputPressed(e);
            }
            @Override
            public void keyReleased(KeyEvent e) {
                keyInputReleased(e);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        drawPacman(g);
        drawGhosts(g);
        drawHUD(g);
    }

    private void drawPacman(Graphics g) {
        g.drawImage(pacman.getCurrentImage(), pacman.getX(), pacman.getY(), this);
    }

    private void drawGhosts(Graphics g) {
        for (Ghost ghost : ghosts) {
            g.drawImage(ghost.getCurrentImage(), ghost.getX(), ghost.getY(), this);
        }
    }

    private void drawHUD(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 14));

        g.drawString("Score: " + score, 10, 20);
        g.drawString("Lives: " + lives, 10, 40);
        g.drawString("Time: " + gameTime / 1000 + "s", 10, 60);
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
        repaint();
    }

    private void keyInputPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_LEFT:
                this.pacman.setDirection(-5, 0);
                break;
            case KeyEvent.VK_RIGHT:
                this.pacman.setDirection(5, 0);
                break;
            case KeyEvent.VK_UP:
                this.pacman.setDirection(0, -5);
                break;
            case KeyEvent.VK_DOWN:
                this.pacman.setDirection(0, 5);
                break;
        }
        pacman.move();
        repaint();
    }

    private void keyInputReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_UP:
            case KeyEvent.VK_DOWN:
                this.pacman.setDirection(0, 0);
                break;
        }
        pacman.move();
        repaint();
    }
}