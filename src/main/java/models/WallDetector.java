package models;

import constants.Direction;

import java.awt.*;
import java.util.*;
import java.util.List;

public class WallDetector {
    private final int cellSize;
    private final Map<Point, List<Wall>> grid;

    public WallDetector(List<Wall> walls, int cellSize) {
        this.cellSize = cellSize;
        this.grid = new HashMap<>();

        for (Wall wall : walls) {
            Point cell = getCell(wall.getBounds().getLocation());
            grid.computeIfAbsent(cell, k -> new ArrayList<>()).add(wall);
        }
    }

    private Point getCell(Point position) {
        int cellX = position.x / cellSize;
        int cellY = position.y / cellSize;
        return new Point(cellX, cellY);
    }

    public boolean willCollide(MovableGrid movable) {
        int originalSpeed = movable.getSpeed();
        Direction direction = movable.getCurrentDirection();

        for (int currentSpeed = originalSpeed; currentSpeed >= 1; currentSpeed--) {
            Point futurePosition = movable.calculateFuturePosition(currentSpeed, direction);
            Rectangle futureBounds = new Rectangle(futurePosition.x, futurePosition.y, (int) movable.getWidth(), (int) movable.getHeight());

            boolean collisionDetected = false;

            for (Point cell : nearbyCells(futureBounds)) {
                List<Wall> cellWalls = grid.get(cell);
                if (cellWalls != null) {
                    for (Wall wall : cellWalls) {
                        if (futureBounds.intersects(wall.getBounds())) {
                            collisionDetected = true;
                            break;
                        }
                    }
                }
                if (collisionDetected) {
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


    private Iterable<Point> nearbyCells(Rectangle bounds) {
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
                Point nextCell = new Point(currentX, currentY);
                currentX++;
                if (currentX > maxX) {
                    currentX = bounds.x / cellSize;
                    currentY++;
                }
                return nextCell;
            }
        };
    }
}