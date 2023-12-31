package utils;

import java.awt.geom.Rectangle2D;

import main.Game;

public class HelperMethods {
    public static boolean CanMoveHere(float x, float y, float width, float height, int[][] levelData) {
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
        int maxWidth = levelData[0].length * Game.TILES_SIZE;
        if (x < 0 || x >= maxWidth) {
            return true;
        }
        if (y < 0 || y >= Game.GAME_HEIGHT) {
            return true;
        }
        float xIndex = x / Game.TILES_SIZE;
        float yIndex = y / Game.TILES_SIZE;
        return IsTileSolid((int) xIndex, (int) yIndex, levelData);
    }

    public static boolean IsTileSolid(int tileX, int tileY, int[][] levelData) {
        int value = levelData[tileY][tileX];

        if (value >= 48 || value < 0 || value != 11) {
            return true;
        }
        return false;
    }

    public static float GetEntityXPositionNextToWall(Rectangle2D.Float hitbox, float xSpeed) {
        int currentTile = (int) (hitbox.x / Game.TILES_SIZE);
        if (xSpeed > 0) {
            int tileXPosition = currentTile * Game.TILES_SIZE;
            int xOffset = (int) (Game.TILES_SIZE - hitbox.width);
            return tileXPosition + xOffset - 1;
        } else {
            return currentTile * Game.TILES_SIZE;
        }
    }

    public static float GetEntityYPositionUnderRoofAboveFloor(Rectangle2D.Float hitbox, float airSpeed) {
        int currentTile = (int) (hitbox.y / Game.TILES_SIZE);
        if (airSpeed > 0) {
            int tileYPosition = currentTile * Game.TILES_SIZE;
            int yOffset = (int) (Game.TILES_SIZE - hitbox.height);
            return tileYPosition + yOffset - 1;
        } else {
            return currentTile * Game.TILES_SIZE;
        }
    }

    public static boolean IsEntitiyOnGround(Rectangle2D.Float hitbox, int[][] levelData) {
        if (!IsSolid(hitbox.x, hitbox.y + hitbox.height + 1, levelData)) {
            if (!IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, levelData)) {
                return false;
            }
        }
        return true;
    }

    public static boolean IsFloor(Rectangle2D.Float hitbox, float xSpeed, int[][] levelData) {
        if (xSpeed > 0)
            return IsSolid(hitbox.x + hitbox.width + xSpeed, hitbox.y + hitbox.height + 1, levelData);
        else
            return IsSolid(hitbox.x + xSpeed, hitbox.y + hitbox.height + 1, levelData);
    }

    public static boolean isTileWalkable(int xStart, int xEnd, int y, int[][] levelData) {
        for (int i = 0; i < xEnd - xStart; i++) {
            if (IsTileSolid(xStart + i, y, levelData)) {
                return false;
            }
            if (!IsTileSolid(xStart + i, y + 1, levelData)) {
                return false;
            }
        }
        return true;
    }

    public static boolean IsSightClear(int[][] levelData, Rectangle2D.Float firstHitbox, Rectangle2D.Float secondHitbox,
            int tileY) {
        int firstTileX = (int) (firstHitbox.x / Game.TILES_SIZE);
        int secondTileX = (int) (secondHitbox.x / Game.TILES_SIZE);
        if (firstTileX > secondTileX) {
            return isTileWalkable(secondTileX, firstTileX, tileY, levelData);
        } else {
            return isTileWalkable(firstTileX, secondTileX, tileY, levelData);
        }
    }
}
