package cegepst.ZombieGame;

import cegepst.engine.Buffer;

import java.util.ArrayList;

public class Round {

    private final ArrayList<Zombie> zombies;

    public Round(int nbZombies) {
        zombies = new ArrayList<>();
        for (int i = 0; i < nbZombies; i++) {
            zombies.add(new Zombie());
        }
    }

    public void update(int playerX, int playerY) {
        for (Zombie zombie : zombies) {
            zombie.update(playerX, playerY);
        }
    }

    public void draw(Buffer buffer) {
        for (Zombie zombie : zombies) {
            zombie.draw(buffer);
        }
    }

    public ArrayList<Zombie> getZombies() {
        return zombies;
    }

    public boolean isOver() {
        return zombies.size() <= 0;
    }
}
