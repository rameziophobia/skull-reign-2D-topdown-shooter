package model.level;

import model.spawner.SpawnPoint;
import model.walls.Wall;

public class Level {
    private Wave[] waves;
    private int timeBetweenWaves;
    private Wall[] walls;
    private SpawnPoint[] spawnPoints;

    public Level(Wave[] waves, int timeBetweenWaves, Wall[] walls, SpawnPoint[] spawnPoints) {
        this.waves = waves;
        this.timeBetweenWaves = timeBetweenWaves;
        this.walls = walls;
        this.spawnPoints = spawnPoints;
    }

    public Wave[] getWaves() {
        return waves;
    }

    public int getTimeBetweenWaves() {
        return timeBetweenWaves;
    }

    public Wall[] getWalls() {
        return walls;
    }

    public SpawnPoint[] getSpawnPoints() {
        return spawnPoints;
    }
}
