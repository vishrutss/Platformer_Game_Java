package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import gamestates.Playing;
import main.Game;
import utils.LoadSave;

import static utils.Constants.PlayerConstants.*;
import static utils.HelperMethods.*;

public class Player extends Entity {

    private BufferedImage[][] animations;
    private int animationTick, animationIndex, animationSpeed = 15;
    private int playerAction = IDLE;
    private boolean moving = false, attacking = false;
    private boolean left, right, up, down, jump;
    private float playerSpeed = 1.5f * Game.SCALE;
    private int[][] levelData;
    private float xDrawOffset = 21 * Game.SCALE, yDrawOffset = 4 * Game.SCALE;

    // Jumping and Gravity
    private float airSpeed = 0f, gravity = 0.1f * Game.SCALE, jumpSpeed = -3.7f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
    private boolean inAir = false;

    // Status bar UI
    private BufferedImage healthPowerBarImg;

    private int statusBarWidth = (int) (192 * Game.SCALE), statusBarHeight = (int) (58 * Game.SCALE);
    private int statusBarX = (int) (10 * Game.SCALE), statusBarY = (int) (10 * Game.SCALE);

    private int healthBarWidth = (int) (150 * Game.SCALE), healthBarHeight = (int) (4 * Game.SCALE);
    private int healthBarXStart = (int) (34 * Game.SCALE), healthBarYStart = (int) (14 * Game.SCALE);

    private int maxHealth = 100, currentHealth = maxHealth, healthWidth = healthBarWidth;

    // Attack hitbox
    private Rectangle2D.Float attackHitbox;
    private boolean attackChecked;

    private int flipX = 0, flipW = 1;
    private Playing playing;

    public Player(float x, float y, int width, int height, Playing playing) {
        super(x, y, width, height);
        this.playing = playing;
        loadAnimations();
        initHitbox(x, y, (int) (20 * Game.SCALE), (int) (28 * Game.SCALE));
        initAttackHitbox();
    }

    private void initAttackHitbox() {
        attackHitbox = new Rectangle2D.Float(x, y, (int) (20 * Game.SCALE), (int) (20 * Game.SCALE));
    }

    public void update() {
        updateHealthBar();
        updateAttackHitbox();
        updatePosition();
        if (attacking) {
            checkAttack();
        }
        updateAnimationTick();
        setAnimation();
    }

    private void checkAttack() {
        if (attackChecked || animationIndex != 1) {
            return;
        }
        attackChecked = true;
        playing.checkEnemyHit(attackHitbox);
    }

    private void updateAttackHitbox() {
        if (right) {
            attackHitbox.x = hitbox.x + hitbox.width + (int) (Game.SCALE * 10);
        } else if (left) {
            attackHitbox.x = hitbox.x - hitbox.width - (int) (Game.SCALE * 10);
        }
        attackHitbox.y = hitbox.y + (int) (Game.SCALE * 10);
    }

    private void updateHealthBar() {
        healthWidth = (int) ((currentHealth / (float) maxHealth) * healthBarWidth);
    }

    public void render(Graphics g, int levelOffset) {
        g.drawImage(animations[playerAction][animationIndex], (int) (hitbox.x - xDrawOffset) - levelOffset + flipX,
                (int) (hitbox.y - yDrawOffset), width * flipW, height, null);
        drawAttackHitbox(g, levelOffset);
        drawUI(g);
    }

    private void drawAttackHitbox(Graphics g, int levelOffset) {
        g.setColor(Color.RED);
        g.drawRect((int) (attackHitbox.x - levelOffset), (int) attackHitbox.y, (int) attackHitbox.width,
                (int) attackHitbox.height);
    }

    private void drawUI(Graphics g) {
        g.drawImage(healthPowerBarImg, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
        g.setColor(Color.RED);
        g.fillRect(healthBarXStart + statusBarX, healthBarYStart + statusBarY, healthWidth, healthBarHeight);
    }

    private void setAnimation() {
        int startAnimation = playerAction;

        if (moving) {
            playerAction = RUN;
        } else {
            playerAction = IDLE;
        }

        if (inAir) {
            if (airSpeed > 0) {
                playerAction = FALL;
            } else {
                playerAction = JUMP;
            }
        }

        if (attacking) {
            playerAction = ATTACK;
            if(startAnimation != ATTACK) {
                animationIndex = 1;
                animationTick = 0;
                return;
            }
        }
        if (startAnimation != playerAction) {
            resetAnimation();
        }
    }

    private void resetAnimation() {
        animationTick = 0;
        animationIndex = 0;
    }

    private void updateAnimationTick() {
        animationTick++;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= GetAssetAmount(playerAction)) {
                animationIndex = 0;
                attacking = false;
                attackChecked = false;
            }
        }
    }

    private void updatePosition() {

        moving = false;

        if (jump) {
            jump();
        }

        if (!inAir) {
            if ((!left && !right) || (left && right)) {
                return;
            }
        }

        float xSpeed = 0;

        if (left) {
            xSpeed -= playerSpeed;
            flipX = width;
            flipW = -1;
        }
        if (right) {
            xSpeed += playerSpeed;
            flipX = 0;
            flipW = 1;
        }

        if (!inAir) {
            if (!IsEntitiyOnGround(hitbox, levelData)) {
                inAir = true;
            }
        }

        if (inAir) {
            if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, levelData)) {
                hitbox.y += airSpeed;
                airSpeed += gravity;
                updateXPosition(xSpeed);
            } else {
                hitbox.y = GetEntityYPositionUnderRoofAboveFloor(hitbox, airSpeed);
                if (airSpeed > 0) {
                    resetInAir();
                } else {
                    airSpeed = fallSpeedAfterCollision;
                }
                updateXPosition(xSpeed);
            }
        } else {
            updateXPosition(xSpeed);
        }
        moving = true;
    }

    private void jump() {
        if (inAir) {
            return;
        }
        inAir = true;
        airSpeed = jumpSpeed;
    }

    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
    }

    public void changeHealth(int amount) {
        currentHealth += amount;
        if (currentHealth > maxHealth) {
            currentHealth = maxHealth;
        }
        if (currentHealth < 0) {
            currentHealth = 0;
        }
    }

    private void updateXPosition(float xSpeed) {
        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, levelData)) {
            hitbox.x += xSpeed;
        } else {
            hitbox.x = GetEntityXPositionNextToWall(hitbox, xSpeed);
        }
    }

    private void loadAnimations() {
        BufferedImage image = LoadSave.GetAsset(LoadSave.PLAYER_ASSET);
        animations = new BufferedImage[ANIMATIONS_DIMENSION_Y][ANIMATIONS_DIMENSION_X];
        for (int i = 0; i < animations.length; i++) {
            for (int j = 0; j < animations[i].length; j++) {
                animations[i][j] = image.getSubimage(j * 64, i * 40, 64, 40);
            }
        }
        healthPowerBarImg = LoadSave.GetAsset(LoadSave.HEALTH_POWER_BAR);
    }

    public void loadLevelData(int[][] levelData) {
        this.levelData = levelData;
        if (!IsEntitiyOnGround(hitbox, levelData)) {
            inAir = true;
        }
    }

    public void resetDirectionBooleans() {
        left = false;
        right = false;
        up = false;
        down = false;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

}
