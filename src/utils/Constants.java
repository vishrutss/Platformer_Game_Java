package utils;

import main.Game;

public class Constants {

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
