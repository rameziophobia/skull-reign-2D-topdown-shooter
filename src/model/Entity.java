package model;

public abstract class Entity extends GameObject {

    protected float speed;//todo

    public Entity(String url, float speed) {
        super(url);
        this.speed = speed;
    }

    public abstract void takeDmg(double dmg);

    public abstract void heal(float amount);

}
