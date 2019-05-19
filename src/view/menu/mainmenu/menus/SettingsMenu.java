package view.menu.mainmenu.menus;

import controller.SettingsManager;
import controller.audiomanager.AudioFile;
import controller.audiomanager.AudioManager;
import controller.audiomanager.MixerType;
import javafx.scene.control.Label;
import model.ui.menu.Menu;
import model.ui.menu.MenuButtonTransition;
import model.ui.menu.SettingsSlider;
import view.menu.mainmenu.MenuScene;

public class SettingsMenu extends Menu {

    private final SettingsSlider masterVolumeSlider;
    private final SettingsSlider sfxVolumeSlider;
    private final SettingsSlider musicVolumeSlider;
    private final SettingsSlider ambientVolumeSlider;

    public SettingsMenu(MenuScene menuScene) {
        super(menuScene);

        Label lbl_settingsMenu = MenuScene.createMenuTitle("Settings");

        masterVolumeSlider = new SettingsSlider("Master Volume");
        sfxVolumeSlider = new SettingsSlider("SFX Volume");
        ambientVolumeSlider = new SettingsSlider("Ambient Volume");
        musicVolumeSlider = new SettingsSlider("Music Volume");

        resetSliders();

        addNodeAll(
                lbl_settingsMenu,
                masterVolumeSlider,
                sfxVolumeSlider,
                ambientVolumeSlider,
                musicVolumeSlider,
                new MenuButtonTransition("Save", this, Menus.MAIN, () -> {
                    this.applyVolumeSettings();
                    SettingsManager.saveAudiSettings();
                    AudioManager.stopAudio(AudioFile.MENU_MUSIC);
                    AudioManager.playAudio(AudioFile.MENU_MUSIC);
                }),
                new MenuButtonTransition("Back", this, Menus.MAIN, this::resetSliders));
    }

    private void resetSliders() {
        masterVolumeSlider.setValue(AudioManager.getMasterVolume() * 100);
        sfxVolumeSlider.setValue(AudioManager.getMixerVolume(MixerType.SFX) * 100);
        ambientVolumeSlider.setValue(AudioManager.getMixerVolume(MixerType.AMBIENT) * 100);
        musicVolumeSlider.setValue(AudioManager.getMixerVolume(MixerType.MUSIC) * 100);
    }

    private void applyVolumeSettings() {
        AudioManager.setMasterVolume(masterVolumeSlider.getValue() / 100);

        AudioManager.setMixerVolume(MixerType.SFX, sfxVolumeSlider.getValue() / 100);
        AudioManager.setMixerVolume(MixerType.AMBIENT, ambientVolumeSlider.getValue() / 100);
        AudioManager.setMixerVolume(MixerType.MUSIC, musicVolumeSlider.getValue() / 100);
    }
}
