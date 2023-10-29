package main;

import java.awt.Graphics;

import entities.Player;

public class Game implements Runnable {
    private GameWindow window;
    private GamePanel panel;
    private Thread gameThread;
    private final int FPS_LIMIT = 120;
    private final int UPS_LIMIT = 100;

    private Player player;

    public Game() {
        initClasses();
        panel = new GamePanel(this);
        window = new GameWindow(panel);
        panel.requestFocus();
        startGameLoop();
    }

    private void initClasses() {
        player = new Player(200, 200);
    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {
        player.update();
    }

    public void render(Graphics g) {
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
