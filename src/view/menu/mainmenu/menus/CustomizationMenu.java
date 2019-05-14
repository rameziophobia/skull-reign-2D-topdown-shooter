package view.menu.mainmenu.menus;

import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import model.ui.menu.Menu;
import model.ui.menu.MenuButtonTransition;
import view.menu.mainmenu.MenuScene;

public class CustomizationMenu extends Menu {
    public CustomizationMenu(MenuScene menuScene) {
        super(menuScene);

        TextField txt_playerName = new TextField();
        txt_playerName.setPromptText("Enter Name Here...");
        txt_playerName.setMaxWidth(350);
        txt_playerName.setPrefHeight(50);
        txt_playerName.setPadding(new Insets(0, 0, 0, 5));
        txt_playerName.setFont(Font.font(20));

        addNodeAll(
                MenuScene.createMenuTitle("Enter Player Name"),
                txt_playerName,
                new MenuButtonTransition("Next", this, Menus.NewGame),
                new MenuButtonTransition("Back", this, Menus.Main, menuScene::closeDoor)
        );
    }
}
