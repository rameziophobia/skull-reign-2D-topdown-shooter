package model.Enemies;

import model.Entity;
import view.GameViewManager;
import view.LevelManager;
import javafx.geometry.Point2D;
import model.Sprite;
import model.player.Player;
import model.projectiles.EnemyProjectileControl;
import model.projectiles.ProjectileType;

import java.util.Random;

import static view.GameViewManager.*;

public class Enemy extends Entity {
    private static final double MAX_HP = 100;

    private EnemyType enemyType;
    private double angle;

    private double hp = MAX_HP;

    public Enemy(EnemyType enemyType) {
        super(enemyType.getURL(), enemyType.getSPEED());

        this.enemyType = enemyType;

        Random rand = new Random();
        setLayoutY(rand.nextInt(HEIGHT));
        setLayoutX(rand.nextInt(WIDTH));
    }

    @Override
    public void takeDmg(double dmg) {
        this.hp -= dmg;
    }

    @Override
    public void heal(float amount) {
        hp = Math.min(amount + hp, MAX_HP);
    }

    private void UpdateAngle() {
        angle = Math.toDegrees(Math.atan2(GameViewManager.getPlayer().getLayoutY() - getLayoutY(),
                GameViewManager.getPlayer().getLayoutX() - getLayoutX()));
    }

    private void move() {
        setLayoutX(getLayoutX() + Math.cos(Math.toRadians(angle)) * enemyType.getSPEED());
        setLayoutY(getLayoutY() + Math.sin(Math.toRadians(angle)) * enemyType.getSPEED());
    }

    @Override
    public void update() {
        UpdateAngle();
        setRotate(angle);
        move();

        if (hp <= 0) {
            GameViewManager.removeGameObjectFromScene(this);
            LevelManager.removeEnemy(this);
        }
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
