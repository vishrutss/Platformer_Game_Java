package gamestates;

import java.awt.event.MouseEvent;
import main.Game;
import ui.MenuButton;

public class State {

    protected Game game;

    public State(Game game) {
        this.game = game;
    }

    public boolean isInBounds(MouseEvent e, MenuButton button) {
        return button.getBounds().contains(e.getX(), e.getY());
    }

    public Game getGame() {
        return game;
    }
}
