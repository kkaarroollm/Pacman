package models;

import javax.swing.*;
import java.awt.*;

public class Wall extends Grid implements Renderable {
    private final Image wallImg;

    public Wall(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.wallImg = new ImageIcon("src/main/resources/images/map/wallSquare.png").getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
    }

    public void draw(Graphics g) {
        g.drawImage(wallImg, x, y, width, height, null);
    }
}
