package models;

import javax.swing.*;
import java.awt.*;

public class Coin extends EatableGrid {
    private int currentFrame;
    private final Image[] frames = new Image[2];

    public Coin(int x, int y) {
        super(x, y, 10, 10);
        frames[0] = new ImageIcon("src/main/resources/images/coin/coin1.png").getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
        frames[1] = new ImageIcon("src/main/resources/images/coin/coin2.png").getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(getCurrentImage(), (int)getCenterX(), (int)getCenterY(), width, height, null);
    }

    public void updateAnimationFrame() {
        currentFrame = (currentFrame + 1) % frames.length;
    }

    public Image getCurrentImage() {
        return frames[currentFrame];
    }

}
