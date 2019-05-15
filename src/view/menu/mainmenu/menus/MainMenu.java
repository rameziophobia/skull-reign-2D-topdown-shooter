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
                new MenuButtonTransition("New Game", this, Menus.CUSTOMIZATION, menuScene::openDoor),
                new MenuButtonTransition("Load Game", this, Menus.LOAD_GAME),
                new MenuButtonTransition("Hall of Fame", this, Menus.HALL_OF_FAME),
                new MenuButtonTransition("Settings", this, Menus.SETTINGS),
                new MenuButton("Exit", Main::exit));
    }
}
