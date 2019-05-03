package model.projectiles;


import model.GameObject;


import java.util.Random;

import static view.GameViewManager.*;

public class PowerUp extends GameObject {

    private PowerUpType powerUpType;

    public PowerUp(PowerUpType powerUpType) {

        super(powerUpType.getURL());

        this.powerUpType = powerUpType;

        Random rand = new Random();
        setLayoutY(rand.nextInt(HEIGHT-5));
        setLayoutX(rand.nextInt(WIDTH-5));
    }
    private void checkCollision() {

        if(isIntersects(getPlayer())){
            getPlayer().setSPEED(powerUpType.getSpeed());



            getPlayer().getSecondaryBtnHandler().setPowerUp(powerUpType,powerUpType.getScale());
            removeGameObjectFromScene(this);
        }

    }
    @Override
    public void update() {

        checkCollision();

    }
}
