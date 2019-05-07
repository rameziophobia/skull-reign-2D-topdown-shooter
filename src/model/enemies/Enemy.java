package model.enemies;

import javafx.geometry.Point2D;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.Entity;
import model.projectiles.EnemyProjectileControl;
import model.projectiles.ProjectileType;
import view.GameViewManager;
import view.LevelManager;

import java.util.Random;

import static view.GameViewManager.*;

public class Enemy extends Entity {
    private final MoveMode mode;
    private final double MAX_HP;
    private final Text damageTxt = new Text("");

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

        GameViewManager.addGameObjectTOScene(damageTxt);
//        damageTxt.layoutXProperty().bind(this.layoutXProperty());//todo outputs an error
//        damageTxt.layoutYProperty().bind(this.layoutYProperty());//todo bt3ml bug fel weapon slots
        damageTxt.toFront();
//        damageTxt.setFont(Font.loadFont());

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
                break;
            }
            case followPlayer:{
                setLayoutX(getLayoutX() + Math.cos(Math.toRadians(angle)) * enemyType.getSPEED());
                setLayoutY(getLayoutY() + Math.sin(Math.toRadians(angle)) * enemyType.getSPEED());
                break;
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

        enemyProjectileControl.update(angle, new Point2D(getLayoutX(), getLayoutY()));

        if (hp <= 0) {
            removeGameObjectFromScene(this);
            LevelManager.removeEnemy(this);
        }
    }


}
