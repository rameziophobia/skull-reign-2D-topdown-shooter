package model.projectiles;

import controller.animation.AnimationClip;
import controller.animation.SpriteSheet;
import controller.map.Map;
import javafx.animation.Animation;
import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import model.GameObject;
import model.player.Player;
import model.wall.Wall;
import view.GameViewManager;

import java.util.Random;

public class PowerUp extends GameObject {

    public static final Duration animationDuration = Duration.millis(1500);
    private PowerUpType powerUpType;
    private AnimationClip animationClip;
    private boolean animated;
    private Node[] powerUpNodes;

    public PowerUp(PowerUpType powerUpType) {
        super(powerUpType.getURL());

        this.powerUpType = powerUpType;

        Random rand = new Random();
        do {
            setLayoutY(2.5 * Map.BLOCK_SIZE + rand.nextInt((int) (Map.BLOCK_SIZE * (Map.MAP_BLOCKS_HEIGHT - 4) - Map.STARTING_Y)) + Map.STARTING_Y);
            setLayoutX(1.5 * Map.BLOCK_SIZE + rand.nextInt((int) (Map.BLOCK_SIZE * (Map.MAP_BLOCKS_WIDTH - 1.5) - Map.STARTING_X)) + Map.STARTING_X);
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
        setUpNode();
        powerUpAnimation();
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

    private void setUpNode() {
        final double width = getImageWidth(powerUpType.getURL());
        final double height = getImageHeight(powerUpType.getURL());
        final double radius = Math.max(width,height)/2;
        final Circle circle = new Circle(radius, Color.DARKSLATEGREY);
        circle.setCenterX(getLayoutX() + width/2);
        circle.setCenterY(getLayoutY()+height/2);
        circle.setOpacity(0.25);
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(4);

        powerUpNodes = new Node[]{circle};
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
        return powerUpNodes;
    }

    private void powerUpAnimation(){
        final ScaleTransition rotaterProjectile = new ScaleTransition(animationDuration,this);
        final ScaleTransition rotaterCircle = new ScaleTransition(animationDuration,powerUpNodes[0]);

        rotaterCircle.setToX(-1);
        rotaterProjectile.setToX(-1);

        rotaterCircle.setAutoReverse(true);
        rotaterProjectile.setAutoReverse(true);

        rotaterCircle.setCycleCount(Animation.INDEFINITE);
        rotaterProjectile.setCycleCount(Animation.INDEFINITE);

        rotaterCircle.play();
        rotaterProjectile.play();
    }

}
