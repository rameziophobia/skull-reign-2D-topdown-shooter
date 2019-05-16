package model.enemies;

import view.Main;

public enum EnemyType {
    TANK_SAND("tank_sand-42x42.png", 1.7f, 60, 5, 5),
    TANK_DARK("tank_dark-42x42.png", 1f, 80, 5, 10),
    TANK_RED("tank_red-38x38.png", 2, 80, 5, 30),
    TANK_BLUE("tank_blue-42x42.png", 2, 80, 5, 20),
    TANK_HUGE("tank_huge-62x60.png", 0.7f, 80, 5, 40),
    TANK_DARK_LARGE("tank_darkLarge-52x51.png", 0.5f, 200, 5, 50);

    private String URL;
    private float SPEED;
    private float HP;
    private int SCORE;
    private int POWER;

    EnemyType(String name, float speed, float hp, int score, int power) {
        URL = Constants.PATH_RESOURCES_SPRITES_ENEMY + name;
        SPEED = speed;
        HP = hp;
        SCORE = score;
        POWER = power;
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

    private static class Constants {
        private static final String PATH_RESOURCES_SPRITES_ENEMY = Main.PATH_RESOURCES_SPRITES + "enemy/";
    }
}
