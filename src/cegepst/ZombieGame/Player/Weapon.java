package cegepst.ZombieGame.Player;

import cegepst.engine.Buffer;

import java.awt.*;

public class Weapon {
    private int cooldown = 0;
    private int ammo;
    private int mag;
    private final int damage;

    public Weapon(int ammo, int mag, int damage) {
        this.ammo = ammo;
        this.mag = mag;
        this.damage = damage;
    }

    public Bullet fire(Player player) {
        cooldown = 30;
        ammo--;
        return new Bullet(player);
    }

    public boolean canFire() {
        return cooldown == 0 && ammo > 0;
    }

    public void update() {
        cooldown--;
        if (cooldown <= 0) {
            cooldown = 0;
        }
        if (ammo <= 0) {
            ammo = 0;
        }
    }

    public void draw(Buffer buffer, int x, int y) {
        buffer.drawText("Ammos " + ammo + "/" + mag, x + 60, y + 285, Color.ORANGE);
    }

    public void reload() {
        if (canReload()) {
            if (ammo + mag >= 10) {
                mag -= (10 - ammo);
                ammo = 10;
            } else {
                ammo += mag;
                mag = 0;
            }
        }
    }

    public int getDamage() {
        return damage;
    }

    public void addAmmo() {
        mag += 5;
    }

    private boolean canReload() {
        return mag > 0 && ammo < 10;
    }

}
