package models;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Board {
    private final List<Wall> walls;
    private final int BLOCK_SIZE;

    public Board(int blockSize) {
        this.BLOCK_SIZE = blockSize; // 24 // Size screen:
        walls = new ArrayList<>();
        initializeBoard();
    }

    private void initializeBoard() {
        int[][] wallLocations = {
                {0, 0}, {0, 1}, {0, 2}, {0, 3}, {0, 4}, {0, 5}, {0, 6}, {0, 7}, {0, 8}, {0, 9},
                {0, 10}, {0, 11}, {0, 12}, {0, 13}, {0, 14}, {0, 15}, {0, 16}, {0, 17}, {0, 18}, {0, 19},
                {1, 0}, {1, 19},
                {2, 0}, {2, 2}, {2, 3}, {2, 5}, {2, 6}, {2, 9}, {2, 10}, {2, 13}, {2, 14}, {2, 16}, {2, 17}, {2, 19},
                {3, 0}, {3, 19},
                {4, 0}, {4, 1}, {4, 2}, {4, 3}, {4, 4}, {4, 5}, {4, 6}, {4, 7}, {4, 8}, {4, 9}, {4, 10}, {4, 11}, {4, 12}, {4, 13}, {4, 14}, {4, 15}, {4, 16}, {4, 17}, {4, 18}, {4, 19}
        };

        for (int[] loc : wallLocations) {
            int x = loc[1] * BLOCK_SIZE;
            int y = loc[0] * BLOCK_SIZE;
            walls.add(new Wall(x, y, BLOCK_SIZE, BLOCK_SIZE));
        }
    }

    public void draw(Graphics g) {
        for (Wall wall : walls) {
            wall.draw(g);
        }
    }

    public List<Wall> getWalls() {
        return walls;
    }
}
