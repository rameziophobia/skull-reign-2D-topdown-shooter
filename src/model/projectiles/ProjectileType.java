package model.projectiles;


public enum ProjectileType {
    BULLET  ("file:src/model/resources/projectiles/bullet.png",
            15,2,13,3),
    FIRE ("file:src/model/resources/projectiles/fire.png",
            1,35,32,12);

    public final String URL;
    public final double FIRERATE;
    public final int DAMAGE;
    public final int WIDTH;
    public final int HEIGHT;
    //todo: add speed

    ProjectileType(String type, double fireRate, int damage, int width, int height) {
        this.URL = type;
        this.FIRERATE = fireRate;
        this.DAMAGE = damage;
        this.WIDTH = width;
        this.HEIGHT = height;
    }
}
