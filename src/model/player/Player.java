package model.player;

import javafx.animation.ScaleTransition;
import javafx.util.Duration;
import model.Entity;
import model.walls.Wall;
import view.LevelManager;
import view.game.stats.StatBar;
import model.projectiles.PlayerProjectileControl;
import model.projectiles.ProjectileType;
import view.GameViewManager;
import view.InputManager;

import static java.lang.Math.atan2;

public class Player extends Entity {



    private static final float MAX_SPEED = 8;
    private static float SPEED = 4;
    private static final double MAX_HP = 500;
    private static final double MAX_SHIELD = 500;
    private static final long REGENERATION_TIME_CD_MS = 5000;

    private StatBar HPRectangle;
    private StatBar ShieldRectangle;
    private final PlayerProjectileControl primaryBtnHandler;
    private final PlayerProjectileControl secondaryBtnHandler;
    private double currentHp = MAX_HP;
    private double angle;

    private boolean upPressed;
    private boolean downPressed;
    private boolean leftPressed;
    private boolean rightPressed;

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
            if(Wall.canMoveUp(this, LevelManager.getWallArrayList())&& !atTopBorder()){
                if (rightPressed || leftPressed) {
                    setLayoutY(getLayoutY() - SPEED / DIAGONAL_FACTOR); // to avoid moving fast diagonally
                } else {
                    setLayoutY(getLayoutY() - SPEED);
                }
            }
        } else if (downPressed) {
            if(Wall.canMoveDown(this, LevelManager.getWallArrayList()) && !atBottomBorder()){
                if (rightPressed || leftPressed) {
                    setLayoutY(getLayoutY() + SPEED / DIAGONAL_FACTOR);
                } else {
                    setLayoutY(getLayoutY() + SPEED);
                }
            }
        }
        if (rightPressed) {
            if(Wall.canMoveRight(this, LevelManager.getWallArrayList()) && !atRightBorder()){
                if (upPressed || downPressed) {
                    setLayoutX(getLayoutX() + SPEED / DIAGONAL_FACTOR);
                } else {
                    setLayoutX(getLayoutX() + SPEED);
                }
            }

        } else if (leftPressed) {
            if(Wall.canMoveLeft(this, LevelManager.getWallArrayList()) && !atLeftBorder()){
                if (upPressed || downPressed) {
                    setLayoutX(getLayoutX() - SPEED / DIAGONAL_FACTOR);
                } else {
                    setLayoutX(getLayoutX() - SPEED);
                }
            }
        }

    }

    private void warp() {
        setLayoutY((getLayoutY() < 0) ? (getLayoutY() + GameViewManager.HEIGHT) : (getLayoutY() % GameViewManager.HEIGHT));
        setLayoutX((getLayoutX() < 0) ? (getLayoutX() + GameViewManager.WIDTH) : (getLayoutX() % GameViewManager.WIDTH));
    }
    private boolean atRightBorder(){
        return( getLayoutX() >= GameViewManager.WIDTH - 49);

    }
    private boolean atLeftBorder(){
        return( getLayoutX() < 0);

    }
    private boolean atBottomBorder(){
        return( getLayoutY()  >= GameViewManager.HEIGHT - 43);

    }
    private boolean atTopBorder(){
        return( getLayoutY() < 0);

    }

    @Override
    public void takeDmg(double dmg) {
        if (ShieldRectangle.getCurrentValue() > 0) {
            ShieldRectangle.decreaseCurrent(dmg);
            barScaleAnimator(ShieldRectangle);
        } else {
            HPRectangle.decreaseCurrent(dmg);
            barScaleAnimator(HPRectangle);
        }
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
    public static void setSPEED(float speed) {
        if((Player.SPEED * speed) >8){
            Player.SPEED = 8;
        }
        else if(speed != 0){
            Player.SPEED *= speed;
        }
        else{
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
        updateAngle(InputManager.getMouseXPos(), InputManager.getMouseYPos());
        setRotate(angle);

        move();

        secondaryBtnHandler.update(angle);
        primaryBtnHandler.update(angle);
    }
}
