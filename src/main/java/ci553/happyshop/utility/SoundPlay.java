package ci553.happyshop.utility;

import javafx.scene.media.AudioClip;

import java.net.URL;

public class SoundPlay {
    private AudioClip clip;

    public SoundPlay() {
        URL url = getClass().getResource("/audio/backgroundSound.wav");
        System.out.println("Audio URL: " + url);

        if (url == null) {
            System.out.println("Audio file NOT found: /audio/backgroundSound.wav");
            return;
        }

        try {
            clip = new AudioClip(url.toExternalForm());
            clip.setVolume(0.2);
            clip.setCycleCount(AudioClip.INDEFINITE);
            clip.play();
            System.out.println("Background audio started (AudioClip)");
        } catch (Exception ex) {
            System.out.println("Background audio failed (AudioClip).");
            ex.printStackTrace();
        }
    }

    public void stop() {
        if (clip != null) clip.stop();
    }
}



