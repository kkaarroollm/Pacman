package models;

import constants.Direction;
import controllers.Game;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Ghost extends MovableGrid implements Renderable {
    private final Board board;
    private final Random random;
    private int distanceMoved;
    int maxDistance = Game.BLOCK_SIZE * 5;
    final int DEFAULT_SPEED = 5;
    final Image[] frames;

    public Ghost(Board board, int x, int y) {
        super(x, y, 24, 24, 5);
        this.board = board;
        this.random = new Random();
        this.frames = new Image[2];
        this.currentDirection = Direction.RIGHT;
        this.distanceMoved = 0;
        frames[0] = new ImageIcon("src/main/resources/images/ghost/ghost1.png").getImage().getScaledInstance(18, 18, Image.SCALE_DEFAULT);
        frames[1] = new ImageIcon("src/main/resources/images/ghost/ghost2.png").getImage().getScaledInstance(18, 18, Image.SCALE_DEFAULT);
    }

    @Override
    public void move() {
        if (board.canChasePacman(this)) {
            System.out.println("Chasing pacman");
            chasePacman();
        }
        maxDistance = Game.BLOCK_SIZE * (2 * random.nextInt(2, 4));
        if (distanceMoved >= maxDistance || !board.hasNoWallCollisions(this)) {
            this.setCurrentDirection(getRandomDirection());
            distanceMoved = 0;
        }

        Unit nextPosition = calculateFuturePosition(speed, currentDirection);
        if (board.hasNoWallCollisions(this)) {
            x = nextPosition.getX();
            y = nextPosition.getY();
            distanceMoved += speed;
            this.setSpeed(DEFAULT_SPEED);
        }
    }

    private Direction getRandomDirection() {
        Direction[] directions = Direction.values();
        Direction newDirection;

        do {
            newDirection = directions[random.nextInt(directions.length)];
        } while (newDirection == Direction.NONE || newDirection == Direction.getOpposite(currentDirection));

        return newDirection;
    }


    public void chasePacman() {
        // implement chasing pacman
    }

    @Override
    public void updateAnimationFrame() {
        currentFrame = (currentFrame + 1) % frames.length;
    }

    @Override
    public Image getCurrentImage() {
        return frames[currentFrame];
    }
}
