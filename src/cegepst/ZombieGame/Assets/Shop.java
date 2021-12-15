package cegepst.ZombieGame.Assets;

import cegepst.ZombieGame.GamePad;
import cegepst.ZombieGame.Player.Player;
import cegepst.ZombieGame.Player.Weapon;
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
    private boolean weaponIsBought;
    private boolean shopIsOpened;
    private int shopCooldown;

    public Shop() {
        loadSpriteSheets();
        loadImages();
    }

    public void draw(Buffer buffer, int x, int y) {
        if (shopIsOpened) {
            buffer.drawImage(shopImage, x - 200, y - 200);
            if (!weaponIsBought) {
                drawPotion(buffer, x, y);
                drawWeapon(buffer, x, y);
            } else {
                drawPotionInMiddle(buffer, x, y);
            }
        }
    }

    public void update(GamePad gamePad, Player player, boolean roundPaused) {
        if (roundPaused && gamePad.isShopPressed()) {
            open();
        }
        if (!roundPaused) {
            close();
        }
        reduceCooldown();
        if (shopIsOpened) {
            if (gamePad.weaponIsBought() && player.getMoney() >= 20) {
                buyWeapon(player);
            }
            if (gamePad.healthPotionIsBought() && player.getMoney() >= 10 && player.canHeal()) {
                buyPotion(player);
            }
        }
    }

    private void open() {
        if (shopCooldown <= 0) {
            shopCooldown = 10;
            shopIsOpened = !shopIsOpened;
        }
    }

    private void close() {
        shopIsOpened = false;
    }

    private void loadSpriteSheets() {
        try {
            shopSpriteSheet = ImageIO.read(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("images/shop.png")));
            potionSpriteSheet = ImageIO.read(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("images/health.png")));
            weaponSpriteSheet = ImageIO.read(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("images/weapon.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadImages() {
        shopImage = shopSpriteSheet.getSubimage(0, 0, 399, 400);
        potionImage = potionSpriteSheet.getSubimage(0, 0, 60, 68);
        weaponImage = weaponSpriteSheet.getSubimage(0, 0, 80, 42);
    }

    private void drawPotion(Buffer buffer, int x, int y) {
        buffer.drawImage(potionImage, x - 150, y - 150);
        buffer.drawText("Health potion", x - 155, y - 65, Color.black);
        buffer.drawText("10$", x - 130, y - 35, Color.black);
        buffer.drawText("Press 1 to purchase", x - 170, y - 5, Color.black);
    }

    private void drawWeapon(Buffer buffer, int x, int y) {
        buffer.drawImage(weaponImage, x + 85, y - 130);
        buffer.drawText("MP5", x + 105, y - 65, Color.black);
        buffer.drawText("20$", x + 105, y - 35, Color.black);
        buffer.drawText("Press 2 to purchase", x + 70, y - 5, Color.black);
    }

    private void drawPotionInMiddle(Buffer buffer, int x, int y) {
        buffer.drawImage(potionImage, x - 25, y - 150);
        buffer.drawText("Health potion", x - 30, y - 65, Color.black);
        buffer.drawText("10$", x - 5, y - 35, Color.black);
        buffer.drawText("Press 1 to purchase", x - 50, y - 5, Color.black);
    }

    private void buyWeapon(Player player) {
        Weapon.getInstance().createWeapon(30, Weapon.getInstance().getMagSize(), 25, 10);
        player.setMoney(player.getMoney() - 20);
        weaponIsBought = true;
    }

    private void buyPotion(Player player) {
        player.heal();
        player.setMoney(player.getMoney() - 10);
    }

    private void reduceCooldown() {
        shopCooldown--;
        if (shopCooldown <= 0) {
            shopCooldown = 0;
        }
    }
}
