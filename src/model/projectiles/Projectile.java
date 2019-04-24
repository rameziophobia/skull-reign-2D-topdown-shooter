package model.projectiles;

import controller.Animation.AnimationClip;
import controller.Animation.SpriteSheet;
import javafx.geometry.Point2D;
import model.Enemies.Enemy;
import model.GameObject;
import view.GameViewManager;
import view.LevelManager;

public class Projectile extends GameObject {

    private ProjectileType projectileType;
    private double angle;
    private double scale = 1.0;
    private AnimationClip animationClip;

    private float speed;

    Projectile(Point2D spawnPoint, ProjectileType projectileType, double angle) {
        super(projectileType.getURL());
        this.projectileType = projectileType;
        this.speed = projectileType.getSPEED();
        this.angle = angle;

        if (projectileType.isANIMATED()) {
            this.animated = true;
            SpriteSheet spriteSheet = new SpriteSheet(projectileType.getURL(), 0);
            animationClip = new AnimationClip(spriteSheet,
                    spriteSheet.getFrameCount() * 1.2f,
                    false,
                    AnimationClip.INF_REPEATS,
                    this);
        }

        spawnProjectile(spawnPoint, angle);
    }

    private void spawnProjectile(Point2D spawnPoint, double angle) {
        setLayoutX(spawnPoint.getX());
        setLayoutY(spawnPoint.getY());
        setRotate(angle);
    }

    void addSpeed(float speed) {
        this.speed = this.speed + speed;
    }

    void setScale(double scale) {
        this.scale = 1 + scale / 100;
        setScaleX(this.scale);
        setScaleY(this.scale);
    }

    public double getDamage() {
        return projectileType.getDAMAGE() * (((scale - 1) * 2) + 1); //todo wot ?
//        return projectileType.getDAMAGE() * (2*scale - 2); //todo wot ?
    }

    void move() {
        setLayoutY(getLayoutY() + Math.sin(Math.toRadians(angle)) * speed);
        setLayoutX(getLayoutX() + Math.cos(Math.toRadians(angle)) * speed);
    }

    private void checkCollision_player() {
        for (Enemy enemy : LevelManager.getEnemyArrayList()) {
            if (isIntersects(enemy)) {
                enemy.takeDmg(getDamage());
                GameViewManager.removeGameObjectFromScene(this);
            }
        }
    }

    private void checkCollision_border() {
        if ((getLayoutY() > GameViewManager.HEIGHT || getLayoutY() < 0)
        && (getLayoutX() > GameViewManager.WIDTH || getLayoutX() < 0)) {
            GameViewManager.removeGameObjectFromScene(this);
        }
    }

    @Override
    public void update() {
        move();

        if (animated) { //todo everything should be animated
            animationClip.animate();
        }

        checkCollision_player();
        checkCollision_border();
    }


}