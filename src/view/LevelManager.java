package view;

import javafx.geometry.Point2D;
import model.enemies.Enemy;
import model.obstacles.Obstacle;
import model.spawners.Spawner;

import java.util.ArrayList;
import java.util.List;

public class LevelManager {//todo temp static

    private static final float SPAWN_CD_ENEMY = 1000 * 10;
    private static final float SPAWN_CD_OBSTACLES = 1000 * 5f;

    private static ArrayList<Enemy> enemyArrayList = new ArrayList<>();

    private static long nextEnemySpawnTime;
    private static long nextObstaclesSpawnTime; //todo dup code

    private static Spawner spawner;
    private static boolean initialized;

    private LevelManager() {
    }

    public static ArrayList<Enemy> getEnemyArrayList() {
        return enemyArrayList;
    }

    public static void createEnemies() {
//        if (nextEnemySpawnTime < System.currentTimeMillis()) {
//            nextEnemySpawnTime = System.currentTimeMillis() + (long) (SPAWN_CD_ENEMY);
//
//            Enemy enemy = new Enemy(TANK_SAND);
//            enemyArrayList.add(enemy);
//            GameViewManager.addGameObjectTOScene(enemy);
//        }
        if (!initialized)
            initialize();//temp
        spawner.spawn();
    }

    private static void initialize() {
        spawner = new Spawner(true);

        final List<Point2D> spawnPoints = spawner.getSpawnPoints();
        spawnPoints.add(new Point2D(250, 250));
        spawnPoints.add(new Point2D(750, 250));
        spawnPoints.add(new Point2D(250, 750));
        spawnPoints.add(new Point2D(750, 750));

        initialized = true;
    }

    public static void createObstacles() {//todo implement timer
        if (nextObstaclesSpawnTime < System.currentTimeMillis()) {
            nextObstaclesSpawnTime = System.currentTimeMillis() + (long) (SPAWN_CD_OBSTACLES);

            GameViewManager.addGameObjectTOScene(new Obstacle());
        }
    }

    public static void removeEnemy(Enemy enemy) {
        enemyArrayList.remove(enemy);
    }
}
