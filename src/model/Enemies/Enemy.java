package model.Enemies;

import javafx.geometry.Point2D;
import model.Sprite;
import model.player.Player;
import model.projectiles.EnemyProjectileControl;
import model.projectiles.ProjectileType;

import java.util.Random;

import static view.GameViewManager.*;


public abstract class Enemy extends Sprite {

    private final Player player;
    private double angle;
    public boolean dead = false;
    private EnemyType enemyType;
    private EnemyProjectileControl projectileControl;

    public Enemy(EnemyType enemyType, Player player) {
        super(enemyType.URL, enemyType.SPEED, new Point2D(1, 1));
        this.player = player;
        this.enemyType = enemyType; //todo: add shooting mechanic to enemies .. done bas u can add more patterns (functions fe projectileControl)

        Random rand = new Random();//todo: spawn location p.s. sadek kan 3amal eno mygoosh gambak
        int startX = rand.nextInt(HEIGHT);
        int startY = rand.nextInt(WIDTH);
        setSpawner(new Point2D(startX,startY));
        setLayoutX(startX);
        setLayoutY(startY);

        projectileControl = new EnemyProjectileControl //todo zwd el vars ka param le constructor enemy
                (this, spawner,Math.random() > 0.5 ? ProjectileType.ICEICLE:ProjectileType.FIREBALL,player,6,0.1,1);//todo: change magics

        updateDirection(new Point2D(player.getLayoutX(),  player.getLayoutY()));
        setRotate(angle);
    }

    protected void setSpawner(Point2D spawner) {
        this.spawner = spawner;
    }


    public void updateDirection(Point2D playerLocation) {//todo: msh lazem ye3mlo rotate
        double diffX = playerLocation.getX() - getLayoutX();
        double diffY = playerLocation.getY() - getLayoutY();
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

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
        if(dead){
            gamePane.getChildren().removeAll(projectileControl.getProjArr());
            projectileControl.getProjArr().removeAll(projectileControl.getProjArr());
        }
    }

    public void followPlayer(Point2D playerLocation) {//todo: mesh lazem kollo ye follow el player
        updateDirection(playerLocation);
        move();
    }
    public void update(double playerXPos, double playerYPos){
        Point2D playerLocation = new Point2D(playerXPos,  playerYPos);
        followPlayer(playerLocation);

        projectileControl.update(angle,new Point2D(getLayoutX(),getLayoutY()));//todo: enter values projectileControls mn 7eta 8er hna (endless mode class)
    }

}
