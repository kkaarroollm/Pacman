package models;

import constants.Direction;

import java.util.*;

public class CollisionDetector {
    private final int cellSize;
    Map<Unit, Wall> wallsMap;
    Map<Unit, EatableGrid> eatables;

    public CollisionDetector(Board board, int cellSize) {
        this.cellSize = cellSize;
        this.wallsMap = new HashMap<>();
        this.eatables = new HashMap<>();

        for (Wall wall : board.getWalls()) {
            Unit cell = getCell(wall);
            wallsMap.put(cell, wall);
        }

        for (Coin coin : board.getCoins()) {
            Unit cell = getCell(coin);
            eatables.put(cell, coin);
        }
    }

    private Unit getCell(Grid position) {
        int cellX = position.x / cellSize;
        int cellY = position.y / cellSize;
        return new Unit(cellX, cellY);
    }

    public boolean willCollideWithWall(MovableGrid movable) {
        int originalSpeed = movable.getSpeed();
        Direction direction = movable.getCurrentDirection();

        for (int currentSpeed = originalSpeed; currentSpeed >= 1; currentSpeed--) {
            Unit futurePosition = movable.calculateFuturePosition(currentSpeed, direction);
            Grid futureBounds = new Grid(futurePosition.getX(), futurePosition.getY(), movable.width, movable.height);

            boolean collisionDetected = false;

            for (Unit cell : getNearbyPoints(futureBounds)) {
                Wall cellWall = wallsMap.get(cell);
                System.out.println(cellWall);
                if (cellWall != null && futureBounds.hit(cellWall)) {
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
        Grid pacmanBounds = new Grid(pacman.x, pacman.y, pacman.width, pacman.height);

        for (Unit cell : getNearbyPoints(pacmanBounds)) {
            EatableGrid eatable = eatables.get(cell);
            if (eatable != null && pacmanBounds.overlay(eatable)) {
                eatables.remove(cell);
                return eatable;
            }
        }
        return null;
    }

    private Iterable<Unit> getNearbyPoints(Grid bounds) {
        return () -> new Iterator<>() {
            int currentX = bounds.x / cellSize; // Top left
            int currentY = bounds.y / cellSize; // Top left
            final int maxX = (bounds.x + bounds.width) / cellSize; // Bottom right
            final int maxY = (bounds.y + bounds.height) / cellSize; // Bottom right;

            @Override
            public boolean hasNext() {
                return currentX <= maxX && currentY <= maxY;
            }

            @Override
            public Unit next() {
                Unit nextUnit = new Unit(currentX, currentY);
                currentX++;
                if (currentX > maxX) {
                    currentX = bounds.x / cellSize;
                    currentY++;
                }
                return nextUnit;
            }
        };
    }

    // method which checks if the ghost is in the nearby 5 cells, if yes, then it returns the direction to the pacman,
    // if not, then it returns a random direction, it trying to catch pacman, but idk how now
}
