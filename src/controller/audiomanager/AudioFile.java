package controller.audiomanager;

public enum AudioFile {
    FOOTSTEPS(Constants.PATH_RESOURCES_AUDIO_SFX  + "336598__inspectorj__footsteps-concrete-a.wav", MixerType.SFX, 0.85);

    private String path;
    private MixerType mixer;
    private double volume = 1.0;
    private int cycleCount = 1;

    AudioFile(String path, MixerType mixer) {
        this.path = path;
        this.mixer = mixer;
    }

    AudioFile(String path, MixerType mixer, double volume) {
        this.path = path;
        this.mixer = mixer;
        this.volume = volume;
    }

    AudioFile(String path, MixerType mixer, int cycleCount) {
        this.path = path;
        this.mixer = mixer;
        this.cycleCount = cycleCount;
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
        private static final String PATH_RESOURCES_AUDIO = "file:resources/audio/";
        private static final String PATH_RESOURCES_AUDIO_SFX = PATH_RESOURCES_AUDIO + "sfx/";
    }
}
