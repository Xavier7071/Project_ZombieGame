package cegepst.engine.entities;


import cegepst.engine.controls.Direction;
import cegepst.engine.controls.MovementController;

import javax.swing.*;
import java.awt.*;

public abstract class ControllableEntity extends MovableEntity {

    private final MovementController controller;

    public ControllableEntity(MovementController controller) {
        this.controller = controller;
    }

    public void moveAccordingToController() {
        if (!controller.isMoving()) {
            return;
        }
        if (controller.isUpPressed()) {
            moveUp();
        } else if (controller.isDownPressed()) {
            moveDown();
        } else if (controller.isLeftPressed()) {
            moveLeft();
        } else if (controller.isRightPressed()) {
            moveRight();
        }
    }

    public Direction mouseDirection() {
        if (controller.getMouseYPosition() < 485) {
            return Direction.UP;
        }
        if (controller.getMouseYPosition() > 580) {
            return Direction.DOWN;
        }
        if (controller.getMouseXPosition() > 1000) {
            return Direction.RIGHT;
        }
        if (controller.getMouseXPosition() < 900) {
            return Direction.LEFT;
        }
        return Direction.DOWN;
    }
}
