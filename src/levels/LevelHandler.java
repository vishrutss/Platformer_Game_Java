package levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import utils.LoadSave;

public class LevelHandler {
    private Game game;
    private BufferedImage[] levelAsset;

    public LevelHandler(Game game) {
        this.game = game;
        importOutsideLevel();
    }

    private void importOutsideLevel() {
        BufferedImage image = LoadSave.getAsset(LoadSave.LEVEL_ASSET);
        levelAsset = new BufferedImage[48];
        for(int i=0;i<4;i++){
            for(int j=0;j<12;j++){
                int index = i*12+j;
                levelAsset[index] = image.getSubimage(j*Game.TILES_SIZE, i*Game.TILES_SIZE, Game.TILES_SIZE, Game.TILES_SIZE);
            }
        }
    }

    public void draw(Graphics g) {
        g.drawImage(levelAsset[0], 0, 0, null);
    }

    public void update() {

    }
}
