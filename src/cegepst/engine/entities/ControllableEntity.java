package cegepst.engine.entities;


import cegepst.engine.controls.Direction;
import cegepst.engine.controls.MovementController;

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
        if (controller.getMouseYPosition() < 400) {
            return Direction.UP;
        }
        if (controller.getMouseYPosition() > 580) {
            return Direction.DOWN;
        }
        if (controller.getMouseXPosition() > 800) {
            return Direction.RIGHT;
        }
        if (controller.getMouseXPosition() < 700) {
            return Direction.LEFT;
        }
        return Direction.DOWN;
    }
}
