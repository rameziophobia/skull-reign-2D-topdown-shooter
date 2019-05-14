package view.menu.mainmenu.menus;

import javafx.scene.control.Label;
import model.player.PlayerType;
import model.ui.menu.Menu;
import model.ui.menu.MenuButton;
import model.ui.menu.MenuButtonTransition;
import view.GameViewManager;
import view.menu.mainmenu.MenuScene;

public class NewGameMenu extends Menu {
    private GameViewManager gameViewManager;

    public NewGameMenu(MenuScene menuScene) {
        super(menuScene);

        Label lbl_newGameMenu = MenuScene.createMenuTitle("Choose Game Type");

        MenuButton new_campaign = new MenuButton("New Campaign");
        new_campaign.setOnAnimationEndAction(() -> {
            menuScene.stopLoop();
            createGameViewManager();
            gameViewManager.createNewGame(PlayerType.ROBOT);
            new_campaign.setTranslateY(0);//todo temp
            new_campaign.setOpacity(1);//todo temp
        });

        addNodeAll(
                lbl_newGameMenu,
                new_campaign,
                new MenuButton("Endless"),
                new MenuButtonTransition("Back", this, Menus.Customization));
    }

    private void createGameViewManager() {
        gameViewManager = new GameViewManager();
    }

}
