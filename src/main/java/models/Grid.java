package models;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Grid extends Rectangle {
    protected int x, y;
    protected int width, height;
    protected Rectangle bounds;
    protected BufferedImage image;

    public Grid(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        bounds = new Rectangle(x, y, width, height);
    }

    public Grid(int x, int y, int width, int height, BufferedImage image) {
        this(x, y, width, height);
        this.image = image;
    }

    public boolean isCollidingWith(Grid other) {
        return bounds.intersects(other);
    }

    public abstract void draw(Graphics g);


}
