package model.Enemies;

public enum EnemyType {
    TANK_SAND ("file:src/model/resources/enemy/tank_sand.png",2,36, 42),
    TANK_DARK ("file:src/model/resources/enemy/tank_dark.png",2,36, 42),
    TANK_RED ("file:src/model/resources/enemy/tank_red.png", 2,36, 42),
    TANK_BLUE ("file:src/model/resources/enemy/tank_blue.png", 2,36, 42),

    TANK_HUGE ("file:src/model/resources/enemy/tank_huge.png",0.5,30,30),//todo change xy
    TANK_DARK_LARGE ("file:src/model/resources/enemy/tank_darkLarge.png", 1,30,30);//todo change xy


    public String URL;
    public double SPEED;
    public final int WIDTH;
    public final int HEIGHT;

    EnemyType(String s, double speed, int width, int height) {
        URL = s;
        this.SPEED = speed;
        this.WIDTH = width;
        this.HEIGHT = height;
    }


}
