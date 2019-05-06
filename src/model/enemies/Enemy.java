package model.enemies;

import javafx.geometry.Point2D;
import javafx.scene.shape.Path;
import model.Entity;
import model.projectiles.EnemyProjectileControl;
import model.projectiles.ProjectileType;
import view.LevelManager;

import java.util.Random;

import static view.GameViewManager.*;

public class Enemy extends Entity {
    private final MoveMode mode;
    private final double MAX_HP;

    private EnemyType enemyType;
    private double angle;

    private double hp;

    private EnemyProjectileControl enemyProjectileControl;

    private Path path = new Path();
    public enum MoveMode {stationary, followPlayer, path}

    public Enemy(EnemyType enemyType, ProjectileType projectileType, Point2D start, Point2D end) {
        this(enemyType, projectileType, MoveMode.path);//todo implement path
    }

    public Enemy(EnemyType enemyType, ProjectileType projectileType, MoveMode mode) {
        super(enemyType.getURL(), enemyType.getSPEED());

        this.mode = mode;
        this.enemyType = enemyType;
        this.MAX_HP = enemyType.getHP();
        hp = MAX_HP;

        Random rand = new Random();
        setLayoutY(rand.nextInt(HEIGHT));
        setLayoutX(rand.nextInt(WIDTH));
        enemyProjectileControl = new EnemyProjectileControl(projectileType);
    }

    public EnemyProjectileControl getEnemyProjectileControl() {
        return enemyProjectileControl;
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

        switch (mode){
            case path:{

            }
            case followPlayer:{
                setLayoutX(getLayoutX() + Math.cos(Math.toRadians(angle)) * enemyType.getSPEED());
                setLayoutY(getLayoutY() + Math.sin(Math.toRadians(angle)) * enemyType.getSPEED());
            }
            default:
            case stationary:{
                break;
            }
        }
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
