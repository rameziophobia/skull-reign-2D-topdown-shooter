package view;

import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class levelUI {
    private String UIString;
    private FadeTransition FadeIn;
    private FadeTransition FadeOut;
    private Label label1;
    private Label label2;
    private int waveNumber = 1;
    private int waveBreakSeconds ;

    public levelUI(String UISring,int waveBreakSeconds,int paddingoffset) {
        this.UIString = UISring;
        this.waveBreakSeconds = waveBreakSeconds;

        label1 = new Label(UISring + waveNumber);
        label2 = new Label(UISring+" Completed");

        label1.setTextFill(Color.GHOSTWHITE);
        label1.setFont(Main.FutureThinFont);
        label1.setPrefWidth(GameViewManager.WIDTH);
        label1.setAlignment(Pos.TOP_RIGHT);
        label1.setPadding(new Insets(10 + paddingoffset,10,0,0));

        label2.setTextFill(Color.GHOSTWHITE);
        label2.setFont(Main.KenvectorThin);
        label2.setPrefWidth(GameViewManager.WIDTH);
        label2.setLayoutY(GameViewManager.HEIGHT/4);
        label2.setAlignment(Pos.CENTER);
        label2.setOpacity(0);
        label2.setPadding(new Insets(paddingoffset*2,0,0,0));

        FadeIn = new FadeTransition();
        FadeOut = new FadeTransition();

    }

    private void animatelabels(Label lbl1,Label lbl2,int waveTimer) {
        FadeOut(lbl1,Duration.millis(700));
        FadeIn(lbl2,Duration.millis(500),waveTimer);
    }

    private void FadeIn(Label lbl,Duration duration,int waveTimer){
        FadeIn.setAutoReverse(true);
        FadeIn.setCycleCount(2*waveTimer);
        FadeIn.setNode(lbl);
        FadeIn.setFromValue(0);
        FadeIn.setToValue(1);
        FadeIn.setDuration(duration);
        FadeIn.play();
    }

    private void FadeOut(Label lbl,Duration duration){
        FadeOut.setAutoReverse(true);
        FadeOut.setNode(lbl);
        FadeOut.setFromValue(1);
        FadeOut.setToValue(0);
        FadeOut.setDuration(duration);
        FadeOut.play();
        FadeOut.setOnFinished(e-> {
            lbl.setOpacity(1);
            lbl.setText(UIString +" "+ ++waveNumber);
        });
    }

    public void setUICounter(int value){
        waveNumber = value;
    }

    public void incrementUICounterWithAnimation(){
        animatelabels(label1,label2,waveBreakSeconds);
    }

    public void addUIToGame() {
        GameViewManager.getMainPane().addToUIPane(label1);
        GameViewManager.getMainPane().addToUIPane(label2);
    }
}

