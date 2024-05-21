package models;

import utils.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Pacman implements Movable {
    private int x = 50, y = 50;
    private int dx, dy;
    private int currentFrame;
    private final BufferedImage[][] frames = new BufferedImage[4][3];
    final int frameWidth = 40;
    final int frameHeight = 40;
    private int currentDirection;

    // pac orientation
    private static final int UP = 0;
    private static final int RIGHT = 1;
    private static final int DOWN = 2;
    private static final int LEFT = 3;

    public Pacman() {
        try {
            loadAndProcessImages();
            currentFrame = 0;
            currentDirection = RIGHT;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void move() {
        x += dx;
        y += dy;
    }

    public void setDirection(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;

        if (dx > 0) currentDirection = RIGHT;
        else if (dx < 0) currentDirection = LEFT;
        else if (dy > 0) currentDirection = DOWN;
        else if (dy < 0) currentDirection = UP;
    }

    private void loadAndProcessImages() throws IOException {
        BufferedImage pacImg1 = ImageUtils.loadImage("src/main/resources/images/pacman/pacman1.png");
        BufferedImage pacImg2 = ImageUtils.loadImage("src/main/resources/images/pacman/pacman2.png");
        BufferedImage pacImg3 = ImageUtils.loadImage("src/main/resources/images/pacman/pacman3.png");

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
        return ImageUtils.resizeImage(rotatedImage, frameWidth, frameHeight);
    }

    public void updateAnimationFrame() {
        currentFrame = (currentFrame + 1) % frames[currentDirection].length;
    }

    public Image getCurrentImage() {
        return frames[currentDirection][currentFrame];
    }

}