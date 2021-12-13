package cegepst.ZombieGame;

import cegepst.engine.Buffer;
import cegepst.engine.entities.StaticEntity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Item extends StaticEntity {
    private Image image;
    private BufferedImage spriteSheet;
    private final String spritePath;

    public Item(int x, int y, int width, int height, String spritePath) {
        this.spritePath = spritePath;
        this.width = width;
        this.height = height;
        loadSpriteSheet();
        loadImage();
        teleport(x, y);
    }

    @Override
    public void draw(Buffer buffer) {
        buffer.drawImage(image, x, y);
    }

    private void loadSpriteSheet() {
        try {
            spriteSheet = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(spritePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadImage() {
        image = spriteSheet.getSubimage(0, 0, width, height);
    }
}
