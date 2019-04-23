package model.projectiles;

import view.Main;

public enum ProjectileType {
    BULLET  (Constants.PATH_RESOURCES_SPRITES_PROJECTILES + "/bullet-13x3.png",
            15,1, 10, 3,false),
    FIRE (Constants.PATH_RESOURCES_SPRITES_PROJECTILES + "/fire-32x12.png",
            1,35, 6, 1,false),
    REDLASER01     (Constants.PATH_RESOURCES_SPRITES_PROJECTILES + "/laserRed01-9x54.png", 3,15, 15, 3,false),
    GREENLASER01 (Constants.PATH_RESOURCES_SPRITES_PROJECTILES + "/laserGreen01-9x54.png", 3,15, 15, 3,false),
    BLUELASER01   (Constants.PATH_RESOURCES_SPRITES_PROJECTILES + "/laserBlue01-9x54.png", 3,15, 15, 3,false),

    REDLASER02     (Constants.PATH_RESOURCES_SPRITES_PROJECTILES + "/laserRed02-13x37.png", 2,10, 8, 2,false),
    GREENLASER02 (Constants.PATH_RESOURCES_SPRITES_PROJECTILES + "/laserGreen02-13x37.png", 2,10, 8, 2,false),
    BLUELASER02  ( Constants.PATH_RESOURCES_SPRITES_PROJECTILES + "/laserBlue02-13x37.png", 2,10, 8, 2,false),

    REDLASER03   (Constants.PATH_RESOURCES_SPRITES_PROJECTILES + "/laserRed03-9x37.png", 20,1.5, 10, 3,false),
    GREENLASER03(Constants.PATH_RESOURCES_SPRITES_PROJECTILES + "/laserGreen03-9x37.png", 20,1.5, 10, 3,false),
    BLUELASER03 (Constants.PATH_RESOURCES_SPRITES_PROJECTILES + "/laserBlue03-9x37.png", 20,1.5, 10, 3,false),

    LASERRED08   (Constants.PATH_RESOURCES_SPRITES_PROJECTILES + "/laserRed08-48x45.png", 1.5,50, 6, 5,false),

    EYEBALL   (Constants.PATH_RESOURCES_SPRITES_PROJECTILES + "/eyeball-animated-40x40.png", 1.5,50, 6, 5,true),
    FIREBALL   (Constants.PATH_RESOURCES_SPRITES_PROJECTILES + "/fireball_0 -animated-64x16.png", 1.5,50, 6, 5,true),
    FLAMEBALL   (Constants.PATH_RESOURCES_SPRITES_PROJECTILES + "/flameball-animated-32x32.png", 1.5,50, 6, 5,true),
    SHOCK   (Constants.PATH_RESOURCES_SPRITES_PROJECTILES + "/shock-animated-64x13.png", 1.5,50, 6, 5,true),
    ICEICLE   (Constants.PATH_RESOURCES_SPRITES_PROJECTILES + "/icicle-right-animated-64x11.png", 1.5,50, 6, 5,true),
    FLAME   (Constants.PATH_RESOURCES_SPRITES_PROJECTILES + "/flameball-32x32.png", 2,40, 7, 10,true), //todo: i edited the pic now it causes errors, replace ir
    ELECTRIC   (Constants.PATH_RESOURCES_SPRITES_PROJECTILES + "/electric-animated-63x83.png", 2,40, 7, 10,true),
    CAT   (Constants.PATH_RESOURCES_SPRITES_PROJECTILES + "/black-cat-animated-32x26.png", 1.3,40, 5, 10,true),
    WHIRLWIND   (Constants.PATH_RESOURCES_SPRITES_PROJECTILES + "/whirlwind-animated-16x19.png", 2,40, 7, 5,true);


    public final String URL;
    public final double FIRERATE;
    public final double DAMAGE;
    public final int SPEED;
    public final double MULTANGLE; //probably not necessary
    public final boolean ANIMATED;


    ProjectileType(String type, double fireRate, double damage, int speed, double multAngle, boolean animated) {
        this.URL = type;
        this.FIRERATE = fireRate;
        this.DAMAGE = damage;
        this.SPEED = speed;
        this.MULTANGLE = multAngle;
        this.ANIMATED = animated;

    }

    private static class Constants{
        static final String PATH_RESOURCES_SPRITES_PROJECTILES = Main.PATH_RESOURCES_SPRITES + "projectiles/";
    }
}
