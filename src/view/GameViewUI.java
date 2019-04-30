package view;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.player.Player;
import view.game.ProjectileUI;

import static view.GameViewManager.*;

public class GameViewUI {

//    private HealthBars healthBars = new HealthBars(); //todo: zabat da
    private Group group = new Group();
    private Bars HPRectangle;
    private Bars ShieldRectangle;
    private VBox HPVBox;

    public GameViewUI() {
        createHPbar();
        createWeaponBar();
        createSpecialAttackBar();
    }

    private static void createSpecialAttackBar() {
    }

    private static void createWeaponBar() {
        GameViewManager.addGameObjectTOScene(new ProjectileUI());
    }

    private VBox createHPbar() {
        HPRectangle = new Bars(20, Color.DARKRED, true, Player.getMaxHp(), "/model/resources/edited_HPBar_png.png");
        ImageView HPImage = HPRectangle.addImage(325, 35);
        StackPane stackHP = HPRectangle.createStackBar(300, 20, Color.DARKRED, Player.getMaxHp(), HPImage);

        ShieldRectangle = new Bars(7, Color.LIGHTBLUE, true, Player.getMaxShield(), "/model/resources/edited_HPBar_png.png");
        ImageView ShieldImage = ShieldRectangle.addImage(325, 20);
        StackPane stackShield = ShieldRectangle.createStackBar(300, 7, Color.LIGHTBLUE, Player.getMaxShield(), ShieldImage);

        HPVBox = createVBox();
        HPVBox.getChildren().addAll(stackHP, stackShield);
        return HPVBox;
    }

    private VBox createVBox() {
        VBox vbx = new VBox();
        vbx.setPadding(new Insets(5, 10, 0, 5));
        vbx.setSpacing(5);
        return vbx;
    }

    public VBox getVBox() {
        return HPVBox;
    }

    public Bars getHPRectangle() {
        return HPRectangle;
    }

    public Bars getShieldRectangle() {
        return ShieldRectangle;
    }

    public Group getGroup() {
        return group;
    }
}
