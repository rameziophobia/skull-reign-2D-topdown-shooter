package model;

import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class Sprite extends ImageView {


    protected int height;
    protected int width;
    protected double speed;
    protected Point2D spawner;

    public Sprite(String url,int width, int height,double speed,Point2D spawner) {
        super(url);
        this.height = height;
        this.width = width;
        this.speed = speed;
        this.spawner = spawner;
    }

    public Point2D getSpawner() {
        return spawner.add(getCoordinate());
    }

    protected void setSpriteY(double y){
        setLayoutY(y);
    }

    protected void setSpriteX(double x){
        setLayoutX(x);
    }

    public Point2D getCoordinate(){
        return new Point2D(getLayoutX(),getLayoutY());
    }

    public Rectangle getBounds() {
        return new Rectangle(getLayoutX(), getLayoutY(), width, height);
    }

    public boolean isIntersects(Sprite s) {
        return getBounds().intersects(s.getBounds().getX(),
                s.getBounds().getY(),
                s.getBounds().getWidth(),
                s.getBounds().getHeight());
    }//todo: ((Path)Shape.intersect(bullet, target)).getElements().size() > 0 better implementation
}
