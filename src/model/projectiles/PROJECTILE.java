package model.projectiles;

public enum PROJECTILE {
    BULLET  ("file:src/model/resources/projectiles/bullet.png", 100);

    private final String type;
    private final int interval;

    PROJECTILE(String type, int interval) {
        this.type = type;
        this.interval = interval;
    }

    public String getType() {
        return type;
    }

    public int getInterval() {
        return interval;
    }
}
