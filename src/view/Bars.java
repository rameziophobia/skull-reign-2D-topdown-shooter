package view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Bars extends Rectangle {
    private double currentValue;
    private static final double WIDTH = 300;
    private boolean canRegenerate;
    private double maxValue ;

    public void setCurrentValue(double currentValue) {
        this.currentValue = currentValue;
    }

    public double getCurrentValue() {
        return currentValue;
    }

    public Bars(double height, Color fill,boolean canRegenerate,double max) {
        super(WIDTH, height, fill);
        this.canRegenerate = canRegenerate;
        this.maxValue=max;
    }
    public void increaseCurrent(double byValue){
        setCurrentValue(Math.min(currentValue+byValue,maxValue));
    }
    public void decreaseCurrent(double byValue){
        setCurrentValue(Math.max(currentValue - byValue, 0));
    }

    public void regeneration(){
        if(canRegenerate && currentValue < maxValue/2){
            setCurrentValue(maxValue/2);
        }
    }
}
