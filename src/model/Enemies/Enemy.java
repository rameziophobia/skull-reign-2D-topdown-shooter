package model.Enemies;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Sprite;

import java.util.Random;

import static view.GameViewManager.HEIGHT;
import static view.GameViewManager.WIDTH;


public abstract class Enemy extends Sprite {


//    private final double SPEED = 2; //replaced in children
    private double angle;
    private ENEMY_ENUM enemyType;

    public ENEMY_ENUM getEnemyType() {
        return enemyType;
    }

    public Enemy(ENEMY_ENUM enemyType, double playerXPos, double playerYPos) {
        super(enemyType.getType());
        Random rand = new Random();
        setLayoutY(rand.nextInt(HEIGHT));
        setLayoutX(rand.nextInt(WIDTH));
        this.enemyType = enemyType;
        updateDirection(playerXPos, playerYPos);
        setRotate(angle);
    }

    public void updateDirection(double playerXPos, double playerYPos) {
        double diffX = playerXPos - getLayoutX();
        double diffY = playerYPos - getLayoutY();
        angle = Math.toDegrees(Math.atan2(diffY , diffX));
        setRotate(angle);
    }

    public abstract int getHitRadius();

    public abstract double getHp_current() ;

    public abstract void setHp_current(double hp) ;

    public abstract void move();

    public double getAngle() {
        return angle;
    }
}
