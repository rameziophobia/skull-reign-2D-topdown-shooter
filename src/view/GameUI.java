package view;

import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import model.ui.game.CounterLabel;
import view.game.ProjectileUI;
import view.game.stats.HealthBars;

public class GameUI {
    private final CounterLabel levelLabel;
    private final CounterLabel waveLabel;
    private Group group = new Group();
    private HealthBars healthBars = new HealthBars();

    public GameUI(Pane mainPane) {
        createWeaponBar();
        setCrosshair(mainPane);

        waveLabel = new CounterLabel("Wave", 3, 30);
        waveLabel.addUIToGame();

        levelLabel = new CounterLabel("Level", 2, 0);
        levelLabel.addUIToGame();
    }

    public CounterLabel getLevelLabel() {
        return levelLabel;
    }

    public CounterLabel getWaveLabel() {
        return waveLabel;
    }

    private static void createWeaponBar() {
        GameViewManager.getMainPane().addToUIPane(new ProjectileUI());
    }

    public Group getGroup() {
        return group;
    }

    public HealthBars getPlayerHealthBars() {
        return healthBars;
    }

    public static void setCrosshair(Pane mainPane) {
        Image image = new Image(Main.PATH_RESOURCES_SPRITES + "crosshair/crosshair177.png");
        mainPane.setCursor(new ImageCursor(image,
                image.getWidth() / 2,
                image.getHeight() / 2));
    }

}
