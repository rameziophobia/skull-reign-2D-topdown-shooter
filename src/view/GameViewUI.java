package view;

import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Group;
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

    public GameViewUI(){
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
        HPVBox = new VBox();

        StackPane stackHP = new StackPane();
        Rectangle limitHP = new Rectangle(300,20);
        limitHP.setFill(Color.TRANSPARENT);
        limitHP.setStroke(Color.DARKRED);
        limitHP.setStrokeWidth(2);
        HPRectangle = new Bars(7,Color.DARKRED,false, Player.getMaxHp());
        HPRectangle.setHeight(20);
        HPRectangle.setCurrentValue(Player.getMaxHp());
        stackHP.getChildren().addAll(limitHP,HPRectangle);

        StackPane stackShield = new StackPane();
        Rectangle limitShield = new Rectangle(300,7);
        limitShield.setFill(Color.TRANSPARENT);
        limitShield.setStroke(Color.LIGHTBLUE);
        limitShield.setStrokeWidth(2);
        ShieldRectangle = new Bars(7,Color.LIGHTBLUE,true, Player.getMaxShield());
        ShieldRectangle.setHeight(7);
        ShieldRectangle.setCurrentValue(Player.getMaxShield());
        stackShield.getChildren().addAll(limitShield,ShieldRectangle);

        HPVBox.setPadding(new Insets(5,10,0,5));
        HPVBox.setSpacing(5);
        HPVBox.getChildren().addAll(stackHP,stackShield);
        return HPVBox;
    }
    public VBox getVBox(){
        return HPVBox;
    }

    public Bars getHPRectangle(){
        return HPRectangle;
    }
    public Bars getShieldRectangle(){
        return ShieldRectangle;
    }

    public Group getGroup() {
        return group;
    }
}
