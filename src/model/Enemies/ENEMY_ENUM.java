package model.Enemies;

public enum ENEMY_ENUM {
    TANK_SAND ("file:src/model/resources/enemy/tank_sand.png",2),
    TANK_HUGE ("file:src/model/resources/enemy/tank_huge.png",0.5),
    TANK_DARK ("file:src/model/resources/enemy/tank_dark.png",2),
    TANK_RED ("file:src/model/resources/enemy/tank_red.png", 2),
    TANK_BLUE ("file:src/model/resources/enemy/tank_blue.png", 2),
    TANK_DARK_LARGE ("file:src/model/resources/enemy/tank_darkLarge.png", 1);


    private String type;
    private double speed;

    public double getSpeed() {
        return speed;
    }

    public String getType() {
        return type;
    }

    ENEMY_ENUM(String s, double speed) {
        type = s;
        this.speed = speed;
    }
}
