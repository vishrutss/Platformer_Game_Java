package entities;

import static utils.Constants.Enemy.*;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gamestates.Playing;
import utils.LoadSave;

public class EnemyHandler {

    private Playing playing;
    private BufferedImage[][] crabImages;
    private ArrayList<Crab> crabs = new ArrayList<>();

    public EnemyHandler(Playing playing) {
        this.playing = playing;
        loadEnemyImages();
        addEnemies();
    }

    private void addEnemies() {
        crabs = LoadSave.GetCrabs();
    }

    public void update(int[][] levelData, Player player) {
        for (Crab crab : crabs) {
            if (crab.isActive())
                crab.update(levelData, player);
        }
    }

    public void draw(Graphics g, int xLevelOffset) {
        drawCrabs(g, xLevelOffset);
    }

    private void drawCrabs(Graphics g, int xLevelOffset) {
        for (Crab crab : crabs) {
            if (crab.isActive()) {
                g.drawImage(crabImages[crab.getEnemyState()][crab.getAnimationIndex()],
                        (int) crab.getHitbox().x - xLevelOffset - CRAB_OFFSET_X + crab.flipx(),
                        (int) crab.getHitbox().y - CRAB_OFFSET_Y, CRAB_WIDTH * crab.flipw(), CRAB_HEIGHT, null);
                crab.drawAttackHitbox(g, xLevelOffset);
            }
        }
    }

    public void checkEnemyHit(Rectangle2D attackHitbox) {
        for (Crab crab : crabs) {
            if (crab.isActive()) {
                if (crab.getHitbox().intersects(attackHitbox)) {
                    crab.takeDamage(10);
                    return;
                }
            }
        }
    }

    private void loadEnemyImages() {
        crabImages = new BufferedImage[5][9];
        BufferedImage temp = LoadSave.GetAsset(LoadSave.ENEMY_CRAB);
        for (int i = 0; i < crabImages.length; i++) {
            for (int j = 0; j < crabImages[i].length; j++) {
                crabImages[i][j] = temp.getSubimage(j * CRAB_DEFAULT_WIDTH, i * CRAB_DEFAULT_HEIGHT, CRAB_DEFAULT_WIDTH,
                        CRAB_DEFAULT_HEIGHT);
            }
        }
    }
}
