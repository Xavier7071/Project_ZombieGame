package cegepst.ZombieGame;

import cegepst.engine.Buffer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class World {

    private static final String MAP_PATH = "images/map-test.png";
    private Image background;
    private ArrayList<Blockade> worldBorders;

    public World() {
        worldBorders = new ArrayList<>();
        Blockade topBorder = new Blockade();
        topBorder.teleport(0, 0);
        topBorder.setDimension(400, 48);
        Blockade topBorder2 = new Blockade();
        topBorder2.teleport(464, 0);
        topBorder2.setDimension(400, 48);
        worldBorders.add(topBorder);
        worldBorders.add(topBorder2);
    }

    public void draw(Buffer buffer) {
        buffer.drawImage(background, 0, 0);
        for (Blockade blockade : worldBorders) {
            blockade.draw(buffer);
        }
    }

    public void load() {
        try {
            background = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(MAP_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Blockade> getWorldBorders() {
        return worldBorders;
    }
}
