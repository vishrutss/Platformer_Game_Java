package entities;

import static utils.Constants.Enemy.*;

import main.Game;

public class Crab extends Enemy {

    public Crab(float x, float y) {
        super(x, y, CRAB_WIDTH, CRAB_HEIGHT, CRAB);
        initHitbox(x, y, (int) (22 * Game.SCALE), (int) (19 * Game.SCALE));
    }

    public void update(int[][] levelData, Player player) {
        updateMove(levelData, player);
        updateAnimationTick();
    }

    private void updateMove(int[][] levelData, Player player) {
        if (firstUpdate) {
            firstUpdateCheck(levelData);
        }
        if (inAir) {
            updateInAir(levelData);
        } else {
            switch (enemyState) {
                case IDLE:
                    newState(RUN);
                    break;
                case RUN:
                    if (canSeePlayer(levelData, player))
                        turnToPlayer(player);
                    if (isPlayerInAttackRange(player))
                        newState(ATTACK);
                    movePlayer(levelData);
                    break;
            }
        }
    }

}
