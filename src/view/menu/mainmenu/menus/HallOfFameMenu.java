package view.menu.mainmenu.menus;

import javafx.scene.control.Label;
import model.ui.menu.Menu;
import model.ui.menu.MenuButtonTransition;
import view.menu.mainmenu.MenuScene;

public class HallOfFameMenu extends Menu {
    public HallOfFameMenu(MenuScene menuScene) {
        super(menuScene);

        Label lbl_hallOfFameMenu = MenuScene.createMenuTitle("Hall Of Fame");

        addNodeAll(
                lbl_hallOfFameMenu,
                new MenuButtonTransition("Back", MenuScene.BUTTON_SCALE, this, "Main"));
    }
}
