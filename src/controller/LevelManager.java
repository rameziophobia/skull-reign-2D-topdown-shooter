package controller;

import model.enemies.Enemy;
import model.enemies.EnemyType;
import model.level.Level;
import model.level.Wave;
import model.player.Player;
import model.projectiles.ProjectileType;
import model.spawner.SpawnPoint;
import model.walls.Wall;
import view.GameViewManager;

import java.util.ArrayList;

public class LevelManager {

    private final ArrayList<Enemy> enemyArrayList;
    private final ArrayList<Wall> wallArrayList;

    private boolean spawnable = true;//todo ?

    private Level[] levels;

    private int currentLevelIndex;
    private int currentWaveIndex;
    private int currentEnemyIndex;
    private long nextEnemySpawnTime;

    public LevelManager() {
        enemyArrayList = new ArrayList<>();
        wallArrayList = new ArrayList<>();

        levels = new Level[2];

        levels[0] = new Level(
                new Wave[]{
                        new Wave(
                                new Enemy[]{
                                        new Enemy(EnemyType.TANK_RED, ProjectileType.GREENLASER01, Enemy.MoveMode.followPlayer),
                                        new Enemy(EnemyType.TANK_RED, ProjectileType.GREENLASER01, Enemy.MoveMode.followPlayer),
                                        new Enemy(EnemyType.TANK_RED, ProjectileType.GREENLASER01, Enemy.MoveMode.followPlayer),
                                        new Enemy(EnemyType.TANK_RED, ProjectileType.GREENLASER01, Enemy.MoveMode.followPlayer),
                                        new Enemy(EnemyType.TANK_RED, ProjectileType.GREENLASER01, Enemy.MoveMode.followPlayer),
                                        new Enemy(EnemyType.TANK_RED, ProjectileType.GREENLASER01, Enemy.MoveMode.followPlayer)
                                },
                                3000,
                                5,
                                6000
                        ),
                        new Wave(
                                new Enemy[]{
                                        new Enemy(EnemyType.TANK_RED, ProjectileType.GREENLASER01, Enemy.MoveMode.followPlayer),
                                        new Enemy(EnemyType.TANK_RED, ProjectileType.GREENLASER01, Enemy.MoveMode.followPlayer),
                                        new Enemy(EnemyType.TANK_RED, ProjectileType.GREENLASER01, Enemy.MoveMode.followPlayer),
                                        new Enemy(EnemyType.TANK_RED, ProjectileType.GREENLASER01, Enemy.MoveMode.followPlayer),
                                        new Enemy(EnemyType.TANK_RED, ProjectileType.GREENLASER01, Enemy.MoveMode.followPlayer),
                                        new Enemy(EnemyType.TANK_RED, ProjectileType.GREENLASER01, Enemy.MoveMode.followPlayer)
                                },
                                3000,
                                5,
                                6000
                        )
                },
                10000,
                new Wall[]{
                        new Wall(GameViewManager.WIDTH / 4, GameViewManager.HEIGHT / 4),
                        new Wall(GameViewManager.WIDTH * 3 / 4, GameViewManager.HEIGHT / 4),
                        new Wall(GameViewManager.WIDTH / 4, GameViewManager.HEIGHT * 3 / 4),
                        new Wall(GameViewManager.WIDTH * 3 / 4, GameViewManager.HEIGHT * 3 / 4)
                },
                new SpawnPoint[]{
                        new SpawnPoint(200, GameViewManager.CENTER_Y),
                        new SpawnPoint(GameViewManager.CENTER_X, GameViewManager.CENTER_Y),
                        new SpawnPoint(GameViewManager.CENTER_X, 200),
                        new SpawnPoint(200, 200)
                }
        );

        levels[1] = new Level(
                new Wave[]{
                        new Wave(
                                new Enemy[]{
                                        new Enemy(EnemyType.TANK_RED, ProjectileType.GREENLASER01, Enemy.MoveMode.followPlayer),
                                        new Enemy(EnemyType.TANK_RED, ProjectileType.GREENLASER01, Enemy.MoveMode.followPlayer),
                                        new Enemy(EnemyType.TANK_RED, ProjectileType.GREENLASER01, Enemy.MoveMode.followPlayer),
                                        new Enemy(EnemyType.TANK_RED, ProjectileType.GREENLASER01, Enemy.MoveMode.followPlayer),
                                        new Enemy(EnemyType.TANK_RED, ProjectileType.GREENLASER01, Enemy.MoveMode.followPlayer),
                                        new Enemy(EnemyType.TANK_RED, ProjectileType.GREENLASER01, Enemy.MoveMode.followPlayer)
                                },
                                3000,
                                5,
                                6000
                        ),
                        new Wave(
                                new Enemy[]{
                                        new Enemy(EnemyType.TANK_RED, ProjectileType.GREENLASER01, Enemy.MoveMode.followPlayer),
                                        new Enemy(EnemyType.TANK_RED, ProjectileType.GREENLASER01, Enemy.MoveMode.followPlayer),
                                        new Enemy(EnemyType.TANK_RED, ProjectileType.GREENLASER01, Enemy.MoveMode.followPlayer),
                                        new Enemy(EnemyType.TANK_RED, ProjectileType.GREENLASER01, Enemy.MoveMode.followPlayer),
                                        new Enemy(EnemyType.TANK_RED, ProjectileType.GREENLASER01, Enemy.MoveMode.followPlayer),
                                        new Enemy(EnemyType.TANK_RED, ProjectileType.GREENLASER01, Enemy.MoveMode.followPlayer)
                                },
                                3000,
                                5,
                                6000
                        )
                },
                10000,
                new Wall[]{
                        new Wall(GameViewManager.WIDTH / 4, GameViewManager.HEIGHT / 4),
                        new Wall(GameViewManager.WIDTH * 3 / 4, GameViewManager.HEIGHT / 4),
                        new Wall(GameViewManager.WIDTH / 4, GameViewManager.HEIGHT * 3 / 4),
                },
                new SpawnPoint[]{
                        new SpawnPoint(200, GameViewManager.CENTER_Y),
                        new SpawnPoint(GameViewManager.CENTER_X, GameViewManager.CENTER_Y),
                }
        );

        loadLevel();
    }

