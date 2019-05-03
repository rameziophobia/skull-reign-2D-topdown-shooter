package view.menu.mainmenu.menus;

import javafx.scene.control.Label;
import model.ui.menu.Menu;
import model.ui.menu.MenuButton;
import model.ui.menu.MenuButtonTransition;
import view.Main;
import view.menu.mainmenu.MenuScene;

public class MainMenu extends Menu {
    public MainMenu(MenuScene menuScene) {
        super(menuScene);

        Label lbl_mainMenu = MenuScene.createMenuTitle("Main Menu");

        addNodeAll(
                lbl_mainMenu,
                new MenuButtonTransition("New Game", MenuScene.BUTTON_SCALE, this, "NewGame"), //todo scale is repeated
                new MenuButtonTransition("Load Game", MenuScene.BUTTON_SCALE, this, "LoadGame"),
                new MenuButtonTransition("Hall of Fame", MenuScene.BUTTON_SCALE, this, "HallOfFame"),
                new MenuButtonTransition("Settings", MenuScene.BUTTON_SCALE, this, "Settings"),
                new MenuButton("Exit", MenuScene.BUTTON_SCALE, () -> Main.getStage().close()));
    }
}
