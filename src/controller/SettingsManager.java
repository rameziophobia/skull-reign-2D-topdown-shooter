package controller;

import controller.audiomanager.AudioManager;
import controller.audiomanager.MixerType;
import controller.json.JsonParser;
import controller.json.settings.AudioSettings;

public class SettingsManager {
    private static AudioSettings audioSettings;

    private SettingsManager() {
    }

    public static void init() {
        audioSettings = JsonParser.readAudioSettings();
        if (audioSettings == null) {
            saveAudiSettings();
        }

        AudioManager.setMasterVolume(audioSettings.getMasterVolume());

        AudioManager.setMixerVolume(MixerType.SFX, audioSettings.getSfxVolume());
        AudioManager.setMixerVolume(MixerType.MUSIC, audioSettings.getMusicVolume());
        AudioManager.setMixerVolume(MixerType.AMBIENT, audioSettings.getAmbientVolume());
    }

    public static void saveAudiSettings() {
        audioSettings = new AudioSettings(
                AudioManager.getMasterVolume(),
                AudioManager.getMixerVolume(MixerType.SFX),
                AudioManager.getMixerVolume(MixerType.AMBIENT),
                AudioManager.getMixerVolume(MixerType.MUSIC)
        );
        JsonParser.writeAudioSettings(audioSettings);
    }

    public static AudioSettings getAudioSettings() {
        return audioSettings;
    }
}
