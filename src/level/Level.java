package level;

import javafx.geometry.Point2D;

import java.util.List;

public class Level {
    private final int numOfTornado;

    private final int powerAdder;
    private final float powerMultiplier;
    private final int numOfWaves;
    private final long spawnCD;
    private final List<Point2D> spawnPoints;

    private final List<Point2D> wallPos;

    public Level(int numOfTornado, int powerAdder, float powerMultiplier, int numOfWaves, long spawnCD, List<Point2D> spawnPoints, List<Point2D> wallPos) {
        this.numOfTornado = numOfTornado;
        this.powerAdder = powerAdder;
        this.powerMultiplier = powerMultiplier;
        this.numOfWaves = numOfWaves;
        this.spawnCD = spawnCD;
        this.spawnPoints = spawnPoints;
        this.wallPos = wallPos;
    }

    public int getNumOfTornado() {
        return numOfTornado;
    }

    public int getPowerAdder() {
        return powerAdder;
    }

    public float getPowerMultiplier() {
        return powerMultiplier;
    }

    public int getNumOfWaves() {
        return numOfWaves;
    }

    public long getSpawnCD() {
        return spawnCD;
    }

    public List<Point2D> getSpawnPoints() {
        return spawnPoints;
    }

    public List<Point2D> getWallPos() {
        return wallPos;
    }
}
