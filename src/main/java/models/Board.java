package models;

import utils.BoardReaderUtils;

import java.awt.*;
import java.util.List;

public class Board {
    private List<Wall> walls;
    private String filename;

    public Board() {
        filename = "src/main/resources/boards/board.txt";
        this.walls = BoardReaderUtils.readWalls(filename);
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
