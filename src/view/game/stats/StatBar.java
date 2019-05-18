package view.game.stats;

import javafx.animation.ScaleTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class StatBar extends Rectangle {
    private double currentValue;
    private static double WIDTH = 300;
    private boolean canRegenerate;
    private double maxValue;

    public StatBar(double height, Color fill, boolean canRegenerate, double max) {
        super(WIDTH, height, fill);
        this.canRegenerate = canRegenerate;
        this.maxValue = max;
        setCurrentValue(maxValue);
    }

    public StatBar(double height, double width, Color fill, boolean canRegenerate, double max) {
        super(width, height, fill);
        this.canRegenerate = canRegenerate;
        this.maxValue = max;
        setCurrentValue(maxValue);
    }

    public void setCurrentValue(double currentValue) {
        this.currentValue = currentValue;
    }

    public double getCurrentValue() {
        return currentValue;
    }

    public void increaseCurrent(double byValue) {
        setCurrentValue(Math.min(currentValue + byValue, maxValue));
    }

    public void decreaseCurrent(double byValue) {
        setCurrentValue(Math.max(currentValue - byValue, 0));
    }

    public void regeneration() {
        if (canRegenerate && currentValue < maxValue / 2) {
            setCurrentValue(maxValue / 2);
        }
    }

    public void barScaleAnimator(double MAX_HP) {
        ScaleTransition HPAnimation = new ScaleTransition(Duration.seconds(0.1), this);
        HPAnimation.setToX((this.getCurrentValue()) / MAX_HP);

        HPAnimation.play();
    }
}
