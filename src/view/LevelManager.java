package view;

        import model.enemies.Enemy;
        import model.enemies.EnemyType;
        import model.obstacles.Obstacle;
        import model.projectiles.ProjectileType;
        import model.walls.Wall;

        import java.util.ArrayList;
        import java.util.HashMap;

        import static model.enemies.EnemyType.*;

public class LevelManager {//todo temp static

//    private static final float SPAWN_CD_ENEMY = 1000 * 10;
//    private static final float SPAWN_CD_OBSTACLES = 1000 * 5f;

    private static ArrayList<Enemy> enemyArrayList = new ArrayList<>();
    private static ArrayList<Wall> wallArrayList = new ArrayList<>();
    private static HashMap<Integer, Long> nextEnemySpawnTime = new HashMap<>();
    private static long nextObstaclesSpawnTime; //todo dup code
    private static int level = 1;
    private static int enemiesSpawned = 0; //todo this is stupid
    private static boolean initLevel = true;

    private LevelManager() {
    }

    public static ArrayList<Enemy> getEnemyArrayList() {
        return enemyArrayList;
    }

    public static ArrayList<Wall> getWallArrayList() {
        return wallArrayList;
    }

    private static void addEnemy(float spawn_cd, Enemy enemy, int id) {

        nextEnemySpawnTime.putIfAbsent(id, (long) spawn_cd + System.currentTimeMillis());

        if (nextEnemySpawnTime.get(id) < System.currentTimeMillis()) {
            nextEnemySpawnTime.put(id, System.currentTimeMillis() + (long) (spawn_cd));

            enemyArrayList.add(enemy);
            GameViewManager.addGameObjectTOScene(enemy);
            enemiesSpawned++;
            System.out.println(enemiesSpawned);
        }
    }

    private static void createObstacles(float spawn_cd) {
        if (nextObstaclesSpawnTime < System.currentTimeMillis()) {
            nextObstaclesSpawnTime = System.currentTimeMillis() + (long) (spawn_cd);

            GameViewManager.addGameObjectTOScene(new Obstacle());
        }
    }

    public static void removeEnemy(Enemy enemy) {
        enemyArrayList.remove(enemy);
    }

    public static void startLevels() {//todo not the best idea to switch levels

        switch (level) {
            case 1: {
                level1();
                break;
            }
            case 2: {
//                level2();
                break;
            }
            case 3: {
//                level3();
                break;
            }
            default:
            case 4: {
//                level4();
                break;
            }
        }
    }


    private static void level1() {

        if (initLevel) {
            Wall rectangle = new Wall(1200,200);
            wallArrayList.add(rectangle);
            GameViewManager.addGameObjectTOScene(rectangle);
            initLevel = false;
        }

        Enemy en1 = new Enemy(TANK_SAND, ProjectileType.REDLASER01, Enemy.MoveMode.stationary);
        en1.getEnemyProjectileControl().addSpawnRing(3000,90);
        en1.getEnemyProjectileControl().addRing1by1(300,30);
        addEnemy(1000 * 5f, en1, 1);

        if (enemiesSpawned > 3) {
            Enemy en2 = new Enemy(TANK_SAND, ProjectileType.REDLASER01, Enemy.MoveMode.followPlayer);
            en2.getEnemyProjectileControl().addSpawnToPlayer(500);
            addEnemy(1000 * 5f, en2, 2);
        }

        LevelManager.createObstacles(1000 * 15f);

        if (enemiesSpawned > 10) {
            level++;
            initLevel = true;
//            nextEnemySpawnTime.keySet().forEach(k ->
//                    nextEnemySpawnTime.put(k, nextEnemySpawnTime.get(k) + 1000 * 1000L) ); //todo break been el levels
            //todo open gate to the next level ama el player yod5ol feha regains HP, n3ml lvl++
            //todo we hna tb el commented code
        }
    }
//
//    private static void level2() {
//
//        if (initLevel) {
//            clearLevel();
//            initLevel = false;
//        }
//        addEnemy(1000 * 4f, TANK_SAND, ProjectileType.REDLASER01, 1);
//
//        addEnemy(1000 * 4f, TANK_SAND, ProjectileType.REDLASER01, 2);
//
//        addEnemy(1000 * 7f, TANK_RED, ProjectileType.FIREBALL,3);
//
//        addEnemy(1000 * 12f, TANK_DARK, ProjectileType.SHOCK, 4);
//
//        LevelManager.createObstacles(1000 * 15f);
//
//        if (enemiesSpawned > 80) {
//            level++;
//        }
//    }
//
//    private static void level3() {
//
//        if (initLevel) {
//            initLevel = false;
//        }
//        addEnemy(1000 * 4f, TANK_SAND, ProjectileType.REDLASER01, 1);
//
//        addEnemy(1000 * 4f, TANK_SAND, ProjectileType.REDLASER01, 2);
//
//        addEnemy(1000 * 7f, TANK_RED, ProjectileType.FIREBALL, 3);
//
//        addEnemy(1000 * 10f, TANK_DARK,
//                Math.random() > 0.5 ? ProjectileType.SHOCK : ProjectileType.ELECTRIC, 4);
//
//        addEnemy(1000 * 10f, TANK_DARK,
//                Math.random() > 0.5 ? ProjectileType.SHOCK : ProjectileType.ELECTRIC, 5);
//
//        LevelManager.createObstacles(1000 * 10f);
//
//        if (enemiesSpawned > 140) {
//            level++;
//        }
//    }
//
//    private static void level4() {
//
//        if (initLevel) {
//            initLevel = false;
//        }
//
//        addEnemy(1000 * 5, TANK_SAND, ProjectileType.REDLASER01, 1);
//
//        addEnemy(1000 * 7f, TANK_RED, ProjectileType.FIREBALL, 3);
//
//        addEnemy(1000 * 8f, TANK_DARK,
//                Math.random() > 0.5 ? ProjectileType.ICEICLE : ProjectileType.ELECTRIC, 4);
//
//        addEnemy(1000 * 8f, TANK_DARK,
//                Math.random() > 0.5 ? ProjectileType.ICEICLE : ProjectileType.ELECTRIC, 5);
//
//        ProjectileType largeTankProjectile = Math.random() > 0.5 ? ProjectileType.SHOCK : ProjectileType.ELECTRIC;
//
//        addEnemy(1000 * 9f, TANK_DARK_LARGE,
//                largeTankProjectile, 6);
//
//        largeTankProjectile = Math.random() > 0.5 ? ProjectileType.SHOCK : ProjectileType.ELECTRIC;
//
//        addEnemy(1000 * 9f, TANK_DARK_LARGE,
//                largeTankProjectile, 7);
//
//        LevelManager.createObstacles(1000 * 10f);
//
//        if (enemiesSpawned > 220) {
//            level++;
//        }
//    }

    private static void clearLevel(){
        for (Wall wall:wallArrayList ) {
            GameViewManager.removeGameObjectFromScene(wall);
        }
        wallArrayList.clear();
    }
}
