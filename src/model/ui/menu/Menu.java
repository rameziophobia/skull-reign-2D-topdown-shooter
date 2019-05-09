package model.ui.menu;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import view.menu.mainmenu.MenuScene;
import view.menu.mainmenu.menus.Menus;

public class Menu extends VBox {

    private static final Duration FADE_OUT_DURATION = Duration.seconds(0.35);
    private static final int FADE_OUT_TRANSLATE_AMOUNT = 3;

    private static final Duration FADE_IN_DURATION = Duration.seconds(0.35);
    private static final int FADE_IN_TRANSLATE_AMOUNT = -3;
    private static final Duration FADE_IN_DELAY = Duration.seconds(0.5);

    private static final Insets PADDING = new Insets(10, 10, 10, 0);
    private static final int SPACING = 10;

    private final ParallelTransition fadeOutParallelTransition;
    private final ParallelTransition fadeInParallelTransition;
    private final MenuScene menuScene;

    public Menu(MenuScene menuScene) {
        this.menuScene = menuScene;

        setPadding(PADDING);
        setSpacing(SPACING);

        setOpacity(0);
        setTranslateY(-FADE_IN_TRANSLATE_AMOUNT);
        setMouseTransparent(true);

        fadeOutParallelTransition = createFadeOutTrans();

        fadeInParallelTransition = createFadeInTrans();
    }

    private ParallelTransition createFadeInTrans() {
        final TranslateTransition fadeInTranslateTrans = new TranslateTransition();
        fadeInTranslateTrans.setDuration(FADE_IN_DURATION);
        fadeInTranslateTrans.setByY(FADE_IN_TRANSLATE_AMOUNT);

        final FadeTransition fadeInFadeTrans = new FadeTransition();
        fadeInFadeTrans.setDuration(FADE_IN_DURATION);
        fadeInFadeTrans.setToValue(1);

        final ParallelTransition parallelTransition = new ParallelTransition(this, fadeInTranslateTrans, fadeInFadeTrans);
        parallelTransition.setDelay(FADE_IN_DELAY);
        parallelTransition.setOnFinished(e -> setMouseTransparent(false));

        return parallelTransition;
    }

    private ParallelTransition createFadeOutTrans() {
        final TranslateTransition fadeOutTranslateTransition = new TranslateTransition();
        fadeOutTranslateTransition.setDuration(FADE_OUT_DURATION);
        fadeOutTranslateTransition.setByY(FADE_OUT_TRANSLATE_AMOUNT);

        final FadeTransition fadeOutFadeTransition = new FadeTransition();
        fadeOutFadeTransition.setDuration(FADE_OUT_DURATION);
        fadeOutFadeTransition.setToValue(0);

        return new ParallelTransition(this, fadeOutTranslateTransition, fadeOutFadeTransition);
    }


    public void addNode(Node node) {
        getChildren().add(node);
    }

    public void addNodeAll(Node... nodes) {
        for (Node node : nodes) {
            addNode(node);
        }
    }

    public void disableMenu(){
        this.setMouseTransparent(true);
    }

    public void transition(Menus nextMenuKey, MenuButtonTransition menuButtonTransition) {
        fadeOutParallelTransition.setOnFinished(e -> {
            menuScene.menuTransition(nextMenuKey);
            menuButtonTransition.reset();
        });
        fadeOutParallelTransition.play();
    }

    public void fadeIn() {
        fadeInParallelTransition.play();
    }
}
