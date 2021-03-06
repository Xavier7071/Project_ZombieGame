package cegepst.ZombieGame.Player;

import cegepst.ZombieGame.Assets.Animations;
import cegepst.ZombieGame.GamePad;
import cegepst.engine.Buffer;
import cegepst.engine.CollidableRepository;
import cegepst.engine.controls.MovementController;
import cegepst.engine.entities.ControllableEntity;

import java.awt.*;

public class Player extends ControllableEntity {

    private final Animations animations;
    private int health = 100;
    private int stamina = 100;
    private int money = 0;
    private int staminaCooldown;

    public Player(MovementController controller) {
        super(controller);
        setDimension(32, 32);
        setSpeed(5);
        animations = new Animations("images/player.png", 8, width, height);
        Weapon.getInstance().createWeapon(10, 50, 25, 20);
        CollidableRepository.getInstance().registerEntity(this);
    }

    @Override
    public void update() {
        super.update();
        moveAccordingToController();
        animations.update(hasMoved());
        Weapon.getInstance().update();
    }

    @Override
    public void draw(Buffer buffer) {
        animations.draw(buffer, mouseDirection(), x, y);
    }

    public void drawUI(Buffer buffer) {
        buffer.drawRectangle(x - 50, y + 275, 100, 15, Color.black);
        buffer.drawRectangle(x - 50, y + 275, health, 15, Color.RED);
        buffer.drawRectangle(x - 50, y + 260, 100, 8, Color.black);
        buffer.drawRectangle(x - 50, y + 260, stamina, 8, Color.ORANGE);
        buffer.drawText("Money " + money + "$", x + 60, y + 267, new Color(34, 139, 34));
        Weapon.getInstance().draw(buffer, x, y);
    }

    public void playerActions(GamePad gamePad) {
        if (gamePad.isReloadPressed()) {
            Weapon.getInstance().reload();
        }
        if (gamePad.isSprintPressed() && stamina > 0) {
            setSpeed(7);
            staminaCooldown = 60;
            stamina--;
        } else {
            setSpeed(5);
        }
        handleStamina();
    }

    public void addMoney() {
        money += 5;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getMoney() {
        return money;
    }

    public void damage(int damage) {
        health -= damage;
    }

    public boolean isDead() {
        return health <= 0;
    }

    public Bullet fire() {
        return Weapon.getInstance().fire(this);
    }

    public void heal() {
        health = 100;
    }

    public boolean canHeal() {
        return health < 100;
    }

    private void handleStamina() {
        staminaCooldown--;
        if (staminaCooldown <= 0) {
            staminaCooldown = 0;
            stamina++;
            if (stamina >= 100) {
                stamina = 100;
            }
        }
    }
}
