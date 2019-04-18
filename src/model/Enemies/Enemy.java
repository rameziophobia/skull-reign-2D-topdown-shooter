package model.Enemies;

import model.Sprite;

import java.util.Random;

import static view.GameViewManager.HEIGHT;
import static view.GameViewManager.WIDTH;


public abstract class Enemy extends Sprite {


    //    private final double SPEED = 2; //replaced in children
    private double angle;
    private ENEMY_ENUM enemyType;


//    public ENEMY_ENUM getEnemyType() {
//        return enemyType;
//    }

    public Enemy(ENEMY_ENUM enemyType, double playerXPos, double playerYPos) {
        super(enemyType.getType(), enemyType.getWidth(),
                enemyType.getHeight(),enemyType.getSpeed(),null);//todo change null
        this.enemyType = enemyType; //todo: add shooting mechanic to enemies

        Random rand = new Random();
        setSpriteY(rand.nextInt(HEIGHT));
        setSpriteX(rand.nextInt(WIDTH));

        updateDirection(playerXPos, playerYPos);
        setRotate(angle);
    }

    public void updateDirection(double playerXPos, double playerYPos) {
        double diffX = playerXPos - getLayoutX();
        double diffY = playerYPos - getLayoutY();
        angle = Math.toDegrees(Math.atan2(diffY, diffX));
        setRotate(angle);
    }




    public abstract double getCurrentHp();

    public abstract void setHp_current(double hp);

    public void move(){
        double angle = getAngle();
        double speedX = Math.cos(Math.toRadians(angle)) * enemyType.getSpeed();
        double speedY = Math.sin(Math.toRadians(angle)) * enemyType.getSpeed();
        setLayoutY(getLayoutY() + speedY);
        setLayoutX(getLayoutX() + speedX);
    }

    public double getAngle() {
        return angle;
    }
}
