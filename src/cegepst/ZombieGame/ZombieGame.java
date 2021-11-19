package cegepst.ZombieGame;

import cegepst.engine.Buffer;
import cegepst.engine.CollidableRepository;
import cegepst.engine.Game;
import cegepst.engine.entities.StaticEntity;

import java.util.ArrayList;

public class ZombieGame extends Game {

    private World world;
    private GamePad gamePad;
    private Player player;
    private ArrayList<Bullet> bullets;
    private Camera camera;

    @Override
    public void initialize() {
        camera = new Camera();
        gamePad = new GamePad();
        bullets = new ArrayList<>();
        world = new World();
        world.load();
        player = new Player(gamePad);
        player.teleport(1435, 800);
    }

    @Override
    public void update() {
        if (gamePad.isQuitPressed()) {
            stop();
        }
        camera.position(player);
        player.update();
        if (gamePad.isControllerMousePressed() && player.canFire()) {
            bullets.add(player.fire());
        }
        ArrayList<StaticEntity> killedEntities = new ArrayList<>();

        for (Bullet bullet : bullets) {
            bullet.update();
            for (Blockade blockade : world.getWorldBorders()) {
                if (bullet.hitBoxIntersectWith(blockade)) {
                    killedEntities.add(bullet);
                }
            }
        }

        for (StaticEntity entity : killedEntities) {
            if (entity instanceof Bullet) {
                bullets.remove(entity);
            }
            CollidableRepository.getInstance().unregisterEntity(entity);
        }
    }

    @Override
    public void draw(Buffer buffer) {
        world.draw(buffer);
        player.draw(buffer);
        for (Bullet bullet : bullets) {
            bullet.draw(buffer);
        }
    }

    @Override
    public void conclude() {

    }
}
