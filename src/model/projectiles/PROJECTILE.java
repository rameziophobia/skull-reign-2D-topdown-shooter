package model.projectiles;

public enum PROJECTILE {
    BULLET  ("file:src/model/resources/projectiles/bullet.png",
            100,5,9),
    FIRE ("file:src/model/resources/projectiles/fire.png",
            400,30,12);

    private final String type;
    private final int interval;
    private final int damage;
    private final int hitRadius;


    PROJECTILE(String type, int interval, int damage,int hitRadius) {
        this.type = type;
        this.interval = interval;
        this.damage = damage;
        this.hitRadius = hitRadius;
    }

    public int getDamage() {
        return damage;
    }

    public int getHitRadius() {
        return hitRadius;
    }

    public String getType() {
        return type;
    }

    public int getInterval() {
        return interval;
    }
}
