package model;

import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class Sprite extends ImageView {


    protected int height;
    protected int width;
    protected boolean animated;
    protected double speed;
    protected Point2D spawner;

    public Sprite(String url,double speed,Point2D spawner) {
        super(url);
        this.speed = speed;
        this.spawner = spawner;
        height = getImageWidth(url);
        width = getImageHeight(url);
        animated = isAnimated(url);
    }

    public static boolean isAnimated(String url) {
        return url.contains("animated");
    }

    protected void setSpriteY(double y){
        setLayoutY(y);
    }

    protected void setSpriteX(double x){
        setLayoutX(x);
    }

    public Point2D getSpawner() {
        return new Point2D(getLayoutX(),getLayoutY())
        .add(new Point2D(getFitWidth() / 2, getFitHeight() / 2));
    }

    public static int getImageWidth(String url){
        return Integer.valueOf(url.substring(url.lastIndexOf("-") + 1, url.lastIndexOf("x")));
    }

    public static int getImageHeight(String url){
        return Integer.valueOf(url.substring(url.lastIndexOf("x") + 1, url.lastIndexOf(".")));
    }

    private Rectangle getBounds() {
        return new Rectangle(getLayoutX(), getLayoutY(), width, height);
    }
    public boolean isIntersects(Sprite s) {
        return getBounds().intersects(s.getBounds().getX(),
                s.getBounds().getY(),
                s.getBounds().getWidth(),
                s.getBounds().getHeight());
    }//todo: ((Path)Shape.intersect(bullet, target)).getElements().size() > 0 better implementation??

}
