package model.projectiles;

import controller.map.Map;
import javafx.geometry.Point2D;
import view.GameViewManager;

import java.util.ArrayList;
import java.util.Random;

import static controller.map.Map.*;
import static view.GameViewManager.HEIGHT;
import static view.GameViewManager.WIDTH;

public class EnemyProjectileControl {
    private Point2D spawner;

    final int CONTROLS_NUM = 10;
    private long[] patternRate = new long[CONTROLS_NUM];
    private int angle1by1 = 0;
    private double angle;

    private float ringAngle;
    private float ringAngle1by1;
    private double bossRingAngle;
    private boolean boss;
    private int missileSpeed;
    private final ProjectileType type;
    private long[] lastFireTime;
    private float knifeAngle;
    private int showerSpawnX = (int) (STARTING_X + BLOCK_SIZE);
    private int showerSpawnY = (int) (STARTING_Y + BLOCK_SIZE);
    private boolean showerHorizontal = false;

    public enum PatternRate {
        PULSE(0), RING1BY1(1), toPlayer(2), MISSILE(3), KNIVES(4), SHOWER(5);

        int index;


        PatternRate(int i) {
            index = i;
        }

        public int getIndex() {
            return index;
        }


    }

    public EnemyProjectileControl(ProjectileType type) {
        super();
        this.type = type;
        lastFireTime = new long[CONTROLS_NUM];
        for (int i = 0; i < lastFireTime.length; i++) {
            lastFireTime[i] = System.currentTimeMillis() / 1000;
        }
    }

    public void setPatternRate(PatternRate pattern, long rate) {
        patternRate[pattern.getIndex()] = rate;
    }

    public void addMissiles(long rate, int speed) {
        setPatternRate(PatternRate.MISSILE, rate);
        this.missileSpeed = speed;

    }

    public void addRing1by1(long rate, float ringAngle1by1) {
        setPatternRate(PatternRate.RING1BY1, rate);
        this.ringAngle1by1 = ringAngle1by1;
    }

    public void addSpawnToPlayer(long rate) {
        setPatternRate(PatternRate.toPlayer, rate);
    }

    public void addShower(long rate) {
        setPatternRate(PatternRate.SHOWER, rate);
    }

    public void spawnShowerV() {
        int i = PatternRate.SHOWER.getIndex();
        final long timeNow = System.currentTimeMillis();
        if (timeNow > lastFireTime[i] + patternRate[i] && patternRate[i] != 0) {
            Projectile projectile = new Projectile(new Point2D(showerSpawnX, Map.STARTING_Y + BLOCK_SIZE * 2), type, 90, true);
            GameViewManager.getMainPane().addToGamePane(projectile);
            lastFireTime[i] = timeNow;
            showerSpawnX = (showerSpawnX + 100);
            if (showerSpawnX > (WIDTH - STARTING_X)) {
                showerHorizontal = true;
                showerSpawnX = Map.STARTING_X;
            }
        }
    }

    public void spawnShowerH() {
        int i = PatternRate.SHOWER.getIndex();
        final long timeNow = System.currentTimeMillis();
        if (timeNow > lastFireTime[i] + patternRate[i] && patternRate[i] != 0) {
            Projectile projectile = new Projectile(new Point2D(Map.STARTING_X + BLOCK_SIZE * 2 + 3, showerSpawnY), type, 0, true);
            GameViewManager.getMainPane().addToGamePane(projectile);
            lastFireTime[i] = timeNow;
            showerSpawnY = showerSpawnY + 100;
            if (showerSpawnY > (HEIGHT - Map.STARTING_Y)) {
                showerHorizontal = false;
                showerSpawnY = (int) (Map.STARTING_Y + BLOCK_SIZE);
            }
        }
    }

    private void spawnMissile() {
        int i = PatternRate.MISSILE.getIndex();
        final long timeNow = System.currentTimeMillis();
        if (timeNow > lastFireTime[i] + patternRate[i] && patternRate[i] != 0) {
            Random random = new Random();
            double angle = random.nextInt(360);
            Projectile projectile = new Projectile(spawner, type, angle, true, true);

            int x = 50 + random.nextInt(WIDTH - 100);
            int y = 50 + random.nextInt(HEIGHT - 100);

            if (random.nextBoolean()) {
                if (random.nextBoolean()) {
                    x = 20;
                    angle = 90 - random.nextInt(180);
                } else {
                    x = WIDTH - 50;
                    angle = 90 + random.nextInt(180);
                }
            } else {
                if (random.nextBoolean()) {
                    y = 20;
                    angle = random.nextInt(180);
                } else {
                    y = HEIGHT - 50;
                    angle = -1 * random.nextInt(180);
                }
            }
            projectile.spawnProjectile(new Point2D(x, y), angle);
            if (angle < 90 && angle > -90) {
                projectile.setScaleX(-1);
            } else {
                projectile.setScaleX(1);

            }
            GameViewManager.getMainPane().addToGamePane(projectile);
            lastFireTime[i] = timeNow;

        }
    }

    public void addPulseBoss(long rate, float ringAngle) {
        addPulse(rate, ringAngle);
        this.boss = true;
    }

    public void addKnives(long rate, float knifeAngle) {
        setPatternRate(PatternRate.KNIVES, rate);
        this.knifeAngle = knifeAngle;
    }

    public void addPulse(long rate, float ringAngle) {
        setPatternRate(PatternRate.PULSE, rate);
        this.ringAngle = ringAngle;
    }

    public void spawnKnives() {
        int i = PatternRate.KNIVES.getIndex();
        final long timeNow = System.currentTimeMillis();
        if (timeNow > lastFireTime[i] + patternRate[i] && patternRate[i] != 0) {
            int changeDir = (timeNow % 20000 > 8000) ? -1 : 1;
            bossRingAngle += 8 * changeDir;
            for (int j = (int) bossRingAngle; j < 360 + (int) bossRingAngle; j += knifeAngle) {
                Projectile projectile = new Projectile(spawner, type, j, true);
                GameViewManager.getMainPane().addToGamePane(projectile);
            }
            lastFireTime[i] = timeNow;
        }

    }

    public void spawnPulse() {
        int i = PatternRate.PULSE.getIndex();
        final long timeNow = System.currentTimeMillis();
        ArrayList<Projectile> projArrTest = new ArrayList<>();
        if (timeNow > lastFireTime[i] + patternRate[i] && patternRate[i] != 0) {
            final double ang = boss ? bossRingAngle : angle;
            int changeDir = (timeNow % 20000 > 8000) ? -1 : 1;
            bossRingAngle += 0.4 * changeDir;
            for (int j = (int) ang; j < 360 + (int) ang; j += ringAngle) {
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

        spawnPulse();
        spawnRing1by1();
        spawnToPlayer();
        spawnMissile();
        spawnKnives();

        if (showerHorizontal) {
            spawnShowerH();
        } else {
            spawnShowerV();
        }
    }

    public void setSpawner(Point2D spawner) {
        this.spawner = spawner;
    }

    public ProjectileType getType() {
        return type;
    }
}
