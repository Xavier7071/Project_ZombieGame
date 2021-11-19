package cegepst.ZombieGame;

public class Camera {
    private static int x;
    private static int y;
    private static Camera instance;

    public static Camera getInstance() {
        if (instance == null) {
            instance = new Camera();
        }
        return instance;
    }

    public Camera() {
        x = -1050;
        y = -500;
    }

    public void position(Player player) {
        if (player.hasMoved()) {
            switch (player.getDirection()) {
                case RIGHT -> x -= player.getSpeed();
                case LEFT -> x += player.getSpeed();
                case UP -> y += player.getSpeed();
                case DOWN -> y -= player.getSpeed();
            }
        }
    }

    public static int getX() {
        return x;
    }

    public static int getY() {
        return y;
    }
}