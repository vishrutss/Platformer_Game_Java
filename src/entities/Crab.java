package entities;

import static utils.Constants.Enemy.*;

import main.Game;

public class Crab extends Enemy {

    public Crab(float x, float y) {
        super(x, y, CRAB_WIDTH, CRAB_HEIGHT, CRAB);
        initHitbox(x, y, (int) (22 * Game.SCALE), (int) (19 * Game.SCALE));
    }

}
