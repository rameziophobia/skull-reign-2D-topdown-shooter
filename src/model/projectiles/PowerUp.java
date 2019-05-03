package model.projectiles;


import model.GameObject;
import model.walls.Wall;
import view.LevelManager;


import java.util.Random;

import static view.GameViewManager.*;

public class PowerUp extends GameObject {

    private PowerUpType powerUpType;

    public PowerUp(PowerUpType powerUpType) {

        super(powerUpType.getURL());

        this.powerUpType = powerUpType;

        Random rand = new Random();
        do{
            setLayoutY(rand.nextInt((HEIGHT-50)));
            setLayoutX(rand.nextInt((WIDTH-50)));
        }
        while (Wall.canMove(this, LevelManager.getWallArrayList(),false,0));

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
