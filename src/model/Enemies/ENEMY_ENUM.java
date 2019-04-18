package model.Enemies;

import javafx.scene.shape.Rectangle;

public enum ENEMY_ENUM {
    TANK_SAND ("file:src/model/resources/enemy/tank_sand.png",2,36, 42),
    TANK_DARK ("file:src/model/resources/enemy/tank_dark.png",2,36, 42),
    TANK_RED ("file:src/model/resources/enemy/tank_red.png", 2,36, 42),
    TANK_BLUE ("file:src/model/resources/enemy/tank_blue.png", 2,36, 42),

    TANK_HUGE ("file:src/model/resources/enemy/tank_huge.png",0.5,30,30),//todo change xy
    TANK_DARK_LARGE ("file:src/model/resources/enemy/tank_darkLarge.png", 1,30,30);//todo change xy


    private String type;
    private double speed;
    private final int WIDTH;
    private final int HEIGHT;

    ENEMY_ENUM(String s, double speed,int width, int height) {
        type = s;
        this.speed = speed;
        this.WIDTH = width;
        this.HEIGHT = height;
    }

    public double getSpeed() {
        return speed;
    }


    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public String getType() {
        return type;
    }


}
