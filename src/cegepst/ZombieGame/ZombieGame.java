package cegepst.ZombieGame;

import cegepst.engine.Buffer;
import cegepst.engine.Game;

import java.util.ArrayList;

public class ZombieGame extends Game {

    private World world;
    private GamePad gamePad;
    private Player player;
    private ArrayList<Bullet> bullets;

    @Override
    public void initialize() {
        gamePad = new GamePad();
        bullets = new ArrayList<>();
        world = new World();
        world.load();
        player = new Player(gamePad);
        player.teleport(200, 200);
    }

    @Override
    public void update() {
        if (gamePad.isQuitPressed()) {
            stop();
        }
        player.update();
        if (gamePad.isMousePressed()) {
            bullets.add(player.fire());
        }

        for (Bullet bullet: bullets) {
            bullet.update();
        }
    }

    @Override
    public void draw(Buffer buffer) {
        world.draw(buffer);
        player.draw(buffer);
        for (Bullet bullet: bullets) {
            bullet.draw(buffer);
        }
     }

    @Override
    public void conclude() {

    }
}
