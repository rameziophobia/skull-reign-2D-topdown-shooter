package view.game.stats;

import javafx.geometry.Insets;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.player.Player;
import view.GameViewManager;

public class HealthBars extends VBox {
    private StatBar HPRectangle;
    private StatBar ShieldRectangle;

    public HealthBars() {
        VBox HPVBox = new VBox();

        StackPane stackHP = new StackPane();
        Rectangle limitHP = new Rectangle(300, 20);
        limitHP.setFill(Color.TRANSPARENT);
        limitHP.setStroke(Color.DARKRED);
        limitHP.setStrokeWidth(2);

        HPRectangle = new StatBar(7, Color.DARKRED, false, Player.getMaxHp());
        HPRectangle.setHeight(20);
        HPRectangle.setCurrentValue(Player.getMaxHp());

        stackHP.getChildren().addAll(limitHP, HPRectangle);

        StackPane stackShield = new StackPane();
        Rectangle limitShield = new Rectangle(300, 7);
        limitShield.setFill(Color.TRANSPARENT);
        limitShield.setStroke(Color.LIGHTBLUE);
        limitShield.setStrokeWidth(2);
        ShieldRectangle = new StatBar(7, Color.LIGHTBLUE, true, Player.getMaxShield());
        ShieldRectangle.setHeight(7);
        ShieldRectangle.setCurrentValue(Player.getMaxShield());
        stackShield.getChildren().addAll(limitShield, ShieldRectangle);

        HPVBox.setPadding(new Insets(5, 10, 0, 5));
        HPVBox.setSpacing(5);
        HPVBox.getChildren().addAll(stackHP, stackShield);
        GameViewManager.addGameObjectTOScene(HPVBox);
    }

    public StatBar getHPRectangle(){
        return HPRectangle;
    }
    public StatBar getShieldRectangle(){
        return ShieldRectangle;
    }
}

