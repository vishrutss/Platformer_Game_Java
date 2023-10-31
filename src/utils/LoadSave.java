package utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class LoadSave {
    public static final String PLAYER_ASSET = "player.png";
    public static final String LEVEL_ASSET = "level_outside.png";

    public static BufferedImage getAsset(String fileName) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("res/" + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
