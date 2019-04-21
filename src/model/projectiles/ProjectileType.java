package model.projectiles;


public enum ProjectileType {
    BULLET  ("file:src/model/resources/projectiles/bullet.png",
            15,1, 10, 3,13,3),
    FIRE ("file:src/model/resources/projectiles/fire.png",
            1,35, 6, 1,32,12);

    public final String URL;
    public final double FIRERATE;
    public final int DAMAGE;
    public final int SPEED;
    public final double MULTANGLE; //probably not necessary
    public final int WIDTH;
    public final int HEIGHT;

    ProjectileType(String type, double fireRate, int damage, int speed, double multAngle, int width, int height) {
        this.URL = type;
        this.FIRERATE = fireRate;
        this.DAMAGE = damage;
        this.SPEED = speed;
        this.MULTANGLE = multAngle;
        this.WIDTH = width;
        this.HEIGHT = height;
    }
}
