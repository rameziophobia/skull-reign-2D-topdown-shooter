package model;

import com.sun.org.apache.bcel.internal.generic.DUP;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.Control;
import javafx.scene.control.SkinBase;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.util.Duration;

import static view.ViewManager.setCursorHand;

public class subsceneTemp extends SubScene {

    private final static String FONT_PATH = "src/model/resources/kenvector_future_thin.ttf";
    private final static String BACKGROUND_IMAGE = "file:src/model/resources/subscene.png";


    public subsceneTemp() {
        super(new TilePane(), 800, 600);
        TilePane vbx = (TilePane) this.getRoot();
        prefHeight(600);
        prefWidth(600);
        setCursorHand(vbx);
        BackgroundImage img_background = new BackgroundImage(new Image
                (BACKGROUND_IMAGE, 600, 600,
                        false, true), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        vbx.setBackground(new Background(img_background));
        vbx.setAlignment(Pos.CENTER_LEFT);
        vbx.setPadding(new Insets(150));
        opacityProperty().setValue(0);
        setVisible(false);

    }
    public void fadeInSubscene(){
        FadeTransition fadeIn = new FadeTransition();
        fadeIn.setDuration(Duration.seconds(0.2));
        fadeIn.setToValue(1);
        fadeIn.setNode(this);
        fadeIn.play();
        setVisible(true);
    }
    public void fadeOutSubscene(){
        FadeTransition fadeOut = new FadeTransition();
        fadeOut.setDuration(Duration.seconds(0.4));
        fadeOut.setToValue(0);
        fadeOut.setNode(this);
        fadeOut.play();
        setVisible(false);

    }

    public TilePane getPane() {
        return (TilePane) this.getRoot();
    }
}

