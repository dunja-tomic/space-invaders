import javafx.scene.media.AudioClip;

public class SoundEffect {
    private AudioClip soundEffect;

    public SoundEffect(String filePath) {
        soundEffect = new AudioClip(getClass().getResource(filePath).toExternalForm());
    }

    public void playClip() {
        soundEffect.play();
    }
}