package models;

import constants.Direction;

import java.awt.*;

public abstract class MovableGrid extends Grid implements Movable {
    protected int speed;
    protected int currentFrame;
    protected Direction currentDirection;
    protected Direction lastDirection;
    protected Image[][] frames;

    public MovableGrid(int x, int y, int width, int height, int speed) {
        super(x, y, width, height);
        this.speed = speed;
        this.currentFrame = 0;
        this.currentDirection = Direction.NONE;
        this.lastDirection = Direction.RIGHT;
    }

    public abstract void move();

    protected Unit calculateFuturePosition(double speed, Direction currentDirection) {
        double futureX = this.x;
        double futureY = this.y;

        switch (currentDirection) {
            case UP:
                futureY -= speed;
                break;
            case DOWN:
                futureY += speed;
                break;
            case LEFT:
                futureX -= speed;
                break;
            case RIGHT:
                futureX += speed;
                break;
        }

        return new Unit((int) futureX, (int) futureY);
    }

    public void updateAnimationFrame() {
        currentFrame = (currentFrame + 1) % frames[lastDirection.ordinal()].length;
    }

    public Image getCurrentImage() {
        return frames[lastDirection.ordinal()][currentFrame];
    }

    // getters setters
    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setCurrentDirection(Direction currentDirection) {
        this.currentDirection = currentDirection;
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public void setLastDirection(Direction lastDirection) {
        this.lastDirection = lastDirection;
    }

    public void draw(Graphics g) {
        g.drawImage(getCurrentImage(), x, y, width, height, null);
    }

}
