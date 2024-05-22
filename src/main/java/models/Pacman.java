package models;

import constants.Direction;
import utils.ImageUtils;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class Pacman extends MovableGrid {
    private final int UP = Direction.UP.ordinal();
    private final int RIGHT = Direction.RIGHT.ordinal();
    private final int DOWN = Direction.DOWN.ordinal();
    private final int LEFT = Direction.LEFT.ordinal();

    private final WallDetector wallDetector;

    public Pacman(WallDetector wallDetector) {
        super(24, 24, 20, 20, 5);
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
            switch (currentDirection) {
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
            if (currentDirection != Direction.NONE) {
                setLastDirection(currentDirection);
            }

            bounds.setLocation(x, y);
            System.out.println("Pacman x: " + x + " y: " + y);
        }
    }

    private void loadAndProcessImages() throws IOException {
        BufferedImage pacImg1 = ImageUtils.loadImage("src/main/resources/images/pacman/pacman1R.png");
        BufferedImage pacImg2 = ImageUtils.loadImage("src/main/resources/images/pacman/pacman2R.png");
        BufferedImage pacImg3 = ImageUtils.loadImage("src/main/resources/images/pacman/pacman3R.png");

        frames = new BufferedImage[4][3];
        frames[UP][0] = resizeAndRotateImage(pacImg1, -Math.PI / 2, false);
        frames[UP][1] = resizeAndRotateImage(pacImg2, -Math.PI / 2, false);
        frames[UP][2] = resizeAndRotateImage(pacImg3, -Math.PI / 2, false);

        frames[RIGHT][0] = resizeAndRotateImage(pacImg1, 0, false);
        frames[RIGHT][1] = resizeAndRotateImage(pacImg2, 0, false);
        frames[RIGHT][2] = resizeAndRotateImage(pacImg3, 0, false);

        frames[DOWN][0] = resizeAndRotateImage(pacImg1, Math.PI / 2, false);
        frames[DOWN][1] = resizeAndRotateImage(pacImg2, Math.PI / 2, false);
        frames[DOWN][2] = resizeAndRotateImage(pacImg3, Math.PI / 2, false);

        frames[LEFT][0] = resizeAndRotateImage(pacImg1, Math.PI, true);
        frames[LEFT][1] = resizeAndRotateImage(pacImg2, Math.PI, true);
        frames[LEFT][2] = resizeAndRotateImage(pacImg3, Math.PI, true);
    }

    private BufferedImage resizeAndRotateImage(BufferedImage baseImage, double angle, boolean mirror) {
        BufferedImage rotatedImage = ImageUtils.rotateImage(baseImage, angle, mirror);
        return ImageUtils.resizeImage(rotatedImage, width, height);
    }
}