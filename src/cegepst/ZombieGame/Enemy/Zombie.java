package cegepst.ZombieGame.Enemy;

import cegepst.ZombieGame.Animations;

public class Zombie extends Enemy {

    public Zombie() {
        super();
        health = 100;
        setDimension(32, 32);
        animations = new Animations("images/zombie.png", 8, width, height);
    }
}
