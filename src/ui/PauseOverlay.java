package ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main.Game;
import utils.LoadSave;

public class PauseOverlay {

    private BufferedImage backgroundImage;
    private int bgX, bgY, bgWidth, bgHeight;

    public PauseOverlay() {
        loadBackground();
    }

    private void loadBackground() {
        backgroundImage = LoadSave.GetAsset(LoadSave.PAUSE_BACKGROUND);
        bgWidth = (int) (backgroundImage.getWidth() * Game.SCALE);
        bgHeight = (int) (backgroundImage.getHeight() * Game.SCALE);
        bgX = (Game.GAME_WIDTH - bgWidth) / 2;
        bgY = (int) (25 * Game.SCALE);
    }

    public void update() {
    }

    public void draw(Graphics g) {
        g.drawImage(backgroundImage, bgX, bgY, bgWidth, bgHeight, null);
    }

    public void mouseDragged(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
    }
}
