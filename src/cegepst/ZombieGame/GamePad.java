package cegepst.ZombieGame;

import cegepst.engine.RenderingEngine;
import cegepst.engine.controls.MovementController;

import java.awt.event.KeyEvent;

public class GamePad extends MovementController {

    private final int quitKey = KeyEvent.VK_Q;
    private final int reloadKey = KeyEvent.VK_R;
    private final int sprintKey = KeyEvent.VK_SHIFT;

    public GamePad() {
        bindKey(quitKey);
        bindKey(reloadKey);
        bindKey(sprintKey);
        RenderingEngine.getInstance().addKeyListener(this);
        setUpKey(KeyEvent.VK_W);
        setLeftKey(KeyEvent.VK_A);
        setDownKey(KeyEvent.VK_S);
        setRightKey(KeyEvent.VK_D);
    }

    public boolean isQuitPressed() {
        return isKeyPressed(quitKey);
    }

    public boolean isReloadPressed() {
        return isKeyPressed(reloadKey);
    }

    public boolean isSprintPressed() {
        return isKeyPressed(sprintKey);
    }

    public boolean isControllerMousePressed() {
        return isMousePressed();
    }
}
