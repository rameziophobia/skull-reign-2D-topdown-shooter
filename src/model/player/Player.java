package model.player;

import controller.InputManager;
import controller.audiomanager.AudioFile;
import controller.audiomanager.AudioManager;
import javafx.scene.Node;
import model.Entity;
import model.projectiles.PlayerProjectileControl;
import model.projectiles.ProjectileType;
import model.wall.Wall;
import view.GameViewManager;
import view.game.stats.StatBar;

import static java.lang.Math.atan2;

public class Player extends Entity {
    private static int currentScore = 0;
    private static final float MAX_SPEED = 8;
    private static float SPEED = 6;
    private static double MAX_HP = 500;
    private static double MAX_SHIELD = 300;
    private static final long HEALTH_REGEN_INTERVAL = 2000;
    private static long REGENERATION_TIME_CD_SHIELD_MS = 5000;
    private static long REGENERATION_TIME_CD_HP_MS = 6000;

    private StatBar HPRectangle;
    private StatBar ShieldRectangle;
    private final PlayerProjectileControl primaryBtnHandler;
    private final PlayerProjectileControl secondaryBtnHandler;
    private double currentHp = MAX_HP;
    private double currentShield = MAX_SHIELD;
    private double angle;

    private boolean upPressed;
    private boolean downPressed;
    private boolean leftPressed;
    private boolean rightPressed;

    private String name;
    private long timeNow;
    private long nextShieldRegenCheck;
    private long nextHealthDmgCheck;
    private long nextHealthRegenCheck;

