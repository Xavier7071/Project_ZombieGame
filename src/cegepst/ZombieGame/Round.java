package cegepst.ZombieGame;

import cegepst.ZombieGame.Enemy.Zombie;
import cegepst.engine.Buffer;

import java.awt.*;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Round {

    private final ArrayList<Zombie> zombies;
    private int currentRound = 1;
    private LocalTime savedTime;
    private boolean isPaused;
    private long remainingSeconds;

    public Round() {
        zombies = new ArrayList<>();
    }

    public void load(int nbZombies) {
        for (int i = 0; i < nbZombies; i++) {
            zombies.add(new Zombie());
        }
    }

    public void update(int playerX, int playerY) {
        if (isPaused) {
            LocalTime currentTime = java.time.LocalTime.now();
            if (currentTime.isAfter(savedTime)) {
                isPaused = false;
                startRound();
            } else {
                remainingSeconds = currentTime.until(savedTime, ChronoUnit.SECONDS);
            }
        } else if (isOver()) {
            savedTime = java.time.LocalTime.now().plusSeconds(15);
            currentRound++;
            isPaused = true;
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

    public void drawUI(Buffer buffer, int x, int y) {
        buffer.drawText(remainingSeconds + " seconds until next round", x - 50, y - 225, Color.orange);
    }

    public ArrayList<Zombie> getZombies() {
        return zombies;
    }

    public boolean isPaused() {
        return isPaused;
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
