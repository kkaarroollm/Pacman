package models;

import constants.Direction;
import utils.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Pacman extends MovableGrid {
    private final int UP = Direction.UP.ordinal();
    private final int RIGHT = Direction.RIGHT.ordinal();
    private final int DOWN = Direction.DOWN.ordinal();
    private final int LEFT = Direction.LEFT.ordinal();
    private static final int DEFAULT_SPEED = 8;

    private final WallDetector wallDetector;

    public Pacman(WallDetector wallDetector) {
        super(24, 24, 22, 22, DEFAULT_SPEED);
        this.wallDetector = wallDetector;
        try {
            loadAndProcessImages();
        } catch (IOException e) {
            System.err.println("Error loading images");
        }
    }


    @Override
    public void move() {
        if (!wallDetector.willCollide(this)) {
            Point futurePosition = calculateFuturePosition(speed, currentDirection);
            x = futurePosition.x;
            y = futurePosition.y;

            if (currentDirection != Direction.NONE) {
                setLastDirection(currentDirection);
            }

            bounds.setLocation(x, y);
            this.speed = DEFAULT_SPEED;
            System.out.println("Pacman x: " + x + " y: " + y);
        }
    }

    private void loadAndProcessImages() throws IOException {
        BufferedImage pacImg1 = ImageUtils.loadImage("src/main/resources/images/pacman/pacman1R.png");
        BufferedImage pacImg2 = ImageUtils.loadImage("src/main/resources/images/pacman/pacman2R.png");
        BufferedImage pacImg3 = ImageUtils.loadImage("src/main/resources/images/pacman/pacman3R.png");

        frames = new BufferedImage[4][3];
        frames[UP][0] = ImageUtils.rotateImage(pacImg1, -Math.PI / 2, false);
        frames[UP][1] = ImageUtils.rotateImage(pacImg2, -Math.PI / 2, false);
        frames[UP][2] = ImageUtils.rotateImage(pacImg3, -Math.PI / 2, false);

        frames[RIGHT][0] = ImageUtils.rotateImage(pacImg1, 0, false);
        frames[RIGHT][1] = ImageUtils.rotateImage(pacImg2, 0, false);
        frames[RIGHT][2] = ImageUtils.rotateImage(pacImg3, 0, false);

        frames[DOWN][0] = ImageUtils.rotateImage(pacImg1, Math.PI / 2, false);
        frames[DOWN][1] = ImageUtils.rotateImage(pacImg2, Math.PI / 2, false);
        frames[DOWN][2] = ImageUtils.rotateImage(pacImg3, Math.PI / 2, false);

        frames[LEFT][0] = ImageUtils.rotateImage(pacImg1, Math.PI, true);
        frames[LEFT][1] = ImageUtils.rotateImage(pacImg2, Math.PI, true);
        frames[LEFT][2] = ImageUtils.rotateImage(pacImg3, Math.PI, true);
    }
}