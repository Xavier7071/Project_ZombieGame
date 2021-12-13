package cegepst.ZombieGame;

import cegepst.engine.Buffer;
import cegepst.engine.CollidableRepository;
import cegepst.engine.controls.MovementController;
import cegepst.engine.entities.ControllableEntity;

import java.awt.*;

public class Player extends ControllableEntity {

    private final Animations animations;
    private final Weapon weapon;
    private int health = 100;


    public Player(MovementController controller) {
        super(controller);
        setDimension(32, 32);
        setSpeed(5);
        animations = new Animations("images/player.png", 8, width, height);
        weapon = new Weapon(10, 50, 50);
        CollidableRepository.getInstance().registerEntity(this);
    }

    @Override
    public void update() {
        super.update();
        moveAccordingToController();
        animations.update(hasMoved());
        weapon.update();
    }

    @Override
    public void draw(Buffer buffer) {
        animations.draw(buffer, mouseDirection(), x, y);
    }

    public void drawUI(Buffer buffer) {
        buffer.drawRectangle(x - 50, y + 275, 100, 15, Color.black);
        buffer.drawRectangle(x - 50, y + 275, health, 15, Color.RED);
        weapon.draw(buffer, x, y);
    }

    public void damage(int damage) {
        health -= damage;
    }

    public boolean isDead() {
        return health <= 0;
    }

    public void reloadWeapon() {
        weapon.reload();
    }

    public boolean canFire() {
        return weapon.canFire();
    }

    public Bullet fire() {
        return weapon.fire(this);
    }

    public int getDamage() {
        return weapon.getDamage();
    }
}
