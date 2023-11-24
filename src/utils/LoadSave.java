package utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Color;
import javax.imageio.ImageIO;

import main.Game;

public class LoadSave {
    public static final String PLAYER_ASSET = "player.png";
    public static final String LEVEL_ASSET = "level_outside.png";
    public static final String LEVEL_ONE_DATA = "level_one_data.png";
    public static final String MENU_BUTTONS = "menu_buttons.png";
    public static final String MENU_BACKGROUND = "menu_background.png";
    public static final String PAUSE_BACKGROUND = "pause_menu.png";
    public static final String SOUND_BUTTONS = "sound_button.png";
    // Unpause, Restart, Main Menu
    public static final String URM_BUTTONS = "urm_buttons.png";
    public static final String VOLUME_BTNS = "volume_buttons.png";

    public static BufferedImage GetAsset(String fileName) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("res/" + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public static int[][] GetLevelData() {
        int[][] levelData = new int[Game.TILES_IN_HEIGHT][Game.TILES_IN_WIDTH];
        BufferedImage image = GetAsset(LEVEL_ONE_DATA);
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                Color color = new Color(image.getRGB(j, i));
                int value = color.getRed();
                if (value >= 48) {
                    value = 0;
                }
                levelData[i][j] = value;
            }
        }
        return levelData;
    }
}
