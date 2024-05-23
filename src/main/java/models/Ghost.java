package models;

import constants.Direction;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Ghost extends MovableGrid {
    private final WallDetector wallDetector;
    private final Random random;
    Image[] frames;

    public Ghost(WallDetector wallDetector) {
        super(24, 24, 18, 18, 7);
        this.wallDetector = wallDetector;
        this.random = new Random();
        this.frames = new Image[2];
        frames[0] = new ImageIcon("src/main/resources/images/ghost/ghost1.png").getImage().getScaledInstance(18, 18, Image.SCALE_DEFAULT);
        frames[1] = new ImageIcon("src/main/resources/images/ghost/ghost2.png").getImage().getScaledInstance(18, 18, Image.SCALE_DEFAULT);

    }

    @Override
    public void move() {
        if (!wallDetector.willCollide(this)) {
            switch (getRandomDirection()) {
                case UP:
                    y -= speed;
                    break;
                case DOWN:
                    y += speed;
                    break;
                case LEFT:
                    x -= speed;
                    break;
                case RIGHT:
                    x += speed;
                    break;
                case NONE:
                    break;
            }

            bounds.setLocation(x, y);
            System.out.println("Ghost x: " + x + " y: " + y);
        }
    }

    private Direction getRandomDirection() {
        Direction[] directions = Direction.values();
        return directions[random.nextInt(directions.length)];
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
    