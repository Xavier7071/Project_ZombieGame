package cegepst.ZombieGame;

import cegepst.engine.Buffer;
import cegepst.engine.CollidableRepository;
import cegepst.engine.controls.Direction;
import cegepst.engine.entities.MovableEntity;

public abstract class Enemy extends MovableEntity {

    protected Animations animations;
    protected int health;
    protected boolean usePathFinding;
    protected int usePathFindingCooldown;
    protected Direction lastDirection;

    public Enemy() {
        setDimension(32, 32);
        setSpeed(6);
        teleport(2110, 1180);
        CollidableRepository.getInstance().registerEntity(this);
    }

    public void damage(int damage) {
        health -= damage;
    }

    public int getHealth() {
        return health;
    }

    public void update(int playerX, int playerY) {
        super.update();
        if (usePathFindingCooldown > 0) {
            findPath(lastDirection);
        } else {
            usePathFinding = checkCollisions();
            if (!usePathFinding) {
                moveToPlayer(playerX, playerY);
            }
        }
        usePathFindingCooldown--;
        if (usePathFindingCooldown <= 0) {
            usePathFindingCooldown = 0;
        }
        animations.update(hasMoved());
    }

    @Override
    public void draw(Buffer buffer) {
        animations.draw(buffer, getDirection(), x, y);
    }

    private boolean checkCollisions() {
        for (Blockade blockade : World.getInstance().getBorders()) {
            if (hitBoxIntersectWith(blockade)) {
                usePathFindingCooldown = 10;
                lastDirection = getDirection();
                findPath(getDirection());
                return true;
            }
        }
        return false;
    }

    private void findPath(Direction direction) {
        if (direction == Direction.LEFT || direction == Direction.RIGHT) {
            moveDown();
        } else {
            moveLeft();
        }
    }

    private void moveToPlayer(int playerX, int playerY) {
        boolean movesX = false;
        setSpeed(6);
        if (playerX > x) {
            movesX = true;
            moveRight();
        } else if (playerX + 10 < x) {
            movesX = true;
            moveLeft();
        }
        if (movesX) {
            setSpeed(3);
        }
        if (playerY > y) {
            moveDown();
        } else if (playerY + 10 < y) {
            moveUp();
        }
    }
}
