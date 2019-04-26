package model.projectiles;
import java.util.ArrayList;

public abstract class ProjectileControl {
    protected ArrayList<Projectile> projArr = new ArrayList<>();
    protected double angle;

    public ProjectileControl() {
    }

    protected void update(double angle){
        this.angle = angle;
    }
}
