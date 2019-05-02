package model;

import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public abstract class GameObject extends ImageView {
    protected int height;
    protected int width;
    protected boolean animated;

    public GameObject(String url) {
        super(url);
        height = getImageWidth(url);
        width = getImageHeight(url);
        animated = isAnimated(url);
    }

    public static boolean isAnimated(String url) {
        return url.contains("animated");//todo X: -.-
    }

    public Point2D getSpawner() {
        return new Point2D(getLayoutX(), getLayoutY())
                .add(new Point2D(getFitWidth() / 2, getFitHeight() / 2));
    }

    public Rectangle getBounds() {
        return new Rectangle(getLayoutX(), getLayoutY(), width, height);
    }

    public static int getImageWidth(String url) {
        return Integer.valueOf(url.substring(url.lastIndexOf("-") + 1, url.lastIndexOf("x")));
    }

    public static int getImageHeight(String url) {
        return Integer.valueOf(url.substring(url.lastIndexOf("x") + 1, url.lastIndexOf(".")));
    }

    public boolean isIntersects(GameObject s) {
        return getBounds().intersects(s.getBounds().getX(),
                s.getBounds().getY(),
                s.getBounds().getWidth(),
                s.getBounds().getHeight());
//        return getBoundsInParent().intersects(s.getBoundsInParent());
    }//todo: ((Path)Shape.intersect(bullet, target)).getElements().size() > 0 better implementation??

    public abstract void update();
}
