package cegepst.ZombieGame;

import cegepst.engine.Buffer;

import java.util.ArrayList;

public class Round {

    private final ArrayList<Zombie> zombies;
    private int currentRound = 1;

    public Round() {
        zombies = new ArrayList<>();
    }

    public void load(int nbZombies) {
        for (int i = 0; i < nbZombies; i++) {
            zombies.add(new Zombie());
        }
    }

    public void update(int playerX, int playerY) {
        if (isOver()) {
            currentRound++;
            startRound();
        } else {
            for (Zombie zombie : zombies) {
                zombie.update(playerX, playerY);
            }
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

    public boolean isWon() {
        return currentRound > 10;
    }

    private boolean isOver() {
        return zombies.size() <= 0;
    }

    private void startRound() {
        load(currentRound);
    }
}
