package cegepst.ZombieGame;

import javazoom.jl.player.Player;
import java.io.FileInputStream;

public class Sound {

    public static synchronized void play(String filePath) {
        new Thread(() -> {
            try {

                FileInputStream fis = new FileInputStream(filePath);
                Player playMP3 = new Player(fis);

                playMP3.play();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
