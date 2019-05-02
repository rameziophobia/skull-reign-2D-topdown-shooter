package model.projectiles;


import model.GameObject;
import model.enemies.Enemy;
import model.player.Player;
import view.GameViewManager;
import view.LevelManager;

import java.util.Random;

import static view.GameViewManager.*;

public class PowerUp extends GameObject {

    private PowerUpType powerUpType;

    public PowerUp(PowerUpType powerUpType) {

        super(powerUpType.getURL());

        this.powerUpType = powerUpType;

        Random rand = new Random();
        setLayoutY(rand.nextInt(HEIGHT));
        setLayoutX(rand.nextInt(WIDTH));
    }
    private void checkCollision_entity() {

        if(isIntersects(getPlayer())){
            getPlayer().setSPEED(powerUpType.getSPEED());

            removeGameObjectFromScene(this);
        }

    }
    @Override
    public void update() {
        checkCollision_entity();

    }
}
