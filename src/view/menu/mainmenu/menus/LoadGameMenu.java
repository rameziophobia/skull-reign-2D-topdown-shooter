package view.menu.mainmenu.menus;

import javafx.scene.control.Label;
import model.ui.menu.Menu;
import model.ui.menu.MenuButton;
import model.ui.menu.MenuButtonTransition;
import view.menu.mainmenu.MenuScene;

public class LoadGameMenu extends Menu {
    public LoadGameMenu(MenuScene menuScene) {
        super(menuScene);

        Label lbl_loadGameMenu = MenuScene.createMenuTitle("Choose Save");

        addNodeAll(
                lbl_loadGameMenu,
                new MenuButton("Load", MenuScene.BUTTON_SCALE),
                new MenuButtonTransition("Back", MenuScene.BUTTON_SCALE, this, "Main"));
    }
}
