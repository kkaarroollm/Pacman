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
        if (board.isPacmanInGhostZone(this)) {
            System.out.println("Chasing Pacman");
            chasePacman();
        } else {
            moveRandomly();
        }
    }

    private void moveRandomly() {
        maxDistance = Game.BLOCK_SIZE * (2 * random.nextInt(2, 4));
        if (distanceMoved >= maxDistance || !board.hasNoWallCollisions(this)) {
            this.setCurrentDirection(getRandomDirection());
            distanceMoved = 0;
        }
        moveInCurrentDirection();
    }

    private void moveInCurrentDirection() {
        Unit nextPosition = calculateFuturePosition(speed, currentDirection);
        if (board.hasNoWallCollisions(this)) {
            updatePosition(nextPosition);
        }
    }

    private void updatePosition(Unit futurePosition) {
        x = futurePosition.getX();
        y = futurePosition.getY();
        distanceMoved += speed;
        this.setSpeed(DEFAULT_SPEED);
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
        Direction[] directions = getChaseDirections(
                board.collisionDetector.getPacman().x,
                board.collisionDetector.getPacman().y,
                x, y
        );

        for (Direction direction : directions) {
            if (tryMoveInDirection(direction)) {
                return;
            }
        }

        moveInAnyValidDirection();
    }

    private Direction[] getChaseDirections(int pacmanX, int pacmanY, int ghostX, int ghostY) {
        int deltaX = pacmanX - ghostX;
        int deltaY = pacmanY - ghostY;

        Direction primaryDirection = (Math.abs(deltaX) > Math.abs(deltaY)) ?
                (deltaX > 0 ? Direction.RIGHT : Direction.LEFT) :
                (deltaY > 0 ? Direction.DOWN : Direction.UP);

        Direction secondaryDirection = (primaryDirection == Direction.RIGHT || primaryDirection == Direction.LEFT) ?
                (deltaY > 0 ? Direction.DOWN : Direction.UP) :
                (deltaX > 0 ? Direction.RIGHT : Direction.LEFT);

        return new Direction[]{primaryDirection, secondaryDirection};
    }

    private void moveInAnyValidDirection() {
        for (Direction direction : Direction.values()) {
            if (direction != Direction.NONE && direction != Direction.getOpposite(currentDirection)) {
                if (tryMoveInDirection(direction)) {
                    break;
                }
            }
        }
    }

    private boolean tryMoveInDirection(Direction direction) {
        Unit futurePosition = calculateFuturePosition(speed, direction);
        if (board.hasNoWallCollisionsAtPos(this, direction, futurePosition.getX(), futurePosition.getY())) {
            moveInDirection(direction, futurePosition);
            return true;
        }
        return false;
    }

    private void moveInDirection(Direction direction, Unit futurePosition) {
        updatePosition(futurePosition);
        this.setCurrentDirection(direction);
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
