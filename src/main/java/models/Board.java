package models;

import controllers.Game;
import utils.BoardReaderUtils;

import java.awt.*;
import java.util.List;

public class Board {
    private final BoardElements boardObjects;
    private final CollisionDetector collisionDetector;
    String filename;

    public Board() {
        filename = "src/main/resources/boards/board.txt";
        this.boardObjects = BoardReaderUtils.readMap(filename);
        this.collisionDetector = new CollisionDetector(this, Game.BLOCK_SIZE);
    }

    public void draw(Graphics g) {
        for (Wall wall : boardObjects.walls()) {
            wall.draw(g);
        }
        for (Coin coin : boardObjects.coins()) {
            coin.draw(g);
        }
    }

    public void update(Graphics g) {
        for (Coin coin : boardObjects.coins()) {
            coin.draw(g);
        }
    }

    public List<Wall> getWalls() {
        return boardObjects.walls();
    }

    public List<Coin> getCoins() {
        return boardObjects.coins();
    }

    public boolean checkCollisionWithWalls(MovableGrid movable) {
        return collisionDetector.willCollideWithWall(movable);
    }

    public void eatEatables(Pacman pacman) {
        EatableGrid eatable = collisionDetector.checkAndEatEatables(pacman);
        if (eatable instanceof Coin) {
            boardObjects.coins().remove(eatable);
        }
        System.out.println("Coins left: " + getCoins().size());
    }

}
