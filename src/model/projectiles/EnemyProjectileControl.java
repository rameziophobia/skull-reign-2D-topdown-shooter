package model.projectiles;

import javafx.geometry.Point2D;
import model.Enemies.Enemy;
import model.player.Player;

import java.util.ArrayList;

import static view.GameViewManager.gamePane;

public class EnemyProjectileControl extends ProjectileControl {

    private final Enemy enemy;
    private Point2D spawner;

    private final double[] patternRate;
    private int angle1by1 = 0;

    public enum patternRates {//todo fakesssssssssss
        RING(0), RING1BY1(1), toPlayer(2);
        int index;

        patternRates(int i) {
            index = i;
        }

        public int getIndex() {
            return index;
        }
    }

    private final ProjectileType type;
    private double FireRate;
    private long[] lastFireTime;


    public EnemyProjectileControl(Enemy enemy, Point2D spawner, ProjectileType type, Player player,
                                  double ringRate, double ringRate1by1, double toPlayerRate) {
        this(enemy, spawner, type, player, new double[]{ringRate, ringRate1by1, toPlayerRate});
    }

    public EnemyProjectileControl(Enemy enemy, Point2D spawner, ProjectileType type, Player player, double[] patternRate) {
        super(player);
        this.patternRate = patternRate;
        this.enemy = enemy;
        this.spawner = spawner;
        this.type = type;
        lastFireTime = new long[patternRate.length];
        for(int i = 0; i < lastFireTime.length; i++){
            lastFireTime[i] = System.currentTimeMillis() / 1000;
        }
    }

    public void spawnRing() {
        int i = patternRates.RING.getIndex();
        final long timeNow = System.currentTimeMillis() / 1000;
        if (timeNow > lastFireTime[i] + patternRate[i]) {
            for (int j = 0; j < 360; j += type.MULTANGLE * 3) {//todo: magic? multAngle
                Projectile projectile = new Projectile(spawner, type, j);
                gamePane.getChildren().add(projectile);
                projArr.add(projectile);
            }
            lastFireTime[i] = timeNow;
        }

    }

    public void spawnRing1by1() {
        int i = patternRates.RING1BY1.getIndex();

        final long timeNow = System.currentTimeMillis();
        if (timeNow > lastFireTime[i] + patternRate[i] * 1000) {
            System.out.println(System.currentTimeMillis() / 1000 + " " + lastFireTime[i]);
            angle1by1 += type.MULTANGLE;
            Projectile projectile = new Projectile(spawner, type, angle + angle1by1);
            gamePane.getChildren().add(projectile);
            projArr.add(projectile);
            lastFireTime[i] = timeNow;
        }
    }

    public void spawnToPlayer() {
        int i = patternRates.toPlayer.getIndex();

        final long timeNow = System.currentTimeMillis() / 1000;
        if (timeNow > lastFireTime[i] + patternRate[i]) {
            Projectile projectile = new Projectile(spawner, type, angle);
            gamePane.getChildren().add(projectile);
            projArr.add(projectile);
            lastFireTime[i] = timeNow;
        }
    }

    public void removeProjectile() {
        super.removeProjectile();
        if (projArr.size() > 0) {
            if (enemy.isDead()) {
                gamePane.getChildren().removeAll(projArr);
                projArr.removeAll(projArr);
            }
        }
    }

    public void update(double angle, Point2D enemyLocation) {
        update(angle);
        setSpawner(enemyLocation);

        spawnRing();
        spawnRing1by1();
        spawnToPlayer();
        moveProjectile();//todo: if moved to projectile control's update bt3ml bug fel animation ?? why
    }

    protected void update(double angle) {
        super.update(angle);
    }

//    private void removeProjectiles() {
    //todo: remove projectiles from projArray, gamepane if they pass screen limits or if enemy dies
    //todo: or when it collides with a player
//    }

    public void setSpawner(Point2D spawner) {
        this.spawner = spawner;
    }
}
