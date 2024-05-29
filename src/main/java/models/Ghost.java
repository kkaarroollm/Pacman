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
        super(x, y, 20, 20, 5);
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
        int pacmanX = board.collisionDetector.getPacman().x;
        int pacmanY = board.collisionDetector.getPacman().y;

        Direction[] directions = getChaseDirections(pacmanX, pacmanY, x, y);

        for (Direction direction : directions) {
            if (tryMoveInDirection(direction)) {
                return;
            }
        }

    }

    private Direction[] getChaseDirections(int pacmanX, int pacmanY, int ghostX, int ghostY) {
        int deltaX = pacmanX - ghostX;
        int deltaY = pacmanY - ghostY;

        Direction primaryDirection;
        if (Math.abs(deltaX) > Math.abs(deltaY)) {
            if (deltaX > 0) {
                primaryDirection = Direction.RIGHT;
            } else {
                primaryDirection = Direction.LEFT;
            }
        } else {
            if (deltaY > 0) {
                primaryDirection = Direction.DOWN;
            } else {
                primaryDirection = Direction.UP;
            }
        }

        Direction secondaryDirection;
        if (primaryDirection == Direction.RIGHT || primaryDirection == Direction.LEFT) {
            if (deltaY > 0) {
                secondaryDirection = Direction.DOWN;
            } else {
                secondaryDirection = Direction.UP;
            }
        } else {
            if (deltaX > 0) {
                secondaryDirection = Direction.RIGHT;
            } else {
                secondaryDirection = Direction.LEFT;
            }
        }

        return new Direction[]{primaryDirection, secondaryDirection};
    }


    private boolean tryMoveInDirection(Direction direction) {
        setCurrentDirection(direction);

        if (board.hasNoWallCollisions(this)) {
            moveInCurrentDirection();
            return true;
        }
        return false;
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
