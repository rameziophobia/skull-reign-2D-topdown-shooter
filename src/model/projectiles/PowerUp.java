package model.projectiles;


import controller.animation.AnimationClip;
import controller.animation.SpriteSheet;
import model.GameObject;
import model.player.Player;
import model.walls.Wall;
import view.LevelManager;


import java.util.Random;

import static view.GameViewManager.*;

public class PowerUp extends GameObject {

    private PowerUpType powerUpType ;
    private AnimationClip animationClip;
    private boolean animated;

    public PowerUp(PowerUpType powerUpType) {

        super(powerUpType.getURL());

        this.powerUpType = powerUpType;

        Random rand = new Random();
        do{
            setLayoutY(rand.nextInt((HEIGHT-50)));
            setLayoutX(rand.nextInt((WIDTH-50)));
        }
        while (Wall.canMove(this, LevelManager.getWallArrayList(),false,0));
        this.animated = powerUpType.isANIMATED();
        if(animated){
            SpriteSheet spriteSheet = new SpriteSheet(powerUpType.getURL(), 0);
            animationClip = new AnimationClip(spriteSheet,
                    spriteSheet.getFrameCount() * 1.2f,
                    false,
                    AnimationClip.INF_REPEATS,
                    this);
            animationClip.animate();
        }


    }
    private void checkCollision() {

        if(isIntersects(getPlayer())){
            Player.setSPEED(powerUpType.getSpeed());
            getPlayer().getSecondaryBtnHandler().setPowerUp(powerUpType,powerUpType.getScale());
            if(powerUpType.getProjectileType()!= null){
                getPlayer().getSecondaryBtnHandler().addType(powerUpType.getProjectileType());
            }
            removeGameObjectFromScene(this);
        }

    }
    public static void  disableSpeed(){
        Player.setSPEED(0);
    }
    public static void disableScale(){
        getPlayer().getSecondaryBtnHandler().setPowerUp(PowerUpType.SCALE,0f);
    }
    public static void disableMult(){
        getPlayer().getSecondaryBtnHandler().setPowerUp(PowerUpType.MULT,0f );
    }
    public static void disableSpeedProjectile(){
        getPlayer().getSecondaryBtnHandler().setPowerUp(PowerUpType.SPEEDPROJECTILE,0f);
    }

    @Override
    public void update() {
        if(animated){
            animationClip.animate();
        }
        checkCollision();

    }
}
