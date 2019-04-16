package model.Enemies;

import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

public class normalTank extends Enemy {

    private static final int SPEED = 2;
    private static final double MAX_HP = 100;
    private double hp_current = 100;
    private final ImageView tankImg;
    private final int hitRadius = 45;

    public int getHitRadius() {
        return hitRadius;
    }

    public normalTank(ENEMY_ENUM enemyType, double playerXPos, double playerYPos) {
        super(enemyType, playerXPos, playerYPos);
        tankImg = super.getEnemyImage();
        Circle hitBox = new Circle(hitRadius);
    }

    public double getHp_current() {
        return hp_current;
    }

    public void setHp_current(double hp_current) {
        this.hp_current = hp_current;
    }

    public void move() {
        double angle = super.getAngle();
        double speedX = Math.cos(Math.toRadians(angle)) * SPEED;
        double speedY = Math.sin(Math.toRadians(angle)) * SPEED;
        tankImg.setLayoutY(tankImg.getLayoutY() + speedY);
        tankImg.setLayoutX(tankImg.getLayoutX() + speedX);
    }
}
