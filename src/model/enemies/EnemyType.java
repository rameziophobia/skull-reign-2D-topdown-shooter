package model.enemies;

import view.Main;

public enum EnemyType {
    TANK_SAND("tank_sand-42x42.png", 1.7f, 60, 5,5, true),
    TANK_DARK("tank_dark-42x42.png", 1f, 80, 5,10, false),
    TANK_RED("tank_red-38x38.png", 2, 80, 5,15, false),
    TANK_BLUE("tank_blue-42x42.png", 2, 80, 5,20, false),
    TANK_HUGE("tank_huge-62x60.png", 0.7f, 80, 5,25, false),
    MAGE1("mage-1-85x94.png", 1.2f, 20000, 5000,2000, true),
    MAGE2("mage-2-122x110.png", 1.3f, 40000, 15000,2500, true),
    MAGE3("mage-3-87x110.png", 1.4f, 80000, 50000, 3000,true),
    TANK_DARK_LARGE("tank_darkLarge-52x51.png", 0.5f, 200, 5,30, false);

    private String URL;
    private float SPEED;
    private float HP;
    private int SCORE;
    private boolean ANIMATED;
    private int POWER;

    EnemyType(String s, float speed, float hp, int score, int POWER, boolean ANIMATED) {
        URL = Constants.PATH_RESOURCES_SPRITES_ENEMY + s;
        SPEED = speed;
        HP = hp;
        SCORE = score;
        this.POWER = POWER;
        this.ANIMATED = ANIMATED;
    }

    public int getPOWER(){
        return POWER;
    }

    public String getURL() {
        return URL;
    }

    public float getSPEED() {
        return SPEED;
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