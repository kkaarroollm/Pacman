package utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageUtils {

    public static BufferedImage loadImage(String imagePath) throws IOException {
        return ImageIO.read(new File(imagePath));
    }

    public static BufferedImage rotateImage(BufferedImage img, double angle, boolean mirror) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage rotatedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotatedImage.createGraphics();
        g2d.rotate(angle, w / 2.0, h / 2.0);
        if (mirror) {
            g2d.drawImage(img, 0, h, w, -h, null);
        } else {
            g2d.drawImage(img, 0, 0, null);
        }
        g2d.dispose();
        System.out.println("Image rotated");
        return rotatedImage;
        // check how it works
    }
}