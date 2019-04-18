package model.Enemies;

public class normalTank extends Enemy {


    private static final double MAX_HP = 100;
    private double hp_current = 100;

    public normalTank(ENEMY_ENUM enemyType, double playerXPos, double playerYPos) {
        super(enemyType, playerXPos, playerYPos);
    }

    public double getHp_current() {
        return hp_current;
    }

    public void setHp_current(double hp_current) {
        this.hp_current = hp_current;
    }

}
