package cegepst.engine.controls;

import cegepst.engine.RenderingEngine;

import java.awt.event.*;
import java.util.HashMap;

public abstract class Controller implements KeyListener, MouseListener, MouseMotionListener {

    private final HashMap<Integer, Boolean> pressedKeys;
    private boolean isMousePressed = false;
    private int mouseXPosition, mouseYPosition;

    public Controller() {
        pressedKeys = new HashMap<>();
        RenderingEngine.getInstance().addMouseListener(this);
        RenderingEngine.getInstance().addMouseMotionListener(this);
    }

    protected void bindKeys(int[] keys) {
        for (int keycode : keys) {
            pressedKeys.put(keycode, false);
        }
    }

    protected void bindKey(int key) {
        pressedKeys.put(key, false);
    }

    protected void clearKeys() {
        pressedKeys.clear();
    }

    protected void removeKey(int key) {
        pressedKeys.remove(key);
    }

    protected boolean isKeyPressed(int key) {
        return pressedKeys.containsKey(key) && pressedKeys.get(key);
    }

    public boolean isMousePressed() {
        return isMousePressed;
    }

    public int getMouseXPosition() {
        return mouseXPosition;
    }

    public int getMouseYPosition() {
        return mouseYPosition;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keycode = e.getKeyCode();
        if (pressedKeys.containsKey(keycode)) {
            pressedKeys.put(keycode, true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keycode = e.getKeyCode();
        if (pressedKeys.containsKey(keycode)) {
            pressedKeys.put(keycode, false);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        isMousePressed = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        isMousePressed = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseXPosition = e.getX();
        mouseYPosition = e.getY();
    }
}
