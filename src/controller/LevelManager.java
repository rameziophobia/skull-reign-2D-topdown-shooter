package controller;

import model.enemies.Enemy;
import model.wall.Wall;

import java.util.ArrayList;
import java.util.List;

public abstract class LevelManager {
    public abstract List<Enemy> getEnemyArrayList() ;
    public abstract ArrayList<Wall> getWallArrayList();
    public abstract void reduceNumOfTornado();
    public abstract void removeEnemy(Enemy enemy);
    public abstract void update();
    }