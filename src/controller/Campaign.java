package controller;

import controller.json.JsonParser;
import controller.map.Map;
import model.enemies.Enemy;
import model.player.Player;
import model.projectiles.PowerUp;
import model.projectiles.PowerUpType;
import model.spawner.SpawnPoint;
import model.tornado.Tornado;
import model.ui.game.CounterLabel;
import model.wall.Wall;
import view.GameViewManager;
import model.level.Level;
import java.util.ArrayList;
import java.util.List;

public class Campaign extends LevelManager {
        private static final int TIME_BETWEEN_LEVELS_MS = 7000;
        private final ArrayList<Enemy> enemyArrayList;
        private final ArrayList<Wall> wallArrayList;

        private final Level[] levels;
        private final CounterLabel levelLabel, waveLabel;

        private int currentLevelIndex;
        private int currentWaveIndex;
        private int currentEnemyIndex;

        private long nextEnemySpawnTime;
        private long nextTornadoSpawnTime;
        private long nextPowerUpSpawnTime;
        private long nextWaveTime;
        private long nextLevelTime;

        private Level currentLevel;
        private Enemy[] currentWave;
        private ArrayList<SpawnPoint> currentSpawnPoints;
        private List<Tornado> currentTornadoList;

        private int numOfPowerUps;

        private boolean waitingForNextWave;
        private boolean waitingForNextLevel;

        public Campaign(CounterLabel levelLabel, CounterLabel waveLabel) {
            this.levelLabel = levelLabel;
            this.waveLabel = waveLabel;

            enemyArrayList = new ArrayList<>();
            wallArrayList = new ArrayList<>();
            currentTornadoList = new ArrayList<>();

            Tornado.setMapLimits(
                    Map.BLOCK_SIZE + Map.STARTING_X,
                    Map.STARTING_X + (Map.MAP_BLOCKS_WIDTH - 1) * Map.BLOCK_SIZE,
                    Map.BLOCK_SIZE * 3 + Map.STARTING_Y,
                    Map.STARTING_Y + (Map.MAP_BLOCKS_HEIGHT - 1) * Map.BLOCK_SIZE
            );

            levels = JsonParser.readLevels();

            spawnNextLevel();
        }

        public void update() {
            if (currentEnemyIndex == currentWave.length
                    && enemyArrayList.size() == 0) {
                if (currentWaveIndex + 1 == levels[currentLevelIndex].getEnemies().length) {
                    if (currentLevelIndex + 1 < levels.length) {
                        if (waitingForNextLevel) {
                            if (nextLevelTime < System.currentTimeMillis()) {
                                waveLabel.setUICounter(1);
                                loadLevel();
                                waitingForNextLevel = false;
                            }
                        } else {
                            levelLabel.incrementUICounterWithAnimation();
                            nextLevelTime = System.currentTimeMillis() + TIME_BETWEEN_LEVELS_MS;
                            waitingForNextLevel = true;
                            currentSpawnPoints.forEach(spawnPoint -> spawnPoint.setActive(false));
                        }
                    }
                } else {
                    if (waitingForNextWave) {
                        if (nextWaveTime < System.currentTimeMillis()) {
                            currentWave = currentLevel.getEnemies()[++currentWaveIndex];
                            currentEnemyIndex = 0;
                            waitingForNextWave = false;
                            currentSpawnPoints.forEach(spawnPoint -> spawnPoint.setActive(true));
                        }
                    } else {
                        waveLabel.incrementUICounterWithAnimation();
                        nextWaveTime = System.currentTimeMillis() + currentLevel.getTimeBetweenWaves();
                        waitingForNextWave = true;
                        currentSpawnPoints.forEach(spawnPoint -> spawnPoint.setActive(false));
                    }
                }
            }

            createEnemies();
            createObstacles();
            createPowerUp();
        }

        private void resetExtra() {
            currentTornadoList.forEach(tornado -> GameViewManager.getMainPane().removeFromGamePane(tornado));
            numOfPowerUps = 0;
        }

        private void loadLevel() {
            currentLevelIndex++;
            currentWaveIndex = 0;
            currentEnemyIndex = 0;

            removeLastLevel();
            spawnNextLevel();
        }

        private void removeLastLevel() {
            final Map map = levels[currentLevelIndex - 1].getMap();
            GameViewManager.getMainPane().removeAllFromFrontPane(map.getFrontNodes());
            GameViewManager.getMainPane().removeAllFromBackPane(map.getBackNodes());

            map.getWallNodes().forEach(node -> GameViewManager.getMainPane().removeFromGamePane(node));
            GameViewManager.getInstance().getWallArrayList().removeAll(map.getWallNodes());
            map.getSpawnPointsNodes().forEach(node -> GameViewManager.getMainPane().removeFromGamePane(node));

            resetExtra();

            levels[currentLevelIndex - 1] = null;
        }

        private void spawnNextLevel() {
            Player player = GameViewManager.getPlayer();
            player.setLayoutX(GameViewManager.CENTER_X);
            player.setLayoutY(GameViewManager.CENTER_Y);

            currentLevel = levels[currentLevelIndex];
            final Map map = currentLevel.getMap();
            GameViewManager.getMainPane().addAllToFrontPane(map.getFrontNodes());
            GameViewManager.getMainPane().addAllToBackPane(map.getBackNodes());

            map.getWallNodes().forEach(node -> GameViewManager.getMainPane().addToGamePane(node));
            GameViewManager.getInstance().getWallArrayList().addAll(map.getWallNodes());
            currentSpawnPoints = map.getSpawnPointsNodes();
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
                        GameViewManager.getMainPane().addToGamePane(enemy);
                        enemyArrayList.add(enemy);
                    }
                }
            }
        }

        private void createObstacles() {
            if (currentTornadoList.size() < currentLevel.getNumberOfTornados()) {
                if (System.currentTimeMillis() > nextTornadoSpawnTime) {
                    nextTornadoSpawnTime = System.currentTimeMillis() + currentLevel.getTimeBetweenTornado();

                    final Tornado tornado = new Tornado(this);
                    currentTornadoList.add(tornado);
                    GameViewManager.getMainPane().addToGamePane(tornado);
                }
            }
        }

        private void createPowerUp() {
            if (numOfPowerUps < currentLevel.getNumberOfPowerups()) {
                if (System.currentTimeMillis() > nextPowerUpSpawnTime) {
                    nextPowerUpSpawnTime = System.currentTimeMillis() + currentLevel.getTimeBetweenPowerups();
                    GameViewManager.getMainPane().addToGamePane(new PowerUp(PowerUpType.getRandomPowerUpType()));
                    numOfPowerUps++;
                }
            }
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

        public void removeEnemy(Enemy enemy) {
            enemyArrayList.remove(enemy);
        }
}


