package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

import entities.EnemyHandler;
import entities.Player;
import levels.LevelHandler;
import main.Game;
import ui.PauseOverlay;
import utils.LoadSave;
import static utils.Constants.Environment.*;

public class Playing extends State implements StateMethods {
    private Player player;
    private LevelHandler levelHandler;
    private EnemyHandler enemyHandler;
    private PauseOverlay pauseOverlay;
    private boolean paused = false;
    private int xLevelOffset;
    private int leftBorder = (int) (Game.GAME_WIDTH * 0.2), rightBorder = (int) (Game.GAME_WIDTH * 0.8);
    private int levelTilesWide = LoadSave.GetLevelData()[0].length;
    private int maxTilesOffset = levelTilesWide - Game.TILES_IN_WIDTH;
    private int maxLevelOffsetX = maxTilesOffset * Game.TILES_SIZE;
    private BufferedImage background, bg_clouds, bg_small_clouds;
    private int[] small_clouds_pos;
    private Random random = new Random();

    public Playing(Game game) {
        super(game);
        initClasses();
        background = LoadSave.GetAsset(LoadSave.GAME_BACKGROUND);
        bg_clouds = LoadSave.GetAsset(LoadSave.GAME_BG_CLOUDS);
        bg_small_clouds = LoadSave.GetAsset(LoadSave.GAME_BG_SMALL_CLOUDS);
        small_clouds_pos = new int[8];
        for (int i = 0; i < small_clouds_pos.length; i++) {
            small_clouds_pos[i] = (int) (90 * Game.SCALE) + random.nextInt((int) (100 * Game.SCALE));
        }
    }

    private void initClasses() {
        levelHandler = new LevelHandler(game);
        enemyHandler = new EnemyHandler(this);
        player = new Player(200, 200, (int) (64 * Game.SCALE), (int) (40 * Game.SCALE));
        player.loadLevelData(levelHandler.getCurrentLevel().getLevelData());
        pauseOverlay = new PauseOverlay(this);
    }

    @Override
    public void update() {
        if (!paused) {
            levelHandler.update();
            player.update();
            enemyHandler.update();
            checkCloseToBorder();
        } else {
            pauseOverlay.update();
        }
    }

    private void checkCloseToBorder() {
        int playerX = (int) player.getHitbox().x;
        int diff = playerX - xLevelOffset;
        if (diff > rightBorder) {
            xLevelOffset += diff - rightBorder;
        } else if (diff < leftBorder) {
            xLevelOffset += diff - leftBorder;
        }
        if (xLevelOffset > maxLevelOffsetX) {
            xLevelOffset = maxLevelOffsetX;
        } else if (xLevelOffset < 0) {
            xLevelOffset = 0;
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(background, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        drawClouds(g);
        levelHandler.draw(g, xLevelOffset);
        player.render(g, xLevelOffset);
        enemyHandler.draw(g, xLevelOffset);
        if (paused) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
            pauseOverlay.draw(g);
        }
    }

    private void drawClouds(Graphics g) {
        for (int i = 0; i < 3; i++) {
            g.drawImage(bg_clouds, 0 + i * BIG_CLOUD_WIDTH - (int) (xLevelOffset * 0.3), (int) (204 * Game.SCALE),
                    BIG_CLOUD_WIDTH, BIG_CLOUD_HEIGHT,
                    null);
        }
        for (int i = 0; i < small_clouds_pos.length; i++) {
            g.drawImage(bg_small_clouds, SMALL_CLOUD_WIDTH * 4 * i - (int) (xLevelOffset * 0.4), small_clouds_pos[i],
                    SMALL_CLOUD_WIDTH,
                    SMALL_CLOUD_HEIGHT, null);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            player.setAttacking(true);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (paused)
            pauseOverlay.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (paused)
            pauseOverlay.mouseReleased(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (paused)
            pauseOverlay.mouseMoved(e);
    }

    public void mouseDragged(MouseEvent e) {
        if (paused)
            pauseOverlay.mouseDragged(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                player.setLeft(true);
                break;
            case KeyEvent.VK_D:
                player.setRight(true);
                break;
            case KeyEvent.VK_SPACE:
                player.setJump(true);
                break;
            case KeyEvent.VK_ESCAPE:
                paused = !paused;
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                player.setLeft(false);
                break;
            case KeyEvent.VK_D:
                player.setRight(false);
                break;
            case KeyEvent.VK_SPACE:
                player.setJump(false);
                break;
        }
    }

    public void unPauseGame() {
        paused = false;
    }

    public void windowFocusLost() {
        player.resetDirectionBooleans();
    }

    public Player getPlayer() {
        return player;
    }
}
