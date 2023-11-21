package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import static utils.Constants.UI.UrmButtons.*;

import utils.LoadSave;

public class UrmButton extends PauseButton {

    private BufferedImage[] urmImages;
    private int rowIndex, index;
    private boolean mouseOver, mousePressed;

    public UrmButton(int x, int y, int width, int height, int rowIndex) {
        super(x, y, width, height);
        this.rowIndex = rowIndex;
        loadImages();
    }

    private void loadImages() {
        BufferedImage temp = LoadSave.GetAsset(LoadSave.URM_BUTTONS);
        urmImages = new BufferedImage[3];
        for (int i = 0; i < urmImages.length; i++) {
            urmImages[i] = temp.getSubimage(i * URM_BTN_SIZE_DEFAULT, rowIndex * URM_BTN_SIZE_DEFAULT,
                    URM_BTN_SIZE_DEFAULT, URM_BTN_SIZE_DEFAULT);
        }
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
        g.drawImage(urmImages[index], x, y, URM_BTN_SIZE, URM_BTN_SIZE, null);
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
