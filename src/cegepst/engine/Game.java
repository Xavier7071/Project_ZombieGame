package cegepst.engine;

import java.awt.event.KeyListener;

public abstract class Game {

    private boolean playing = true;
    private RenderingEngine renderingEngine;

    public abstract void initialize();

    public abstract void update();

    public abstract void draw(Buffer buffer);

    public abstract void conclude();

    public Game() {
        renderingEngine = RenderingEngine.getInstance();
    }

    public final void start() {
        initialize();
        run();
        conclude();
    }

    public final void stop() {
        playing = false;
    }

    @Deprecated
    public void addKeyListener(KeyListener listener) {
        renderingEngine.addKeyListener(listener);
    }

    private void run() {
        renderingEngine.start();
        GameTime.getInstance();
        while (playing) {
            update();
            draw(renderingEngine.getRenderingBuffer());
            renderingEngine.renderBufferOnScreen();
            GameTime.getInstance().synchronize();
        }
        renderingEngine.stop();
    }
}
