package model.projectiles;

import model.Sprite;

public class ProjectileMaker  extends Sprite {

    private final double SPEED = 6;

    private double angle;
    PROJECTILE proj;



    public PROJECTILE getProj() {
        return proj;
    }

    public ProjectileMaker(double posX, double posY, PROJECTILE projectile, double angle) {
        super(projectile.getType(), projectile.getWidth(), projectile.getHeight());

        spawnProjectile(posX,posY,angle);
        proj = projectile;
        this.angle = angle;
    }

    public void spawnProjectile(double posX, double posY, double angle) {
        setSpriteX(posX);
        setSpriteY(posY);
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
