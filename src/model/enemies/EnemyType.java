package model.enemies;

import view.Main;

public enum EnemyType {
    TANK_SAND(Constants.PATH_RESOURCES_SPRITES_ENEMY + "tank_sand-42x42.png", 1.7f, 60, 5, true),
    TANK_DARK(Constants.PATH_RESOURCES_SPRITES_ENEMY + "tank_dark-42x42.png", 1f, 80, 5, false),
    TANK_RED(Constants.PATH_RESOURCES_SPRITES_ENEMY + "tank_red-38x38.png", 2, 80, 5, false),
    TANK_BLUE(Constants.PATH_RESOURCES_SPRITES_ENEMY + "tank_blue-42x42.png", 2, 80, 5, false),
    TANK_HUGE(Constants.PATH_RESOURCES_SPRITES_ENEMY + "tank_huge-62x60.png", 0.7f, 80, 5, false),
    MAGE1(Constants.PATH_RESOURCES_SPRITES_ENEMY + "mage-1-85x94.png", 1.2f, 400, 5000, true),
    MAGE2(Constants.PATH_RESOURCES_SPRITES_ENEMY + "mage-2-122x110.png", 1.3f, 30000, 15000, true),
    MAGE3(Constants.PATH_RESOURCES_SPRITES_ENEMY + "mage-3-87x110.png", 1.4f, 80000, 50000, true),
    TANK_DARK_LARGE(Constants.PATH_RESOURCES_SPRITES_ENEMY + "tank_darkLarge-52x51.png", 0.5f, 200, 5,false);

    private String URL;
    private float SPEED;
    private float HP;
    private int SCORE;
    private boolean animated;

    EnemyType(String s, float speed, float hp, int score, boolean animated) {
        URL = s;
        SPEED = speed;
        HP = hp;
        SCORE = score;
        this.animated = animated;
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
        return animated;
    }

    private static class Constants {
        private static final String PATH_RESOURCES_SPRITES_ENEMY = Main.PATH_RESOURCES_SPRITES + "enemy/";
    }
}
