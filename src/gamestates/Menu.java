package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.Color;

import main.Game;
import ui.MenuButton;

public class Menu extends State implements StateMethods {

    private MenuButton[] menuButtons = new MenuButton[3];

    public Menu(Game game) {
        super(game);
        loadButtons();
    }

    private void loadButtons() {
        menuButtons[0] = new MenuButton(Game.GAME_WIDTH / 2, (int) (150 * Game.SCALE), 0, GameState.PLAYING);
        menuButtons[1] = new MenuButton(Game.GAME_WIDTH / 2, (int) (220 * Game.SCALE), 1, GameState.OPTIONS);
        menuButtons[2] = new MenuButton(Game.GAME_WIDTH / 2, (int) (290 * Game.SCALE), 2, GameState.QUIT);
    }

    @Override
    public void update() {
        for (MenuButton button : menuButtons) {
            button.update();
        }
    }

    @Override
    public void draw(Graphics g) {
        for (MenuButton button : menuButtons) {
            button.draw(g);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (MenuButton button : menuButtons) {
            if (isInBounds(e, button)) {
                button.setMousePressed(true);
                break;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (MenuButton button : menuButtons) {
            if (isInBounds(e, button)) {
                if (button.isMousePressed()) {
                    button.applyGameState();
                }
                break;
            }
        }
        resetButtons();
    }

    private void resetButtons() {
        for (MenuButton button : menuButtons) {
            button.resetBools();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (MenuButton button : menuButtons) {
            button.setMouseOver(false);
        }
        for (MenuButton button : menuButtons) {
            if (isInBounds(e, button)) {
                button.setMouseOver(true);
                break;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            GameState.currentState = GameState.PLAYING;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}
