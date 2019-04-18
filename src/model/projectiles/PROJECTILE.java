package model.projectiles;


import javafx.scene.shape.Rectangle;

public enum PROJECTILE {
    BULLET  ("file:src/model/resources/projectiles/bullet.png",
            100,5,13,3),
    FIRE ("file:src/model/resources/projectiles/fire.png",
            400,30,32,12);

    private final String TYPE;
    private final int INTERVAL;
    private final int DAMAGE;
    private final int WIDTH;
    private final int HEIGHT;



    PROJECTILE(String type, int interval, int damage,int width, int height) {
        this.TYPE = type;
        this.INTERVAL = interval;
        this.DAMAGE = damage;
        this.WIDTH = width;
        this.HEIGHT = height;
    }

    public int getDamage() {
        return DAMAGE;
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public String getType() {
        return TYPE;
    }

    public int getInterval() {
        return INTERVAL;
    }
}
