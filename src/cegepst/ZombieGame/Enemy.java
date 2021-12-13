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
        setSpeed(7);
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
        usePathFinding = false;
        if (usePathFindingCooldown > 0) {
            findPath(lastDirection);
        } else {
            for (Blockade blockade : World.getInstance().getBorders()) {
                if (hitBoxIntersectWith(blockade)) {
                    usePathFindingCooldown = 10;
                    usePathFinding = true;
                    lastDirection = getDirection();
                    findPath(getDirection());
                    break;
                }
            }
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

    private void findPath(Direction direction) {
        if (direction == Direction.LEFT || direction == Direction.RIGHT) {
            moveDown();
            moveUp();
        } else {
            moveLeft();
        }
    }

    private void moveToPlayer(int playerX, int playerY) {
        if (playerX > x) {
            moveRight();
        } else if (playerX + 10 < x) {
            moveLeft();
        }
        if (playerY > y) {
            moveDown();
        } else if (playerY + 10 < y) {
            moveUp();
        }
    }
}
