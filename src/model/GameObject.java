package model;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public abstract class GameObject extends ImageView {
    protected int height;
    protected int width;
    protected boolean animated;

    public GameObject(String url) {
        this(new Image(url));
    }

    public GameObject(Image image) {
        super(image);
        //impl_getUrl() was added to javafx 11
        if (image.impl_getUrl().contains("-")) {
            height = getImageWidth(image.impl_getUrl());
            width = getImageHeight(image.impl_getUrl());
        }
        animated = isAnimated(image.impl_getUrl());
    }

    public static boolean isAnimated(String url) {
        return url.contains("animated");
    }

    public Point2D getSpawner() {
        return new Point2D(getLayoutX() + (width >> 1), getLayoutY() + (height >> 1));
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
        return getBoundsInParent().intersects(s.getBoundsInParent());
    }

    public abstract void update();

    public abstract Node[] getChildren();

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
