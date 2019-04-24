package model.Enemies;

import model.Entity;
import view.GameViewManager;

import java.util.Random;

import static view.GameViewManager.HEIGHT;
import static view.GameViewManager.WIDTH;

public class Enemy extends Entity {
    private static final double MAX_HP = 100;

    private EnemyType enemyType;
    private double angle;

    private double hp = MAX_HP;

    public Enemy(EnemyType enemyType) {
        super(enemyType.getURL(), enemyType.getSPEED());

        this.enemyType = enemyType;

        Random rand = new Random();
        setLayoutY(rand.nextInt(HEIGHT));
        setLayoutX(rand.nextInt(WIDTH));
    }

    @Override
    public void takeDmg(double dmg) {
        this.hp -= dmg;
    }

    @Override
    public void heal(float amount) {
        hp = Math.min(amount + hp, MAX_HP);
    }

    private void UpdateAngle() {
        angle = Math.toDegrees(Math.atan2(GameViewManager.getPlayer().getLayoutY() - getLayoutY(),
                GameViewManager.getPlayer().getLayoutX() - getLayoutX()));
    }

    private void move() {
        setLayoutX(getLayoutX() + Math.cos(Math.toRadians(angle)) * enemyType.getSPEED());
        setLayoutY(getLayoutY() + Math.sin(Math.toRadians(angle)) * enemyType.getSPEED());
    }

    @Override
    public void update() {
        UpdateAngle();
        setRotate(angle);
        move();

        if (hp <= 0) {
            GameViewManager.removeGameObjectFromScene(this);
        }
    }
}
