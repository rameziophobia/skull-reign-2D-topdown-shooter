package model.ui.menu;

import controller.animation.AnimationClip;
import controller.animation.SpriteSheet;
import controller.audiomanager.AudioFile;
import controller.audiomanager.AudioManager;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import view.Main;
import view.menu.mainmenu.MenuScene;

import java.util.Random;

public class MenuButton extends StackPane {

    private static final String FILE_RESOURCES_SPRITES_UI_BUTTON_BUTTON_BACKGROUND = MenuScene.PATH_RESOURCES_SPRITES_UI + "menu/button/Button_Background.png";
    private static final Insets LABEL_MARGIN = new Insets(0, 0, 0, 40);

    private static final Duration TRANSITION_DURATION = Duration.seconds(0.5);
    private static final int TRANSLATE_TRANSITION_AMOUNT = 3;

    private static final String FILE_FLAMEBALL = Main.PATH_RESOURCES_SPRITES + "flameball-32x32.png";
    private static SpriteSheet fireSpriteSheet = new SpriteSheet(FILE_FLAMEBALL, 0);

    private static final Font FONT = Font.font(15);
    private static final Color COLOR_WHITE = Color.WHITE;

    private static double buttonScale = 1;

    private Action onAnimationEndAction;

    private boolean isFireShown;
    private AnimationClip fireRightAnimationClip;
    private AnimationClip fireLeftAnimationClip;
    private ImageView img_fireRight;
    private ImageView img_fireLeft;
    private ImageView imageView;

    public MenuButton(String text) {
        this(text, buttonScale);
    }

    public MenuButton(String text, double scale) {
        this(text, scale, () -> {
        });
    }

    public MenuButton(String text, Action onAnimationEndAction) {
        this(text, buttonScale, onAnimationEndAction);
    }

    public MenuButton(String text, double scale, Action onAnimationEndAction) {
        setOnAnimationEndAction(onAnimationEndAction);

        createContent(text, scale);

        setPickOnBounds(false);
        setAlignment(Pos.CENTER_LEFT);

        setOnMouseClick();

        setOnMouseEnterAndExit();
    }

    public static void setButtonScale(double buttonScale) {
        MenuButton.buttonScale = buttonScale;
    }

    private void setOnMouseEnterAndExit() {
        setOnMouseEntered(e -> {
            imageView.setScaleX(1.1);
            imageView.setScaleY(1.1);

            isFireShown = true;
            img_fireRight.setOpacity(1);
            img_fireLeft.setOpacity(1);

//            AudioManager.playAudio(AudioFile.FIRE);
        });
        setOnMouseExited(e -> {
            imageView.setScaleX(1);
            imageView.setScaleY(1);

            isFireShown = false;
            img_fireRight.setOpacity(0);
            img_fireLeft.setOpacity(0);

//            AudioManager.stopAudio(AudioFile.FIRE);
        });
    }

    private void createContent(String text, double scale) {
        final Label label = new Label(text);
        label.setTextFill(COLOR_WHITE);
        label.setFont(FONT);
        StackPane.setMargin(label, LABEL_MARGIN);

        imageView = new ImageView(FILE_RESOURCES_SPRITES_UI_BUTTON_BUTTON_BACKGROUND);
        imageView.setFitWidth(imageView.getImage().getWidth() * scale);
        imageView.setFitHeight(imageView.getImage().getHeight() * scale);

        createAnimatedFire();

        getChildren().addAll(imageView, label, img_fireLeft, img_fireRight);
    }

    private void createAnimatedFire() {
        Random random = new Random();

        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setHue(random.nextFloat() * (random.nextBoolean() ? 1 : -1));

        img_fireRight = createFire(colorAdjust, new Insets(0, 0, 0, imageView.getFitWidth() - 32 - 10));

        fireRightAnimationClip = new AnimationClip(fireSpriteSheet,
                8, false, AnimationClip.INF_REPEATS, img_fireRight);

        img_fireLeft = createFire(colorAdjust, new Insets(0, 0, 0, 10));

        fireLeftAnimationClip = new AnimationClip(fireSpriteSheet,
                8, false, AnimationClip.INF_REPEATS, img_fireLeft);
    }

    private ImageView createFire(ColorAdjust colorAdjust, Insets marginInset) {
        ImageView imageView = new ImageView();
        imageView.setScaleX(1.25);
        imageView.setScaleY(1.25);
        imageView.setOpacity(0);

        StackPane.setAlignment(imageView, Pos.CENTER_LEFT);
        StackPane.setMargin(imageView, marginInset);

        imageView.setEffect(colorAdjust);

        imageView.setMouseTransparent(true);

        return imageView;
    }

    protected void setOnMouseClick() {
        final ParallelTransition parallelTransition = createFadeOutTransition();

        setOnMouseClicked(e -> {
            AudioManager.playAudio(AudioFile.BUTTON_CLICK);
            parallelTransition.setOnFinished(event -> onAnimationEndAction.run());
            parallelTransition.play();
        });
    }

    private ParallelTransition createFadeOutTransition() {
        final TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(TRANSITION_DURATION);
        translateTransition.setByY(TRANSLATE_TRANSITION_AMOUNT);

        final FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(TRANSITION_DURATION);
        fadeTransition.setToValue(0);

        return new ParallelTransition(this, translateTransition, fadeTransition);
    }

    public void setOnAnimationEndAction(Action onAnimationEndAction) {
        this.onAnimationEndAction = onAnimationEndAction;
    }

    public void update() {
        if (isFireShown) {
            fireLeftAnimationClip.animate();
            fireRightAnimationClip.animate();
        }
    }
}
