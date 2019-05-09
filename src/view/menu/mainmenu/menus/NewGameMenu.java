package view.menu.mainmenu.menus;

import javafx.scene.control.Label;
import model.ui.menu.Menu;
import model.ui.menu.MenuButton;
import model.ui.menu.MenuButtonTransition;
import view.menu.mainmenu.MenuScene;

public class NewGameMenu extends Menu {
    public NewGameMenu(MenuScene menuScene) {
        super(menuScene);

        Label lbl_newGameMenu = MenuScene.createMenuTitle("Choose Game Type");

        addNodeAll(
                lbl_newGameMenu,
                new MenuButton("New Campaign"),
                new MenuButton("Endless"),
                new MenuButtonTransition("Back", this, Menus.Main, menuScene::closeDoor));
    }
}
