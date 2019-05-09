package model.ui.menu;

import controller.animation.AnimationClip;
import controller.animation.SpriteSheet;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class MenuBackground extends Pane {
    private final ImageView img_doorOpen;
    private final ImageView img_doorClosed;

    public MenuBackground() {
        ImageView img_background = new ImageView("file:resources/sprites/ScalledBackground.png");
        ImageView img_Light = new ImageView("file:resources/sprites/Pent.png");
        img_doorOpen = new ImageView("file:resources/sprites/Door_Open.png");
        img_doorClosed = new ImageView("file:resources/sprites/Door_Closed.png");

        ImageView img_fire = new ImageView();

        SpriteSheet spriteSheetfire = new SpriteSheet("file:resources/sprites/Fire-32x84.png", 0);

        AnimationClip animationClipfire = new AnimationClip(spriteSheetfire, 7, false, AnimationClip.INF_REPEATS, img_fire);

        img_fire.setLayoutX(450 + (16 * 2 * 4) + (4 * 8));
        img_fire.setLayoutY(220 + (3 * 16 * 4) + (4 * 8));

        ImageView img_fire2 = new ImageView();
        SpriteSheet spriteSheetfire2 = new SpriteSheet("file:resources/sprites/Fire-32x84.png", 0);

        AnimationClip animationClipfire2 = new AnimationClip(spriteSheetfire2, 7, false, AnimationClip.INF_REPEATS, img_fire2);

        img_fire2.setLayoutX(450 + (16 * 9 * 4));
        img_fire2.setLayoutY(220 + (3 * 16 * 4) + (4 * 8));

        SpriteSheet spriteSheet = new SpriteSheet("file:resources/sprites/BloodFountain-64x192.png", 0);

        ImageView img_bloodFountain = new ImageView();

        AnimationClip animationClip = new AnimationClip(spriteSheet,
                3, false, AnimationClip.INF_REPEATS, img_bloodFountain);

        ImageView img_bloodFountain2 = new ImageView();

        AnimationClip animationClip2 = new AnimationClip(spriteSheet,
                3, false, AnimationClip.INF_REPEATS, img_bloodFountain2);

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                animationClip.animate();
                animationClip2.animate();
                animationClipfire.animate();
                animationClipfire2.animate();
            }
        }.start();

        img_bloodFountain.setLayoutX(450 + 192);
        img_bloodFountain.setLayoutY(220);

        img_bloodFountain2.setLayoutX(450 + (16 * 4 * 8));
        img_bloodFountain2.setLayoutY(220);

        img_background.setLayoutX(450);
        img_background.setLayoutY(220);

        img_doorOpen.setLayoutX(450);
        img_doorOpen.setLayoutY(220);
        img_doorOpen.setOpacity(0);

        img_doorClosed.setLayoutX(450);
        img_doorClosed.setLayoutY(220);

        img_Light.setLayoutX(450 + (5.5*16*4));
        img_Light.setLayoutY(220 + (3*16*4));

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.25), img_Light);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.setAutoReverse(true);
        fadeTransition.setCycleCount(Transition.INDEFINITE);
        fadeTransition.play();

        getChildren().addAll(
                img_background,
                img_bloodFountain,
                img_bloodFountain2,
                img_fire,
                img_fire2,
                img_Light,
                img_doorClosed,
                img_doorOpen
        );
    }

    private void createBloodFountain() {

    }

    public void openDoor(){
        img_doorClosed.setOpacity(0);
        img_doorOpen.setOpacity(1);
    }

    public void closeDoor(){
        img_doorClosed.setOpacity(1);
        img_doorOpen.setOpacity(0);
    }
}
