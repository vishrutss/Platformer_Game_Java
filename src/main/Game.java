package main;

import java.awt.Graphics;

import entities.Player;
import levels.LevelHandler;

public class Game implements Runnable {
    private GameWindow window;
    private GamePanel panel;
    private Thread gameThread;
    private final int FPS_LIMIT = 120;
    private final int UPS_LIMIT = 100;
    public static final int TILES_DEFAULT_SIZE = 32;
    public static final float SCALE = 1.5f;
    public static final int TILES_IN_WIDTH = 26;
    public static final int TILES_IN_HEIGHT = 14;
    public static final int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
    public static final int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public static final int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;

    private Player player;
    private LevelHandler levelHandler;

    public Game() {
        System.out.println("Tiles size: " + TILES_SIZE);
        initClasses();
        panel = new GamePanel(this);
        window = new GameWindow(panel);
        panel.requestFocus();
        startGameLoop();
    }

    private void initClasses() {
        levelHandler = new LevelHandler(this);
        player = new Player(200, 200, (int) (64 * SCALE), (int) (40 * SCALE));
        player.loadLevelData(levelHandler.getCurrentLevel().getLevelData());
    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {
        player.update();
        levelHandler.update();
    }

    public void render(Graphics g) {
        levelHandler.draw(g);
        player.render(g);
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
        player.resetDirectionBooleans();
    }

    public Player getPlayer() {
        return player;
    }
}
