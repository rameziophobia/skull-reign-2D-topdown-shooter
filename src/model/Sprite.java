package model;

import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class Sprite extends ImageView {


    protected int height;
    protected int width;

    public Sprite(String url,int width, int height) {
        super(url);
        this.height = height;
        this.width = width;
    }


    protected void setSpriteY(double y){
        setLayoutY(y);
    }

    protected void setSpriteX(double x){
        setLayoutX(x);
    }

    public Rectangle getBounds() {
        return new Rectangle(getLayoutX(), getLayoutY(), width, height);
    }

    public boolean isIntersects(Sprite s) {
        return getBounds().intersects(s.getBounds().getX(),
                s.getBounds().getY(),
                s.getBounds().getWidth(),
                s.getBounds().getHeight());
    }
}
