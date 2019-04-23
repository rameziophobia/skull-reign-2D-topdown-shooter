package model.Enemies;

import javafx.geometry.Point2D;
import model.Sprite;

import java.util.Random;

import static view.GameViewManager.HEIGHT;
import static view.GameViewManager.WIDTH;


public abstract class Enemy extends Sprite {

    private double angle;
    private EnemyType enemyType;

    public Enemy(EnemyType enemyType, double playerXPos, double playerYPos) {
        super(enemyType.URL,enemyType.SPEED,new Point2D(1,1));
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
        double speedX = Math.cos(Math.toRadians(angle)) * enemyType.SPEED;
        double speedY = Math.sin(Math.toRadians(angle)) * enemyType.SPEED;
        setLayoutY(getLayoutY() + speedY);
        setLayoutX(getLayoutX() + speedX);
    }

    public double getAngle() {
        return angle;
    }
}
