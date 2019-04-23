package controller.audiomanager;

import javafx.scene.media.AudioClip;

import java.util.HashMap;

public class AudioMixer {
    private double mixerVolume;

    private HashMap<AudioFile, AudioClip> audioClipHashMap = new HashMap<>();

    public AudioMixer(double mixerVolume) {
        this.mixerVolume = mixerVolume;
    }

    public double getMixerVolume() {
        return mixerVolume;
    }

    public void setMixerVolume(double mixerVolume) {
        this.mixerVolume = mixerVolume;
    }

    public void addAudio(AudioFile audioFile) {
        AudioClip audioClip = new AudioClip(audioFile.getPath());
        audioClip.setCycleCount(audioFile.getCycleCount());

        audioClipHashMap.put(audioFile, audioClip);
    }

    public void playAudio(AudioFile audioFile, double volume) {
        final AudioClip audioClip = audioClipHashMap.get(audioFile);

        if (!audioClip.isPlaying())
            audioClip.play(volume * mixerVolume * AudioManager.getMasterVolume() * audioFile.getVolume());
    }

    public void stopAudio(AudioFile audioFile) {
        final AudioClip audioClip = audioClipHashMap.get(audioFile);

        if (audioClip.isPlaying())
            audioClip.stop();
    }

}
