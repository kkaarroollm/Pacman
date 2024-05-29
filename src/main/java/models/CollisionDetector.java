package models;

import constants.Direction;
import exceptions.MissingPacmanException;

import java.util.*;

public class CollisionDetector {
    private final int cellSize;
    Map<Unit, Wall> wallsMap;
    Map<Unit, EatableGrid> eatables;
    private Pacman pacman;

    public CollisionDetector(Board board, int cellSize) {
        this.pacman = null;
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

    public void setPacman(Pacman pacman) {
        System.out.println("Setting pacman");
        this.pacman = pacman;
    }

    public Pacman getPacman() {
        if (this.pacman == null) {
            throw new MissingPacmanException("Pacman has not been set in CollisionDetector");
        }
        return this.pacman;
    }

    public boolean willCollideWithWall(MovableGrid movable) {
        int originalSpeed = movable.getSpeed();
        Direction direction = movable.getCurrentDirection();

        for (int currentSpeed = originalSpeed; currentSpeed >= 1; currentSpeed--) {
            Unit futurePosition = movable.calculateFuturePosition(currentSpeed, direction);
            Grid futureBounds = new Grid(futurePosition.getX(), futurePosition.getY(), movable.width, movable.height);

            boolean collisionDetected = false;

            for (Unit cell : getNearbyCells(futureBounds)) {
                Wall cellWall = wallsMap.get(cell);
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


    public EatableGrid checkAndEatEatables() {
        Pacman pacman = getPacman();
        Grid pacmanBounds = new Grid(pacman.x, pacman.y, pacman.width, pacman.height);

        for (Unit cell :  getNearbyCells(pacmanBounds)) {
            EatableGrid eatable = eatables.get(cell);
            if (eatable != null && pacmanBounds.overlay(eatable)) {
                eatables.remove(cell);
                return eatable;
            }
        }
        return null;
    }

    private List<Unit> getNearbyCells(Grid gridObject) {
        List<Unit> cells = new ArrayList<>();
        int minCellX = gridObject.x / cellSize;
        int minCellY = gridObject.y / cellSize;
        int maxCellX = (gridObject.x + gridObject.width) / cellSize;
        int maxCellY = (gridObject.y + gridObject.height) / cellSize;

        for (int x = minCellX; x <= maxCellX; x++) {
            for (int y = minCellY; y <= maxCellY; y++) {
                cells.add(new Unit(x, y));
            }
        }
        return cells;
    }

    public boolean isPacmanInGhostZone(Ghost ghost) {
        // TODO Implement here that the ghost should see the pacman in range but also in the direction of the pacman so pacman will be able to eat the ghost
        int chaseRangeCell = 5;
        Unit ghostCell = getCell(ghost);
        Unit pacmanCell = getCell(this.pacman);

        int distanceX = Math.abs(ghostCell.getX() - pacmanCell.getX());
        int distanceY = Math.abs(ghostCell.getY() - pacmanCell.getY());
        double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);

        return distance <= chaseRangeCell;
    }

}
