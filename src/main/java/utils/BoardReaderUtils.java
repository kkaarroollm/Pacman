package utils;

import constants.BoardElements;
import models.Coin;
import models.Wall;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static controllers.Game.BLOCK_SIZE;

public class BoardReaderUtils {
    public static BoardElements readMap(String filename){
        List<Wall> walls = new ArrayList<>();
        List<Coin> coins = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int row = 0;
            while ((line = reader.readLine()) != null) {
                for (int col = 0; col < line.length(); col++) {
                    char cell = line.charAt(col);
                    int x = col * BLOCK_SIZE;
                    int y = row * BLOCK_SIZE;
                    if (cell == '1') {
                        walls.add(new Wall(x, y, BLOCK_SIZE, BLOCK_SIZE));
                    } else if (cell == '0') {
                        coins.add(new Coin(x, y));
                    }
                }
                row++;
            }
        } catch (IOException e) {
            System.err.println("Could not read map from file: " + filename);
        }
        return new BoardElements(walls, coins);
    }
}
