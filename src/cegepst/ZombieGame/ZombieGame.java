package cegepst.ZombieGame;

import cegepst.engine.Buffer;
import cegepst.engine.CollidableRepository;
import cegepst.engine.Game;
import cegepst.engine.entities.StaticEntity;

import java.awt.*;
import java.util.ArrayList;

public class ZombieGame extends Game {

    private GamePad gamePad;
    private Player player;
    private Camera camera;
    private Round round;
    private ArrayList<Bullet> bullets;

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
    }

    @Override
    public void update() {
        if (gamePad.isQuitPressed()) {
            stop();
        }
        if (!player.isDead() && !round.isWon()) {
            player.playerActions(gamePad);
            player.update();
            camera.position(player);
            checkCollisions();
            round.update(player.getX(), player.getY());
            if (gamePad.isControllerMousePressed() && player.canFire()) {
                bullets.add(player.fire());
            }
        }
    }

    @Override
    public void draw(Buffer buffer) {
        World.getInstance().draw(buffer);
        player.draw(buffer);
        round.draw(buffer);
        for (Bullet bullet : bullets) {
            bullet.draw(buffer);
        }
        World.getInstance().drawObjects(buffer);
        player.drawUI(buffer);
        if (player.isDead()) {
            buffer.drawRectangle(player.getX() - 400, player.getY() - 300, 800, 600, new Color(0, 0, 0));
            buffer.drawText("Game Over", player.getX(), player.getY(), Color.red);
        }
        if (round.isWon()) {
            buffer.drawRectangle(player.getX() - 400, player.getY() - 300, 800, 600, new Color(0, 0, 0));
            buffer.drawText("You won", player.getX(), player.getY(), Color.orange);
        }
    }

    @Override
    public void conclude() {
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
            for (Zombie zombie : round.getZombies()) {
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
                round.getZombies().remove(entity);
            }
            CollidableRepository.getInstance().unregisterEntity(entity);
        }
        for (Zombie zombie : round.getZombies()) {
            if (zombie.hitBoxIntersectWith(player)) {
                player.damage(1);
            }
        }
    }
}
