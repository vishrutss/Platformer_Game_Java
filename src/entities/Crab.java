package entities;

import static utils.Constants.Enemy.*;
import static utils.Constants.Directions.*;
import java.awt.geom.Rectangle2D;
import java.awt.Color;
import java.awt.Graphics;

import main.Game;

public class Crab extends Enemy {

    // Attack hitbox
    private Rectangle2D.Float attackHitbox;
    private int attackHitboxOffsetX;

    public Crab(float x, float y) {
        super(x, y, CRAB_WIDTH, CRAB_HEIGHT, CRAB);
        initHitbox(x, y, (int) (22 * Game.SCALE), (int) (19 * Game.SCALE));
        initAttackHitbox();
    }

    private void initAttackHitbox() {
        attackHitbox = new Rectangle2D.Float(x, y, (int) (82 * Game.SCALE), (int) (19 * Game.SCALE));
        attackHitboxOffsetX = (int) (30 * Game.SCALE);
    }

    public void update(int[][] levelData, Player player) {
        updateMove(levelData, player);
        updateAnimationTick();
        updateAttackHitbox();
    }

    private void updateAttackHitbox() {
        attackHitbox.x = hitbox.x - attackHitboxOffsetX;
        attackHitbox.y = hitbox.y;
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

    public void drawAttackHitbox(Graphics g, int xLevelOffset) {
        g.setColor(Color.RED);
        g.drawRect((int) (attackHitbox.x - xLevelOffset), (int) attackHitbox.y, (int) attackHitbox.width,
                (int) attackHitbox.height);
    }

    public int flipx() {
        if (walkDirection == RIGHT) {
            return width;
        } else {
            return 0;
        }
    }

    public int flipw() {
        if (walkDirection == RIGHT) {
            return -1;
        } else {
            return 1;
        }
    }

}
