package controller.json.settings;

import com.google.gson.annotations.Expose;

public class AudioSettings {
    @Expose
    private double masterVolume;
    @Expose
    private double sfxVolume;
    @Expose
    private double ambientVolume;
    @Expose
    private double musicVolume;

    public AudioSettings(double masterVolume, double sfxVolume, double ambientVolume, double musicVolume) {
        this.masterVolume = masterVolume;
        this.sfxVolume = sfxVolume;
        this.ambientVolume = ambientVolume;
        this.musicVolume = musicVolume;
    }

    public double getMasterVolume() {
        return masterVolume;
    }

    public double getSfxVolume() {
        return sfxVolume;
    }

    public double getAmbientVolume() {
        return ambientVolume;
    }

    public double getMusicVolume() {
        return musicVolume;
    }
}
