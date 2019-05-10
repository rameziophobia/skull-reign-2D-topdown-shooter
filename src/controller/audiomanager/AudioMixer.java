package controller.audiomanager;

import javafx.scene.media.AudioClip;

import java.util.HashMap;

class AudioMixer {
    private double mixerVolume;

    private final HashMap<AudioFile, AudioClip> audioClipHashMap = new HashMap<>();

    AudioMixer() {
        this.mixerVolume = 1;
    }

    double getMixerVolume() {
        return mixerVolume;
    }

    void setMixerVolume(double mixerVolume) {
        this.mixerVolume = mixerVolume;
    }

    void addAudio(AudioFile audioFile) {
        final AudioClip audioClip = new AudioClip(audioFile.getPath());
        audioClip.setCycleCount(audioFile.getCycleCount());

        audioClipHashMap.put(audioFile, audioClip);
    }

    void playAudio(AudioFile audioFile, double volume) {
        final AudioClip audioClip = audioClipHashMap.get(audioFile);

        if (!audioClip.isPlaying())
            audioClip.play(volume * mixerVolume * AudioManager.getMasterVolume() * audioFile.getVolume());
    }

    void stopAudio(AudioFile audioFile) {
        final AudioClip audioClip = audioClipHashMap.get(audioFile);

        if (audioClip.isPlaying())
            audioClip.stop();
    }

}
