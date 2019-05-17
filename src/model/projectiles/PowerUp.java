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

    private PowerUpType powerUpType;
    private AnimationClip animationClip;
    private boolean animated;

    public PowerUp(PowerUpType powerUpType) {

        super(powerUpType.getURL());

        this.powerUpType = powerUpType;

        Random rand = new Random();
        do {
            setLayoutY(rand.nextInt((HEIGHT - 50)));
            setLayoutX(rand.nextInt((WIDTH - 50)));
        }
        while (LevelManager.getWallArrayList().size() > 0 &&
                Wall.canMove(this, LevelManager.getWallArrayList(), false, 0,5));
        this.animated = powerUpType.isANIMATED();
        if (animated) {
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

        if (isIntersects(getPlayer())) {
            Player.setSPEED(powerUpType.getSpeed());

            final PlayerProjectileControl BtnHandler =
                    (animated) ?
                            getPlayer().getSecondaryBtnHandler() :
                            getPlayer().getPrimaryBtnHandler();

            BtnHandler.setPowerUp(powerUpType, powerUpType.getScale());
            if (powerUpType.getProjectileType() != null) {
                BtnHandler.addType(powerUpType.getProjectileType());
            }
            removeGameObjectFromScene(this);
        }
    }

    //todo: will we use these methods?
    public static void disableSpeed() {
        Player.setSPEED(0);
    }

    public static void disableScale(boolean primary) {
        setPowerUp(primary, PowerUpType.SCALE);
    }

    public static void disableMult(boolean primary) {
        setPowerUp(primary, PowerUpType.MULT);
    }

    public static void disableSpeedProjectile(boolean primary) {
        setPowerUp(primary, PowerUpType.SPEEDPROJECTILE);
    }

    private static void setPowerUp(boolean primary, PowerUpType scale) {
        if (primary) {
            getPlayer().getPrimaryBtnHandler().setPowerUp(scale, 0f);
        } else {
            getPlayer().getSecondaryBtnHandler().setPowerUp(scale, 0f);
        }
    }

    @Override
    public void update() {
        if (animated) {
            animationClip.animate();
        }
        checkCollision();

    }
}
