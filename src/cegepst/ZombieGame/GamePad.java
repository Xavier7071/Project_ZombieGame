package cegepst.ZombieGame;

import cegepst.engine.RenderingEngine;
import cegepst.engine.controls.MovementController;

import java.awt.event.KeyEvent;

public class GamePad extends MovementController {

    private final int quitKey = KeyEvent.VK_Q;

    public GamePad() {
        bindKey(quitKey);
        RenderingEngine.getInstance().addKeyListener(this);
    }

    public boolean isQuitPressed() {
        return isKeyPressed(quitKey);
    }
}
