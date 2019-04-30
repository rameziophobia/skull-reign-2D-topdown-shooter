package view.game.stats;

import javafx.geometry.Insets;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.player.Player;
import view.GameViewManager;

import java.util.ArrayList;

public class HealthBars extends VBox {
    private static final int HP_HEIGHT = 20;
    private static final int SHIELD_HEIGHT = 7;
    private static final int BARWIDTH = 300;
    private static final Color HP_COLOR = Color.DARKRED;
    private static final Color SHIELD_COLOR = Color.LIGHTBLUE;

    private ArrayList<StatBar> statRectangles = new ArrayList<>(); //0 for HP, 1 for Shield

    public HealthBars() {
        VBox HPVBox = new VBox();
        HPVBox.setSpacing(5);
        HPVBox.setPadding(new Insets(5, 10, 0, 5));

        StackPane stackHP = getStackPane(HP_HEIGHT, HP_COLOR, false, Player.getMaxHp());
        StackPane stackShield = getStackPane(SHIELD_HEIGHT, SHIELD_COLOR, true, Player.getMaxShield());

        HPVBox.getChildren().addAll(stackHP, stackShield);
        GameViewManager.addGameObjectTOScene(HPVBox);
    }

    private StackPane getStackPane(int height, Color color, boolean regen, double maxHP) {
        StackPane stack = new StackPane();
        Rectangle limit = getLimitRectangle(height, color);
        StatBar rectangle = new StatBar(height, color, regen, maxHP);
        statRectangles.add(rectangle);
        stack.getChildren().addAll(limit, rectangle);
        return stack;
    }

    private Rectangle getLimitRectangle(int height, Color color) {
        Rectangle limitHP = new Rectangle(HealthBars.BARWIDTH, height);
        limitHP.setFill(Color.TRANSPARENT);
        limitHP.setStroke(color);
        limitHP.setStrokeWidth(2);
        return limitHP;
    }

    public StatBar getHPRectangle() {
        return statRectangles.get(0);
    }

    public StatBar getShieldRectangle() {
        return statRectangles.get(1);
    }
}