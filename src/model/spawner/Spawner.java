//package model.spawner;
//
//import javafx.geometry.Point2D;
//import javafx.scene.control.Label;
//import javafx.scene.paint.Color;
//import javafx.scene.text.Font;
//import model.enemies.Enemy;
//import model.enemies.EnemyType;
//import view.GameViewManager;
//import view.LevelManager;
//
//import java.util.*;
//
//public class Spawner {
//    private final List<Point2D> spawnPoints;
//
//    private final Random random;
//    private final List<EnemyType> sortedEnemyTypes;
//    private final List<List<Enemy>> enemies;
//    private final List<Enemy> spawnedEnemies;
//    private final int numOfWaves;
//    private final long spawnCD;
//    private final float multiplier;
//    private final int adder;
//
//    private boolean debug;
//    private Label lbl_debug;
//
//    private int currentEnemyIndex; //todo temp?
//    private int currentWave;
//
//    private long nextSpawnTime;
//    private int currentX = 1;
//
////    public Spawner() {
////        this(false);
////    }
//
//    public Spawner(int numOfWaves, long spawnCD, float multiplier, int adder, boolean debug) {
//        this.debug = debug;
//        this.numOfWaves = numOfWaves;
//        this.spawnCD = spawnCD;
//
//        this.multiplier = multiplier;
//        this.adder = adder;
//
//        if (debug)
//            initializeDebug();
//
//        sortedEnemyTypes = Arrays.asList(EnemyType.values());
//        sortedEnemyTypes.sort(Comparator.comparingInt(EnemyType::getPOWER));
//
//        this.spawnPoints = new ArrayList<>();
//        this.random = new Random();
//        this.enemies = new ArrayList<>();
//        this.spawnedEnemies = new ArrayList<>();
//
//        prepEnemies();
//    }
//
//    private void initializeDebug() {
//        lbl_debug = new Label();
//        lbl_debug.setTextFill(Color.WHITE);
//        lbl_debug.setFont(new Font(20));
//
//        lbl_debug.setLayoutX(50);
//        lbl_debug.setLayoutY(500);
//
//        GameViewManager.addGameObjectTOScene(lbl_debug);
//    }
//
//    public List<Point2D> getSpawnPoints() {
//        return spawnPoints;
//    }
//
//    private void prepEnemies() {
//        for (int k = 0; k < numOfWaves; k++) {
//            final double totalPower = adder + multiplier * (20 * (1.5 * currentX + (Math.sin(2 * currentX) / 2)));
//            currentX++;
//
//            final List<Enemy> sortedEnemies = new ArrayList<>();
//
//            //todo dups ?
//            for (int i = sortedEnemyTypes.size() - 1; i > 0; i--) {
//                final EnemyType enemyType = sortedEnemyTypes.get(i);
//                if (enemyType.getPOWER() <= totalPower) {
//                    for (int j = 0; j < (int) (totalPower / enemyType.getPOWER()); j++) {
//                        sortedEnemies.add(new Enemy(enemyType));
//                    }
//                }
//            }
//            if (totalPower > 0)
//                sortedEnemies.add(new Enemy(sortedEnemyTypes.get(0)));
//
//            enemies.add(new ArrayList<>());
//            while (sortedEnemies.size() > 0) {
//                int index = random.nextInt(sortedEnemies.size());
//                enemies.get(k).add(sortedEnemies.get(index));
//                sortedEnemies.remove(index);
//            }
//        }
//    }
//
//    public void spawn() {
//        if (currentWave < numOfWaves && nextSpawnTime < System.currentTimeMillis()) {
//            nextSpawnTime = System.currentTimeMillis() + spawnCD;
//            if (currentEnemyIndex < enemies.get(currentWave).size()) {
//                final Enemy enemy = getEnemyRandomPos();
//
//                spawnedEnemies.add(enemy);
//                LevelManager.addEnemy(enemy);
//
//                updateDebug();
//            } else if (spawnedEnemies.size() == 0) {
//                currentEnemyIndex = 0;
//                currentWave++;
//                spawn();
//            }
//        }
//    }
//
//    private Enemy getEnemyRandomPos() {
//        final Enemy enemy = enemies.get(currentWave).get(currentEnemyIndex++);
//        final Point2D spawnPoint = spawnPoints.get(random.nextInt(spawnPoints.size())); //todo seq, parallel or one by one?
//        enemy.setLayoutX(spawnPoint.getX());
//        enemy.setLayoutY(spawnPoint.getY());
//        return enemy;
//    }
//
//    private void updateDebug() {
//        if (debug) {
//            lbl_debug.setText("Wave: " + (currentWave + 1) + "/" + numOfWaves + " Enemy" + currentEnemyIndex + "/" + enemies.get(currentWave).size());
//        }
//    }
//
//    public void removeEnemy(Enemy enemy) {
//        spawnedEnemies.remove(enemy);
//    }
//
//    public int getCurrentWave() {
//        return currentWave;
//    }
//
//    public boolean isDoneSpawning() {
//        return currentWave == numOfWaves && spawnedEnemies.size() == 0;
//    }
//}