    public void addEnemy(Enemy enemy) {
        GameViewManager.addToScene(enemy);
        enemyArrayList.add(enemy);
    }

    public void removeEnemy(Enemy enemy) {
        enemyArrayList.remove(enemy);
    }

    public void update() {
        if (currentEnemyIndex == levels[currentLevelIndex].getWaves()[currentWaveIndex].getEnemies().length
                && enemyArrayList.size() == 0) {
            if (currentWaveIndex + 1 == levels[currentLevelIndex].getWaves().length) {
                if (currentLevelIndex+1 < levels.length) {
                    System.out.println("lvl Increased " + currentLevelIndex);
                    currentLevelIndex++;
                    System.out.println("Wave reset " + currentWaveIndex);
                    currentWaveIndex = 0;
                    currentEnemyIndex = 0;
                    loadLevel();
                }
            } else {
                System.out.println("Wave Increased " + currentWaveIndex);
                currentWaveIndex++;
                currentEnemyIndex = 0;
            }
        }

        createEnemies();
    }

    private void loadLevel() {
        Player player = GameViewManager.getPlayer();
        player.setLayoutX(GameViewManager.CENTER_X);
        player.setLayoutY(GameViewManager.CENTER_Y);

        if (currentLevelIndex != 0)//todo -.-
            for (SpawnPoint spawnPoint : levels[currentLevelIndex - 1].getSpawnPoints()) {
                GameViewManager.removeFromScene(spawnPoint);
            }
        for (SpawnPoint spawnPoint : levels[currentLevelIndex].getSpawnPoints()) {
            GameViewManager.addToScene(spawnPoint);
            spawnPoint.setActive(true);//todo temp
        }

        if (currentLevelIndex != 0)//todo -.-
            for (Wall wall : levels[currentLevelIndex - 1].getWalls()) {
                wallArrayList.remove(wall);
                GameViewManager.removeFromScene(wall);
            }
        for (Wall wall : levels[currentLevelIndex].getWalls()) {
            wallArrayList.add(wall);
            GameViewManager.addToScene(wall);
        }
    }

    private void createEnemies() {
        if (nextEnemySpawnTime < System.currentTimeMillis()) {
            final Wave wave = levels[currentLevelIndex].getWaves()[currentWaveIndex];// todo cache
            final SpawnPoint[] spawnPoints = levels[currentLevelIndex].getSpawnPoints();

            nextEnemySpawnTime = System.currentTimeMillis() + wave.getTimeBetweenSpawns();
            for (SpawnPoint spawnPoint : spawnPoints) {
                if (currentEnemyIndex < wave.getEnemies().length) {
                    spawnPoint.setSpawning(true);

                    System.out.println("Spawning Enemy " + currentEnemyIndex);
                    final Enemy enemy = wave.getEnemies()[currentEnemyIndex++];
                    enemy.setLayoutX(spawnPoint.getSpawnPointX());
                    enemy.setLayoutY(spawnPoint.getSpawnPointY());
                    addEnemy(enemy);
                }
            }
        }
    }

    public void createObstacles() {
    }

    public void createPowerUp() {
    }

    public boolean isSpawnable() {
        return spawnable;
    }

    public void setSpawnable(boolean state) {
        spawnable = state;
    }

    public ArrayList<Enemy> getEnemyArrayList() {
        return enemyArrayList;
    }

    public ArrayList<Wall> getWallArrayList() {
        return wallArrayList;
    }
}
