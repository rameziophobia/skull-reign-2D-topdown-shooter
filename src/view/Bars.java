package view;

import javafx.animation.ScaleTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Bars extends Rectangle {
    private double currentValue;
    private static final double WIDTH = 300;
    private boolean canRegenerate;
    private double maxValue;
    private Image image;

    public Bars(double height, Color fill, boolean canRegenerate, double max, String url) {
        super(WIDTH, height, fill);
        this.canRegenerate = canRegenerate;
        this.maxValue = max;
        setImage(url);
    }

    public StackPane createStackBar(double rectangleWidth, double rectangleHeight, Color fill, double MaxValue, ImageView image) {
        StackPane stack = new StackPane();
        Rectangle rectangle = new Rectangle(rectangleWidth, rectangleHeight);
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStroke(fill);
        rectangle.setStrokeWidth(2);

        this.setCurrentValue(MaxValue);
        stack.getChildren().addAll(image, rectangle, this);
        return stack;
    }

    public ImageView addImage(double width, double height) {
        ImageView viewer = new ImageView();
        viewer.setImage(this.image);
        viewer.setFitHeight(height);
        viewer.setFitWidth(width);
        viewer.setSmooth(true);
        return viewer;
    }

    public void increaseCurrent(double byValue) {
        setCurrentValue(Math.min(currentValue + byValue, maxValue));
        barScaleAnimator();
    }

    public void decreaseCurrent(double byValue) {
        setCurrentValue(Math.max(currentValue - byValue, 0));
        barScaleAnimator();
    }

    public void regeneration() {
        if (canRegenerate && currentValue < maxValue / 2) {
            setCurrentValue(maxValue / 2);
        }
        barScaleAnimator();
    }

    private void barScaleAnimator() {//todo change paramaters to Bars only
        ScaleTransition HPAnimation = new ScaleTransition(Duration.seconds(0.1), this);

        HPAnimation.setToX((this.getCurrentValue()) / maxValue);

        HPAnimation.play();
    }

    public void setImage(String url) {
        this.image = new Image(url);
    }

    public void setCurrentValue(double currentValue) {
        this.currentValue = currentValue;
    }

    public double getCurrentValue() {
        return currentValue;
    }

}
