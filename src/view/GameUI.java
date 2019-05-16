package view;

import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import view.game.ProjectileUI;
import view.game.stats.HealthBars;

import static view.GameViewManager.getGamePane;

public class GameUI {

    private Group group = new Group();
    private HealthBars healthBars = new HealthBars();

    public GameUI(AnchorPane gamePane) {
        createWeaponBar();
        createSpecialAttackBar();
        createBackground(gamePane);
        setCrosshair(gamePane);
    }

    private static void createSpecialAttackBar() {
    }

    private static void createWeaponBar() {
        GameViewManager.addGameObjectTOScene(new ProjectileUI());
    }

    public Group getGroup() {
        return group;
    }

    public HealthBars getHealthBars() {
        return healthBars;
    }


    public static void createBackground(AnchorPane gamePane) {
        Image backgroundImage = new Image("file:resources/sprites/tiles/floor2.png");
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        gamePane.setBackground(new Background(background));
    }

    public static void setCrosshair(Pane pane) {
        Image image = new Image("file:resources/sprites/crosshair/4.png"); // todo use path constants
        pane.setCursor(new ImageCursor(image,
                image.getWidth() / 2,
                image.getHeight() / 2));
    }

}
