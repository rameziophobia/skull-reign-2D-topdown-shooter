package model.projectiles;

import javafx.geometry.Point2D;

import static view.GameViewManager.addGameObjectTOScene;

public class EnemyProjectileControl {

    private Point2D spawner;

    private final double[] patternRate;
    private int angle1by1 = 0;
    private double angle;

    public enum patternRates {
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
    private long[] lastFireTime;


    public EnemyProjectileControl(ProjectileType type,
                                  double ringRate, double ringRate1by1, double toPlayerRate) {
        this(type, new double[]{ringRate, ringRate1by1, toPlayerRate});
    }

    public EnemyProjectileControl(ProjectileType type, double[] patternRate) {
        super();
        this.patternRate = patternRate;
        this.type = type;
        lastFireTime = new long[patternRate.length];
        for (int i = 0; i < lastFireTime.length; i++) {
            lastFireTime[i] = System.currentTimeMillis() / 1000;
        }
    }

    public void spawnRing() {
        int i = patternRates.RING.getIndex();
        final long timeNow = System.currentTimeMillis() / 1000;
        if (timeNow > lastFireTime[i] + patternRate[i]) {
            for (int j = 0; j < 360; j += type.getMULTANGLE() * 3) {//todo: magic? multAngle .. momkn yb2a variable
                Projectile projectile = new Projectile(spawner, type, j, true);
                addGameObjectTOScene(projectile);
            }
            lastFireTime[i] = timeNow;
        }

    }

    public void spawnRing1by1() {
        int i = patternRates.RING1BY1.getIndex();

        final long timeNow = System.currentTimeMillis();
        if (timeNow > lastFireTime[i] + patternRate[i] * 1000) {
            angle1by1 += type.getMULTANGLE() * 2; //todo: magicNum
            Projectile projectile = new Projectile(spawner, type, angle + angle1by1, true);
            addGameObjectTOScene(projectile);
            lastFireTime[i] = timeNow;
        }
    }

    public void spawnToPlayer() {
        int i = patternRates.toPlayer.getIndex();

        final long timeNow = System.currentTimeMillis() / 1000;
        if (timeNow > lastFireTime[i] + patternRate[i]) {
            Projectile projectile = new Projectile(spawner, type, angle, true);
            addGameObjectTOScene(projectile);
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
