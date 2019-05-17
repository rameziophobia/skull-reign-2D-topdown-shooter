package controller;

import controller.map.Map;
import controller.map.MapLoader;
import model.enemies.Enemy;
import model.enemies.EnemyType;
import model.level.Level;
import model.level.Wave;
import model.player.Player;
import model.projectiles.ProjectileType;
import model.spawner.SpawnPoint;
import model.wall.Wall;
import view.GameViewManager;

import java.util.ArrayList;

public class LevelManager {
    private final ArrayList<Enemy> enemyArrayList;
    private final ArrayList<Wall> wallArrayList;

    private Level[] levels;

    private int currentLevelIndex;
    private int currentWaveIndex;
    private int currentEnemyIndex;
    private long nextEnemySpawnTime;
    private Level currentLevel;
    private ArrayList<SpawnPoint> currentSpawnPoints;

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
                new MapLoader(Map.LEVEL_01)
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
                new MapLoader(Map.LEVEL_02)
        );

        loadLevel();
    }

    public void addEnemy(Enemy enemy) {
        GameViewManager.getMainPane().addToGamePane(enemy);
        enemyArrayList.add(enemy);
    }

    public void removeEnemy(Enemy enemy) {
        enemyArrayList.remove(enemy);
    }

    public void update() {
        if (currentEnemyIndex == levels[currentLevelIndex].getWaves()[currentWaveIndex].getEnemies().length
                && enemyArrayList.size() == 0) {
            if (currentWaveIndex + 1 == levels[currentLevelIndex].getWaves().length) {
                if (currentLevelIndex + 1 < levels.length) {
//                    System.out.println("lvl Increased " + currentLevelIndex);
//                    System.out.println("Wave reset " + currentWaveIndex);
                    currentLevelIndex++;
                    currentWaveIndex = 0;
                    currentEnemyIndex = 0;
                    loadLevel();
                }
            } else {
//                System.out.println("Wave Increased " + currentWaveIndex);
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


        if (currentLevelIndex != 0) {//todo -.-
            final MapLoader mapLoader = levels[currentLevelIndex - 1].getMapLoader();
            GameViewManager.getMainPane().removeAllFromFrontPane(mapLoader.getFrontNodes());
            GameViewManager.getMainPane().removeAllFromBackPane(mapLoader.getBackNodes());

            System.out.println(mapLoader.getBackNodes().size());

            mapLoader.getWallNodes().forEach(node -> GameViewManager.getMainPane().removeFromGamePane(node));
            wallArrayList.removeAll(mapLoader.getWallNodes());
            mapLoader.getSpawnPointsNodes().forEach(node -> GameViewManager.getMainPane().removeFromGamePane(node));
        }

        currentLevel = levels[currentLevelIndex];
        final MapLoader mapLoader = currentLevel.getMapLoader();
        GameViewManager.getMainPane().addAllToFrontPane(mapLoader.getFrontNodes());
        GameViewManager.getMainPane().addAllToBackPane(mapLoader.getBackNodes());

        mapLoader.getWallNodes().forEach(node -> GameViewManager.getMainPane().addToGamePane(node));
        wallArrayList.addAll(mapLoader.getWallNodes());
        currentSpawnPoints = mapLoader.getSpawnPointsNodes();
        currentSpawnPoints.forEach(node -> {
            GameViewManager.getMainPane().addToGamePane(node);
            node.setActive(true);
        });
    }

    private void createEnemies() {
        if (nextEnemySpawnTime < System.currentTimeMillis()) {
            final Wave wave = currentLevel.getWaves()[currentWaveIndex];// todo cache

            nextEnemySpawnTime = System.currentTimeMillis() + wave.getTimeBetweenSpawns();
            for (SpawnPoint spawnPoint : currentSpawnPoints) {
                if (currentEnemyIndex < wave.getEnemies().length) {
                    spawnPoint.setSpawning(true);

//                    System.out.println("Spawning Enemy " + currentEnemyIndex);
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

    public ArrayList<Enemy> getEnemyArrayList() {
        return enemyArrayList;
    }

    public ArrayList<Wall> getWallArrayList() {
        return wallArrayList;
    }
}
