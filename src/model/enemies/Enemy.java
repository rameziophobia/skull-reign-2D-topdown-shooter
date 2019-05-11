package model.enemies;

import javafx.geometry.Point2D;
import model.Entity;
import model.player.Player;
import model.projectiles.EnemyProjectileControl;
import model.projectiles.ProjectileType;
import view.LevelManager;

import java.util.Random;

import static view.GameViewManager.*;

public class Enemy extends Entity {
    private static final double MAX_HP = 100;

    private EnemyType enemyType;
    private double angle;

    private double hp = MAX_HP;

    private EnemyProjectileControl enemyProjectileControl;

    public Enemy(EnemyType enemyType) {
        super(enemyType.getURL(), enemyType.getSPEED());

        this.enemyType = enemyType;

        Random rand = new Random();
        setLayoutY(rand.nextInt(HEIGHT));
        setLayoutX(rand.nextInt(WIDTH));
        enemyProjectileControl = new EnemyProjectileControl
                (Math.random() > 0.5 ? ProjectileType.ICEICLE : ProjectileType.FIREBALL,
                        6, 0.1, 0.3);//todo: change magics
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

    private void removeProjectiles() {
        //todo

    }

    @Override
    public void update() {
        updateAngle();
        setRotate(angle);
        move();
        removeProjectiles();

        enemyProjectileControl.update(angle, new Point2D(getLayoutX(), getLayoutY()));//todo: enter values projectileControls mn 7eta 8er hna (endless mode class)

        if (hp <= 0||!LevelManager.isSpawnable()) {
            removeGameObjectFromScene(this);
            LevelManager.removeEnemy(this);
            Player.increaseCurrentScore(this.getScoreValue());
        }

    }

    public int getScoreValue(){
        return enemyType.getScore();
    }

}
