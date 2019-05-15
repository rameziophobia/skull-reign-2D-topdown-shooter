package controller.audiomanager;

import view.Main;

public enum AudioFile {
    FOOTSTEPS("336598__inspectorj__footsteps-concrete-a.wav", MixerType.SFX, 0.85),
    BURST(Constants.FOLDER_PROJECTILE + "burst.mp3", MixerType.SFX, 0.85),
    S1(Constants.FOLDER_PROJECTILE + "s1.mp3", MixerType.SFX, 0.85),
    S2(Constants.FOLDER_PROJECTILE + "s2.mp3", MixerType.SFX, 0.85),
    S3(Constants.FOLDER_PROJECTILE + "s3.mp3", MixerType.SFX, 0.85),
    MACHINE(Constants.FOLDER_PROJECTILE + "machine.mp3", MixerType.SFX, 0.85),
    LASER(Constants.FOLDER_PROJECTILE + "laser.wav", MixerType.SFX, 0.85),
    WOOSH(Constants.FOLDER_PROJECTILE + "woosh.mp3", MixerType.SFX, 0.85),
    ELECTRIC1(Constants.FOLDER_PROJECTILE + "electric1.mp3", MixerType.SFX, 0.85),
    BUTTON_CLICK(Constants.FOLDER_MENU  + "tap-crisp.aif", MixerType.SFX, 0.7, 1),
    FIRE("fire.mp3", MixerType.SFX, 0.05, 1),//todo
    FIREBALL("334234__liamg-sfx__fireball-cast-1.wav", MixerType.SFX, 0.9),
    HURT("SFX_Hurt10.wav", MixerType.SFX, 1);

    private final String path;
    private final MixerType mixer;
    private final double volume;
    private final int cycleCount;

    AudioFile(String name, MixerType mixer) {
        this(name, mixer, 1, 1);
    }

    AudioFile(String name, MixerType mixer, double volume) {
        this(name, mixer, volume, 1);
    }

    AudioFile(String name, MixerType mixer, double volume, int cycleCount) {
        final String path;
        switch (mixer) {
            case SFX:
                path = Constants.PATH_RESOURCES_AUDIO_SFX;
                break;
            case MUSIC:
                path = Constants.PATH_RESOURCES_AUDIO_MUSIC;
                break;
            case AMBIENT:
                path = Constants.PATH_RESOURCES_AUDIO_AMBIENT;
                break;
            default:
                throw new UnsupportedOperationException();
        }
        this.path = path + name;
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
        private static final String PATH_RESOURCES_AUDIO_MUSIC = PATH_RESOURCES_AUDIO + "music/";
        private static final String PATH_RESOURCES_AUDIO_AMBIENT = PATH_RESOURCES_AUDIO + "ambient/";

        private static final String FOLDER_PROJECTILE = "projectiles/";
        private static final String FOLDER_MENU = "menu/";
    }
}