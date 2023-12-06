package entities;

import static utils.Constants.Enemy.*;
import static utils.HelperMethods.*;
import static utils.Constants.Directions.*;

import main.Game;

public abstract class Enemy extends Entity {
    private int animationIndex, enemyState, enemyType;
    private int animationTick, animationSpeed = 25;
    private boolean firstUpdate = true;
    private boolean inAir;
    private float fallSpeed;
    private float gravity = 0.04f * Game.SCALE;
    private float walkSpeed = 0.35f * Game.SCALE;
    private int walkDirection = LEFT;

    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType;
        initHitbox(x, y, width, height);
    }

    private void updateAnimationTick() {
        animationTick++;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= GetAssetAmount(enemyType, enemyState)) {
                animationIndex = 0;
            }
        }
    }

    private void updateMove(int[][] levelData) {
        if (firstUpdate) {
            if (!IsEntitiyOnGround(hitbox, levelData)) {
                inAir = true;
            }
            firstUpdate = false;
        }
        if (inAir) {
            if (CanMoveHere(hitbox.x, hitbox.y + fallSpeed, hitbox.width, hitbox.height, levelData)) {
                hitbox.y += fallSpeed;
                fallSpeed += gravity;
            } else {
                inAir = false;
                hitbox.y = GetEntityYPositionUnderRoofAboveFloor(hitbox, fallSpeed);
            }
        } else {
            switch (enemyState) {
                case IDLE:
                    enemyState = RUN;
                    break;
                case RUN:
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
                    break;
            }
        }
    }

    public void update(int[][] levelData) {
        updateMove(levelData);
        updateAnimationTick();
    }

    public int getAnimationIndex() {
        return animationIndex;
    }

    public int getEnemyState() {
        return enemyState;
    }
}
