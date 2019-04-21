package model.player;

import javafx.animation.ScaleTransition;
import javafx.util.Duration;
import model.Sprite;
import view.Bars;
import view.GameViewManager;


public class Player extends Sprite {

    private final static int WIDTH = 28;
    private final static int HEIGHT = 43;
    private final static double SPEED = 4;
    private static final double MAX_HP = 100;
    private static final double MAX_SHIELD = 100;
    private Bars HPRectangle;
    private Bars ShieldRectangle;

    public Player(PLAYERS player, Bars HPBar,Bars ShieldBar) { //todo: change magics
        super(player.URL, WIDTH, HEIGHT,SPEED,player.spawner,null);
        setLayoutX(GameViewManager.WIDTH / 2 - getFitWidth() / 2);
        setLayoutY(GameViewManager.HEIGHT / 2 - getFitHeight() / 2); //todo: howa leh msh fel center >.< ?
        HPRectangle = HPBar;
        ShieldRectangle = ShieldBar;
    }





    public void move(boolean upPressed, boolean downPressed,
    boolean leftPressed, boolean rightPressed) { //todo can be coded more efficiently
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

    public void warp(){
        setLayoutY((getLayoutY() < 0) ? (getLayoutY() + GameViewManager.HEIGHT) :(getLayoutY() % GameViewManager.HEIGHT));
        setLayoutX((getLayoutX() < 0) ? (getLayoutX() + GameViewManager.WIDTH) : (getLayoutX() % GameViewManager.WIDTH));
    }
    public void takeDmg(double damage) {

        if(ShieldRectangle.getCurrentValue() > 0){
            ShieldRectangle.decreaseCurrent(damage);
            barScaleAnimator(ShieldRectangle);
            GameViewManager.nextRegenTime = System.currentTimeMillis() + GameViewManager.regenerationTimeLimitms;
        }
        else {
            HPRectangle.decreaseCurrent(damage);
            barScaleAnimator(HPRectangle);
        }
    }

    public void increaseHP(double Value){
        HPRectangle.increaseCurrent(Value);
        barScaleAnimator(HPRectangle);
    }

    public void regenerate(){
        ShieldRectangle.regeneration();
        barScaleAnimator(ShieldRectangle);
    }

    private void barScaleAnimator( Bars HP) {//todo change paramaters to Bars only
        ScaleTransition HPAnimation = new ScaleTransition(Duration.seconds(0.1),HP);

        HPAnimation.setToX((HP.getCurrentValue())/MAX_HP);

        HPAnimation.play();
    }

    public static double getMaxHp() {
        return MAX_HP;
    }

    public static double getMaxShield() {
        return MAX_SHIELD;
    }
}
