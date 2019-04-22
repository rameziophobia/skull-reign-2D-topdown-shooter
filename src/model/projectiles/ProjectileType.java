package model.projectiles;


public enum ProjectileType {
    BULLET  ("file:src/model/resources/projectiles/bullet-13x3.png",
            15,1, 10, 3),
    FIRE ("file:src/model/resources/projectiles/fire-32x12.png",
            1,35, 6, 1);

    public final String URL;
    public final double FIRERATE;
    public final int DAMAGE;
    public final int SPEED;
    public final double MULTANGLE; //probably not necessary

    ProjectileType(String type, double fireRate, int damage, int speed, double multAngle) {
        this.URL = type;
        this.FIRERATE = fireRate;
        this.DAMAGE = damage;
        this.SPEED = speed;
        this.MULTANGLE = multAngle;
    }
}
