package model.projectiles;


public enum ProjectileType {
    BULLET  ("file:src/model/resources/projectiles/bullet-13x3.png",
            15,1, 10, 3),
    FIRE ("file:src/model/resources/projectiles/fire-32x12.png",
            1,35, 6, 1),
    REDLASER01     ("file:src/model/resources/projectiles/laserRed01-9x54.png", 3,15, 15, 3),
    GREENLASER01  ("file:src/model/resources/projectiles/laserGreen01-9x54.png", 3,15, 15, 3),
    BLUELASER01   ("file:src/model/resources/projectiles/laserBlue01-9x54.png", 3,15, 15, 3),

    REDLASER02    ("file:src/model/resources/projectiles/laserRed02-13x37.png", 2,10, 8, 2),
    GREENLASER02 ("file:src/model/resources/projectiles/laserGreen02-13x37.png", 2,10, 8, 2),
    BLUELASER02  ("file:src/model/resources/projectiles/laserBlue02-13x37.png", 2,10, 8, 2),

    REDLASER03     ("file:src/model/resources/projectiles/laserRed03-9x37.png", 20,1.5, 10, 3),
    GREENLASER03  ("file:src/model/resources/projectiles/laserGreen03-9x37.png", 20,1.5, 10, 3),
    BLUELASER03   ("file:src/model/resources/projectiles/laserBlue03-9x37.png", 20,1.5, 10, 3),

    LASERRED08   ("file:src/model/resources/projectiles/laserRed08-48x45.png", 1.5,50, 6, 5);


    public final String URL;
    public final double FIRERATE;
    public final double DAMAGE;
    public final int SPEED;
    public final double MULTANGLE; //probably not necessary

    ProjectileType(String type, double fireRate, double damage, int speed, double multAngle) {
        this.URL = type;
        this.FIRERATE = fireRate;
        this.DAMAGE = damage;
        this.SPEED = speed;
        this.MULTANGLE = multAngle;
    }
}
