package entities;

import static utils.Constants.Enemy.*;
import static utils.HelperMethods.*;
import static utils.Constants.Directions.*;

import main.Game;

public abstract class Enemy extends Entity {
    protected int animationIndex, enemyState, enemyType;
    protected int animationTick, animationSpeed = 15;
    protected boolean firstUpdate = true;
    protected boolean inAir;
    protected float fallSpeed;
    protected float gravity = 0.04f * Game.SCALE;
    protected float walkSpeed = 0.5f * Game.SCALE;
    protected int walkDirection = LEFT;
    protected int tileY;
    protected float attackRange = Game.TILES_SIZE;

    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType;
        initHitbox(x, y, width, height);
    }

    protected void newState(int enemyState) {
        this.enemyState = enemyState;
        animationTick = 0;
        animationIndex = 0;
    }

    protected boolean canSeePlayer(int[][] levelData, Player player) {
        int playerTileY = (int) (player.getHitbox().y / Game.TILES_SIZE);
        if (playerTileY == tileY && isPlayerInRange(player) && IsSightClear(levelData, hitbox, player.hitbox, tileY)) {
            return true;
        }
        return false;
    }

    protected void turnToPlayer(Player player) {
        if (player.hitbox.x < hitbox.x) {
            walkDirection = LEFT;
        } else {
            walkDirection = RIGHT;
        }
    }

    protected boolean isPlayerInRange(Player player) {
        int absRange = (int) Math.abs(player.hitbox.x - hitbox.x);
        return absRange <= attackRange * 5;
    }

    protected boolean isPlayerInAttackRange(Player player) {
        int absRange = (int) Math.abs(player.hitbox.x - hitbox.x);
        return absRange <= attackRange;
    }

    protected void updateAnimationTick() {
        animationTick++;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= GetAssetAmount(enemyType, enemyState)) {
                animationIndex = 0;
                if (enemyState == ATTACK) {
                    enemyState = IDLE;
                }
            }
        }
    }

    protected void firstUpdateCheck(int[][] levelData) {
        if (!IsEntitiyOnGround(hitbox, levelData)) {
            inAir = true;
        }
        firstUpdate = false;
    }

    protected void updateInAir(int[][] levelData) {
        if (CanMoveHere(hitbox.x, hitbox.y + fallSpeed, hitbox.width, hitbox.height, levelData)) {
            hitbox.y += fallSpeed;
            fallSpeed += gravity;
        } else {
            inAir = false;
            hitbox.y = GetEntityYPositionUnderRoofAboveFloor(hitbox, fallSpeed);
            tileY = (int) (hitbox.y / Game.TILES_SIZE);
        }
    }

    protected void movePlayer(int[][] levelData) {
        float xSpeed = 0;
        if (walkDirection == LEFT) {
            xSpeed = -walkSpeed;
        } else {
            xSpeed = walkSpeed;
        }
        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, levelData)) {
            if (IsFloor(hitbox, xSpeed, levelData)) {
                hitbox.x += xSpeed;
                return;
            }
        }
        walkDirection = walkDirection == LEFT ? RIGHT : LEFT;
    }

    public int getAnimationIndex() {
        return animationIndex;
    }

    public int getEnemyState() {
        return enemyState;
    }
}
