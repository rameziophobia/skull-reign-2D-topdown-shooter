package model.Enemies;

public enum EnemyType {
    TANK_SAND ("file:src/model/resources/enemy/tank_sand-42x42.png",2),
    TANK_DARK ("file:src/model/resources/enemy/tank_dark-42x42.png",2),
    TANK_RED ("file:src/model/resources/enemy/tank_red-38x38.png", 2),
    TANK_BLUE ("file:src/model/resources/enemy/tank_blue-42x42.png", 2),
    TANK_HUGE ("file:src/model/resources/enemy/tank_huge-62x60.png",0.5),
    TANK_DARK_LARGE ("file:src/model/resources/enemy/tank_darkLarge-52x51.png",30);


    public String URL;
    public double SPEED;

    EnemyType(String s, double speed) {
        URL = s;
        this.SPEED = speed;
    }


}
