package view;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import view.game.ProjectileUI;
import view.game.stats.HealthBars;

public class GameUI {

    private Group group = new Group();
    private HealthBars healthBars = new HealthBars();

    public GameUI(Pane gamePane) {
        createWeaponBar();
        createBackground(gamePane);
        setCrosshair(gamePane);
    }

    private static void createWeaponBar() {
        GameViewManager.addToScene(new ProjectileUI());
    }

    public Group getGroup() {
        return group;
    }

    public HealthBars getHealthBars() {
        return healthBars;
    }

    public static void createBackground(Pane gamePane) {
        gamePane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    public static void setCrosshair(Pane pane) {
        Image image = new Image("file:resources/sprites/crosshair/crosshair177.png"); // todo use path constants
        pane.setCursor(new ImageCursor(image,
                image.getWidth() / 2,
                image.getHeight() / 2));
    }

}
