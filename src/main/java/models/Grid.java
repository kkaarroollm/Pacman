package models;


public class Grid {
    protected int x, y;
    protected int width, height;

    public Grid(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getCenterX() {
        return x + width / 2;
    }

    public int getCenterY() {
        return y + height / 2;
    }

    public boolean hit(Grid other) {
        return this.x < other.x + other.width &&
                this.x + this.width > other.x &&
                this.y < other.y + other.height &&
                this.y + this.height > other.y;
    }

    public boolean overlay(Grid other) {
        return other.x >= this.x &&
                other.y >= this.y &&
                other.x + other.width <= this.x + this.width &&
                other.y + other.height <= this.y + this.height;
    }

}
