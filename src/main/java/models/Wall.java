package models;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Wall extends Grid {
    public Wall(int x, int y, int width, int height, BufferedImage image) {
        super(x, y, width, height, image);
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image, x, y, width, height, null);
    }
}
