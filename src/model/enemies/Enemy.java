package model.enemies;

import javafx.geometry.Point2D;
import javafx.scene.shape.Path;
import javafx.scene.text.Text;
import model.Entity;
import model.player.Player;
import model.projectiles.EnemyProjectileControl;
import model.projectiles.ProjectileType;
import view.GameViewManager;
import view.LevelManager;

import java.util.Random;

import static view.GameViewManager.*;

public class Enemy extends Entity {
    private final double MAX_HP;
    private final Text damageTxt = new Text("");

    private EnemyType enemyType;
    private double angle;
    private double randomAngle = Math.random() * 360;

    private Path path = new Path();
    private double minDistance;
    private long lastMoved;
    private long moveInterval = 999999999;
    private MoveMode mode;

    private double hp;

    private EnemyProjectileControl enemyProjectileControl;
    private boolean bool;
    private long nextMove;
    private boolean farFromPlayer;
    private short randomSign;

    public enum MoveMode {stationary, followPlayer, random, circular}

    public Enemy(EnemyType enemyType, ProjectileType projectileType, MoveMode mode, double minDistance, long move_interval_ms) {
        this(enemyType, projectileType, mode, minDistance);
        this.moveInterval = move_interval_ms;
    }

    public Enemy(EnemyType enemyType, ProjectileType projectileType, MoveMode mode, double minDistance) {
        this(enemyType, projectileType, mode);
        this.minDistance = minDistance;
    }

    public Enemy(EnemyType enemyType, ProjectileType projectileType, MoveMode mode) {
        super(enemyType.getURL(), enemyType.getSPEED());

        this.mode = mode;
        this.enemyType = enemyType;
        this.MAX_HP = enemyType.getHP();
        Random r = new Random();
        this.randomSign = (short) (r.nextBoolean() ? -1 : 1);
        hp = MAX_HP;

        Random rand = new Random();
        setLayoutY(height + rand.nextInt(HEIGHT - 2 * height - 10));
        setLayoutX(width + rand.nextInt(WIDTH - 2 * width - 10));
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

    private void calculateDistance() {
        farFromPlayer = Math.hypot(
                getLayoutX() - getPlayer().getLayoutX(),
                getLayoutY() - getPlayer().getLayoutY())
                > minDistance;
    }

    private void updateAngle() {
        angle = Math.toDegrees(Math.atan2(getPlayer().getLayoutY() - getLayoutY(),
                getPlayer().getLayoutX() - getLayoutX()));
    }

    public void setMoveMode(MoveMode mode) {
        this.mode = mode;
    }

    private void move() {

        if (System.currentTimeMillis() % (moveInterval * 2) > moveInterval && farFromPlayer) {

            switch (mode) {
                case followPlayer: {
                    moveImageView(angle);
                    break;
                }
                case circular: {
                    moveImageView(angle + 90 * randomSign);
                    break;
                }
                //todo remove it if it looks stupid with the new assets
                case random: {

                    if (nextMove < System.currentTimeMillis()) {
                        randomAngle = -30 + Math.random() * 30;
                        nextMove = System.currentTimeMillis() + moveInterval;
                    }
                    moveImageView(randomAngle + angle);
                    break;
                }
                default:
                case stationary: {
                    break;
                }
            }
        }

    }

    private void moveImageView(double angle) {
        double nextX = getLayoutX() + Math.cos(Math.toRadians(angle)) * enemyType.getSPEED();
        double nextY = getLayoutY() + Math.sin(Math.toRadians(angle)) * enemyType.getSPEED();

        if (nextX + 15 + width < WIDTH && nextX > 15) {
            setLayoutX(nextX);
        } else {
            randomSign *= -1;
        }
        if (nextY + 15 + height < HEIGHT && nextY > 15) {
            setLayoutY(nextY);
        } else {
            randomSign *= -1;
        }

    }

    @Override
    public void update() {
        updateAngle();
        setRotate(angle);
        calculateDistance();
        move();

        enemyProjectileControl.update(angle, getSpawner());

        if (hp <= 0 || !LevelManager.isSpawnable()) {
            removeGameObjectFromScene(this);
            LevelManager.removeEnemy(this);
            if (hp <= 0)
                Player.increaseCurrentScore(this.getScoreValue());
        }
    }

    public int getScoreValue() {
        return enemyType.getScore();
    }

}
