package controller.audiomanager;

import java.util.HashMap;

public class AudioManager {
    private static double masterVolume = 1.0;

    private static final HashMap<MixerType, AudioMixer> AUDIO_MIXER_HASH_MAP = new HashMap<>();

    private AudioManager() {
    }

    public static void setMasterVolume(double masterVolume) {
        AudioManager.masterVolume = Math.min(Math.max(masterVolume, 0), 1.0);
    }

    public static double getMasterVolume() {
        return masterVolume;
    }

    public static void init() {
        for (AudioFile audioFile : AudioFile.values()) {

            AUDIO_MIXER_HASH_MAP.putIfAbsent(audioFile.getMixer(), new AudioMixer(1.0));

            AUDIO_MIXER_HASH_MAP.get(audioFile.getMixer()).addAudio(audioFile);
        }
    }

    public static void playAudio(AudioFile audioFile) {
        playAudio(audioFile, 1.0);
    }

    public static void playAudio(AudioFile audioFile, double volume) {
        AUDIO_MIXER_HASH_MAP.get(audioFile.getMixer()).playAudio(audioFile, volume);
    }

    public static void stopAudio(AudioFile audioFile) {
        AUDIO_MIXER_HASH_MAP.get(audioFile.getMixer()).stopAudio(audioFile);
    }

    public static void setMixerVolume(MixerType mixerVolume, double volume) {
        if (AUDIO_MIXER_HASH_MAP.containsKey(mixerVolume)) {
            AUDIO_MIXER_HASH_MAP.get(mixerVolume).setMixerVolume(volume);
        }
    }
    public static double getMixerVolume(MixerType mixerVolume) {
        if (AUDIO_MIXER_HASH_MAP.containsKey(mixerVolume)) {
            return AUDIO_MIXER_HASH_MAP.get(mixerVolume).getMixerVolume();
        }
        return 1;
    }
}

