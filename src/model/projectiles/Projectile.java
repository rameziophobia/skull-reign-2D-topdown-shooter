package model.projectiles;

import controller.Animation.AnimationClip;
import controller.Animation.SpriteSheet;
import javafx.geometry.Point2D;
import model.Sprite;

public class Projectile extends Sprite {

    private AnimationClip animationClip;
    private double angle;
    private ProjectileType proj;
    private double scale = 1;


    public ProjectileType getProj() {
        return proj;
    }

    Projectile(Point2D spawner, ProjectileType projectileType, double angle) {
        super(projectileType.URL, projectileType.SPEED, new Point2D(1, 1));

        if(projectileType.ANIMATED){
            this.animated = true;
            SpriteSheet spriteSheet = new SpriteSheet(projectileType.URL, 0);
            animationClip = new AnimationClip(spriteSheet, spriteSheet.getFrameCount() * 1.2f, false, -1,  this);
        }

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
        double speedX = Math.cos(Math.toRadians(angle)) * speed;
        double speedY = Math.sin(Math.toRadians(angle)) * speed;
        setSpriteY(getLayoutY() + speedY);
        setSpriteX(getLayoutX() + speedX);
        if(animated) {
            animationClip.animate();
        }
    }



    void addSpeed(double speed) {
        this.speed = this.speed + speed;
    }

    void setScale(double scale) {
        this.scale = 1 + scale / 100;
        setScaleX(this.scale);
        setScaleY(this.scale);
    }

    public double getScale() {
        return scale;
    }

    public double getSpeed() {
        return speed;
    }

    public double getDamage() {
        return proj.DAMAGE * (((getScale() - 1) * 2) + 1);
    }

}