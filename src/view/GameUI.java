package view;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import model.ui.game.CounterLabel;
import view.game.ProjectileUI;
import view.game.stats.HealthBars;

public class GameUI {

//    private final CounterLabel levelLabel;
    private final CounterLabel waveLabel;
    private Group group = new Group();
    private HealthBars healthBars = new HealthBars();

    public GameUI(Pane gamePane,Boolean endless) {
        createWeaponBar();
        createBackground(gamePane);
        setCrosshair(gamePane);

        waveLabel = new CounterLabel("Wave", 6, 30);
        waveLabel.addUIToGame();

//        levelLabel = new CounterLabel("Level", 10, 0);
//
//        if(endless){
//            levelLabel.addUIToGame();
//        }

    }

//    public CounterLabel getLevelLabel() {
//        return levelLabel;
//    }

    public CounterLabel getWaveLabel() {
        return waveLabel;
    }

    private static void createWeaponBar() {
        GameViewManager.getMainPane().addToUIPane(new ProjectileUI());
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
        Image image = new Image(Main.PATH_RESOURCES_SPRITES + "crosshair/crosshair177.png"); // todo use path constants
        pane.setCursor(new ImageCursor(image,
                image.getWidth() / 2,
                image.getHeight() / 2));
    }
}
