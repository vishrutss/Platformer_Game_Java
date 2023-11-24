package inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import gamestates.GameState;
import main.GamePanel;

public class MouseInputs implements MouseListener, MouseMotionListener {

    private GamePanel panel;

    public MouseInputs(GamePanel panel) {
        this.panel = panel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        switch (GameState.currentState) {
            case PLAYING:
                panel.getGame().getPlaying().mouseClicked(e);
                break;
            default:
                break;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (GameState.currentState) {
            case PLAYING:
                panel.getGame().getPlaying().mousePressed(e);
                break;
            case MENU:
                panel.getGame().getMenu().mousePressed(e);
                break;
            default:
                break;
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (GameState.currentState) {
            case PLAYING:
                panel.getGame().getPlaying().mouseReleased(e);
                break;
            case MENU:
                panel.getGame().getMenu().mouseReleased(e);
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        switch (GameState.currentState) {
            case PLAYING:
                panel.getGame().getPlaying().mouseDragged(e);
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        switch (GameState.currentState) {
            case PLAYING:
                panel.getGame().getPlaying().mouseMoved(e);
                break;
            case MENU:
                panel.getGame().getMenu().mouseMoved(e);
                break;
            default:
                break;
        }
    }

}
