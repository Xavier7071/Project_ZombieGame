package cegepst.ZombieGame.Player;

import cegepst.ZombieGame.Assets.Sound;
import cegepst.engine.Buffer;

import java.awt.*;

public class Weapon {
    private static Weapon instance;
    private int cooldown = 0;
    private int weaponCooldown;
    private int ammo;
    private int maxAmmo;
    private int mag;
    private int damage;

    public static Weapon getInstance() {
        if (instance == null) {
            instance = new Weapon();
        }
        return instance;
    }

    public void createWeapon(int maxAmmo, int mag, int damage, int weaponCooldown) {
        this.ammo = maxAmmo;
        this.maxAmmo = maxAmmo;
        this.mag = mag;
        this.damage = damage;
        this.weaponCooldown = weaponCooldown;
    }

    public Bullet fire(Player player) {
        cooldown = weaponCooldown;
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
        buffer.drawText("Ammo " + ammo + "/" + mag, x + 60, y + 285, Color.ORANGE);
    }

    public void reload() {
        if (canReload()) {
            Sound.play("resources/sounds/reload.mp3");
            if (ammo + mag >= maxAmmo) {
                mag -= (maxAmmo - ammo);
                ammo = maxAmmo;
            } else {
                ammo += mag;
                mag = 0;
            }
        }
    }

    public int getMagSize() {
        return mag;
    }

    public int getDamage() {
        return damage;
    }

    public void addAmmo() {
        mag += 5;
    }

    private boolean canReload() {
        return mag > 0 && ammo < maxAmmo;
    }
}
