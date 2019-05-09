package model.ui.menu;

import controller.animation.AnimationClip;
import controller.animation.SpriteSheet;
import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import view.Main;

import java.util.ArrayList;
import java.util.List;

public class MenuBackground extends Pane {
    private final ImageView img_doorOpen;
    private final ImageView img_doorClosed;

    private final List<AnimationClip> animationClips;

    public MenuBackground() {
        animationClips = new ArrayList<>();

        final ImageView img_background = new ImageView(Main.PATH_RESOURCES_SPRITES + "ScalledBackground.png");
        img_background.setLayoutX(450);
        img_background.setLayoutY(220);

        final ImageView img_pulsingPent = new ImageView(Main.PATH_RESOURCES_SPRITES + "Pent.png");
        img_pulsingPent.setLayoutX(450 + (5.5 * 16 * 4));
        img_pulsingPent.setLayoutY(220 + (3 * 16 * 4));

        img_doorOpen = new ImageView(Main.PATH_RESOURCES_SPRITES + "Door_Open.png");
        img_doorOpen.setLayoutX(450);
        img_doorOpen.setLayoutY(220);
        img_doorOpen.setOpacity(0);

        img_doorClosed = new ImageView(Main.PATH_RESOURCES_SPRITES + "Door_Closed.png");
        img_doorClosed.setLayoutX(450);
        img_doorClosed.setLayoutY(220);

        final SpriteSheet fireSpriteSheet = new SpriteSheet(Main.PATH_RESOURCES_SPRITES + "Fire-32x84.png", 0);

        final ImageView img_fireLeft = createFire(fireSpriteSheet, 450 + (16 * 2 * 4) + (4 * 8));
        final ImageView img_fireRight = createFire(fireSpriteSheet, 450 + (16 * 9 * 4));

        final SpriteSheet spriteSheet = new SpriteSheet(Main.PATH_RESOURCES_SPRITES + "BloodFountain-64x192.png", 0);

        final ImageView img_bloodFountainLeft = createBloodFountain(spriteSheet, 450 + (3 * 16 * 4));
        final ImageView img_bloodFountainRight = createBloodFountain(spriteSheet, 450 + (8 * 16 * 4));

        startPentPulse(img_pulsingPent);

        getChildren().addAll(
                img_background,
                img_bloodFountainLeft,
                img_bloodFountainRight,
                img_fireLeft,
                img_fireRight,
                img_pulsingPent,
                img_doorClosed,
                img_doorOpen
        );
    }

    private ImageView createFire(SpriteSheet fireSpriteSheet, int x) {
        final ImageView imageView = new ImageView();
        imageView.setLayoutX(x);
        imageView.setLayoutY(220 + (3 * 16 * 4) + (4 * 8));

        animationClips.add(new AnimationClip(fireSpriteSheet, 7, false, AnimationClip.INF_REPEATS, imageView));
        return imageView;
    }

    private ImageView createBloodFountain(SpriteSheet spriteSheet, int x) {
        final ImageView imageView = new ImageView();
        imageView.setLayoutX(x);
        imageView.setLayoutY(220);

        animationClips.add(new AnimationClip(spriteSheet,
                3, false, AnimationClip.INF_REPEATS, imageView));
        return imageView;
    }

    private void startPentPulse(ImageView img_pulsingPent) {
        final FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.25), img_pulsingPent);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.setAutoReverse(true);
        fadeTransition.setCycleCount(Transition.INDEFINITE);
        fadeTransition.play();
    }

    public List<AnimationClip> getAnimationClips() {
        return animationClips;
    }

    public void openDoor() {
        img_doorClosed.setOpacity(0);
        img_doorOpen.setOpacity(1);
    }

    public void closeDoor() {
        img_doorClosed.setOpacity(1);
        img_doorOpen.setOpacity(0);
    }
}
