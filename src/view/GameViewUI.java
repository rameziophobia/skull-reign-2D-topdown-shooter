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

import static view.GameViewManager.HEIGHT;
import static view.GameViewManager.WIDTH;

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

    private static Group createSpecialAttackBar() {
        Group randomRotator = new Group();
        Random rand = new Random();
        final int startX = rand.nextInt(WIDTH);
        final int startY = rand.nextInt(HEIGHT);
        final Circle circle = new Circle(startX, startY, 15);
        circle.setFill(Color.DARKRED);

        final Path path = new Path();
        path.getElements().add(new MoveTo(startX, startY));
        path.getElements().add(new QuadCurveTo(rand.nextInt(WIDTH) , rand.nextInt(HEIGHT) ,
                rand.nextInt(WIDTH) , rand.nextInt(HEIGHT) ));

        path.setOpacity(0);

        final PathTransition pathTransition = new PathTransition();

        pathTransition.setDuration(Duration.seconds(4.0));
        pathTransition.setDelay(Duration.seconds(.5));
        pathTransition.setPath(path);
        pathTransition.setNode(circle);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setCycleCount(Timeline.INDEFINITE);
        pathTransition.setAutoReverse(true);
        pathTransition.play();

        randomRotator.getChildren().add(path);
        randomRotator.getChildren().add(circle);
        return randomRotator;
    }

    private void createWeaponBar() {
    }

    private VBox createHPbar() {
        HPVBox = new VBox();

        StackPane stackHP = new StackPane();
        Rectangle limitHP = new Rectangle(300,20);
        limitHP.setFill(Color.TRANSPARENT);
        limitHP.setStroke(Color.DARKRED);
        limitHP.setStrokeWidth(2);
        HPRectangle = new Bars(7,Color.DARKRED,false,player.getMaxHp());
        HPRectangle.setHeight(20);
        HPRectangle.setCurrentValue(player.getMaxHp());
        stackHP.getChildren().addAll(limitHP,HPRectangle);

        StackPane stackShield = new StackPane();
        Rectangle limitShield = new Rectangle(300,7);
        limitShield.setFill(Color.TRANSPARENT);
        limitShield.setStroke(Color.LIGHTBLUE);
        limitShield.setStrokeWidth(2);
        ShieldRectangle = new Bars(7,Color.LIGHTBLUE,true,player.getMaxShield());
        ShieldRectangle.setHeight(7);
        ShieldRectangle.setCurrentValue(player.getMaxShield());
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
