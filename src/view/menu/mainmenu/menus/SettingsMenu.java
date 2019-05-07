package view.menu.mainmenu.menus;

import javafx.scene.control.Label;
import model.ui.menu.Menu;
import model.ui.menu.MenuButton;
import model.ui.menu.MenuButtonTransition;
import view.menu.mainmenu.MenuScene;

public class SettingsMenu extends Menu {
    public SettingsMenu(MenuScene menuScene) {
        super(menuScene);

        Label lbl_settingsMenu = MenuScene.createMenuTitle("Settings");

        addNodeAll(
                lbl_settingsMenu,
                new MenuButton("Save"),
                new MenuButtonTransition("Back", this, Menus.Main));
    }
}
