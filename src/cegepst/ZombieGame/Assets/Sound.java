package cegepst.ZombieGame.Assets;

import javazoom.jl.player.Player;

import java.io.FileInputStream;

public class Sound {

    public static synchronized void play(String filePath) {
        new Thread(() -> {
            try {
                FileInputStream fileInputStream = new FileInputStream(filePath);
                Player playMP3 = new Player(fileInputStream);
                playMP3.play();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
