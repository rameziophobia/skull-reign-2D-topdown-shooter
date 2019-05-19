package view.menu;

import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

import static view.GameViewManager.HEIGHT;
import static view.GameViewManager.WIDTH;

public class GameEnd extends VBox {
    private static final Duration FADE_DURATION = Duration.millis(2000);
    private static final int SPACING = 80;

    private FadeTransition fadeTransition;
    private Label lbl_score;
    private Label lbl_name;

    public GameEnd() {
        this.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setOpacity(0);
        this.setMouseTransparent(true);

        this.setPrefHeight(HEIGHT);
        this.setPrefWidth(WIDTH);
        this.setAlignment(Pos.CENTER);
        this.setSpacing(SPACING);

        addLabels();

        setUpFade();
    }

    private void setUpFade() {
        fadeTransition = new FadeTransition(FADE_DURATION, this);
        fadeTransition.setToValue(1);
        fadeTransition.setOnFinished(e -> this.setMouseTransparent(false));
    }

    private void addLabels() {
        final Label lbl_died = new Label("YOU DIED");
        lbl_died.setTextFill(Color.RED);
        lbl_died.setFont(Font.font("Arial", FontWeight.BOLD, 140));

        lbl_name = new Label();
        lbl_name.setTextFill(Color.WHITE);
        lbl_name.setFont(Font.font(40));

        lbl_score = new Label();
        lbl_score.setTextFill(Color.WHITE);
        lbl_score.setFont(Font.font(40));

        final Label lbl_continue = new Label("Click Anywhere to Continue");
        lbl_continue.setTextFill(Color.DARKORANGE);
        lbl_continue.setFont(Font.font(20));

        this.getChildren().addAll(
                lbl_died,
                lbl_name,
                lbl_score,
                lbl_continue
        );
    }

    public void setName(String name) {
        lbl_name.setText("Name: " + name);
    }

    public void setScore(int score) {
        lbl_score.setText("Score: " + score);
    }

    public void show() {
        fadeTransition.play();
    }
}
