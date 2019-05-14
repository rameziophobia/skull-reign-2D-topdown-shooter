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
                new MenuButtonTransition("New Game", this, Menus.Customization, menuScene::openDoor),
                new MenuButtonTransition("Load Game", this, Menus.LoadGame),
                new MenuButtonTransition("Hall of Fame", this, Menus.HallOfFame),
                new MenuButtonTransition("Settings", this, Menus.Settings),
                new MenuButton("Exit", Main::exit));
    }
}
