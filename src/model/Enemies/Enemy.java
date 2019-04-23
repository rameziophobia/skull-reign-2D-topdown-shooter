package model.Enemies;

import javafx.geometry.Point2D;
import model.Sprite;
import model.projectiles.EnemyProjectileControl;
import model.projectiles.ProjectileType;

import java.util.Random;

import static view.GameViewManager.HEIGHT;
import static view.GameViewManager.WIDTH;


public abstract class Enemy extends Sprite {

    private double angle;
    private EnemyType enemyType;
    private EnemyProjectileControl projectileControl;
//    private double playerXPos;
//    private double playerYPos;

    public Enemy(EnemyType enemyType, double playerXPos, double playerYPos) {
        super(enemyType.URL, enemyType.SPEED, new Point2D(1, 1));
        this.enemyType = enemyType; //todo: add shooting mechanic to enemies

//        this.playerXPos = playerXPos;
//        this.playerYPos = playerYPos;

        Random rand = new Random();//todo: spawn location
        int startX = rand.nextInt(HEIGHT);
        int startY = rand.nextInt(WIDTH);
        setSpawner(new Point2D(startX,startY));
        setLayoutX(startX);
        setLayoutY(startY);

        projectileControl = new EnemyProjectileControl
                (this, spawner, 5, ProjectileType.ICEICLE);//todo: change magics

        updateDirection(playerXPos, playerYPos);
        setRotate(angle);
    }

    protected void setSpawner(Point2D spawner) {
        this.spawner = spawner;
    }


    public void updateDirection(double playerXPos, double playerYPos) {//todo: msh lazem ye3mlo rotate
        double diffX = playerXPos - getLayoutX();
        double diffY = playerYPos - getLayoutY();
        angle = Math.toDegrees(Math.atan2(diffY, diffX));
        setRotate(angle);
    }

    public abstract double getCurrentHp();

    public abstract void setHp_current(double hp);

    public void move() {
        double speedX = Math.cos(Math.toRadians(angle)) * enemyType.SPEED;
        double speedY = Math.sin(Math.toRadians(angle)) * enemyType.SPEED;

        double nextPosX = getLayoutX() + speedX;
        double nextPosY = getLayoutY() + speedY;

        setLayoutX(nextPosX);
        setLayoutY(nextPosY);

        setSpawner(new Point2D(nextPosX,nextPosY));
    }

//    public double getAngle() {
//        return angle;
//    }

    public void followPlayer(double playerXPos, double playerYPos) {//todo: mesh lazem kollo ye follow el player
        updateDirection(playerXPos, playerYPos);
        move();
    }
    public void update(double playerXPos, double playerYPos,double time){
        followPlayer(playerXPos,  playerYPos);
        projectileControl.setTime(time);
        projectileControl.spawnRing();
        projectileControl.update(getLocation());
    }

    private Point2D getLocation() {
        return new Point2D(getLayoutX(),getLayoutY());
    }
}
