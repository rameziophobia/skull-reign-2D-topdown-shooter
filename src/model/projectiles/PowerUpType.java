package model.projectiles;

import view.Main;

import java.util.Random;

import static view.GameViewManager.getPlayer;

public enum PowerUpType {
    MULT(Constants.PATH_RESOURCES_SPRITES_POWERUPS + "blue-40x40.png", 1, 1, null, false),
    SCALE(Constants.PATH_RESOURCES_SPRITES_POWERUPS + "red-40x40.png", 1, 6, null, false),
    SPEEDPROJECTILE(Constants.PATH_RESOURCES_SPRITES_POWERUPS + "yellow-40x40.png", 1, 1.3f, null, false),
    SPEEDUP(Constants.PATH_RESOURCES_SPRITES_POWERUPS + "yellow-40x40.png", 1.2f, 1, null, false),
    GREENLASER01(Constants.PATH_RESOURCES_SPRITES_PROJECTILES + "laserGreen01-9x54.png", 1, 1, ProjectileType.GREENLASER01, false),
    REDLASER02(Constants.PATH_RESOURCES_SPRITES_PROJECTILES + "laserRed02-13x37.png", 1, 1, ProjectileType.REDLASER02, false),
    GREENLASER03(Constants.PATH_RESOURCES_SPRITES_PROJECTILES + "laserGreen03-9x37.png", 1, 1, ProjectileType.GREENLASER03, false),
    FIREBALL(Constants.PATH_RESOURCES_SPRITES_PROJECTILES + "fireball_0 -animated-64x16.png", 1, 1, ProjectileType.FIREBALL, true),
    FLAMEBALL(Constants.PATH_RESOURCES_SPRITES_PROJECTILES + "flameball-animated-32x32.png", 1, 1, ProjectileType.FLAMEBALL, true),
    ELECTRIC(Constants.PATH_RESOURCES_SPRITES_PROJECTILES + "electric-animated-63x83.png", 1, 1, ProjectileType.ELECTRIC, true),
    WHIRLWIND(Constants.PATH_RESOURCES_SPRITES_PROJECTILES + "whirlwind-animated-16x19.png", 1, 1, ProjectileType.WHIRLWIND, true),
    ICEICLE(Constants.PATH_RESOURCES_SPRITES_PROJECTILES + "icicle-right-animated-64x11.png", 1, 1, ProjectileType.ICEICLE, true),
    SHOCK(Constants.PATH_RESOURCES_SPRITES_PROJECTILES + "shock-animated-64x13.png", 1, 1, ProjectileType.SHOCK, true);


    private final String URL;
    private float scale;
    private float speed;
    private ProjectileType projectileType;
    private final boolean ANIMATED;


    PowerUpType(String URL, float speed, float scale, ProjectileType projectileType, boolean ANIMATED) {
        this.URL = URL;
        this.speed = speed;
        this.scale = scale;
        this.projectileType = projectileType;
        this.ANIMATED = ANIMATED;
    }

    public String getURL() {
        return URL;
    }

    public float getScale() {
        return scale;
    }

    public float getSpeed() {
        return speed;
    }

    public boolean isANIMATED() {
        return ANIMATED;
    }

    public ProjectileType getProjectileType() {
        return projectileType;
    }

    private static class Constants {
        private static final String PATH_RESOURCES_SPRITES_POWERUPS = Main.PATH_RESOURCES_SPRITES + "powerups/";
        private static final String PATH_RESOURCES_SPRITES_PROJECTILES = Main.PATH_RESOURCES_SPRITES + "projectiles/";
    }

    public static PowerUpType getRandomPowerUpType() {
        Random random = new Random();
        PowerUpType power;
        do {
            power = values()[random.nextInt(values().length)];
        }
        while (getPlayer().getSecondaryBtnHandler().getWeaponSettings().containsKey(power.projectileType));
        return power;
    }

}
