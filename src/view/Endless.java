package view;

import controller.LevelManager;
import controller.map.MapLoader;
import javafx.geometry.Point2D;
import model.enemies.Enemy;
import model.enemies.EnemyType;
import model.enemies.ProjectileControlType;
import model.projectiles.PowerUp;
import model.projectiles.PowerUpType;
import model.projectiles.ProjectileType;
import model.tornado.Tornado;
import model.wall.Wall;
import controller.map.Map;

import java.util.*;

public class Endless extends LevelManager {
    private final List<Point2D> spawnPoints;

    private final Random random;
    private final List<EnemyType> sortedEnemyTypes;
    private final List<ProjectileControlType> sortedProjectileControlType;
    private final List<List<Enemy>> enemies;
    private final List<Enemy> spawnedEnemies;
    private final long spawnCD;
    private final long powerUpCD = 20000;

    private boolean debug;

    private int currentEnemyIndex; //todo temp?
    private int currentWave=1;
    private int currentX=1;
    private int k;

    private long nextSpawnTime;
    private long nextPowerUPSpawnTime;
    private static ArrayList<Enemy> enemyArrayList = new ArrayList<>();
    private static ArrayList<Wall> wallArrayList = new ArrayList<>();

    
    public Endless(long spawnCD, boolean debug) {
        this.debug = debug;
        this.spawnCD = spawnCD;


        sortedEnemyTypes = Arrays.asList(EnemyType.values());
        sortedEnemyTypes.sort(Comparator.comparingInt(EnemyType::getPOWER));

        sortedProjectileControlType = Arrays.asList(ProjectileControlType.values());
        sortedProjectileControlType.sort(Comparator.comparingInt(ProjectileControlType::getPOWER));

        this.spawnPoints = new ArrayList<>();
        this.random = new Random();
        this.enemies = new ArrayList<>();
        this.spawnedEnemies = new ArrayList<>();

        Enemy.setMapLimits( MapLoader.BLOCK_SIZE + MapLoader.STARTING_X,
                MapLoader.STARTING_X + (MapLoader.MAP_BLOCKS_WIDTH - 1) * MapLoader.BLOCK_SIZE,
                MapLoader.BLOCK_SIZE * 3 + MapLoader.STARTING_Y,
                MapLoader.STARTING_Y + (MapLoader.MAP_BLOCKS_HEIGHT - 1) * MapLoader.BLOCK_SIZE);
        prepEnemies();

    }
    public List<Point2D> getSpawnPoints() {
        return spawnPoints;
    }
    private void prepEnemies() {
            final double totalPower =   25+(10 * (1.5 * currentX + (Math.sin(2 * currentX) / 2)));
            System.out.println(totalPower);
            currentX++;

            //todo dups ?
            enemies.add(new LinkedList<>());
            enemies.get(k).add((new Enemy(sortedEnemyTypes.get(0),ProjectileType.FIRE,sortedProjectileControlType.get(0),Enemy.MoveMode.followPlayer)));
            for (int i = sortedEnemyTypes.size() - 1; i > 0; i--) {
                final EnemyType enemyType = sortedEnemyTypes.get(i);
                for(int j = sortedProjectileControlType.size() -1 ; j>0; j--){
                    final ProjectileControlType projectileControlType = sortedProjectileControlType.get(j);
                    if(projectileControlType.getPOWER()+enemyType.getPOWER()<=totalPower){
                        for (int l = 0; l < (int) (totalPower / (enemyType.getPOWER()+projectileControlType.getPOWER())); l++) {
                            enemies.get(k).add(random.nextInt(enemies.get(k).size()),(new Enemy(enemyType,ProjectileType.FIRE,projectileControlType,Enemy.MoveMode.followPlayer)));
                        }
                    }
                }
            }
            k++;
    }

    public void spawn() {
        if (nextSpawnTime < System.currentTimeMillis()) {
            nextSpawnTime = System.currentTimeMillis() + spawnCD;
            System.out.println(currentWave);
            System.out.println(currentEnemyIndex);
            System.out.println(spawnedEnemies.size());
            if (currentEnemyIndex < enemies.get(currentWave).size()) {
                final Enemy enemy = enemies.get(currentWave).get(currentEnemyIndex++);

                spawnedEnemies.add(enemy);
                GameViewManager.getMainPane().addToGamePane(enemy);

            } else if (spawnedEnemies.size() == 0) {
                currentEnemyIndex = 0;
                currentWave++;
                spawn();
            }
        }
    }
//    private Enemy getEnemyRandomPos() {
//        final Enemy enemy = enemies.get(currentWave).get(currentEnemyIndex++);
//        enemy.setLayoutX(random.nextInt(GameViewManager.HEIGHT));
//        enemy.setLayoutY(random.nextInt(GameViewManager.HEIGHT));
//        return enemy;
//    }

    private void createTorandoes() {//todo implement timer

    }

    private void createPowerUp() {//todo implement timer
        if (nextPowerUPSpawnTime < System.currentTimeMillis()) {
            nextPowerUPSpawnTime = System.currentTimeMillis() + powerUpCD-currentWave*20;
            PowerUp powerUp = new PowerUp(PowerUpType.getRandomPowerUpType());
            GameViewManager.getMainPane().addToGamePane(powerUp);
        }
    }


    public int getCurrentWave() {
        return currentWave;
    }
    @Override
    public List<Enemy>getEnemyArrayList() {
        return spawnedEnemies;
    }
    @Override
    public ArrayList<Wall> getWallArrayList() {
        return wallArrayList;
    }
    public void addWallArrayList(Wall wall) {
        wallArrayList.add(wall);
    }

    @Override
    public void reduceNumOfTornado(){

    }

    @Override
    public void removeEnemy(Enemy enemy) {
        spawnedEnemies.remove(enemy);
    }
    @Override
    public void update() {
        GameViewManager.getInstance().getGameUI().getWaveLabel().setUICounter(currentWave);

        if(k < 50){
            prepEnemies();
        }
        spawn();
        createPowerUp();
    }
}