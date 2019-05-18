package controller.audiomanager;

import javafx.scene.media.AudioClip;
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
    MENU_MUSIC(Constants.FOLDER_MENU  + "Mystical_Pixels.mp3",MixerType.MUSIC,0.7, AudioClip.INDEFINITE),
    GAME_MUSIC_BASIC(Constants.FOLDER_GAME+"backgroundmusic.mp3",MixerType.AMBIENT,0.4,AudioClip.INDEFINITE),
    FIRE("fire.mp3", MixerType.SFX, 0.05, 1),//todo
    FIREBALL("334234__liamg-sfx__fireball-cast-1.wav", MixerType.SFX, 0.9),
    HURT("SFX_Hurt10.wav", MixerType.SFX, 1),
    PLAYER_DEATH(Constants.FOLDER_PLAYER+"playerdeath.wav",MixerType.SFX,0.6,1),
    PLAYER_POWERUP(Constants.FOLDER_PLAYER+"powerup.wav",MixerType.SFX,0.6,1),
    ENEMY_DEATH("enemydeath.wav",MixerType.SFX,0.15,1),
    BOSS_DEATH(Constants.FOLDER_BOSS+"bossdeath.wav",MixerType.SFX,0.6,1),
    BOSS_MUSIC(Constants.FOLDER_GAME + "bossfight.mp3",MixerType.AMBIENT,0.7, AudioClip.INDEFINITE),
    BOSS_PHASE(Constants.FOLDER_BOSS+"bossphasechange.wav",MixerType.SFX,0.2,1);

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
        private static final String FOLDER_GAME = "game/";
        private static final String FOLDER_PLAYER = "player/";
        private static final String FOLDER_BOSS = "boss/";
    }
}
