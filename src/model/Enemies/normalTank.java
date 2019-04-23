package model.Enemies;

import model.player.Player;

public class normalTank extends Enemy {


    private static final double MAX_HP = 100;
    private double currentHp = MAX_HP;

    public normalTank(EnemyType enemyType, Player player) {
        super(enemyType,player);
    }

    public double getCurrentHp() {
        return currentHp;
    }

    public void setHp_current(double hp_current) {
        this.currentHp = hp_current;
    }

}