    public Player(PlayerType player, StatBar HPBar, StatBar ShieldBar) {
        super(player.getURL(), SPEED);

        setLayoutX((GameViewManager.WIDTH >> 1) - getFitWidth() / 2 - 300);
        setLayoutY((GameViewManager.HEIGHT >> 1) - getFitHeight() / 2 - 300);

        HPRectangle = HPBar;
        ShieldRectangle = ShieldBar;

        primaryBtnHandler = new PlayerProjectileControl(ProjectileType.BULLET,
                PlayerProjectileControl.buttons.PRIMARY);
        secondaryBtnHandler = new PlayerProjectileControl(ProjectileType.WHIRLWIND,
                PlayerProjectileControl.buttons.SECONDARY);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUpPressed(boolean upPressed) {
        this.upPressed = upPressed;
    }

    public void setDownPressed(boolean downPressed) {
        this.downPressed = downPressed;
    }

    public void setLeftPressed(boolean leftPressed) {
        this.leftPressed = leftPressed;
    }

    public void setRightPressed(boolean rightPressed) {
        this.rightPressed = rightPressed;
    }

    public void endlessStats() {
        MAX_HP = 2000;
        MAX_SHIELD = 1000;
        currentHp = MAX_HP;
        currentShield = MAX_SHIELD;
        ShieldRectangle.setCurrentValue(currentShield);
        HPRectangle.setCurrentValue(currentHp);
        ShieldRectangle.setMaxValue(MAX_SHIELD);
        HPRectangle.setMaxValue(MAX_HP);
        HPRectangle.barScaleAnimator(MAX_HP);
        ShieldRectangle.barScaleAnimator(MAX_SHIELD);
    }

    private void move() {
        double DIAGONAL_FACTOR = 1.5;
        if (upPressed) {
            if (Wall.canMoveUp(this, GameViewManager.getInstance().getWallArrayList()) && !atTopBorder()) {
                if (rightPressed || leftPressed) {
                    setLayoutY(getLayoutY() - SPEED / DIAGONAL_FACTOR); // to avoid moving fast diagonally
                } else {
                    setLayoutY(getLayoutY() - SPEED);
                }
            }
        } else if (downPressed && Wall.canMoveDown(this, GameViewManager.getInstance().getWallArrayList()) && !atBottomBorder()) {
            if (rightPressed || leftPressed) {
                setLayoutY(getLayoutY() + SPEED / DIAGONAL_FACTOR);
            } else {
                setLayoutY(getLayoutY() + SPEED);
            }
        }
        if (rightPressed) {

            if (Wall.canMoveRight(this, GameViewManager.getInstance().getWallArrayList()) && !atRightBorder()) {
                if (upPressed || downPressed) {
                    setLayoutX(getLayoutX() + SPEED / DIAGONAL_FACTOR);
                } else {
                    setLayoutX(getLayoutX() + SPEED);
                }
            }
        } else if (leftPressed && Wall.canMoveLeft(this, GameViewManager.getInstance().getWallArrayList()) && !atLeftBorder()) {
            if (upPressed || downPressed) {
                setLayoutX(getLayoutX() - SPEED / DIAGONAL_FACTOR);
            } else {
                setLayoutX(getLayoutX() - SPEED);
            }
        }

    }

    private void warp() {
        setLayoutY((getLayoutY() < 0) ? (getLayoutY() + GameViewManager.HEIGHT) : (getLayoutY() % GameViewManager.HEIGHT));
        setLayoutX((getLayoutX() < 0) ? (getLayoutX() + GameViewManager.WIDTH) : (getLayoutX() % GameViewManager.WIDTH));
    }

    private boolean atRightBorder() {
        return (getLayoutX() >= GameViewManager.WIDTH - 49);

    }

    private boolean atLeftBorder() {
        return (getLayoutX() < 1);

    }

    private boolean atBottomBorder() {
        return (getLayoutY() >= GameViewManager.HEIGHT - 43);

    }

    private boolean atTopBorder() {
        return (getLayoutY() < 3);
    }

    @Override
    public void takeDmg(double dmg) {
        AudioManager.playNewAudio(AudioFile.HURT, 1);
        nextShieldRegenCheck = timeNow + REGENERATION_TIME_CD_SHIELD_MS;
        if (ShieldRectangle.getCurrentValue() > 0) {
            ShieldRectangle.decreaseCurrent(dmg);
            ShieldRectangle.barScaleAnimator(MAX_SHIELD);
            currentShield = ShieldRectangle.getCurrentValue();
        } else {
            nextHealthDmgCheck = timeNow + REGENERATION_TIME_CD_HP_MS;
            HPRectangle.decreaseCurrent(dmg);
            HPRectangle.barScaleAnimator(MAX_HP);
            currentHp = HPRectangle.getCurrentValue();
        }
        if (currentHp <= 0)
            killPlayer();
    }

    @Override
    public void heal(float amount) {
        HPRectangle.increaseCurrent(amount);
        HPRectangle.barScaleAnimator(MAX_HP);
        currentHp = HPRectangle.getCurrentValue();
    }

    private void shieldRegen() {
        if (nextShieldRegenCheck < timeNow) {
            ShieldRectangle.regeneration();
            ShieldRectangle.barScaleAnimator(MAX_SHIELD);
            currentShield = ShieldRectangle.getCurrentValue();
        }
    }

    private void healthRegen() {
        if (nextHealthDmgCheck < timeNow && nextHealthRegenCheck < timeNow) {
            nextHealthRegenCheck = timeNow + HEALTH_REGEN_INTERVAL;
            heal((float) (MAX_HP / 40));
        }
    }


    public PlayerProjectileControl getPrimaryBtnHandler() {
        return primaryBtnHandler;
    }

    public PlayerProjectileControl getSecondaryBtnHandler() {
        return secondaryBtnHandler;
    }

    public static double getMaxHp() {
        return MAX_HP;
    }

    public static double getMaxShield() {
        return MAX_SHIELD;
    }

    public static void setSPEED(float speed) {
        if ((Player.SPEED * speed) > MAX_SPEED) {
            Player.SPEED = MAX_SPEED;
        } else if (speed != 0) {
            Player.SPEED *= speed;
        } else {
            Player.SPEED = 4;
        }

    }

    public static float getSPEED() {
        return SPEED;
    }

    private void updateAngle(double x, double y) {
        angle = Math.toDegrees(atan2(y - getSpawner().getY(), x - getSpawner().getX()));
    }

    @Override
    public void update() {
        timeNow = System.currentTimeMillis();
        if (currentHp > 0) {
            updateAngle(InputManager.getMouseXPos(), InputManager.getMouseYPos());
            setRotate(angle);

            shieldRegen();
            healthRegen();
            move();
            warp();

            secondaryBtnHandler.update(angle);
            primaryBtnHandler.update(angle);
        }
    }

    @Override
    public Node[] getChildren() {
        return null;
    }

    public static void increaseCurrentScore(int amount) {
        currentScore += amount;
        GameViewManager.updateLabel();
    }

    public void resetScore() {
        currentScore = 0;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public void killPlayer() {
        AudioManager.playAudio(AudioFile.PLAYER_DEATH);
        GameViewManager.endGameSequence();
    }

    public static void increasePlayerCurrentScore(int amount) {
        currentScore += amount;
    }

}
