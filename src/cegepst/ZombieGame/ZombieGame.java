package cegepst.ZombieGame;

import cegepst.ZombieGame.Assets.Blockade;
import cegepst.ZombieGame.Assets.Camera;
import cegepst.ZombieGame.Assets.Shop;
import cegepst.ZombieGame.Assets.Sound;
import cegepst.ZombieGame.Enemy.Zombie;
import cegepst.ZombieGame.Items.Ammo;
import cegepst.ZombieGame.Items.Item;
import cegepst.ZombieGame.Items.Money;
import cegepst.ZombieGame.Player.Bullet;
import cegepst.ZombieGame.Player.Player;
import cegepst.ZombieGame.Player.Weapon;
import cegepst.engine.Buffer;
import cegepst.engine.CollidableRepository;
import cegepst.engine.Game;
import cegepst.engine.RenderingEngine;
import cegepst.engine.entities.StaticEntity;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class ZombieGame extends Game {

    private GamePad gamePad;
    private Player player;
    private Camera camera;
    private Round round;
    private Shop shop;
    private ArrayList<Bullet> bullets;
    private ArrayList<Item> items;
    ArrayList<StaticEntity> killedEntities;

    @Override
    public void initialize() {
        gamePad = new GamePad();
        World.getInstance().load();
        player = new Player(gamePad);
        player.teleport(2110, 1280);
        camera = new Camera(player);
        round = new Round();
        round.load(1);
        bullets = new ArrayList<>();
        items = new ArrayList<>();
        shop = new Shop();
        killedEntities = new ArrayList<>();
        RenderingEngine.getInstance().getScreen().changeCursor(Cursor.CROSSHAIR_CURSOR);
    }

    @Override
    public void update() {
        if (gamePad.isQuitPressed()) {
            stop();
        }
        if (gamePad.isCheatPressed()) {
            Weapon.getInstance().createWeapon(30, 500, 50, 10);
            player.setMoney(999);
        }
        if (!player.isDead() && !round.isWon()) {
            shop.update(gamePad, player, round.isPaused());
            player.playerActions(gamePad);
            player.update();
            camera.position(player);
            checkCollisions();
            round.update(player.getX(), player.getY());
            if (gamePad.isControllerMousePressed() && Weapon.getInstance().canFire()) {
                bullets.add(player.fire());
                Sound.play("resources/sounds/weapon.mp3");
            }
        }
    }

    @Override
    public void draw(Buffer buffer) {
        World.getInstance().draw(buffer);
        for (Item item : items) {
            item.draw(buffer);
        }
        player.draw(buffer);
        round.draw(buffer);
        for (Bullet bullet : bullets) {
            bullet.draw(buffer);
        }
        World.getInstance().drawObjects(buffer);
        player.drawUI(buffer);
        shop.draw(buffer, player.getX(), player.getY());
        round.drawUI(buffer, player.getX(), player.getY());
        if (player.isDead()) {
            drawGameOver(buffer);
        }
        if (round.isWon()) {
            drawWin(buffer);
        }
    }

    @Override
    public void conclude() {
    }

    private void checkCollisions() {
        checkBulletsCollisions();
        checkItemsCollisions();
        destroyEntities();
        checkZombiesCollisions();
    }

    private void checkBulletsCollisions() {
        for (Bullet bullet : bullets) {
            bullet.update();
            for (Blockade blockade : World.getInstance().getBorders()) {
                if (bullet.hitBoxIntersectWith(blockade)) {
                    killedEntities.add(bullet);
                }
            }
            for (Zombie zombie : round.getZombies()) {
                if (bullet.hitBoxIntersectWith(zombie)) {
                    zombie.damage(Weapon.getInstance().getDamage());
                    killedEntities.add(bullet);
                }
                if (zombie.getHealth() <= 0) {
                    spawnRandomItem(zombie);
                    killedEntities.add(zombie);
                }
            }
        }
    }

    private void checkItemsCollisions() {
        for (Item item : items) {
            if (player.hitBoxIntersectWith(item)) {
                if (item instanceof Money) {
                    player.addMoney();
                } else {
                    Weapon.getInstance().addAmmo();
                }
                killedEntities.add(item);
            }
        }
    }

    private void destroyEntities() {
        for (StaticEntity entity : killedEntities) {
            if (entity instanceof Bullet) {
                bullets.remove(entity);
            }
            if (entity instanceof Zombie) {
                round.getZombies().remove(entity);
            }
            if (entity instanceof Item) {
                items.remove(entity);
            }
            CollidableRepository.getInstance().unregisterEntity(entity);
        }
    }

    private void checkZombiesCollisions() {
        for (Zombie zombie : round.getZombies()) {
            if (zombie.hitBoxIntersectWith(player)) {
                player.damage(1);
            }
        }
    }

    private void spawnRandomItem(Zombie zombie) {
        Random random = new Random();
        int number = random.nextInt((2 - 1) + 1) + 1;
        if (number == 1) {
            items.add(new Money(zombie.getX(), zombie.getY()));
        } else {
            items.add(new Ammo(zombie.getX(), zombie.getY()));
        }
    }

    private void drawWin(Buffer buffer) {
        buffer.drawRectangle(player.getX() - 400, player.getY() - 300, 800, 600, new Color(0, 0, 0));
        buffer.drawText("You won", player.getX(), player.getY(), Color.orange);
    }

    private void drawGameOver(Buffer buffer) {
        buffer.drawRectangle(player.getX() - 400, player.getY() - 300, 800, 600, new Color(0, 0, 0));
        buffer.drawText("Game Over", player.getX(), player.getY(), Color.red);
    }
}
