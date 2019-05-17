package model.level;

import controller.map.MapLoader;

public class Level {
    private Wave[] waves;
    private int timeBetweenWaves;
    private MapLoader mapLoader;

    public Level(Wave[] waves, int timeBetweenWaves, MapLoader mapLoader) {
        this.waves = waves;
        this.timeBetweenWaves = timeBetweenWaves;
        this.mapLoader = mapLoader;
    }

    public Wave[] getWaves() {
        return waves;
    }

    public int getTimeBetweenWaves() {
        return timeBetweenWaves;
    }

    public MapLoader getMapLoader() {
        return mapLoader;
    }
}
