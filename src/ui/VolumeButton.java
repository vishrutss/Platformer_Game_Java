package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import utils.LoadSave;
import static utils.Constants.UI.VolumeButtons.*;

public class VolumeButton extends PauseButton {

    private BufferedImage[] volumeImages;
    private BufferedImage sliderImage;
    private int index = 0, buttonX, minX, maxX;
    private boolean mouseOver, mousePressed;

    public VolumeButton(int x, int y, int width, int height) {
        super(x + width / 2, y, VOLUME_BTN_WIDTH, height);
        bounds.x -= VOLUME_BTN_WIDTH / 2;
        buttonX = x + width / 2;
        this.x = x;
        this.width = width;
        minX = x + VOLUME_BTN_WIDTH / 2;
        maxX = x + width - VOLUME_BTN_WIDTH / 2;
        loadImages();
    }

    private void loadImages() {
        BufferedImage temp = LoadSave.GetAsset(LoadSave.VOLUME_BTNS);
        volumeImages = new BufferedImage[3];
        for (int i = 0; i < volumeImages.length; i++) {
            volumeImages[i] = temp.getSubimage(i * VOLUME_BTN_WIDTH_DEFAULT, 0, VOLUME_BTN_WIDTH_DEFAULT,
                    VOLUME_BTN_HEIGHT_DEFAULT);
        }
        sliderImage = temp.getSubimage(3 * VOLUME_BTN_WIDTH_DEFAULT, 0, VOLUME_SLIDER_WIDTH_DEFAULT,
                VOLUME_BTN_HEIGHT_DEFAULT);
    }

    public void update() {
        index = 0;
        if (mouseOver) {
            index = 1;
        }
        if (mousePressed) {
            index = 2;
        }
    }

    public void draw(Graphics g) {
        g.drawImage(sliderImage, x, y, width, height, null);
        g.drawImage(volumeImages[index], buttonX - VOLUME_BTN_WIDTH / 2, y, VOLUME_BTN_WIDTH, height, null);
    }

    public void changeX(int x) {
        if (x < minX) {
            buttonX = minX;
        } else if (x > maxX) {
            buttonX = maxX;
        } else {
            buttonX = x;
        }
        bounds.x = buttonX - VOLUME_BTN_WIDTH / 2;
    }

    public void resetBools() {
        mouseOver = false;
        mousePressed = false;
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }
}
