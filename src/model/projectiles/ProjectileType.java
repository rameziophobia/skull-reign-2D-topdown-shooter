package model.projectiles;


public enum ProjectileType {
    BULLET  ("file:src/model/resources/projectiles/bullet-13x3.png",
            15,1, 10, 3,false),
    FIRE ("file:src/model/resources/projectiles/fire-32x12.png",
            1,35, 6, 1,false),
    REDLASER01     ("file:src/model/resources/projectiles/laserRed01-9x54.png", 3,15, 15, 3,false),
    GREENLASER01 ("file:src/model/resources/projectiles/laserGreen01-9x54.png", 3,15, 15, 3,false),
    BLUELASER01   ("file:src/model/resources/projectiles/laserBlue01-9x54.png", 3,15, 15, 3,false),

    REDLASER02     ("file:src/model/resources/projectiles/laserRed02-13x37.png", 2,10, 8, 2,false),
    GREENLASER02 ("file:src/model/resources/projectiles/laserGreen02-13x37.png", 2,10, 8, 2,false),
    BLUELASER02  ( "file:src/model/resources/projectiles/laserBlue02-13x37.png", 2,10, 8, 2,false),

    REDLASER03   ("file:src/model/resources/projectiles/laserRed03-9x37.png", 20,1.5, 10, 3,false),
    GREENLASER03("file:src/model/resources/projectiles/laserGreen03-9x37.png", 20,1.5, 10, 3,false),
    BLUELASER03 ("file:src/model/resources/projectiles/laserBlue03-9x37.png", 20,1.5, 10, 3,false),

    LASERRED08   ("file:src/model/resources/projectiles/laserRed08-48x45.png", 1.5,50, 6, 5,false),

    EYEBALL   ("file:src/model/resources/projectiles/eyeball-animated-40x40.png", 1.5,50, 6, 5,true),
    FIREBALL   ("file:src/model/resources/projectiles/fireball_0 -animated-64x16.png", 1.5,50, 6, 5,true),
    FLAMEBALL   ("file:src/model/resources/projectiles/flameball-animated-32x32.png", 1.5,50, 6, 5,true),
    SHOCK   ("file:src/model/resources/projectiles/shock-animated-64x13.png", 1.5,50, 6, 5,true),
    ICEICLE   ("file:src/model/resources/projectiles/icicle-right-animated-64x11.png", 1.5,50, 6, 5,true),
    FLAME   ("file:src/model/resources/projectiles/flameball-32x32.png", 2,40, 7, 10,true), //todo: i edited the pic now it causes errors, replace ir
    ELECTRIC   ("file:src/model/resources/projectiles/electric-animated-63x83.png", 2,40, 7, 10,true),
    CAT   ("file:src/model/resources/projectiles/black-cat-animated-32x26.png", 1.3,40, 5, 10,true),
    WHIRLWIND   ("file:src/model/resources/projectiles/whirlwind-animated-16x19.png", 2,40, 7, 5,true);


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
}
