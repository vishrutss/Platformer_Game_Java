package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import main.GamePanel;

public class KeyboardInputs implements KeyListener {

    private GamePanel panel;
    public KeyboardInputs(GamePanel panel){
        this.panel = panel;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        e.getKeyCode();
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                panel.updateYDelta(-5);
                break;
            case KeyEvent.VK_A:
                panel.updateXDelta(-5);
                break;
            case KeyEvent.VK_S:
                panel.updateYDelta(5);
                break;
            case KeyEvent.VK_D:
                panel.updateXDelta(5);
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
