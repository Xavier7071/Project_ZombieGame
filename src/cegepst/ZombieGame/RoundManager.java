package cegepst.ZombieGame;

import cegepst.engine.Buffer;

import java.util.ArrayList;

public class RoundManager {

    private Round round;
    private int currentRound = 1;

    public RoundManager() {
        startRound();
    }

    public void update(int playerX, int playerY) {
        if (round.isOver()) {
            currentRound++;
            startRound();
        } else {
            round.update(playerX, playerY);
        }
    }

    public void draw(Buffer buffer) {
        round.draw(buffer);
    }

    public ArrayList<Zombie> getZombies() {
        return round.getZombies();
    }

    private void startRound() {
        round = new Round(1 * currentRound);
    }
}
