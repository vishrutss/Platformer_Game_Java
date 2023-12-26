package main;

import java.awt.Graphics;

import gamestates.GameState;
import gamestates.Menu;
import gamestates.Playing;

public class Game implements Runnable {
    private GameWindow window;
    private GamePanel panel;
    private Thread gameThread;
    private final int FPS_LIMIT = 120;
    private final int UPS_LIMIT = 100;

    private Playing playing;
    private Menu menu;

    public static final int TILES_DEFAULT_SIZE = 32;
    public static final float SCALE = 1.5f;
    public static final int TILES_IN_WIDTH = 26;
    public static final int TILES_IN_HEIGHT = 14;
    public static final int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
    public static final int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public static final int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;

    public Game() {
        System.out.println("Tiles size: " + TILES_SIZE);
        initClasses();
        panel = new GamePanel(this);
        window = new GameWindow(panel);
        panel.setFocusable(true);
        panel.requestFocus();
        startGameLoop();
    }

    private void initClasses() {
        playing = new Playing(this);
        menu = new Menu(this);
    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {

        switch (GameState.currentState) {
            case PLAYING:
                playing.update();
                break;
            case MENU:
                menu.update();
                break;
            case OPTIONS:
            case QUIT:
            default:
                System.exit(0);
                break;
        }
    }

    public void render(Graphics g) {

        switch (GameState.currentState) {
            case PLAYING:
                playing.draw(g);
                break;
            case MENU:
                menu.draw(g);
                break;
            default:
                break;
        }
    }

    @Override
    public void run() {
        double timePerFrame = 1_000_000_000.0 / FPS_LIMIT;
        double timePerUpdate = 1_000_000_000.0 / UPS_LIMIT;
        long previousTime = System.nanoTime();
        int fps = 0, ups = 0;
        long lastCheck = System.currentTimeMillis();

        double deltaUpdate = 0, deltaFrame = 0;

        while (true) {
            long currentTime = System.nanoTime();
            deltaUpdate += (currentTime - previousTime) / timePerUpdate;
            deltaFrame += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;
            if (deltaUpdate >= 1) {
                update();
                ups++;
                deltaUpdate--;
            }
            if (deltaFrame >= 1) {
                panel.repaint();
                fps++;
                deltaFrame--;
            }
            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + fps + "| UPS: " + ups);
                fps = 0;
                ups = 0;
            }
        }
    }

    public void windowFocusLost() {
        if (GameState.currentState == GameState.PLAYING) {
            playing.getPlayer().resetDirectionBooleans();
        }
    }

    public Menu getMenu() {
        return menu;
    }

    public Playing getPlaying() {
        return playing;
    }
}
