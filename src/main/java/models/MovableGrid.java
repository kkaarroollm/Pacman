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

    public int getSpeed() {
        return speed;
    }

    public void updateAnimationFrame() {
        currentFrame = (currentFrame + 1) % frames[lastDirection.ordinal()].length;
    }

    public Image getCurrentImage() {
        return frames[lastDirection.ordinal()][currentFrame];
    }

    // getters setters

    public int getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentDirection(Direction currentDirection) {
        this.currentDirection = currentDirection;
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public Direction getLastDirection() {
        return lastDirection;
    }

    public void setLastDirection(Direction lastDirection) {
        this.lastDirection = lastDirection;
    }

    public Image[][] getFrames() {
        return frames;
    }

    public void setFrames(Image[][] frames) {
        this.frames = frames;
    }

    @Override
    public double getX(){
        return bounds.x;
    }

    @Override
    public double getY(){
        return bounds.y;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(getCurrentImage(), x, y, width, height, null);
    }

}
