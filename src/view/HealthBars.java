package view;

import javafx.geometry.Insets;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.player.Player;

import static view.GameViewManager.gamePane;

public class HealthBars extends VBox {
    private Bars HPRectangle;
    private Bars ShieldRectangle;

    HealthBars() {
        VBox HPVBox = new VBox();

        StackPane stackHP = new StackPane();
        Rectangle limitHP = new Rectangle(300, 20);
        limitHP.setFill(Color.TRANSPARENT);
        limitHP.setStroke(Color.DARKRED);
        limitHP.setStrokeWidth(2);

        HPRectangle = new Bars(7, Color.DARKRED, false, Player.getMaxHp());
        HPRectangle.setHeight(20);
        HPRectangle.setCurrentValue(Player.getMaxHp());

        stackHP.getChildren().addAll(limitHP, HPRectangle);

        StackPane stackShield = new StackPane();
        Rectangle limitShield = new Rectangle(300, 7);
        limitShield.setFill(Color.TRANSPARENT);
        limitShield.setStroke(Color.LIGHTBLUE);
        limitShield.setStrokeWidth(2);
        ShieldRectangle = new Bars(7, Color.LIGHTBLUE, true, Player.getMaxShield());
        ShieldRectangle.setHeight(7);
        ShieldRectangle.setCurrentValue(Player.getMaxShield());
        stackShield.getChildren().addAll(limitShield, ShieldRectangle);

        HPVBox.setPadding(new Insets(5, 10, 0, 5));
        HPVBox.setSpacing(5);
        HPVBox.getChildren().addAll(stackHP, stackShield);
        gamePane.getChildren().add(HPVBox);
    }

    public Bars getHPRectangle(){
        return HPRectangle;
    }
    public Bars getShieldRectangle(){
        return ShieldRectangle;
    }
}

