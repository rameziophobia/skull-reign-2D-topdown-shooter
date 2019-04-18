package model.projectiles;

import model.Sprite;

public class ProjectileMaker  extends Sprite {
    private final double SPEED = 6;

    private double posX;
    private double posY;
    private double angle;
    PROJECTILE proj;



    public PROJECTILE getProj() {
        return proj;
    }

    public ProjectileMaker(double posX, double posY, PROJECTILE projectile, double angle) {
        super(projectile.getType());
        this.posX = posX;
        this.posY = posY;
        this.angle = angle;
        proj = projectile;
        spawnProjectile();

    }

    public void spawnProjectile() {
        setLayoutX(posX);
        setLayoutY(posY);
        setRotate(angle);
    }

    public void move() {
        double speedX = Math.cos(Math.toRadians(angle)) * SPEED;
        double speedY = Math.sin(Math.toRadians(angle)) * SPEED;
        setLayoutY(getLayoutY() + speedY);
        setLayoutX(getLayoutX() + speedX);

    }

}
