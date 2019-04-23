package model;

import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class Sprite extends ImageView {


    protected int height;
    protected int width;
    protected double speed;
    protected Point2D spawner;

    public Sprite(String url,int width, int height,double speed,Point2D spawner,Point2D center) {
        super(url);
        this.height = height;
        this.width = width;
        this.speed = speed;
        this.spawner = spawner;
    }

    protected void setSpriteY(double y){
        setLayoutY(y);
    }

    protected void setSpriteX(double x){
        setLayoutX(x);
    }

    public  double calcDistanceToSpawner(){
        return Math.hypot(spawner.getX() - 0, spawner.getY() - 0);
    }


    public Point2D getSpawner() {
        return new Point2D(getLayoutX(),getLayoutY())
        .add(new Point2D(getFitWidth() / 2, getFitHeight() / 2));
    }

    public Rectangle getBounds() {
        return new Rectangle(getLayoutX(), getLayoutY(), width, height);
    }
    public boolean isIntersects(Sprite s) {
        return getBounds().intersects(s.getBounds().getX(),
                s.getBounds().getY(),
                s.getBounds().getWidth(),
                s.getBounds().getHeight());
//        return getBoundsInParent().intersects(s.getBoundsInParent());
    }//todo: ((Path)Shape.intersect(bullet, target)).getElements().size() > 0 better implementation??

}
