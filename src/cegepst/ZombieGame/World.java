package cegepst.ZombieGame;

import cegepst.ZombieGame.Assets.Blockade;
import cegepst.engine.Buffer;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Objects;

public class World {
    private static World instance;
    private static final String MAP_PATH = "images/final-map.png";
    private static final String OBJECTS_PATH = "images/objects.png";
    private ArrayList<Blockade> borders;
    private Image background;
    private Image objectsLayer;

    public static World getInstance() {
        if (instance == null) {
            instance = new World();
        }
        return instance;
    }

    public void draw(Buffer buffer) {
        buffer.drawImage(background, 0, 0);
        for (Blockade blockade : borders) {
            blockade.draw(buffer);
        }
    }

    public void drawObjects(Buffer buffer) {
        buffer.drawImage(objectsLayer, 0, 0);
    }

    public void load() {
        borders = new ArrayList<>();
        readJson("resources/collisions/worldBorders.json");
        readJson("resources/collisions/objects.json");
        try {
            background = ImageIO.read(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream(MAP_PATH)));
            objectsLayer = ImageIO.read(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream(OBJECTS_PATH)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Blockade> getBorders() {
        return borders;
    }

    private void readJson(String filePath) {
        try {
            Path fileName = Path.of(filePath);
            String jsonString = Files.readString(fileName);
            JSONObject jsonObj = new JSONObject(jsonString);
            JSONArray array = jsonObj.getJSONArray("objects");
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                int x = (int) Math.ceil(obj.getDouble("x")) + 2050;
                int y = (int) Math.ceil(obj.getDouble("y")) + 2550;
                createBlockade(x, y);
            }
        } catch (IOException e) {
            System.err.println("ERREUR");
        }
    }

    private void createBlockade(int x, int y) {
        Blockade blockade = new Blockade();
        blockade.teleport(x, y);
        blockade.setDimension(10, 10);
        borders.add(blockade);
    }
}
