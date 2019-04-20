package model;

import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

import java.util.Collection;

import static java.lang.Math.*;

public class Sprite extends ImageView {


    protected int height;
    protected int width;
    protected double speed;
    protected Point2D spawner;
    protected Circle c;

    public Sprite(String url,int width, int height,double speed,Point2D spawner,Point2D center) {
        super(url);
        this.height = height;
        this.width = width;
        this.speed = speed;
        this.spawner = spawner;
//        initSpawner(spawner.getX(), spawner.getY(),angle);
    }

    protected void setSpriteY(double y){
        setLayoutY(y);
    }

    protected void setSpriteX(double x){
        setLayoutX(x);
    }

    public Point2D getCenter(){
        return new Point2D(getLayoutX() ,getLayoutY());
    }

    public Rectangle getBounds() {
        return new Rectangle(getLayoutX(), getLayoutY(), width, height);
    }

//    public Point2D getSpawner(double angle){
//
//        c = new Circle(50);
//        c.setCenterX(spawner.getX() + getLayoutX());
//        c.setCenterY(spawner.getY() + getLayoutY());
//        c.setLayoutX(spawner.getX() + getLayoutX());
//        c.setLayoutY(spawner.getY() + getLayoutY());
//        Rotate r = new Rotate(angle,getLayoutX(),getLayoutY());
////        System.out.println("before: " + c.getLayoutX() + " " + c.getLayoutY() + " " + c.getCenterX() + " " + c.getCenterY());
//        c.getTransforms().add(r);
//        System.out.println(r.getPivotX() + " " + r.getPivotY());
////        System.out.println("after: " + c.getLayoutX() + " " + c.getLayoutY() + " " + c.getCenterX() + " " + c.getCenterY());
//        return new Point2D(c.getLayoutX(),c.getLayoutY());
//
//}

    public double calcShootAngle(){
//        return atan2(spawner.getY() - getLayoutY(),spawner.getX() - getLayoutX());
        return atan2(spawner.getX(), spawner.getY());
    }

    public  double calcDistanceToSpawner(){
        return Math.hypot(spawner.getX() - 0, spawner.getY() - 0);
    }

//    public Point2D getSpawner() {
//        return getCenter().setRotat;
//        return getCoordinate().add(
//                new Point2D(calcDistanceToSpawner() * sin(calcShootAngle() + getRotate()),
//                        calcDistanceToSpawner() * cos(calcShootAngle() + getRotate())));
//    }

    public Point2D getSpawner() {
        return new Point2D(getLayoutX(),getLayoutY())
        .add(new Point2D(getFitWidth() / 2, getFitHeight() / 2));
    }

    public boolean isIntersects(Sprite s) {
        return getBounds().intersects(s.getBounds().getX(),
                s.getBounds().getY(),
                s.getBounds().getWidth(),
                s.getBounds().getHeight());
    }//todo: ((Path)Shape.intersect(bullet, target)).getElements().size() > 0 better implementation??
}
