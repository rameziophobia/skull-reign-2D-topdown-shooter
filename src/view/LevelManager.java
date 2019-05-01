package view;

import model.enemies.Enemy;
import model.enemies.EnemyType;
import model.obstacles.Obstacle;
import model.projectiles.ProjectileType;

import java.util.ArrayList;
import java.util.HashMap;

import static model.enemies.EnemyType.*;

public class LevelManager {//todo temp static

//    private static final float SPAWN_CD_ENEMY = 1000 * 10;
//    private static final float SPAWN_CD_OBSTACLES = 1000 * 5f;

    private static ArrayList<Enemy> enemyArrayList = new ArrayList<>();
    private static HashMap<EnemyType, Long> nextEnemySpawnTime = new HashMap<>();
    private static long nextObstaclesSpawnTime; //todo dup code
    private static int level = 1;

    private LevelManager() {
    }

    public static ArrayList<Enemy> getEnemyArrayList() {
        return enemyArrayList;
    }

    public static void createEnemies(float spawn_cd, EnemyType enemyType, ProjectileType projectileType,
                                     double ringRate, double ringRate1by1, double toPlayerRate) {

        nextEnemySpawnTime.putIfAbsent(enemyType,(long)spawn_cd + System.currentTimeMillis());

        if (nextEnemySpawnTime.get(enemyType) < System.currentTimeMillis()) {
            nextEnemySpawnTime.put(enemyType,System.currentTimeMillis() + (long) (spawn_cd));

            Enemy enemy = new Enemy(enemyType, projectileType,
                    ringRate,ringRate1by1, toPlayerRate);

            enemyArrayList.add(enemy);
            GameViewManager.addGameObjectTOScene(enemy);
        }
    }

    public static void createObstacles(float spawn_cd) {
        if (nextObstaclesSpawnTime < System.currentTimeMillis()) {
            nextObstaclesSpawnTime = System.currentTimeMillis() + (long) (spawn_cd);

            GameViewManager.addGameObjectTOScene(new Obstacle());
        }
    }

    public static void removeEnemy(Enemy enemy) {
        enemyArrayList.remove(enemy);
    }

    public static void start_levels() {

        switch (level){
            case 1:
            {
                level = level1();
                break;
            }
        }
    }


    public static int level1(){
        createEnemies(1000 * 4f, TANK_SAND,ProjectileType.ICEICLE,
                -1,-1,0.5);

        createEnemies(1000 * 7f, TANK_RED,ProjectileType.FIREBALL,
                -1,0.2,-1);

        createEnemies(1000 * 10f, TANK_DARK,ProjectileType.SHOCK,
                5,-1,-1);
        LevelManager.createObstacles(1000 * 5f);
        return 1;
    }
}
