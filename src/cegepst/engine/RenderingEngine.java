package cegepst.engine;

import cegepst.ZombieGame.Assets.Camera;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

public class RenderingEngine {

    private static RenderingEngine instance;

    private JPanel panel;
    private BufferedImage bufferedImage;
    private Screen screen;

    public static RenderingEngine getInstance() {
        if (instance == null) {
            instance = new RenderingEngine();
        }
        return instance;
    }

    public Buffer getRenderingBuffer() {
        System.setProperty("sun.java2d.opengl", "True");
        bufferedImage = new BufferedImage(4000, 2500, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics;
        graphics = bufferedImage.createGraphics();
        graphics.setRenderingHints(getOptimalRenderingHints());
        return new Buffer(graphics);
    }

    public void renderBufferOnScreen() {
        Graphics2D graphics = (Graphics2D) panel.getGraphics();
        graphics.drawImage(bufferedImage, -Camera.getX(), -Camera.getY(), panel);
        Toolkit.getDefaultToolkit().sync();
        graphics.dispose();
    }

    public Screen getScreen() {
        return screen;
    }

    public void start() {
        screen.start();
    }

    public void stop() {
        screen.end();
    }

    public void addKeyListener(KeyListener listener) {
        panel.addKeyListener(listener);
    }

    public void addMouseListener(MouseListener mouseListener) {
        panel.addMouseListener(mouseListener);
    }

    public void addMouseMotionListener(MouseMotionListener mouseMotionListener) {
        panel.addMouseMotionListener(mouseMotionListener);
    }

    private RenderingEngine() {
        initializeScreen();
        initializePanel();
    }

    private void initializeScreen() {
        screen = new Screen();
        screen.setTitle("Zombie Game");
        screen.setSize(800, 600);
    }

    private void initializePanel() {
        panel = new JPanel();
        panel.setBackground(Color.blue); // Non-n??cessaire
        panel.setFocusable(true);
        panel.setDoubleBuffered(true);
        screen.setPanel(panel);
    }

    private RenderingHints getOptimalRenderingHints() {
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        return rh;
    }
}
