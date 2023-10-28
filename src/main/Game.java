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

        while (true) {
            currentFrame = System.nanoTime();
            if (currentFrame - lastFrame >= timePerFrame) {
                panel.repaint();
                lastFrame = currentFrame;
            }
        }
    }
}
