package view.menu.mainmenu.menus;

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

        masterVolumeSlider = new SettingsSlider("Master Volume", AudioManager.getMasterVolume() * 100);

        sfxVolumeSlider = new SettingsSlider("SFX Volume", AudioManager.getMixerVolume(MixerType.SFX) * 100);

        musicVolumeSlider = new SettingsSlider("Music Volume", AudioManager.getMixerVolume(MixerType.MUSIC) * 100);

        ambientVolumeSlider = new SettingsSlider("Ambient Volume", AudioManager.getMixerVolume(MixerType.AMBIENT) * 100);

        addNodeAll(
                lbl_settingsMenu,
                masterVolumeSlider,
                sfxVolumeSlider,
                musicVolumeSlider,
                ambientVolumeSlider,
                new MenuButtonTransition("Save", this, Menus.Main, this::saveVolumeSettings),
                new MenuButtonTransition("Back", this, Menus.Main));
    }

    private void saveVolumeSettings() {
        AudioManager.setMasterVolume(masterVolumeSlider.getValue() / 100);

        AudioManager.setMixerVolume(MixerType.SFX, sfxVolumeSlider.getValue() / 100);
        AudioManager.setMixerVolume(MixerType.MUSIC, musicVolumeSlider.getValue() / 100);
        AudioManager.setMixerVolume(MixerType.AMBIENT, ambientVolumeSlider.getValue() / 100);

        //todo save settings
    }
}
