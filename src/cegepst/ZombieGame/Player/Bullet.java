package cegepst.ZombieGame.Player;

import cegepst.ZombieGame.Player.Player;
import cegepst.engine.Buffer;
import cegepst.engine.controls.Direction;
import cegepst.engine.entities.MovableEntity;

import java.awt.*;

public class Bullet extends MovableEntity {

    private final Direction playerDirection;

    public Bullet(Player player) {
        playerDirection = player.mouseDirection();
        setSpeed(9);
        switch (playerDirection) {
            case RIGHT -> {
                teleport(player.getX() + player.getWidth() + 1, player.getY() + 24 - 2);
                setDimension(8, 4);
            }
            case LEFT -> {
                teleport(player.getX() - 9, player.getY() + 24 - 2);
                setDimension(8, 4);
            }
            case UP -> {
                teleport(player.getX() + 15 - 2, player.getY() - 9);
                setDimension(4, 8);
            }
            case DOWN -> {
                teleport(player.getX() + 15 - 2, player.getY() + player.getHeight() + 1);
                setDimension(4, 8);
            }
        }
    }

    @Override
    public void update() {
        move(playerDirection);
    }

    @Override
    public void draw(Buffer buffer) {
        buffer.drawRectangle(x, y, width, height, Color.YELLOW);
    }
}
