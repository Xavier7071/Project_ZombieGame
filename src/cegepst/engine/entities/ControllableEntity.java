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
        if (controller.getMouseYPosition() < 350) {
            return Direction.UP;
        }
        if (controller.getMouseYPosition() > 510) {
            return Direction.DOWN;
        }
        if (controller.getMouseXPosition() > 799) {
            return Direction.RIGHT;
        }
        if (controller.getMouseXPosition() < 735) {
            return Direction.LEFT;
        }
        return Direction.DOWN;
    }
}
