package levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import utils.LoadSave;

public class LevelHandler {
    private Game game;
    private BufferedImage[] levelAsset;
    private Level levelOne;

    public LevelHandler(Game game) {
        this.game = game;
        importOutsideLevel();
        levelOne = new Level(LoadSave.GetLevelData());
    }

    private void importOutsideLevel() {
        BufferedImage image = LoadSave.GetAsset(LoadSave.LEVEL_ASSET);
        levelAsset = new BufferedImage[48];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 12; j++) {
                int index = i * 12 + j;
                levelAsset[index] = image.getSubimage(j * Game.TILES_SIZE, i * Game.TILES_SIZE, Game.TILES_SIZE,
                        Game.TILES_SIZE);
            }
        }
    }

    public void draw(Graphics g) {
        for (int i = 0; i < Game.TILES_IN_HEIGHT; i++) {
            for (int j = 0; j < Game.TILES_IN_WIDTH; j++) {
                int index = levelOne.getAssetIndex(j, i);
                g.drawImage(levelAsset[index], Game.TILES_SIZE * j, Game.TILES_SIZE * i, Game.TILES_SIZE,
                        Game.TILES_SIZE, null);
            }
        }
    }

    public void update() {

    }
}
