package models;

public interface Movable {
    void updateAnimationFrame(); // Method to update the animation frame
    void move(); // Method to handle movement
    int getX(); // Method to get the current x-coordinate
    int getY(); // Method to get the current y-coordinate
    void setDirection(int dx, int dy); // Method to set the direction of movement
}
