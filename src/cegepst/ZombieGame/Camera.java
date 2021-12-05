package cegepst.ZombieGame;

public class Camera {
    private static int x;
    private static int y;

    public Camera(Player player) {
        x = player.getX() - 800 / 2;
        y = player.getY() - 600 / 2;
    }

    public void position(Player player) {
        if (player.hasMoved()) {
            x = player.getX() - 800 / 2;
            y = player.getY() - 600 / 2;
        }
    }

    public static int getX() {
        return x;
    }

    public static int getY() {
        return y;
    }
}