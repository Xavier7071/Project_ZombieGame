package cegepst.ZombieGame;

import cegepst.ZombieGame.Player.Player;
import cegepst.engine.Buffer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Shop {
    private Image shopImage;
    private Image potionImage;
    private Image weaponImage;
    private BufferedImage shopSpriteSheet;
    private BufferedImage potionSpriteSheet;
    private BufferedImage weaponSpriteSheet;
    private final String shopPath = "images/shop.png";
    private final String potionPath = "images/health.png";
    private final String weaponPath = "images/weapon.png";

    public Shop() {
        loadSpriteSheets();
        loadImages();
    }

    public void draw(Buffer buffer, int x, int y) {
        buffer.drawImage(shopImage, x - 200, y - 200);
        buffer.drawImage(potionImage, x - 150, y - 150);
        buffer.drawText("Health potion", x - 155, y - 65, Color.black);
        buffer.drawText("15$", x - 130, y - 35, Color.black);
        buffer.drawText("Press 1 to purchase", x - 170, y - 5, Color.black);
        buffer.drawImage(weaponImage, x + 85, y - 130);
        buffer.drawText("MP5", x + 105, y - 65, Color.black);
        buffer.drawText("30$", x + 105, y - 35, Color.black);
        buffer.drawText("Press 2 to purchase", x + 70, y - 5, Color.black);
        //ship va etre ouvert
    }

    public void update(GamePad gamePad, Player player) {

        //checker les touches pour get les items
    }

    private void loadSpriteSheets() {
        try {
            shopSpriteSheet = ImageIO.read(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream(shopPath)));
            potionSpriteSheet = ImageIO.read(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream(potionPath)));
            weaponSpriteSheet = ImageIO.read(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream(weaponPath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadImages() {
        shopImage = shopSpriteSheet.getSubimage(0, 0, 399, 400);
        potionImage = potionSpriteSheet.getSubimage(0, 0, 60, 68);
        weaponImage = weaponSpriteSheet.getSubimage(0, 0, 80, 42);
    }
}
