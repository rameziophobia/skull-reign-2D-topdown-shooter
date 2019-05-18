package view;

import model.enemies.Boss;
import model.enemies.Enemy;
import model.enemies.ProjectileControlType;
import model.obstacles.Obstacle;
import model.projectiles.PowerUp;
import model.projectiles.PowerUpType;
import model.projectiles.ProjectileType;
import model.wall.Wall;

import java.util.ArrayList;

import static model.enemies.EnemyType.TANK_SAND;

public class LevelManager {//todo temp static

    private static final float SPAWN_CD_ENEMY = 1000 * 10000;
    private static final float SPAWN_CD_OBSTACLES = 1000 * 5f;
    private static final float SPAWN_CD_POWERUPS = 1000 * 20f;

    private static ArrayList<Enemy> enemyArrayList = new ArrayList<>();
    private static ArrayList<Wall> wallArrayList = new ArrayList<>();

    private static long nextEnemySpawnTime;
    private static long nextObstaclesSpawnTime; //todo dup code
    private static long nextPowerUpSpawnTime;
    private static boolean spawnable = true;

    private LevelManager() {
    }

    public static ArrayList<Enemy> getEnemyArrayList() {
        return enemyArrayList;
    }

    public static ArrayList<Wall> getWallArrayList() {
        return wallArrayList;
    }

    public static void createEnemies() {
        if (nextEnemySpawnTime < System.currentTimeMillis() && spawnable) {
            nextEnemySpawnTime = System.currentTimeMillis() + (long) (SPAWN_CD_ENEMY);

            Enemy enemy = new Enemy(TANK_SAND, ProjectileType.REDLASER01, ProjectileControlType.pulse200rx90a, Enemy.MoveMode.followPlayer);
            Boss boss = new Boss(Boss.EnemyStageEnum.STAGE3);
            enemyArrayList.add(boss);
            GameViewManager.getMainPane().addToGamePane(boss);
            enemyArrayList.add(enemy);
            GameViewManager.getMainPane().addToGamePane(enemy);
        }
    }

    public static void createObstacles() {//todo implement timer
        if (nextObstaclesSpawnTime < System.currentTimeMillis() && isSpawnable()) {
            nextObstaclesSpawnTime = System.currentTimeMillis() + (long) (SPAWN_CD_OBSTACLES);

            GameViewManager.getMainPane().addToGamePane(new Obstacle(1));
        }
    }

    public static void createPowerUp() {//todo implement timer
        if (nextPowerUpSpawnTime < System.currentTimeMillis()) {
            nextPowerUpSpawnTime = System.currentTimeMillis() + (long) (SPAWN_CD_POWERUPS);
            PowerUp powerUp;
            powerUp = new PowerUp(PowerUpType.getRandomPowerUpType());
            GameViewManager.getMainPane().addToGamePane(powerUp);
        }
    }

    public static void createWall() {
//        Wall rectangle = new Wall(1200, 200);
//        wallArrayList.add(rectangle);
//        GameViewManager.addGameObjectTOScene(rectangle);
    }

    public static void removeEnemy(Enemy enemy) {
        enemyArrayList.remove(enemy);
    }

    public static boolean isSpawnable() {
        return spawnable;
    }

    public static void setSpawnable(boolean state) {
        spawnable = state;
    }
}
