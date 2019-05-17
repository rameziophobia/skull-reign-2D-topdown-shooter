package model.ui.game;

import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import view.GameViewManager;
import view.Main;

public class levelUI {
    private String title;
    private FadeTransition FadeIn;
    private FadeTransition FadeOut;
    private Label lbl_sideStat;
    private Label lbl_completed;
    private int waveNumber = 1;
    private int waveBreakSeconds;

    public levelUI(String title, int waveBreakSeconds, int paddingoffset) {
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

        FadeIn = new FadeTransition(Duration.millis(500));
        FadeOut = new FadeTransition(Duration.millis(700));

    }

    private void animatelabels(Label lbl1, Label lbl2, int waveTimer) {
        FadeOut(lbl1);
        FadeIn(lbl2, waveTimer);
    }

    private void FadeIn(Label lbl, int waveTimer) {
        FadeIn.setAutoReverse(true);
        FadeIn.setCycleCount(2 * waveTimer);
        FadeIn.setNode(lbl);
        FadeIn.setFromValue(0);
        FadeIn.setToValue(1);
        FadeIn.play();
    }

    private void FadeOut(Label lbl) {
        FadeOut.setAutoReverse(true);
        FadeOut.setNode(lbl);
        FadeOut.setFromValue(1);
        FadeOut.setToValue(0);
        FadeOut.setOnFinished(e -> {
            lbl.setOpacity(1);
            lbl.setText(title + " " + ++waveNumber);
        });
        FadeOut.play();
    }

    public void setUICounter(int value) {
        waveNumber = value;
    }

    public void incrementUICounterWithAnimation() {
        animatelabels(lbl_sideStat, lbl_completed, waveBreakSeconds);
    }

    public void addUIToGame() {
        GameViewManager.getMainPane().addToUIPane(lbl_sideStat);
        GameViewManager.getMainPane().addToUIPane(lbl_completed);
    }
}

