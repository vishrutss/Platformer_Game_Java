package main;

public class Game implements Runnable {
    private GameWindow window;
    private GamePanel panel;
    private Thread gameThread;
    private final int FPS_LIMIT = 120;

    public Game() {
        panel = new GamePanel();
        window = new GameWindow(panel);
        panel.requestFocus();
        startGameLoop();

    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double timePerFrame = 1_000_000_000.0 / FPS_LIMIT;
        long currentFrame = System.nanoTime(), lastFrame = System.nanoTime();
        int fps = 0;
        long lastCheck = System.currentTimeMillis();

        while (true) {
            currentFrame = System.nanoTime();
            if (currentFrame - lastFrame >= timePerFrame) {
                panel.repaint();
                lastFrame = currentFrame;
                fps++;
            }
            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + fps);
                fps = 0;
            }
        }
    }
}
