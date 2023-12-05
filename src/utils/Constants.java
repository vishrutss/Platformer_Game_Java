package utils;

import main.Game;

public class Constants {

    public static class Enemy {
        public static final int CRAB = 0;

        public static final int IDLE = 0;
        public static final int RUN = 1;
        public static final int ATTACK = 2;
        public static final int HURT = 3;
        public static final int DEAD = 4;

        public static final int CRAB_DEFAULT_WIDTH = 72;
        public static final int CRAB_DEFAULT_HEIGHT = 32;
        public static final int CRAB_WIDTH = (int) (CRAB_DEFAULT_WIDTH * Game.SCALE);
        public static final int CRAB_HEIGHT = (int) (CRAB_DEFAULT_HEIGHT * Game.SCALE);

        public static int GetAssetAmount(int enemyType, int enemyState) {
            switch (enemyType) {
                case CRAB:
                    switch (enemyState) {
                        case IDLE:
                            return 9;
                        case RUN:
                            return 6;
                        case ATTACK:
                            return 7;
                        case HURT:
                            return 4;
                        case DEAD:
                            return 5;
                    }
            }
            return 0;
        }
    }

    public static class Environment {
        public static final int BIG_CLOUD_DEFAULT_WIDTH = 448;
        public static final int BIG_CLOUD_DEFAULT_HEIGHT = 101;
        public static final int BIG_CLOUD_WIDTH = (int) (BIG_CLOUD_DEFAULT_WIDTH * Game.SCALE);
        public static final int BIG_CLOUD_HEIGHT = (int) (BIG_CLOUD_DEFAULT_HEIGHT * Game.SCALE);

        public static final int SMALL_CLOUD_DEFAULT_WIDTH = 74;
        public static final int SMALL_CLOUD_DEFAULT_HEIGHT = 24;
        public static final int SMALL_CLOUD_WIDTH = (int) (SMALL_CLOUD_DEFAULT_WIDTH * Game.SCALE);
        public static final int SMALL_CLOUD_HEIGHT = (int) (SMALL_CLOUD_DEFAULT_HEIGHT * Game.SCALE);
    }

    public static class UI {
        public static class Buttons {
            public static final int BUTTON_WIDTH_DEFAULT = 140;
            public static final int BUTTON_HEIGHT_DEFAULT = 56;
            public static final int BUTTON_WIDTH = (int) (BUTTON_WIDTH_DEFAULT * Game.SCALE);
            public static final int BUTTON_HEIGHT = (int) (BUTTON_HEIGHT_DEFAULT * Game.SCALE);
        }

        public static class PauseButtons {
            public static final int SOUND_BTN_SIZE_DEFAULT = 42;
            public static final int SOUND_BTN_SIZE = (int) (SOUND_BTN_SIZE_DEFAULT * Game.SCALE);
        }

        public static class UrmButtons {
            public static final int URM_BTN_SIZE_DEFAULT = 56;
            public static final int URM_BTN_SIZE = (int) (URM_BTN_SIZE_DEFAULT * Game.SCALE);
        }

        public static class VolumeButtons {
            public static final int VOLUME_BTN_WIDTH_DEFAULT = 28;
            public static final int VOLUME_BTN_HEIGHT_DEFAULT = 44;
            public static final int VOLUME_SLIDER_WIDTH_DEFAULT = 215;

            public static final int VOLUME_BTN_WIDTH = (int) (VOLUME_BTN_WIDTH_DEFAULT * Game.SCALE);
            public static final int VOLUME_BTN_HEIGHT = (int) (VOLUME_BTN_HEIGHT_DEFAULT * Game.SCALE);
            public static final int VOLUME_SLIDER_WIDTH = (int) (VOLUME_SLIDER_WIDTH_DEFAULT * Game.SCALE);
        }
    }

    public static class Directions {
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }

    public static class PlayerConstants {
        public static final int ANIMATIONS_DIMENSION_X = 6, ANIMATIONS_DIMENSION_Y = 9;
        public static final int IDLE = 0;
        public static final int RUN = 1;
        public static final int JUMP = 2;
        public static final int FALL = 3;
        public static final int FALL_GROUND = 4;
        public static final int HURT = 5;
        public static final int ATTACK = 6;
        public static final int ATTACK_JUMP_1 = 7;
        public static final int ATTACK_JUMP_2 = 8;

        public static int GetAssetAmount(int playerAction) {
            switch (playerAction) {
                case RUN:
                    return 6;
                case IDLE:
                    return 5;
                case JUMP:
                case ATTACK:
                case ATTACK_JUMP_1:
                case ATTACK_JUMP_2:
                    return 3;
                case HURT:
                    return 4;
                case FALL_GROUND:
                    return 2;
                case FALL:
                default:
                    return 1;
            }
        }
    }
}
