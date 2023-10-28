package main;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GamePanel extends JPanel {

    private MouseInputs mouseInputs;
    private float xDelta = 100, yDelta = 100;
    private BufferedImage image, subImage;

    public GamePanel() {
        mouseInputs = new MouseInputs(this);
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
        setPanelSize();
        importImage();
    }

    private void importImage() {

        try {
            image = ImageIO.read(new File("res/player.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setPanelSize() {
        Dimension dimension = new Dimension(1280, 800);
        setPreferredSize(dimension);
    }

    public void setRectPosition(int x, int y) {
        this.xDelta = x;
        this.yDelta = y;
        repaint();
    }

    public void updateXDelta(int value) {
        this.xDelta += value;
        repaint();
    }

    public void updateYDelta(int value) {
        this.yDelta += value;
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        subImage = image.getSubimage(64, 8 * 40, 64, 40);
        g.drawImage(subImage, (int) xDelta, (int) yDelta, 128, 80, null);

    }
}
