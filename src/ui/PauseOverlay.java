package ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestates.GameState;
import gamestates.Playing;
import main.Game;
import utils.LoadSave;
import static utils.Constants.UI.PauseButtons.*;
import static utils.Constants.UI.UrmButtons.*;
import static utils.Constants.UI.VolumeButtons.*;

public class PauseOverlay {

    private Playing playing;
    private BufferedImage backgroundImage;
    private int bgX, bgY, bgWidth, bgHeight;
    private SoundButton musicButton, sfxButton;
    private UrmButton unpauseButton, restartButton, mainMenuButton;
    private VolumeButton volumeButton;

    public PauseOverlay(Playing playing) {
        this.playing = playing;
        loadBackground();
        createSoundButtons();
        createUrmButtons();
        createVolumeButton();
    }

    private void createVolumeButton() {
        int volX = (int) (309 * Game.SCALE);
        int volY = (int) (278 * Game.SCALE);
        volumeButton = new VolumeButton(volX, volY, VOLUME_SLIDER_WIDTH, VOLUME_BTN_HEIGHT);
    }

    private void createUrmButtons() {
        int unpauseX = (int) (462 * Game.SCALE);
        int restartX = (int) (387 * Game.SCALE);
        int mainMenuX = (int) (313 * Game.SCALE);
        int urmY = (int) (325 * Game.SCALE);
        unpauseButton = new UrmButton(unpauseX, urmY, URM_BTN_SIZE, URM_BTN_SIZE, 0);
        restartButton = new UrmButton(restartX, urmY, URM_BTN_SIZE, URM_BTN_SIZE, 1);
        mainMenuButton = new UrmButton(mainMenuX, urmY, URM_BTN_SIZE, URM_BTN_SIZE, 2);
    }

    private void createSoundButtons() {
        int soundX = (int) (450 * Game.SCALE);
        int musicY = (int) (140 * Game.SCALE);
        int sfxY = (int) (186 * Game.SCALE);
        musicButton = new SoundButton(soundX, musicY, SOUND_BTN_SIZE, SOUND_BTN_SIZE);
        sfxButton = new SoundButton(soundX, sfxY, SOUND_BTN_SIZE, SOUND_BTN_SIZE);
    }

    private void loadBackground() {
        backgroundImage = LoadSave.GetAsset(LoadSave.PAUSE_BACKGROUND);
        bgWidth = (int) (backgroundImage.getWidth() * Game.SCALE);
        bgHeight = (int) (backgroundImage.getHeight() * Game.SCALE);
        bgX = (Game.GAME_WIDTH - bgWidth) / 2;
        bgY = (int) (25 * Game.SCALE);
    }

    public void update() {
        musicButton.update();
        sfxButton.update();
        unpauseButton.update();
        restartButton.update();
        mainMenuButton.update();
        volumeButton.update();
    }

    public void draw(Graphics g) {
        // Background
        g.drawImage(backgroundImage, bgX, bgY, bgWidth, bgHeight, null);

        // Sound buttons
        musicButton.draw(g);
        sfxButton.draw(g);

        // Urm buttons
        unpauseButton.draw(g);
        restartButton.draw(g);
        mainMenuButton.draw(g);

        // Volume slider
        volumeButton.draw(g);
    }

    public void mouseDragged(MouseEvent e) {
        if(volumeButton.isMousePressed()) {
            volumeButton.changeX(e.getX());
        }
    }

    public void mousePressed(MouseEvent e) {
        if (isIn(e, musicButton)) {
            musicButton.setMousePressed(true);
        } else if (isIn(e, sfxButton)) {
            sfxButton.setMousePressed(true);
        } else if (isIn(e, unpauseButton)) {
            unpauseButton.setMousePressed(true);
        } else if (isIn(e, restartButton)) {
            restartButton.setMousePressed(true);
        } else if (isIn(e, mainMenuButton)) {
            mainMenuButton.setMousePressed(true);
        } else if (isIn(e, volumeButton)) {
            volumeButton.setMousePressed(true);
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (isIn(e, musicButton)) {
            if (musicButton.isMousePressed()) {
                musicButton.setMuted(!musicButton.isMuted());
            }
        } else if (isIn(e, sfxButton)) {
            if (sfxButton.isMousePressed()) {
                sfxButton.setMuted(!sfxButton.isMuted());
            }
        } else if (isIn(e, unpauseButton)) {
            if (unpauseButton.isMousePressed()) {
                playing.unPauseGame();
            }
        } else if (isIn(e, restartButton)) {
            if (restartButton.isMousePressed()) {
                // TODO: Restart level
                System.out.println("Restart Level");
            }
        } else if (isIn(e, mainMenuButton)) {
            if (mainMenuButton.isMousePressed()) {
                GameState.currentState = GameState.MENU;
                playing.unPauseGame();
            }
        }
        musicButton.resetBools();
        sfxButton.resetBools();
        unpauseButton.resetBools();
        restartButton.resetBools();
        mainMenuButton.resetBools();
        volumeButton.resetBools();
    }

    public void mouseMoved(MouseEvent e) {
        musicButton.setMouseOver(false);
        sfxButton.setMouseOver(false);
        unpauseButton.setMouseOver(false);
        restartButton.setMouseOver(false);
        mainMenuButton.setMouseOver(false);
        volumeButton.setMouseOver(false);

        if (isIn(e, musicButton)) {
            musicButton.setMouseOver(true);
        } else if (isIn(e, sfxButton)) {
            sfxButton.setMouseOver(true);
        } else if (isIn(e, unpauseButton)) {
            unpauseButton.setMouseOver(true);
        } else if (isIn(e, restartButton)) {
            restartButton.setMouseOver(true);
        } else if (isIn(e, mainMenuButton)) {
            mainMenuButton.setMouseOver(true);
        } else if (isIn(e, volumeButton)) {
            volumeButton.setMouseOver(true);
        }
    }

    private boolean isIn(MouseEvent e, PauseButton button) {
        return button.getBounds().contains(e.getX(), e.getY());
    }
}
