package model.spawners;

import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import model.enemies.Enemy;
import model.enemies.EnemyType;
import view.GameViewManager;

import java.util.*;

public class Spawner { //todo bastract

    private static final int NUMBER_OF_WAVES = 5;//todo cosnt for now
    private static final long SPAWN_CD_IN_MS = 5000; //todo cosnt for now

    private final List<Point2D> spawnPoints;

    private final Random random;
    private final List<EnemyType> sortedEnemyTypes;
    private final List<Enemy> enemies;

    private boolean debug;
    private Label lbl_debug;

    private int currentX = 1;
    private int currentEnemy; //todo temp?
    private int currentWave;
    private long nextSpawnTime;

    public Spawner() {
        this(false);
    }

    public Spawner(boolean debug) {
        this.debug = debug;

        if (debug)
            initializeDebug();

        sortedEnemyTypes = Arrays.asList(EnemyType.values());
        sortedEnemyTypes.sort(Comparator.comparingInt(EnemyType::getPOWER));

        this.spawnPoints = new ArrayList<>();
        this.random = new Random();
        this.enemies = new ArrayList<>();

        prepEnemies();
    }

    private void initializeDebug() {
        lbl_debug = new Label();
        lbl_debug.setTextFill(Color.WHITE);
        lbl_debug.setFont(new Font(20));

        lbl_debug.setLayoutX(50);
        lbl_debug.setLayoutY(500);

        GameViewManager.addGameObjectTOScene(lbl_debug);
    }

    public List<Point2D> getSpawnPoints() {
        return spawnPoints;
    }

    private void prepEnemies() {
        //preload if the number of waves is known ?
        final double totalPower = 20 * (1.5 * currentX + (Math.sin(2 * currentX) / 2));
        currentX++;

        final List<Enemy> sortedEnemies = new ArrayList<>();

        //todo dups ?
        for (int i = sortedEnemyTypes.size() - 1; i > 0; i--) {
            final EnemyType enemyType = sortedEnemyTypes.get(i);
            if (enemyType.getPOWER() <= totalPower) {
                final int temp = (int) (totalPower / enemyType.getPOWER());
                for (int j = 0; j < temp; j++) {
                    sortedEnemies.add(new Enemy(enemyType));
                }
            }
        }

        //todo maybe ?
        if (totalPower > 0)
            sortedEnemies.add(new Enemy(sortedEnemyTypes.get(0)));

        //rand order
        while (sortedEnemies.size() > 0) {
            int index = random.nextInt(sortedEnemies.size());
            enemies.add(sortedEnemies.get(index));
            sortedEnemies.remove(index);
        }
    }

    public void spawn() {
        if (currentWave < NUMBER_OF_WAVES && nextSpawnTime < System.currentTimeMillis()) {
            nextSpawnTime = System.currentTimeMillis() + SPAWN_CD_IN_MS;
            System.out.println(System.currentTimeMillis() + " gonna try spawning");
            //todo check if max enemy in scene reached ?
            if (currentEnemy < enemies.size()) {
                System.out.println(System.currentTimeMillis() + " spawning");
                final Enemy enemy = enemies.get(currentEnemy++);
                final Point2D spawnPoint = spawnPoints.get(random.nextInt(spawnPoints.size())); //todo seq, parallel or one by one?
                System.out.println("@" + spawnPoint);
                enemy.setLayoutX(spawnPoint.getX());
                enemy.setLayoutY(spawnPoint.getY());
                GameViewManager.addGameObjectTOScene(enemy);

                updateDebug();
            } else {
                System.out.println(System.currentTimeMillis() + " refilling");
                //check if all spawned enemies are dead
                //for now next wave
                currentWave++;
                if (currentWave < NUMBER_OF_WAVES) {
                    prepEnemies();
                    spawn();
                }
            }
        }
    }

    private void updateDebug() {
        if (debug) {
            lbl_debug.setText("Wave: " + (currentWave + 1) + "/" + NUMBER_OF_WAVES + " Enemy" + currentEnemy + "/" + enemies.size());
            //report me
//            for (Point2D point2D : spawnPoints) {
//                Rectangle rectangle = new Rectangle(50, 50, Color.LIGHTSEAGREEN);
//                rectangle.setLayoutX(point2D.getX());
//                rectangle.setLayoutY(point2D.getY());
//                GameViewManager.addGameObjectTOScene(rectangle);
//            }

        }
    }
}
