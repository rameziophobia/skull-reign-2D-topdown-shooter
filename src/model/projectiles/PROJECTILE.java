package model.projectiles;

import javafx.geometry.Point2D;
import model.Sprite;

public class Projectile extends Sprite {

    private static final double SPEED = 6;
    private double angle;
    private ProjectileType proj;


    public ProjectileType getProj() {
        return proj;
    }

    Projectile(Point2D spawner, ProjectileType projectileType, double angle) {
        super(projectileType.URL, projectileType.WIDTH,
                projectileType.HEIGHT, SPEED, new Point2D(1, 1), null);//todo: add speed to enum

        spawnProjectile(spawner, angle);
        proj = projectileType;
        this.angle = angle;
    }

    private void spawnProjectile(Point2D spawner, double angle) {
        setSpriteX(spawner.getX());
        setSpriteY(spawner.getY());
        setRotate(angle);
    }

    void move() {
        double speedX = Math.cos(Math.toRadians(angle)) * SPEED;
        double speedY = Math.sin(Math.toRadians(angle)) * SPEED;
        setSpriteY(getLayoutY() + speedY);
        setSpriteX(getLayoutX() + speedX);

    }
}