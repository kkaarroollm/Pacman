package models;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class WallDetector {
    private final List<Wall> walls;

    public WallDetector(List<Wall> walls) {
        this.walls = walls;
    }

    public boolean willCollide(MovableGrid movable) {
        double futureX = movable.getX();
        double futureY = movable.getY();

        switch (movable.getCurrentDirection()) {
            case UP:
                futureY -= movable.getSpeed();
                break;
            case DOWN:
                futureY += movable.getSpeed();
                break;
            case LEFT:
                futureX -= movable.getSpeed();
                break;
            case RIGHT:
                futureX += movable.getSpeed();
                break;
        }

        Rectangle futureBounds = new Rectangle((int)futureX, (int)futureY, (int)movable.getWidth(), (int)movable.getHeight());

        for (Wall wall : walls) {
            if (futureBounds.intersects(wall.getBounds())) {
                return true;
            }
        }

        return false;
    }
}
