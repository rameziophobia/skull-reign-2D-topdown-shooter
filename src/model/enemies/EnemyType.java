package model.enemies;

import view.Main;

public enum EnemyType {
    TANK_SAND(Constants.PATH_RESOURCES_SPRITES_ENEMY + "tank_sand-42x42.png", 1.7f, 60, 10),
    TANK_DARK(Constants.PATH_RESOURCES_SPRITES_ENEMY + "tank_dark-42x42.png", 1.4f, 80, 15),
    TANK_RED(Constants.PATH_RESOURCES_SPRITES_ENEMY + "tank_red-38x38.png", 2, 80, 20),
    TANK_BLUE(Constants.PATH_RESOURCES_SPRITES_ENEMY + "tank_blue-42x42.png", 2, 80, 25),
    TANK_HUGE(Constants.PATH_RESOURCES_SPRITES_ENEMY + "tank_huge-62x60.png", 0.5f, 80, 30),
    TANK_DARK_LARGE(Constants.PATH_RESOURCES_SPRITES_ENEMY + "tank_darkLarge-52x51.png", 30, 200, 50);

    private String URL;
    private float SPEED;
    private float HP;
    private int score;

    EnemyType(String s, float speed, float hp, int scoreValue) {
        this.URL = s;
        this.SPEED = speed;
        this.HP = hp;
        this.score = scoreValue;
    }

    public String getURL() {
        return URL;
    }

    public float getSPEED() {
        return SPEED;
    }

    public float getHP() {
        return HP;
    }

    private static class Constants {
        private static final String PATH_RESOURCES_SPRITES_ENEMY = Main.PATH_RESOURCES_SPRITES + "enemy/";
    }

    public int getScore() {
        return score;
    }
}
