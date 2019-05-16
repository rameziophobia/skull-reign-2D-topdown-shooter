package model.ui.game;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import view.GameViewManager;
import view.Main;

public class ScoreLabel extends Label {

    private static final Insets PADDING = new Insets(10, 0, 0, 0);

    public ScoreLabel(){
        super("CURRENT SCORE: 0");

        this.setPrefWidth(GameViewManager.WIDTH);

        this.setPadding(PADDING);
        this.setAlignment(Pos.BOTTOM_CENTER);

        this.setFont(Main.FutureThinFont);
        this.setTextFill(Color.GHOSTWHITE);
        this.setTextAlignment(TextAlignment.CENTER);
    }
}
