package model.player;

import javafx.animation.ScaleTransition;
import javafx.geometry.Point2D;
import javafx.util.Duration;
import model.Sprite;
import model.projectiles.PlayerProjectileControl;
import model.projectiles.Projectile;
import model.projectiles.ProjectileType;
import view.Bars;
import view.GameViewManager;

import java.util.ArrayList;

import static java.lang.Math.atan2;


public class Player extends Sprite {

    private final static int WIDTH = 28;
    private final static int HEIGHT = 43;
    private final static double SPEED = 4;
    private static final double MAX_HP = 100;
    private static final double MAX_SHIELD = 100;
    private Bars HPRectangle;
    private Bars ShieldRectangle;
    private final PlayerProjectileControl primaryBtnHandler;
    private final PlayerProjectileControl secondaryBtnHandler;
    private double currentHp = MAX_HP;
    private double angle;


    public Player(PLAYERS player, Bars HPBar, Bars ShieldBar) { //todo: change it to said's char mn 8er rotation
        super(player.URL, SPEED, player.spawner);
        setLayoutX((GameViewManager.WIDTH >> 1) - getFitWidth() / 2);
        setLayoutY((GameViewManager.HEIGHT >> 1) - getFitHeight() / 2);
        HPRectangle = HPBar;
        ShieldRectangle = ShieldBar;
        primaryBtnHandler = new PlayerProjectileControl(ProjectileType.BULLET,
                PlayerProjectileControl.buttons.PRIMARY, this);
        secondaryBtnHandler = new PlayerProjectileControl(ProjectileType.FIRE,
                PlayerProjectileControl.buttons.SECONDARY, this);
    }

    private void move(boolean upPressed, boolean downPressed,
                      boolean leftPressed, boolean rightPressed) {
        final double DIAGONAL_FACTOR = 1.5;


        if (upPressed) {
            if (rightPressed || leftPressed) {
                setLayoutY(getLayoutY() - SPEED / DIAGONAL_FACTOR); // to avoid moving fast diagonally
            } else {
                setLayoutY(getLayoutY() - SPEED);
            }
        }
        if (downPressed) {
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
        }

        if (leftPressed) {
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

    public void takeDmg(double damage) {

        if (ShieldRectangle.getCurrentValue() > 0) {
            ShieldRectangle.decreaseCurrent(damage);
            barScaleAnimator(ShieldRectangle);
            GameViewManager.nextRegenTime = System.currentTimeMillis() + GameViewManager.regenerationTimeLimitms;
        } else {
            HPRectangle.decreaseCurrent(damage);
            barScaleAnimator(HPRectangle);
        }
    }

    public void increaseHP(double Value) {
        HPRectangle.increaseCurrent(Value);
        barScaleAnimator(HPRectangle);
    }

    public void regenerate() {
        ShieldRectangle.regeneration();
        barScaleAnimator(ShieldRectangle);
    }

    private void barScaleAnimator(Bars HP) {//todo change paramaters to Bars only
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

    private double calculateRotation(Point2D mouseLocation) {
        angle = Math.toDegrees(atan2(mouseLocation.getY() - getSpawner().getY(), mouseLocation.getX() - getSpawner().getX()));
        return angle;
    }

    public void control(boolean upPressed, boolean downPressed, //todo: change to an enum array keys pressed
                        boolean leftPressed, boolean rightPressed,
                        double mouseXPos, double mouseYPos) {
        setRotate(calculateRotation(new Point2D(mouseXPos, mouseYPos)));
        move(upPressed, downPressed, leftPressed, rightPressed);
        warp();
        secondaryBtnHandler.update(angle);
        primaryBtnHandler.update(angle);

        //available powerUps
//        primaryBtnHandler.setPowerUp(PowerUp.SCALE, 3);
//        primaryBtnHandler.setPowerUp(PowerUp.MULT, 3);
//        secondaryBtnHandler.setRange(400);
//        primaryBtnHandler.setRange(700);
//        secondaryBtnHandler.setPowerUp(PowerUp.MULT, 10);
//                projectileHandler.setPowerUp(PowerUp.SPEED,30);

    }

    public ArrayList<Projectile> getProjArr() {
        ArrayList<Projectile> projArr =  new ArrayList<Projectile>();
        projArr.addAll(primaryBtnHandler.getProjArr());
        projArr.addAll(secondaryBtnHandler.getProjArr());
        return projArr;
    }
}
