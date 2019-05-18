package view;

import controller.LevelManager;
import controller.map.Map;
import javafx.geometry.Point2D;
import model.enemies.Enemy;
import model.enemies.EnemyType;
import model.enemies.ProjectileControlType;
import model.projectiles.PowerUp;
import model.projectiles.PowerUpType;
import model.projectiles.ProjectileType;
import model.wall.Wall;

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

    private int currentEnemyIndex;
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

        final Map map = new Map("Endless");
        GameViewManager.getMainPane().addAllToFrontPane(map.getFrontNodes());
        GameViewManager.getMainPane().addAllToBackPane(map.getBackNodes());

        map.getWallNodes().forEach(node -> GameViewManager.getMainPane().addToGamePane(node));
        GameViewManager.getInstance().getWallArrayList().addAll(map.getWallNodes());

        GameViewManager.getPlayer().endlessStats();
        sortedEnemyTypes = Arrays.asList(EnemyType.values());
        sortedEnemyTypes.sort(Comparator.comparingInt(EnemyType::getPOWER));

        sortedProjectileControlType = Arrays.asList(ProjectileControlType.values());
        sortedProjectileControlType.sort(Comparator.comparingInt(ProjectileControlType::getPOWER));

        this.spawnPoints = new ArrayList<>();
        this.random = new Random();
        this.enemies = new ArrayList<>();
        this.spawnedEnemies = new ArrayList<>();

        Enemy.setMapLimits( Map.BLOCK_SIZE + Map.STARTING_X,
                Map.STARTING_X + (Map.MAP_BLOCKS_WIDTH - 1) * Map.BLOCK_SIZE,
                Map.BLOCK_SIZE * 3 + Map.STARTING_Y,
                Map.STARTING_Y + (Map.MAP_BLOCKS_HEIGHT - 1) * Map.BLOCK_SIZE);
        prepEnemies();

    }
    public List<Point2D> getSpawnPoints() {
        return spawnPoints;
    }
    private void prepEnemies() {
            final double totalPower =   25+(10 * (1.5 * currentX + (Math.sin(2 * currentX) / 2)));
            currentX++;

            enemies.add(new LinkedList<>());
            enemies.get(k).add((new Enemy(sortedEnemyTypes.get(0),ProjectileType.FIRE,sortedProjectileControlType.get(0),Enemy.MoveMode.FOLLOW_PLAYER)));
            for (int i = sortedEnemyTypes.size() - 1; i > 0; i--) {
                final EnemyType enemyType = sortedEnemyTypes.get(i);
                for(int j = sortedProjectileControlType.size() -1 ; j>0; j--){
                    final ProjectileControlType projectileControlType = sortedProjectileControlType.get(j);
                    if(projectileControlType.getPOWER()+enemyType.getPOWER()<=totalPower){
                        for (int l = 0; l < (int) (totalPower / (enemyType.getPOWER()+projectileControlType.getPOWER())); l++) {
                            enemies.get(k).add(random.nextInt(enemies.get(k).size()),(new Enemy(enemyType,ProjectileType.FIRE,projectileControlType,Enemy.MoveMode.FOLLOW_PLAYER)));
                        }
                    }
                }
            }
            k++;
    }

    public void spawn() {
        if (nextSpawnTime < System.currentTimeMillis()) {
            nextSpawnTime = System.currentTimeMillis() + spawnCD;
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

    private void createTorandoes() {

    }

    private void createPowerUp() {
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


    @Override
    public void removeEnemy(Enemy enemy) {
        spawnedEnemies.remove(enemy);
    }
    @Override
    public void update() {
        GameViewManager.getInstance().getGameUI().getWaveLabel().setUICounter(currentWave);
        if(k < 30){
            prepEnemies();
        }
        spawn();
        createPowerUp();
    }
}