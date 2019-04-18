package model.projectiles;


public enum ProjectileType {
    BULLET  ("file:src/model/resources/projectiles/bullet.png",
            100,5,13,3),
    FIRE ("file:src/model/resources/projectiles/fire.png",
            400,30,32,12);

    public final String URL;
    public final int INTERVAL;
    public final int DAMAGE;
    public final int WIDTH;
    public final int HEIGHT;


    ProjectileType(String type, int interval, int damage, int width, int height) {
        this.URL = type;
        this.INTERVAL = interval;
        this.DAMAGE = damage;
        this.WIDTH = width;
        this.HEIGHT = height;
    }
}
