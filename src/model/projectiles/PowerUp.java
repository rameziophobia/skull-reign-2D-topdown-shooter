package model.projectiles;

import controller.animation.AnimationClip;
import controller.animation.SpriteSheet;
import javafx.scene.Node;
import model.GameObject;
import model.player.Player;
import view.GameViewManager;
import model.wall.Wall;

import java.util.Random;

public class PowerUp extends GameObject {

    private PowerUpType powerUpType;
    private AnimationClip animationClip;
    private boolean animated;

    public PowerUp(PowerUpType powerUpType) {

        super(powerUpType.getURL());

        this.powerUpType = powerUpType;

        Random rand = new Random();
        do {
            setLayoutY(rand.nextInt((GameViewManager.HEIGHT - 50)));
            setLayoutX(rand.nextInt((GameViewManager.WIDTH - 50)));
        }
        while (GameViewManager.getInstance().getWallArrayList().size() > 0 &&
                Wall.canMove(this, GameViewManager.getInstance().getWallArrayList(), false, 0));
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
        if (isIntersects(GameViewManager.getPlayer())) {
            Player.setSPEED(powerUpType.getSpeed());

            final PlayerProjectileControl BtnHandler =
                    (animated) ?
                            GameViewManager.getPlayer().getSecondaryBtnHandler() :
                            GameViewManager.getPlayer().getPrimaryBtnHandler();

            BtnHandler.setPowerUp(powerUpType, powerUpType.getScale());
            if (powerUpType.getProjectileType() != null) {
                BtnHandler.addType(powerUpType.getProjectileType());
            }
            GameViewManager.getMainPane().removeFromGamePane(this);
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
            GameViewManager.getPlayer().getPrimaryBtnHandler().setPowerUp(scale, 0f);
        } else {
            GameViewManager.getPlayer().getSecondaryBtnHandler().setPowerUp(scale, 0f);
        }
    }

    @Override
    public void update() {
        if (animated) {
            animationClip.animate();
        }
        checkCollision();
    }

    @Override
    public Node[] getChildren() {
        return null;
    }
}
