package models;

import constants.Direction;

import java.awt.*;
import java.util.*;

public class CollisionDetector {
    private final int cellSize;
    Map<Point, Wall> wallsMap;
    Map<Point, EatableGrid> eatables;

    public CollisionDetector(Board board, int cellSize) {
        this.cellSize = cellSize;
        this.wallsMap = new HashMap<>();
        this.eatables = new HashMap<>();

        for (Wall wall : board.getWalls()) {
            Point cell = getCell(wall.getLocation());
            wallsMap.put(cell, wall);
        }

        for (Coin coin : board.getCoins()) {
            Point cell = getCell(coin.getLocation());
            eatables.put(cell, coin);
        }
    }

    private Point getCell(Point position) {
        int cellX = position.x / cellSize;
        int cellY = position.y / cellSize;
        return new Point(cellX, cellY);
    }

    public boolean willCollideWithWall(MovableGrid movable) {
        System.out.println("Checking collision with walls");
        int originalSpeed = movable.getSpeed();
        Direction direction = movable.getCurrentDirection();

        for (int currentSpeed = originalSpeed; currentSpeed >= 1; currentSpeed--) {
            Point futurePosition = movable.calculateFuturePosition(currentSpeed, direction);
            Rectangle futureBounds = new Rectangle(futurePosition.x, futurePosition.y, (int) movable.getWidth(), (int) movable.getHeight());

            boolean collisionDetected = false;

            for (Point cell : getNearbyPoints(futureBounds)) {
                Wall cellWall = wallsMap.get(cell);
                if (cellWall != null && futureBounds.intersects(cellWall.getBounds())) {
                    collisionDetected = true;
                    break;
                }
            }

            if (!collisionDetected) {
                movable.setSpeed(currentSpeed);
                return false;
            }
        }

        return true;
    }

    public EatableGrid checkAndEatEatables(Pacman pacman) {
        Point currentPosition = pacman.getLocation();
        Rectangle pacmanBounds = new Rectangle(currentPosition.x, currentPosition.y, (int) pacman.getWidth(), (int) pacman.getHeight());

        for (Point cell : getNearbyPoints(pacmanBounds)) {
            EatableGrid eatable = eatables.get(cell);
            if (eatable != null && pacmanBounds.contains(eatable.getBounds())) {
                eatables.remove(cell);
                return eatable;
            }
        }
        return null;
    }

    private Iterable<Point> getNearbyPoints(Rectangle bounds) {
        return () -> new Iterator<>() {
            int currentX = bounds.x / cellSize;
            int currentY = bounds.y / cellSize;
            final int maxX = (bounds.x + bounds.width) / cellSize;
            final int maxY = (bounds.y + bounds.height) / cellSize;

            @Override
            public boolean hasNext() {
                return currentX <= maxX && currentY <= maxY;
            }

            @Override
            public Point next() {
                Point nextPoint = new Point(currentX, currentY);
                currentX++;
                if (currentX > maxX) {
                    currentX = bounds.x / cellSize;
                    currentY++;
                }
                return nextPoint;
            }
        };
    }
}
