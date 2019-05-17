package controller;

import controller.map.Map;
import controller.map.MapLoader;
import model.enemies.Enemy;
import model.enemies.EnemyType;
import model.level.Level;
import model.player.Player;
import model.projectiles.ProjectileType;
import model.spawner.SpawnPoint;
import model.tornado.Tornado;
import model.ui.game.CounterLabel;
import model.wall.Wall;
import view.GameViewManager;

import java.util.ArrayList;
import java.util.List;

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

    private CounterLabel levelLabel, waveLabel;
    private long nextTornadoSpawnTime;
    private List<Tornado> currentTornadoList;
    private Enemy[] currentWave;

    public LevelManager(CounterLabel levelLabel, CounterLabel waveLabel) {
        this.levelLabel = levelLabel;
        this.waveLabel = waveLabel;

        enemyArrayList = new ArrayList<>();
        wallArrayList = new ArrayList<>();
        currentTornadoList = new ArrayList<>();

        Tornado.setMapLimits(
                MapLoader.BLOCK_SIZE + MapLoader.STARTING_X,
                MapLoader.STARTING_X + (MapLoader.MAP_BLOCKS_WIDTH - 1) * MapLoader.BLOCK_SIZE,
                MapLoader.BLOCK_SIZE * 2 + MapLoader.STARTING_Y,
                MapLoader.STARTING_Y + (MapLoader.MAP_BLOCKS_HEIGHT - 1) * MapLoader.BLOCK_SIZE
        );

        levels = new Level[2];

        levels[0] = new Level(
                new Enemy[][]{
                        new Enemy[]{
                                new Enemy(EnemyType.TANK_RED, ProjectileType.GREENLASER01, Enemy.MoveMode.followPlayer),
                                new Enemy(EnemyType.TANK_RED, ProjectileType.GREENLASER01, Enemy.MoveMode.followPlayer),
                                new Enemy(EnemyType.TANK_RED, ProjectileType.GREENLASER01, Enemy.MoveMode.followPlayer),
                                new Enemy(EnemyType.TANK_RED, ProjectileType.GREENLASER01, Enemy.MoveMode.followPlayer),
                                new Enemy(EnemyType.TANK_RED, ProjectileType.GREENLASER01, Enemy.MoveMode.followPlayer),
                                new Enemy(EnemyType.TANK_RED, ProjectileType.GREENLASER01, Enemy.MoveMode.followPlayer)
                        },
                        new Enemy[]{
                                new Enemy(EnemyType.TANK_RED, ProjectileType.GREENLASER01, Enemy.MoveMode.followPlayer),
                                new Enemy(EnemyType.TANK_RED, ProjectileType.GREENLASER01, Enemy.MoveMode.followPlayer),
                                new Enemy(EnemyType.TANK_RED, ProjectileType.GREENLASER01, Enemy.MoveMode.followPlayer),
                                new Enemy(EnemyType.TANK_RED, ProjectileType.GREENLASER01, Enemy.MoveMode.followPlayer),
                                new Enemy(EnemyType.TANK_RED, ProjectileType.GREENLASER01, Enemy.MoveMode.followPlayer),
                                new Enemy(EnemyType.TANK_RED, ProjectileType.GREENLASER01, Enemy.MoveMode.followPlayer)
                        }
                },
                3000,
                new MapLoader(Map.LEVEL_01),
                6000,
                5,
                10000
        );

        levels[1] = new Level(
                new Enemy[][]{
                        new Enemy[]{
                                new Enemy(EnemyType.TANK_RED, ProjectileType.GREENLASER01, Enemy.MoveMode.followPlayer),
                                new Enemy(EnemyType.TANK_RED, ProjectileType.GREENLASER01, Enemy.MoveMode.followPlayer),
                                new Enemy(EnemyType.TANK_RED, ProjectileType.GREENLASER01, Enemy.MoveMode.followPlayer),
                                new Enemy(EnemyType.TANK_RED, ProjectileType.GREENLASER01, Enemy.MoveMode.followPlayer),
                                new Enemy(EnemyType.TANK_RED, ProjectileType.GREENLASER01, Enemy.MoveMode.followPlayer),
                                new Enemy(EnemyType.TANK_RED, ProjectileType.GREENLASER01, Enemy.MoveMode.followPlayer)
                        },
                        new Enemy[]{
                                new Enemy(EnemyType.TANK_RED, ProjectileType.GREENLASER01, Enemy.MoveMode.followPlayer),
                                new Enemy(EnemyType.TANK_RED, ProjectileType.GREENLASER01, Enemy.MoveMode.followPlayer),
                                new Enemy(EnemyType.TANK_RED, ProjectileType.GREENLASER01, Enemy.MoveMode.followPlayer),
                                new Enemy(EnemyType.TANK_RED, ProjectileType.GREENLASER01, Enemy.MoveMode.followPlayer),
                                new Enemy(EnemyType.TANK_RED, ProjectileType.GREENLASER01, Enemy.MoveMode.followPlayer),
                                new Enemy(EnemyType.TANK_RED, ProjectileType.GREENLASER01, Enemy.MoveMode.followPlayer)
                        }
                },
                3000,
                new MapLoader(Map.LEVEL_02),
                6000,
                5,
                10000
        );

        spawnNextLevel();
    }

    public void addEnemy(Enemy enemy) {
        GameViewManager.getMainPane().addToGamePane(enemy);
        enemyArrayList.add(enemy);
    }

    public void removeEnemy(Enemy enemy) {
        enemyArrayList.remove(enemy);
    }

    public void update() {
        if (currentEnemyIndex == currentWave.length
                && enemyArrayList.size() == 0) {
            if (currentWaveIndex + 1 == levels[currentLevelIndex].getEnemies().length) {
                if (currentLevelIndex + 1 < levels.length) {
                    levelLabel.incrementUICounterWithAnimation();
                    waveLabel.setUICounter(1);
                    loadLevel();
                }
            } else {
                waveLabel.incrementUICounterWithAnimation();
                currentWave = currentLevel.getEnemies()[++currentWaveIndex];
                currentTornadoList.forEach(tornado -> GameViewManager.getMainPane().removeFromGamePane(tornado));
                currentEnemyIndex = 0;
            }
        }

        createEnemies();
        createObstacles();//todo kill all tornados
    }

    private void loadLevel() {
        currentLevelIndex++;
        currentWaveIndex = 0;
        currentEnemyIndex = 0;

        removeLastLevel();
        spawnNextLevel();
    }

    private void removeLastLevel() {
        final MapLoader mapLoader = levels[currentLevelIndex - 1].getMapLoader();
        GameViewManager.getMainPane().removeAllFromFrontPane(mapLoader.getFrontNodes());
        GameViewManager.getMainPane().removeAllFromBackPane(mapLoader.getBackNodes());

        mapLoader.getWallNodes().forEach(node -> GameViewManager.getMainPane().removeFromGamePane(node));
        wallArrayList.removeAll(mapLoader.getWallNodes());
        mapLoader.getSpawnPointsNodes().forEach(node -> GameViewManager.getMainPane().removeFromGamePane(node));

        currentTornadoList.forEach(tornado -> GameViewManager.getMainPane().removeFromGamePane(tornado));

        levels[currentLevelIndex - 1] = null;
    }

    private void spawnNextLevel() {
        Player player = GameViewManager.getPlayer();
        player.setLayoutX(GameViewManager.CENTER_X);
        player.setLayoutY(GameViewManager.CENTER_Y);

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

        currentWave = currentLevel.getEnemies()[0];
    }

    private void createEnemies() {
        if (nextEnemySpawnTime < System.currentTimeMillis()) {
            nextEnemySpawnTime = System.currentTimeMillis() + currentLevel.getTimeBetweenSpawns();
            for (SpawnPoint spawnPoint : currentSpawnPoints) {
                if (currentEnemyIndex < currentWave.length) {
                    spawnPoint.setSpawning(true);

                    final Enemy enemy = currentWave[currentEnemyIndex++];
                    enemy.setLayoutX(spawnPoint.getSpawnPointX());
                    enemy.setLayoutY(spawnPoint.getSpawnPointY());
                    addEnemy(enemy);
                }
            }
        }
    }

    public void createObstacles() {
        if (currentTornadoList.size() < currentLevel.getNumberOfTornados())
            if (System.currentTimeMillis() > nextTornadoSpawnTime) {
                nextTornadoSpawnTime = System.currentTimeMillis() + currentLevel.getTimeBetweenTornado();

                final Tornado tornado = new Tornado(1, this);//todo 1
                currentTornadoList.add(tornado);
                GameViewManager.getMainPane().addToGamePane(tornado);
            }
    }

    public void createPowerUp() {
    }

    public ArrayList<Enemy> getEnemyArrayList() {
        return enemyArrayList;
    }

    public ArrayList<Wall> getWallArrayList() {
        return wallArrayList;
    }

    public void reduceNumOfTornado(Tornado tornado) {
        currentTornadoList.remove(tornado);
    }
}
