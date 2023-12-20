package utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static utils.Constants.Enemy.CRAB;

import java.awt.Color;
import javax.imageio.ImageIO;

import entities.Crab;
import main.Game;

public class LoadSave {
    public static final String PLAYER_ASSET = "player.png";
    public static final String LEVEL_ASSET = "level_outside.png";
    public static final String LEVEL_ONE_DATA = "level_one_data_long.png";
    public static final String MENU_BUTTONS = "menu_buttons.png";
    public static final String MENU_BACKGROUND = "menu_background.png";
    public static final String PAUSE_BACKGROUND = "pause_menu.png";
    public static final String SOUND_BUTTONS = "sound_button.png";
    // Unpause, Restart, Main Menu
    public static final String URM_BUTTONS = "urm_buttons.png";
    public static final String VOLUME_BTNS = "volume_buttons.png";
    public static final String MENU_SCREEN_BACKGROUND = "Forest_Landscape.jpg";
    public static final String GAME_BACKGROUND = "game_background.png";
    public static final String GAME_BG_CLOUDS = "big_clouds.png";
    public static final String GAME_BG_SMALL_CLOUDS = "small_clouds.png";
    public static final String ENEMY_CRAB = "enemy_crab.png";
    public static final String HEALTH_POWER_BAR = "health_power_bar.png";

    public static BufferedImage GetAsset(String fileName) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("res/" + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public static ArrayList<Crab> GetCrabs() {
        BufferedImage image = GetAsset(LEVEL_ONE_DATA);
        ArrayList<Crab> crabs = new ArrayList<>();
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                Color color = new Color(image.getRGB(j, i));
                int value = color.getGreen();
                if (value == CRAB) {
                    crabs.add(new Crab(j * Game.TILES_SIZE, i * Game.TILES_SIZE));
                }
            }
        }
        return crabs;
    }

    public static int[][] GetLevelData() {
        BufferedImage image = GetAsset(LEVEL_ONE_DATA);
        int[][] levelData = new int[image.getHeight()][image.getWidth()];
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
