package model.projectiles;

import javafx.geometry.Point2D;
import model.Sprite;

public class ProjectileMaker  extends Sprite {

    private static final double SPEED = 6;

    private double angle;
    PROJECTILE proj;



    public PROJECTILE getProj() {
        return proj;
    }

    public ProjectileMaker(Point2D spawner, PROJECTILE projectile, double angle) {
        super(projectile.getType(), projectile.getWidth(),
                projectile.getHeight(),SPEED,null);//todo: add speed to enum

        spawnProjectile(spawner,angle);
        proj = projectile;
        this.angle = angle;
    }

    public void spawnProjectile(Point2D spawner, double angle) {
        setSpriteX(spawner.getX());
        setSpriteY(spawner.getY());
        setRotate(angle);
    }

    public void move() {
        double speedX = Math.cos(Math.toRadians(angle)) * SPEED;
        double speedY = Math.sin(Math.toRadians(angle)) * SPEED;
        System.out.println(speedX + " " + speedY + getLayoutX() + " " + getLayoutY());
        setSpriteY(getLayoutY() + speedY);
        setSpriteX(getLayoutX() + speedX);

    }

}
