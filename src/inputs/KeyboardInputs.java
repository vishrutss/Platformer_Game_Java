package inputs;

import static utils.Constants.Directions.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import main.GamePanel;

public class KeyboardInputs implements KeyListener {

    private GamePanel panel;

    public KeyboardInputs(GamePanel panel) {
        this.panel = panel;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                panel.setDirection(UP);
                break;
            case KeyEvent.VK_A:
                panel.setDirection(LEFT);
                break;
            case KeyEvent.VK_S:
                panel.setDirection(DOWN);
                break;
            case KeyEvent.VK_D:
                panel.setDirection(RIGHT);
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_A:
            case KeyEvent.VK_S:
            case KeyEvent.VK_D:
                panel.setMoving(false);
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
