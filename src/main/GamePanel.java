package main;

import javax.swing.JPanel;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import java.awt.Color;
import java.awt.Graphics;

public class GamePanel extends JPanel {

    private MouseInputs mouseInputs;
    private float xDelta = 100, yDelta = 100;
    private float xDirection = 0.1f, yDirection = 0.1f;
    private int fps = 0;
    private long lastCheck = 0;
    private Color color = new Color(123, 50, 250);

    public GamePanel() {
        mouseInputs = new MouseInputs(this);
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
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

        updateRectangle();
        g.setColor(color);
        g.fillRect((int) xDelta, (int) yDelta, 50, 50);

        fps++;
        if (System.currentTimeMillis() - lastCheck >= 1000) {
            lastCheck = System.currentTimeMillis();
            System.out.println("FPS: " + fps);
            fps = 0;
        }
    }

    private void updateRectangle() {
        xDelta += xDirection;
        yDelta += yDirection;
        if (xDelta > getWidth() || xDelta < 0) {
            xDirection *= -1;
            color= new Color((int)(Math.random() * 0x1000000));
        }
        if (yDelta > getHeight() || yDelta < 0) {
            yDirection *= -1;
            color= new Color((int)(Math.random() * 0x1000000));
        }
    }
}
