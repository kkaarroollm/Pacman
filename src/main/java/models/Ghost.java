package models;

import javax.swing.*;
import java.awt.*;

public class Ghost implements Movable{
    private int x, y;
    private int dx, dy;
    private int currentFrame;
    private Image[] frames;

    public Ghost() {
        frames = new Image[3];
        frames[0] = new ImageIcon("res/images/Ghost.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT);
        frames[1] = new ImageIcon("res/images/Ghost.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT);
        frames[2] = new ImageIcon("res/images/Ghost.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT);
        currentFrame = 0;
    }

    public void move() {
        this.x += dx;
        this.y += dy;
    }

    public void updateAnimationFrame() {
        currentFrame = (currentFrame + 1) % frames.length;
    }

    public Image getCurrentImage() {
        return frames[currentFrame];
    }

    public void setDirection(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}