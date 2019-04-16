package model.Enemies;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public abstract class Enemy {


//    private final double SPEED = 2; //replaced in children
    private ImageView enemyImage;
    private double angle;
    private ENEMY_ENUM enemyType;

    public ImageView getEnemyImage() {
        return enemyImage;
    }

    public ENEMY_ENUM getEnemyType() {
        return enemyType;
    }

    public Enemy(ENEMY_ENUM enemyType, double playerXPos, double playerYPos) {
        enemyImage = new ImageView(new Image(enemyType.getType()));
        enemyImage.setLayoutY(200);
        enemyImage.setLayoutX(200);
        this.enemyType = enemyType;
        updateDirection(playerXPos, playerYPos);
        enemyImage.setRotate(angle);
    }

    public void updateDirection(double playerXPos, double playerYPos) {
        double diffX = playerXPos - enemyImage.getLayoutX();
        double diffY = playerYPos - enemyImage.getLayoutY();
        angle = Math.toDegrees(Math.atan2(diffY , diffX));
        System.out.println(playerXPos + " " + playerYPos + " "
                + enemyImage.getLayoutX() + " " + enemyImage.getLayoutY() + " " + angle);
        enemyImage.setRotate(angle);
    }

    public abstract int getHitRadius();

    public abstract double getHp_current() ;

    public abstract void setHp_current(double hp) ;

    public abstract void move();

    public double getAngle() {
        return angle;
    }
}
