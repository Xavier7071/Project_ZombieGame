package cegepst.ZombieGame;

public class Zombie extends Enemy {

    public Zombie() {
        super();
        health = 100;
        setDimension(32, 32);
        setSpeed(5);
        animations = new Animations("images/player.png", 8, width, height);
    }
}
