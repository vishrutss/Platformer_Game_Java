package gamestates;

public enum GameState {
    PLAYING, MENU, OPTIONS, QUIT;

    public static GameState currentState = MENU;
}
