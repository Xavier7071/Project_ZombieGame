package cegepst.ZombieGame;

import cegepst.engine.RenderingEngine;
import cegepst.engine.controls.MovementController;

import java.awt.event.KeyEvent;

public class GamePad extends MovementController {

    private final int quitKey = KeyEvent.VK_ESCAPE;
    private final int reloadKey = KeyEvent.VK_R;
    private final int sprintKey = KeyEvent.VK_SHIFT;
    private final int shopKey = KeyEvent.VK_I;
    private final int healthShopKey = KeyEvent.VK_1;
    private final int weaponShopKey = KeyEvent.VK_2;
    private final int cheatKey = KeyEvent.VK_L;

    public GamePad() {
        bindKey(quitKey);
        bindKey(reloadKey);
        bindKey(sprintKey);
        bindKey(shopKey);
        bindKey(healthShopKey);
        bindKey(weaponShopKey);
        bindKey(cheatKey);
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

    public boolean isShopPressed() {
        return isKeyPressed(shopKey);
    }

    public boolean isControllerMousePressed() {
        return isMousePressed();
    }

    public boolean healthPotionIsBought() {
        return isKeyPressed(healthShopKey);
    }

    public boolean weaponIsBought() {
        return isKeyPressed(weaponShopKey);
    }

    public boolean isCheatPressed() {
        return isKeyPressed(cheatKey);
    }
}
