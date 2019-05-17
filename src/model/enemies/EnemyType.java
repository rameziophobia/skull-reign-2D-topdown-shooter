package model.enemies;

import view.Main;

public enum EnemyType {
    TANK_SAND("tank_sand-42x42.png", 1.7f, 60, 5, 5,false),
    TANK_DARK("tank_dark-42x42.png", 1f, 80, 5, 10,false),
    TANK_RED("tank_red-38x38.png", 2, 80, 5, 30,false),
    TANK_BLUE("tank_blue-42x42.png", 2, 80, 5, 20,false),
    TANK_HUGE("tank_huge-62x60.png", 0.7f, 80, 5, 40,false),
    TANK_DARK_LARGE("tank_darkLarge-52x51.png", 0.5f, 200, 5, 50,false);

    private String URL;
    private float SPEED;
    private float HP;
    private int SCORE;
    private int POWER;
    private boolean ANIMATED;

    EnemyType(String name, float speed, float hp, int score, int power, boolean ANIMATED) {
        URL = Constants.PATH_RESOURCES_SPRITES_ENEMY + name;
        SPEED = speed;
        HP = hp;
        SCORE = score;
        POWER = power;
        this.ANIMATED = ANIMATED;
    }

    public String getURL() {
        return URL;
    }

    public float getSPEED() {
        return SPEED;
    }

    public int getPOWER() {
        return POWER;
    }

    public int getScore() {
        return SCORE;
    }

    public float getHP() {
        return HP;
    }

    public boolean isANIMATED() {
        return ANIMATED;
    }

    private static class Constants {
        private static final String PATH_RESOURCES_SPRITES_ENEMY = Main.PATH_RESOURCES_SPRITES + "enemy/";
    }
}
