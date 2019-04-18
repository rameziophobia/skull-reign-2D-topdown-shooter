package model.Enemies;

import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

public class normalTank extends Enemy {


    private static final double MAX_HP = 100;
    private double hp_current = 100;
    private final int hitRadius = 25;
    private ENEMY_ENUM enemyType;

    public int getHitRadius() {
        return hitRadius;
    }

    public normalTank(ENEMY_ENUM enemyType, double playerXPos, double playerYPos) {
        super(enemyType, playerXPos, playerYPos);
        this.enemyType = enemyType;
    }

    public double getHp_current() {
        return hp_current;
    }

    public void setHp_current(double hp_current) {
        this.hp_current = hp_current;
    }

    public void move() {
        double angle = super.getAngle();
        double speedX = Math.cos(Math.toRadians(angle)) * enemyType.getSpeed();
        double speedY = Math.sin(Math.toRadians(angle)) * enemyType.getSpeed();
        setLayoutY(getLayoutY() + speedY);
        setLayoutX(getLayoutX() + speedX);
    }
}
