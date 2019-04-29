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
        StackPane stackHP = createStackBar(300,20,Color.DARKRED,Player.getMaxHp());
        ImageView HPImage = addImage("/model/resources/edited_HPBar_png.png",325,35);
        stackHP.getChildren().addAll(HPImage);
        HPImage.toBack();

        StackPane stackShield = createStackBar(300,7,Color.LIGHTBLUE,Player.getMaxShield());
        HPVBox.setPadding(new Insets(5,10,0,5));
        HPVBox.setSpacing(5);
        HPVBox.getChildren().addAll(stackHP,stackShield);
        return HPVBox;
    }
    private StackPane createStackBar(double rectangleWidth,double rectangleHeight,Color fill,double MaxValue){
        StackPane stack = new StackPane();
        Rectangle rectangle = new Rectangle(rectangleWidth,rectangleHeight);
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStroke(fill);
        rectangle.setStrokeWidth(2);
        Bars rectangleBar = new Bars(rectangleHeight,fill,true,MaxValue);
        rectangleBar.setCurrentValue(MaxValue);
        stack.getChildren().addAll(rectangle,rectangleBar);
        return stack;
    }
    private ImageView addImage(String url,double width,double height){
        Image image = new Image(url);
        ImageView viewer = new ImageView();
        viewer.setImage(image);
        viewer.setFitHeight(height);
        viewer.setFitWidth(width);
        viewer.setSmooth(true);
        return viewer;
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
