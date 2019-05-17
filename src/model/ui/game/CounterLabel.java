package model.ui.game;

import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import view.GameViewManager;
import view.Main;

public class CounterLabel {
    private static final Duration FADE_OUT_DURATION = Duration.millis(700);
    private static final Duration FADE_IN_DURATION = Duration.millis(500);

    private String title;
    private FadeTransition FadeIn;
    private FadeTransition FadeOut;
    private Label lbl_sideStat;
    private Label lbl_completed;
    private int waveNumber = 1;
    private int waveBreakSeconds;

    public CounterLabel(String title, int waveBreakSeconds, int paddingoffset) {
        this.title = title;
        this.waveBreakSeconds = waveBreakSeconds;

        lbl_sideStat = new Label(title + waveNumber);
        lbl_completed = new Label(title + " Completed");

        lbl_sideStat.setTextFill(Color.GHOSTWHITE);
        lbl_sideStat.setFont(Main.FutureThinFont);
        lbl_sideStat.setPrefWidth(GameViewManager.WIDTH);
        lbl_sideStat.setAlignment(Pos.TOP_RIGHT);
        lbl_sideStat.setPadding(new Insets(10 + paddingoffset, 10, 0, 0));

        lbl_completed.setTextFill(Color.GHOSTWHITE);
        lbl_completed.setFont(Main.KenvectorThin);
        lbl_completed.setPrefWidth(GameViewManager.WIDTH);
        lbl_completed.setLayoutY(GameViewManager.HEIGHT / 4);
        lbl_completed.setAlignment(Pos.CENTER);
        lbl_completed.setOpacity(0);
        lbl_completed.setPadding(new Insets(paddingoffset * 2, 0, 0, 0));

        FadeIn = new FadeTransition(FADE_IN_DURATION, lbl_completed);
        FadeIn.setAutoReverse(true);
        FadeIn.setFromValue(0);
        FadeIn.setToValue(1);

        FadeOut = new FadeTransition(FADE_OUT_DURATION, lbl_sideStat);
        FadeOut.setAutoReverse(true);
        FadeOut.setFromValue(1);
        FadeOut.setToValue(0);
    }

    private void animatelabels(int waveTimer) {
        FadeOut();
        FadeIn(waveTimer);
    }

    private void FadeIn(int waveTimer) {
        FadeIn.setCycleCount(2 * waveTimer);
        FadeIn.play();
    }

    private void FadeOut() {
        FadeOut.setOnFinished(e -> {
            lbl_sideStat.setOpacity(1);
            lbl_sideStat.setText(title + " " + ++waveNumber);
        });
        FadeOut.play();
    }

    public void setUICounter(int value) {
        waveNumber = value;
    }

    public void incrementUICounterWithAnimation() {
        animatelabels(waveBreakSeconds);
    }

    public void addUIToGame() {
        GameViewManager.getMainPane().addToUIPane(lbl_sideStat);
        GameViewManager.getMainPane().addToUIPane(lbl_completed);
    }
}

