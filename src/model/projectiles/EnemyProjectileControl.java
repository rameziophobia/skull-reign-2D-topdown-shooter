package model.projectiles;

import javafx.geometry.Point2D;
import model.Enemies.Enemy;

import java.util.ArrayList;

import static view.GameViewManager.gamePane;

public class EnemyProjectileControl {

    private final Enemy enemy;
    private Point2D spawner;
    private final double patternRate;
    private final ProjectileType type;
//    private final ArrayList<Function> patternArray; //array of projectile patterns to do

    private double FireRate;
    private double time;
    private double lastFireTime;
    private ArrayList<Projectile> projArr = new ArrayList<>();

    public EnemyProjectileControl(Enemy enemy, Point2D spawner, double patternRate, ProjectileType type) {
        this.enemy = enemy;
        this.spawner = spawner;
        this.patternRate = patternRate;
        this.lastFireTime = patternRate;
        this.type = type;
    }

    public void moveProjectile() {

        if (projArr.size() > 0) {

//            ArrayList<Projectile> projArrRemove = new ArrayList<>();

            for (Projectile p : projArr) {
                p.move();
            }
        }
    }

    public void spawnRing() {
        System.out.println(time + " " + lastFireTime);
        if (time > lastFireTime + patternRate) {
            for (int i = 0; i < 360; i += type.MULTANGLE * 3) {
                Projectile projectile = new Projectile(spawner, type, i);
                gamePane.getChildren().add(projectile);
                projArr.add(projectile);
            }
            lastFireTime = time;
        }

    }

    public void spawnRing1by1() {

    }

    public void spawnToPlayer() {

    }

    public void update(Point2D location) {
        setSpawner(location);
        moveProjectile();
    }

    public void setSpawner(Point2D spawner) {
        this.spawner = spawner;
    }

    public void setTime(double time) {
        this.time = time;
    }
}
