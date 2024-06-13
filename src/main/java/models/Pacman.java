package models;

import constants.Direction;
import utils.ImageUtils;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class Pacman extends MovableGrid implements Renderable {
    private final int UP = Direction.UP.ordinal();
    private final int RIGHT = Direction.RIGHT.ordinal();
    private final int DOWN = Direction.DOWN.ordinal();
    private final int LEFT = Direction.LEFT.ordinal();
    private static final int DEFAULT_SPEED = 8;

    private final Board board;

    public Pacman(Board board) {
        super(24, 672, 24, 24, DEFAULT_SPEED);
        this.board = board;
        this.board.collisionDetector.setPacman(this);
        try {
            loadAndProcessImages();
        } catch (IOException e) {
            System.err.println("Error loading images");
        }
    }


    @Override
    public void move() {
        Direction resolvedDirection = resolveRequestedDirection();

        if (resolvedDirection != null) {
            this.setCurrentDirection(resolvedDirection);
        }

        if (board.hasNoWallCollisions(this)) {
            board.eatEatables();

            Unit futurePosition = calculateFuturePosition(speed, currentDirection);
            x = futurePosition.getX();
            y = futurePosition.getY();

            if (currentDirection != Direction.NONE) {
                setLastDirection(currentDirection);
            }

            this.speed = DEFAULT_SPEED;
        }
    }


    public void setRequestedDirection(Direction requestedDirection) {
        this.requestedDirection = requestedDirection;
    }


    protected Direction resolveRequestedDirection() {
        if (requestedDirection == null) {
            return currentDirection;
        }

        boolean isAtCenter = (x % 24 == 0) && (y % 24 == 0);

        if (!isAtCenter) {
            System.out.println("Pacman is not at the center");
            return currentDirection;
        }

        Direction previousDirection = currentDirection;
        this.setCurrentDirection(requestedDirection);

        if (board.hasNoWallCollisions(this)) {
            return requestedDirection;
        } else {
            this.setCurrentDirection(previousDirection);
            return currentDirection;
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