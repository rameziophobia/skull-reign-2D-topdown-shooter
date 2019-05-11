package controller.audiomanager;

import view.Main;

public enum AudioFile {
    FOOTSTEPS(Constants.PATH_RESOURCES_AUDIO_SFX  + "336598__inspectorj__footsteps-concrete-a.wav", MixerType.SFX, 0.85),
    BURST(Constants.PATH_RESOURCES_AUDIO_SFX_PROJECTILES  + "burst.mp3", MixerType.SFX, 0.85),
    S1(Constants.PATH_RESOURCES_AUDIO_SFX_PROJECTILES  + "s1.mp3", MixerType.SFX, 0.85),
    S2(Constants.PATH_RESOURCES_AUDIO_SFX_PROJECTILES  + "s2.mp3", MixerType.SFX, 0.85),
    S3(Constants.PATH_RESOURCES_AUDIO_SFX_PROJECTILES  + "s3.mp3", MixerType.SFX, 0.85),
    MACHINE(Constants.PATH_RESOURCES_AUDIO_SFX_PROJECTILES  + "machine.mp3", MixerType.SFX, 0.85),
    LASER(Constants.PATH_RESOURCES_AUDIO_SFX_PROJECTILES  + "laser.wav", MixerType.SFX, 0.85),
    WOOSH(Constants.PATH_RESOURCES_AUDIO_SFX_PROJECTILES  + "woosh.mp3", MixerType.SFX, 0.85),
    ELECTRIC1(Constants.PATH_RESOURCES_AUDIO_SFX_PROJECTILES  + "electric1.mp3", MixerType.SFX, 0.85);

    private final String path;
    private final MixerType mixer;
    private final double volume;
    private final int cycleCount;

    AudioFile(String path, MixerType mixer) {
        this(path, mixer, 1, 1);
    }

    AudioFile(String path, MixerType mixer, double volume) {
        this(path, mixer, volume, 1);
    }

    AudioFile(String path, MixerType mixer, double volume, int cycleCount) {
        this.path = path;
        this.mixer = mixer;
        this.volume = volume;
        this.cycleCount = cycleCount;
    }

    public String getPath() {
        return path;
    }

    public MixerType getMixer() {
        return mixer;
    }

    public double getVolume() {
        return volume;
    }

    public int getCycleCount() {
        return cycleCount;
    }

    private static class Constants {
        private static final String PATH_RESOURCES_AUDIO = Main.PATH_RESOURCES + "audio/";
        private static final String PATH_RESOURCES_AUDIO_SFX = PATH_RESOURCES_AUDIO + "sfx/";
        private static final String PATH_RESOURCES_AUDIO_SFX_PROJECTILES = PATH_RESOURCES_AUDIO_SFX + "projectiles/";
    }
}
