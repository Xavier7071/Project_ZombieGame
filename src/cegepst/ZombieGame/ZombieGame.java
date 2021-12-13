package cegepst.ZombieGame;

import cegepst.engine.Buffer;
import cegepst.engine.CollidableRepository;
import cegepst.engine.Game;
import cegepst.engine.entities.StaticEntity;

import java.util.ArrayList;

public class ZombieGame extends Game {

    private GamePad gamePad;
    private Player player;
    private Camera camera;
    private RoundManager roundManager;
    private ArrayList<Bullet> bullets;

    @Override
    public void initialize() {
        gamePad = new GamePad();
        World.getInstance().load();
        player = new Player(gamePad);
        player.teleport(2110, 1280);
        camera = new Camera(player);
        roundManager = new RoundManager();
        bullets = new ArrayList<>();
    }

    @Override
    public void update() {
        if (gamePad.isQuitPressed()) {
            stop();
        }
        if (gamePad.isReloadPressed()) {
            player.reloadWeapon();
        }
        player.update();
        camera.position(player);
        checkCollisions();
        roundManager.update(player.getX(), player.getY());
        if (gamePad.isControllerMousePressed() && player.canFire()) {
            bullets.add(player.fire());
        }
        if (player.isDead()) {
            conclude();
        }
    }

    @Override
    public void draw(Buffer buffer) {
        World.getInstance().draw(buffer);
        player.draw(buffer);
        roundManager.draw(buffer);
        for (Bullet bullet : bullets) {
            bullet.draw(buffer);
        }
        World.getInstance().drawObjects(buffer);
        player.drawUI(buffer);
    }

    @Override
    public void conclude() {
        stop();
    }

    private void checkCollisions() {
        ArrayList<StaticEntity> killedEntities = new ArrayList<>();

        for (Bullet bullet : bullets) {
            bullet.update();
            for (Blockade blockade : World.getInstance().getBorders()) {
                if (bullet.hitBoxIntersectWith(blockade)) {
                    killedEntities.add(bullet);
                }
            }
            for (Zombie zombie : roundManager.getZombies()) {
                if (bullet.hitBoxIntersectWith(zombie)) {
                    zombie.damage(player.getDamage());
                    killedEntities.add(bullet);
                }
                if (zombie.getHealth() <= 0) {
                    killedEntities.add(zombie);
                }
            }
        }

        for (StaticEntity entity : killedEntities) {
            if (entity instanceof Bullet) {
                bullets.remove(entity);
            }
            if (entity instanceof Zombie) {
                roundManager.getZombies().remove(entity);
            }
            CollidableRepository.getInstance().unregisterEntity(entity);
        }
        for (Zombie zombie : roundManager.getZombies()) {
            if (player.hitBoxIntersectWith(zombie)) {
                player.damage(5);
            }
        }
    }
}
