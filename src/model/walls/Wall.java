package model.walls;

import javafx.geometry.Point2D;
import model.Sprite;

public class Wall extends Sprite { //todo if needed
    public Wall(double x, double y) {
        super("file:src/model/resources/wall-250x60.png",4,new Point2D(100,100),null);//todo change null
        this.setLayoutY(y);
        this.setLayoutX(x);
    }
}
