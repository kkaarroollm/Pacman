package models;

import java.awt.*;

public abstract class Grid extends Rectangle {
    protected int x, y;
    protected int width, height;

    public Grid(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public abstract void draw(Graphics g);
}
