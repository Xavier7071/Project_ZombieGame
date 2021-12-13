package cegepst.ZombieGame.Assets;

import cegepst.engine.Buffer;
import cegepst.engine.controls.Direction;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Animations {

    private final String spritePath;
    private BufferedImage spriteSheet;
    private Image[] rightFrames;
    private Image[] leftFrames;
    private Image[] upFrames;
    private Image[] downFrames;
    private int currentAnimationFrame = 1;
    private final int animationSpeed;
    private int nextFrame;
    private final int width;
    private final int height;

    public Animations(String spritePath, int animationSpeed, int width, int height) {
        this.spritePath = spritePath;
        this.animationSpeed = animationSpeed;
        nextFrame = animationSpeed;
        this.width = width;
        this.height = height;
        loadSpriteSheet();
        loadAnimationFrames();
    }

    public void update(boolean hasMoved) {
        if (hasMoved) {
            --nextFrame;
            if (nextFrame == 0) {
                ++currentAnimationFrame;
                if (currentAnimationFrame >= leftFrames.length) {
                    currentAnimationFrame = 0;
                }
                nextFrame = animationSpeed;
            }
        } else {
            currentAnimationFrame = 1;
        }
    }

    public void draw(Buffer buffer, Direction direction, int x, int y) {
        switch (direction) {
            case RIGHT -> buffer.drawImage(rightFrames[currentAnimationFrame], x, y);
            case LEFT -> buffer.drawImage(leftFrames[currentAnimationFrame], x, y);
            case UP -> buffer.drawImage(upFrames[currentAnimationFrame], x, y);
            case DOWN -> buffer.drawImage(downFrames[currentAnimationFrame], x, y);
        }
    }

    private void loadSpriteSheet() {
        try {
            spriteSheet = ImageIO.read(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream(spritePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadAnimationFrames() {
        downFrames = new Image[3];
        leftFrames = new Image[3];
        rightFrames = new Image[3];
        upFrames = new Image[3];
        for (int i = 0; i < 3; i++) {
            downFrames[i] = spriteSheet.getSubimage((i * 32), 0, width, height);
            leftFrames[i] = spriteSheet.getSubimage((i * 32), 32, width, height);
            rightFrames[i] = spriteSheet.getSubimage((i * 32), 64, width, height);
            upFrames[i] = spriteSheet.getSubimage((i * 32), 96, width, height);
        }
    }
}
