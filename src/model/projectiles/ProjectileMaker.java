package model.projectiles;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ProjectileMaker {
    private final double SPEED = 6;

    private double posX;
    private double posY;
    private ImageView projectileImage;
    private double angle;
    private PROJECTILE projectile;


    public ImageView getProjectileImage() {
        return projectileImage;
    }

    public ProjectileMaker(double posX, double posY, PROJECTILE projectile, double angle) {
        this.posX = posX;
        this.posY = posY;
        this.projectile = projectile;
        this.angle = angle;
        spawnProjectile(projectile.getType());

    }

    public ImageView spawnProjectile(String projectileUrl) {
        projectileImage = new ImageView(new Image(projectileUrl));
        projectileImage.setLayoutY(posY);
        projectileImage.setLayoutX(posX);
        projectileImage.setRotate(angle);
        return projectileImage;
    }

    public void move() {
        double speedX = Math.cos(Math.toRadians(angle)) * SPEED;
        double speedY = Math.sin(Math.toRadians(angle)) * SPEED;
        projectileImage.setLayoutY(projectileImage.getLayoutY() + speedY);
        projectileImage.setLayoutX(projectileImage.getLayoutX() + speedX);
    }

}
