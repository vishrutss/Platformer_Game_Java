package utils;

import main.Game;

public class HelperMethods {
    public static boolean CanMoveHere(float x, float y, int width, int height, int[][] levelData) {
        if (IsSolid(x, y, levelData)) {
            return false;
        }
        if (IsSolid(x + width, y, levelData)) {
            return false;
        }
        if (IsSolid(x, y + height, levelData)) {
            return false;
        }
        if (IsSolid(x + width, y + height, levelData)) {
            return false;
        }
        return true;
    }

    private static boolean IsSolid(float x, float y, int[][] levelData) {
        if (x < 0 || x >= Game.GAME_WIDTH) {
            return true;
        }
        if (y < 0 || y >= Game.GAME_HEIGHT) {
            return true;
        }
        float xIndex = x / Game.TILES_SIZE;
        float yIndex = y / Game.TILES_SIZE;
        int value = levelData[(int) yIndex][(int) xIndex];

        if (value >= 48 || value < 0 || value != 11) {
            return true;
        }
        return false;
    }
}
