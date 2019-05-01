package model.enemies;

import javafx.geometry.Point2D;
import model.Entity;
import model.projectiles.EnemyProjectileControl;
import model.projectiles.ProjectileType;
import view.LevelManager;

import java.util.Random;

import static view.GameViewManager.*;

public class Enemy extends Entity {
    private final double MAX_HP;

    private EnemyType enemyType;
    private double angle;

    private double hp;

    private EnemyProjectileControl enemyProjectileControl;

    public Enemy(EnemyType enemyType, ProjectileType projectileType, double ringRate, double ringRate1by1, double toPlayerRate) {
        super(enemyType.getURL(), enemyType.getSPEED());

        this.enemyType = enemyType;
        this.MAX_HP = enemyType.getHP();
        hp = MAX_HP;

        Random rand = new Random();
        setLayoutY(rand.nextInt(HEIGHT));
        setLayoutX(rand.nextInt(WIDTH));
        setProjectileControl(projectileType,ringRate,ringRate1by1, toPlayerRate);
    }

    private void setProjectileControl(ProjectileType type, double ringRate, double ringRate1by1, double toPlayerRate) {
        enemyProjectileControl = new EnemyProjectileControl
                (type, ringRate, ringRate1by1, toPlayerRate);
    }

    @Override
    public void takeDmg(double dmg) {
        this.hp -= dmg;
    }

    @Override
    public void heal(float amount) {
        hp = Math.min(amount + hp, MAX_HP);
    }

    private void updateAngle() {
        angle = Math.toDegrees(Math.atan2(getPlayer().getLayoutY() - getLayoutY(),
                getPlayer().getLayoutX() - getLayoutX()));
    }

    private void move() {
        setLayoutX(getLayoutX() + Math.cos(Math.toRadians(angle)) * enemyType.getSPEED());
        setLayoutY(getLayoutY() + Math.sin(Math.toRadians(angle)) * enemyType.getSPEED());
    }

    @Override
    public void update() {
        updateAngle();
        setRotate(angle);
        move();

        enemyProjectileControl.update(angle, new Point2D(getLayoutX(), getLayoutY()));//todo: enter values projectileControls mn 7eta 8er hna (endless mode class)

        if (hp <= 0) {
            removeGameObjectFromScene(this);
            LevelManager.removeEnemy(this);
        }
    }


}
