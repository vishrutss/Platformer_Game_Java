package entities;

import static utils.Constants.Enemy.*;
import static utils.HelperMethods.*;
import static utils.Constants.Directions.*;

import main.Game;

public abstract class Enemy extends Entity {
    protected int animationIndex, enemyState, enemyType;
    protected int animationTick, animationSpeed = 25;
    protected boolean firstUpdate = true;
    protected boolean inAir;
    protected float fallSpeed;
    protected float gravity = 0.04f * Game.SCALE;
    protected float walkSpeed = 0.45f * Game.SCALE;
    protected int walkDirection = LEFT;

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

    protected void updateAnimationTick() {
        animationTick++;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= GetAssetAmount(enemyType, enemyState)) {
                animationIndex = 0;
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
            if (isFloor(hitbox, xSpeed, levelData)) {
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
