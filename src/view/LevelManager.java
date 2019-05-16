package view;

import javafx.geometry.Point2D;
import level.Level;
import model.enemies.Enemy;
import model.projectiles.PowerUp;
import model.projectiles.PowerUpType;
import model.tornado.Tornado;
import model.walls.Wall;

import java.util.ArrayList;
import java.util.List;

//import model.spawner.Spawner;

public class LevelManager {//todo temp static

    private static final float SPAWN_CD_ENEMY = 1000 * 10;
    private static final float SPAWN_CD_OBSTACLES = 1000 * 5f;
    private static final float SPAWN_CD_POWERUPS = 1000 * 20f;

    private static ArrayList<Enemy> enemyArrayList = new ArrayList<>();
    private static ArrayList<Wall> wallArrayList = new ArrayList<>();

    private static long nextEnemySpawnTime;
    private static long nextObstaclesSpawnTime; //todo dup code
    private static long nextPowerUpSpawnTime;
    private static boolean spawnable = true;

//    private static Spawner spawner;
    private static boolean initialized;
    private static int currentLevel;
    private static Level[] levels;

    private LevelManager() {
    }

    private static void initialize() {
        final List<Point2D> spawnPoints = new ArrayList<>();
        spawnPoints.add(new Point2D(250, 250));
        spawnPoints.add(new Point2D(750, 250));
        spawnPoints.add(new Point2D(250, 750));
        spawnPoints.add(new Point2D(750, 750));

        final List<Point2D> wallPos = new ArrayList<>();
        wallPos.add(new Point2D(300, 300));
        wallPos.add(new Point2D(800, 300));
        wallPos.add(new Point2D(300, 800));
        wallPos.add(new Point2D(800, 800));

        levels = new Level[2];

        levels[0] = new Level(
                2,
                5,
                1,
                2,
                3000,
                spawnPoints,
                wallPos
        );

        levels[1] = new Level(
                2,
                5,
                1,
                2,
                3000,
                spawnPoints,
                wallPos
        );

        createSpawner();
        initialized = true;
    }


    public static void addEnemy(Enemy enemy) {
        GameViewManager.addGameObjectTOScene(enemy);
        enemyArrayList.add(enemy);
    }

    public static void removeEnemy(Enemy enemy) {
        enemyArrayList.remove(enemy);
//        spawner.removeEnemy(enemy);
    }

    public static void update() {
//        System.out.println(currentLevel);
        if (!initialized)
            initialize();//temp

//        if (spawner.isDoneSpawning() && currentLevel+1 < levels.length)
//            incLevel();

        createEnemies();
//        createObstacles();
    }

    private static void incLevel() {
        currentLevel++;
        createSpawner();
    }

    public static ArrayList<Enemy> getEnemyArrayList() {
        return enemyArrayList;
    }

    public static ArrayList<Wall> getWallArrayList() {
        return wallArrayList;
    }

    public static void createEnemies() {
//        if (nextEnemySpawnTime < System.currentTimeMillis() && spawnable) {
//            nextEnemySpawnTime = System.currentTimeMillis() + (long) (SPAWN_CD_ENEMY);
//
//            Enemy enemy = new Enemy(TANK_SAND, ProjectileType.REDLASER01, Enemy.MoveMode.stationary);
//            enemy.getEnemyProjectileControl().addSpawnRing(3000, 90);
//            enemy.getEnemyProjectileControl().addRing1by1(300, 30);
//            enemyArrayList.add(enemy);
//            GameViewManager.addGameObjectTOScene(enemy);
//        }

//        spawner.spawn();
    }

    public static void createObstacles() {//todo implement timer
        if (nextObstaclesSpawnTime < System.currentTimeMillis() && isSpawnable()) {
            nextObstaclesSpawnTime = System.currentTimeMillis() + (long) (SPAWN_CD_OBSTACLES);

            GameViewManager.addGameObjectTOScene(new Tornado());
        }
    }
    public static void createPowerUp() {//todo implement timer
        if (nextPowerUpSpawnTime < System.currentTimeMillis()) {
            nextPowerUpSpawnTime = System.currentTimeMillis() + (long) (SPAWN_CD_POWERUPS);
            PowerUp powerUp;
            powerUp = new PowerUp(PowerUpType.getRandomPowerUpType());
            GameViewManager.addGameObjectTOScene(powerUp);
        }
    }
    public static void createWall() {
        Wall rectangle = new Wall(1200, 200);
        wallArrayList.add(rectangle);
        GameViewManager.addGameObjectTOScene(rectangle);
    }

    public static boolean isSpawnable() {
        return spawnable;
    }

    public static void setSpawnable(boolean state) {
        spawnable = state;
    }

    private static void createSpawner() {
        final Level level = levels[currentLevel];
//        spawner = new Spawner(
//                level.getNumOfWaves(),
//                level.getSpawnCD(),
//                level.getPowerMultiplier(),
//                level.getPowerAdder(),
//                true
//        );
//
//        spawner.getSpawnPoints().addAll(level.getSpawnPoints());

    }
}
