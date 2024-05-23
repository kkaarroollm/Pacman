package models;

import java.awt.*;
import java.util.*;
import java.util.List;

public class WallDetector {
    private final int cellSize;
    private final Map<Point, List<Wall>> grid;

    public WallDetector(List<Wall> walls, int cellSize) {
        this.cellSize = cellSize;
        // Setting locations of the walls
        this.grid = new HashMap<>();

        for (Wall wall : walls) {
            Point cell = getCell(wall.getBounds().getLocation());
            grid.computeIfAbsent(cell, k -> new ArrayList<>()).add(wall);
        }

        System.out.println("WallDetector created");
        System.out.println(grid);
    }

    private Point getCell(Point position) {
        int cellX = position.x / cellSize;
        int cellY = position.y / cellSize;
        return new Point(cellX, cellY);
    }

    public boolean willCollide(MovableGrid movable) {
        Point futurePosition = movable.calculateFuturePosition(movable.getSpeed(), movable.getCurrentDirection());
        Rectangle futureBounds = new Rectangle(futurePosition.x, futurePosition.y, (int) movable.getWidth(), (int) movable.getHeight());

        Set<Point> nearbyCells = getNearbyCells(futureBounds);

        for (Point cell : nearbyCells) {
            List<Wall> cellWalls = grid.get(cell);
            if (cellWalls != null) {
                for (Wall wall : cellWalls) {
                    if (futureBounds.intersects(wall.getBounds())) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private Set<Point> getNearbyCells(Rectangle bounds) {
        Set<Point> cells = new HashSet<>();
        int minCellX = bounds.x / cellSize;
        int maxCellX = (bounds.x + bounds.width) / cellSize;
        int minCellY = bounds.y / cellSize;
        int maxCellY = (bounds.y + bounds.height) / cellSize;

        for (int x = minCellX; x <= maxCellX; x++) {
            for (int y = minCellY; y <= maxCellY; y++) {
                cells.add(new Point(x, y));
            }
        }
        return cells;
    }
}
