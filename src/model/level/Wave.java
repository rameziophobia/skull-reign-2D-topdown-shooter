package model.level;

import model.enemies.Enemy;

public class Wave {
    private Enemy[] enemies;
    private int timeBetweenSpawns;
    private int numberOfTornados;
    private int timeBetweenTornado;

    public Wave(Enemy[] enemies, int timeBetweenSpawns, int numberOfTornados, int timeBetweenTornado) {
        this.enemies = enemies;
        this.timeBetweenSpawns = timeBetweenSpawns;
        this.numberOfTornados = numberOfTornados;
        this.timeBetweenTornado = timeBetweenTornado;
    }

    public Enemy[] getEnemies() {
        return enemies;
    }

    public int getTimeBetweenSpawns() {
        return timeBetweenSpawns;
    }

    public int getNumberOfTornados() {
        return numberOfTornados;
    }

    public int getTimeBetweenTornado() {
        return timeBetweenTornado;
    }
}
