package constants;

import models.Coin;
import models.Ghost;
import models.Wall;

import java.util.List;

public record BoardElements(List<Wall> walls, List<Coin> coins) { }

