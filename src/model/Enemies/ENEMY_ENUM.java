package model.Enemies;

public enum ENEMY_ENUM {
    TANK_SAND ("file:src/model/resources/enemy/tank_sand.png",2),
    TANK_HUGE ("file:src/model/resources/enemy/tank_huge.png",1),
    TANK_DARK ("file:src/model/resources/enemy/tank_dark.png",2),
    TANK_RED ("file:src/model/resources/enemy/tank_red.png", 2);


    private String type;
    private int speed;

    public String getType() {
        return type;
    }

    ENEMY_ENUM(String s, int speed) {
        type = s;
        this.speed = speed;
    }
}
