package utils;

import models.Wall;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static controllers.Game.BLOCK_SIZE;

public class BoardReaderUtils {
    private static final List<Wall> walls = new ArrayList<>();
    public static List<Wall> readWalls(String filename){
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int row = 0;
            while ((line = reader.readLine()) != null) {
                for (int col = 0; col < line.length(); col++) {
                    char cell = line.charAt(col);
                    if (cell == '1') {
                        int x = col * BLOCK_SIZE;
                        int y = row * BLOCK_SIZE;
                        walls.add(new Wall(x, y, BLOCK_SIZE, BLOCK_SIZE, getWallImg()));
                    }
                }
                row++;
            }
        } catch (IOException e) {
            System.err.println("Could not read map from file: " + filename);
        }
        return walls;
    }

    private static BufferedImage getWallImg() throws IOException{
        try{
            return ImageUtils.loadImage("src/main/resources/images/map/wallSquare.png");
        } catch (IOException e) {
            System.err.println("Error loading wall image");
            throw e;
        }
    }
}
