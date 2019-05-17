package model.projectiles;

import javafx.geometry.Point2D;
import view.GameViewManager;

import java.util.ArrayList;

public class EnemyProjectileControl {

    private Point2D spawner;

    private long[] patternRate = new long[5];
    private int angle1by1 = 0;
    private double angle;

    private float ringAngle;
    private float ringAngle1by1;

    public enum PatternRate {
        RING(0), RING1BY1(1), toPlayer(2);
        int index;

        PatternRate(int i) {
            index = i;
        }

        public int getIndex() {
            return index;
        }
    }

    private final ProjectileType type;
    private long[] lastFireTime;


    public EnemyProjectileControl(ProjectileType type) {
        super();
        this.type = type;
        lastFireTime = new long[5]; //todo magic
        for (int i = 0; i < lastFireTime.length; i++) {
            lastFireTime[i] = System.currentTimeMillis() / 1000;
        }
    }

    public void setPatternRate(PatternRate pattern, long rate){
        patternRate[pattern.getIndex()] = rate;
    }

    public void addRing1by1(long rate, float ringAngle1by1){
        setPatternRate(PatternRate.RING1BY1,rate);
        this.ringAngle1by1 =  ringAngle1by1;
    }

    public void addSpawnToPlayer(long rate){
        setPatternRate(PatternRate.toPlayer,rate);
    }

    public void addSpawnRing(long rate, float ringAngle){
        setPatternRate(PatternRate.RING,rate);
        this.ringAngle =  ringAngle;
    }

    public void spawnRing() {
        int i = PatternRate.RING.getIndex();
        final long timeNow = System.currentTimeMillis();

        ArrayList<Projectile> projArrTest = new ArrayList<>();

        if (timeNow > lastFireTime[i] + patternRate[i] && patternRate[i] != 0) {
            for (int j = (int) angle; j < 360 + (int) angle; j += ringAngle) {
                Projectile projectile = new Projectile(spawner, type, j, true);
                projArrTest.add(projectile);
            }
            lastFireTime[i] = timeNow;
            projArrTest.forEach(projectile -> GameViewManager.getMainPane().addToGamePane(projectile));
        }

    }

    public void spawnRing1by1() {
        int i = PatternRate.RING1BY1.getIndex();

        final long timeNow = System.currentTimeMillis();
        if (timeNow > lastFireTime[i] + patternRate[i] && patternRate[i] != 0) {
            angle1by1 += ringAngle1by1; //todo: magicNum
            Projectile projectile = new Projectile(spawner, type, angle + angle1by1, true);
            GameViewManager.getMainPane().addToGamePane(projectile);
            lastFireTime[i] = timeNow;
        }
    }

    public void spawnToPlayer() {
        int i = PatternRate.toPlayer.getIndex();

        final long timeNow = System.currentTimeMillis();
        if (timeNow > lastFireTime[i] + patternRate[i] && patternRate[i] != 0) {
            Projectile projectile = new Projectile(spawner, type, angle, true);
            GameViewManager.getMainPane().addToGamePane(projectile);
            lastFireTime[i] = timeNow;
        }
    }

    public void update(double angle, Point2D enemyLocation) {
        this.angle = angle;
        setSpawner(enemyLocation);

        spawnRing();
        spawnRing1by1();
        spawnToPlayer();
    }

    public void setSpawner(Point2D spawner) {
        this.spawner = spawner;
    }
}
