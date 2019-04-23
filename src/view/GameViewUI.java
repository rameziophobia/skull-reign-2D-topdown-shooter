package view;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import model.player.Player;

import static view.GameViewManager.*;

public class GameViewUI {

    Group group = new Group();
    HealthBars healthBars = new HealthBars();

    public GameViewUI(){
        createWeaponBar();
        createSpecialAttackBar();
    }

    private static void createSpecialAttackBar() {
    }

    private void createWeaponBar() {
        gamePane.getChildren().add(new ProjectileUi());
    }


    public Group getGroup() {
        return group;
    }

    public HealthBars getHealthBars() {
        return healthBars;
    }
}
