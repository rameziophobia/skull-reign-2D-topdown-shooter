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

    private ArrayList <StatBar> statRectangles = new ArrayList<>(); //0 for HP, 1 for Shield

    private static final int HPHEIGHT = 20;
    private static final int SHIELDHEIGHT = 7;
    private static final int BARWIDTH = 300;
    private static final Color HPCOLOR = Color.DARKRED;
    private static final Color SHIELDCOLOR = Color.LIGHTBLUE;

    public HealthBars() {
        VBox HPVBox = new VBox();
        HPVBox.setSpacing(5);
        HPVBox.setPadding(new Insets(5, 10, 0, 5));

        StackPane stackHP = getStackPane(HPHEIGHT,HPCOLOR,false,Player.getMaxHp());
        StackPane stackShield = getStackPane(SHIELDHEIGHT,SHIELDCOLOR,true,Player.getMaxShield());

        HPVBox.getChildren().addAll(stackHP, stackShield);
        GameViewManager.addGameObjectTOScene(HPVBox);
    }

    private StackPane getStackPane(int height, Color color,boolean regen, double maxHP) {
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

    public StatBar getHPRectangle(){
        return statRectangles.get(0);
    }
    public StatBar getShieldRectangle(){
        return statRectangles.get(1);
    }
}