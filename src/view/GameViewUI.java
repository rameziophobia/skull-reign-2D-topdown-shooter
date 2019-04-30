package view;

import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Duration;
import model.player.Player;

import java.util.Random;

import static view.GameViewManager.*;

public class GameViewUI {


    Group group = new Group();
    TilePane bars;
    Bars HPRectangle;
    Bars ShieldRectangle;
    VBox HPVBox;
    Player player;

    public GameViewUI() {
        createHPbar();
        createWeaponBar();
        createSpecialAttackBar();
    }

    private static void createSpecialAttackBar() {
    }

    private void createWeaponBar() {
        gamePane.getChildren().add(new ProjectileUi());
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
