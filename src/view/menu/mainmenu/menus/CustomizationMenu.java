package view.menu.mainmenu.menus;

import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import model.ui.menu.Menu;
import model.ui.menu.MenuButtonTransition;
import view.menu.mainmenu.MenuScene;

public class CustomizationMenu extends Menu {
    private final TextField txt_playerName;

    public CustomizationMenu(MenuScene menuScene) {
        super(menuScene);

        txt_playerName = new TextField();
        txt_playerName.setPromptText("Enter Name Here...");
        txt_playerName.setFont(Font.font(20));

        txt_playerName.setMaxWidth(350);
        txt_playerName.setPrefHeight(50);
        txt_playerName.setPadding(new Insets(0, 0, 0, 5));

        txt_playerName.setFocusTraversable(false);
        txt_playerName.setDisable(true);

        addNodeAll(
                MenuScene.createMenuTitle("Enter Player Name"),
                txt_playerName,
                new MenuButtonTransition("Next", this, Menus.NEW_GAME, () -> {
                    txt_playerName.setDisable(true);
                    menuScene.setPlayerName(txt_playerName.getText());
                }),
                new MenuButtonTransition("Back", this, Menus.MAIN, menuScene::closeDoor)
        );
    }

    @Override
    public void fadeIn() {
        super.fadeIn();
        txt_playerName.setDisable(false);
    }
}
