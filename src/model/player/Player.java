package model.player;

import javafx.animation.ScaleTransition;
import javafx.util.Duration;
import model.Entity;
import model.projectiles.PlayerProjectileControl;
import model.projectiles.ProjectileType;
import view.GameViewManager;
import view.InputManager;
import view.LevelManager;
import view.game.stats.StatBar;

import static java.lang.Math.atan2;

public class Player extends Entity {

    private static int currentScore = 0;
    private static final float SPEED = 4;
    private static final double MAX_HP = 200;
    private static final double MAX_SHIELD = 200;
    private static final long REGENERATION_TIME_CD_MS = 5000;

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

    public Player(PlayerType player, StatBar HPBar, StatBar ShieldBar) { //todo: change it to said's char mn 8er rotation
        super(player.getURL(), SPEED);

        setLayoutX((GameViewManager.WIDTH >> 1) - getFitWidth() / 2);
        setLayoutY((GameViewManager.HEIGHT >> 1) - getFitHeight() / 2);

        HPRectangle = HPBar;
        ShieldRectangle = ShieldBar;

        primaryBtnHandler = new PlayerProjectileControl(ProjectileType.BULLET,
                PlayerProjectileControl.buttons.PRIMARY);
        secondaryBtnHandler = new PlayerProjectileControl(ProjectileType.FIRE,
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

    private void move() { //todo can be coded more efficiently
        double DIAGONAL_FACTOR = 1.5;
        if (upPressed) {
            if (rightPressed || leftPressed) {
                setLayoutY(getLayoutY() - SPEED / DIAGONAL_FACTOR); // to avoid moving fast diagonally
            } else {
                setLayoutY(getLayoutY() - SPEED);
            }
        } else if (downPressed) {
            if (rightPressed || leftPressed) {
                setLayoutY(getLayoutY() + SPEED / DIAGONAL_FACTOR);
            } else {
                setLayoutY(getLayoutY() + SPEED);
            }
        }
        if (rightPressed) {
            if (upPressed || downPressed) {
                setLayoutX(getLayoutX() + SPEED / DIAGONAL_FACTOR);
            } else {
                setLayoutX(getLayoutX() + SPEED);
            }
        } else if (leftPressed) {
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

    @Override
    public void takeDmg(double dmg) {
        if (ShieldRectangle.getCurrentValue() > 0) {
            ShieldRectangle.decreaseCurrent(dmg);
            barScaleAnimator(ShieldRectangle);
            currentShield = ShieldRectangle.getCurrentValue();
        } else {
            HPRectangle.decreaseCurrent(dmg);
            barScaleAnimator(HPRectangle);
            currentHp = HPRectangle.getCurrentValue();
        }
        if (currentHp <= 0)
            killPlayer();
    }

    @Override
    public void heal(float amount) {
        HPRectangle.increaseCurrent(amount);
        barScaleAnimator(HPRectangle);
    }

    public void shieldRegen() {
        ShieldRectangle.regeneration();
        barScaleAnimator(ShieldRectangle);
    }

    private void barScaleAnimator(StatBar HP) {//todo change paramaters to StatBar only
        //todo this shouldn't be here
        ScaleTransition HPAnimation = new ScaleTransition(Duration.seconds(0.1), HP);

        HPAnimation.setToX((HP.getCurrentValue()) / MAX_HP);

        HPAnimation.play();
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

    private void updateAngle(double x, double y) {
        angle = Math.toDegrees(atan2(y - getSpawner().getY(), x - getSpawner().getX()));
    }

    @Override
    public void update() {
        if (currentHp > 0) {
            updateAngle(InputManager.getMouseXPos(), InputManager.getMouseYPos());
            setRotate(angle);

            move();
            warp();

            secondaryBtnHandler.update(angle);
            primaryBtnHandler.update(angle);
        }
    }

    public static void increaseCurrentScore(int amount) {
        currentScore += amount;
        System.out.println(currentScore);
        GameViewManager.updateLabel();
    }

    public void resetScore() {
        currentScore = 0;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public void killPlayer() {
        LevelManager.setSpawnable(false);
        GameViewManager.endGameSequence();
    }
}
