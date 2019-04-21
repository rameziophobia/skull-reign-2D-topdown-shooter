package model.player;

import javafx.geometry.Point2D;
import model.Sprite;
import model.projectiles.Projectile;
import model.projectiles.spellMaker;
import model.projectiles.ProjectileType;
import view.GameViewManager;

import java.util.ArrayList;

import static java.lang.Math.atan2;


public class Player extends Sprite {

    private final static int WIDTH = 28;
    private final static int HEIGHT = 43;
    private final static double SPEED = 4;
    private static final double MAX_HP = 100;
    private final spellMaker primaryBtnHandler;
    private final spellMaker secondaryBtnHandler;
    private double currentHp = MAX_HP;
    private double angle;

    //todo: change projArr to array containing array of projectiles for everyType????
    public Player(PLAYERS player, ArrayList<Projectile> projArr) { //todo: change magics
        super(player.URL, WIDTH, HEIGHT,SPEED,player.spawner,null);
        setLayoutX(GameViewManager.WIDTH / 2 - getFitWidth() / 2);
        setLayoutY(GameViewManager.HEIGHT / 2 - getFitHeight() / 2);

        primaryBtnHandler = new spellMaker(ProjectileType.BULLET,
                spellMaker.buttons.PRIMARY,this, projArr);
        secondaryBtnHandler = new spellMaker(ProjectileType.FIRE,
                spellMaker.buttons.SECONDARY,this, projArr);
    }

    private void move(boolean upPressed, boolean downPressed,
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

    private void warp(){
        setLayoutY((getLayoutY() < 0) ? (getLayoutY() + GameViewManager.HEIGHT) :(getLayoutY() % GameViewManager.HEIGHT));
        setLayoutX((getLayoutX() < 0) ? (getLayoutX() + GameViewManager.WIDTH) : (getLayoutX() % GameViewManager.WIDTH));
    }

    private double calculateRotation(Point2D mouseLocation) {
        angle = Math.toDegrees(atan2(mouseLocation.getY() - getLayoutY(), mouseLocation.getX()  - getLayoutX()));
        return angle;
    }

    public void control(boolean upPressed, boolean downPressed, //todo: change to an enum array keys pressed
                        boolean leftPressed, boolean rightPressed,
                        double mouseXPos, double mouseYPos)
    {
        setRotate(calculateRotation(new Point2D(mouseXPos, mouseYPos)));
        move(upPressed, downPressed, leftPressed, rightPressed);
        warp();
        secondaryBtnHandler.update(angle);
        primaryBtnHandler.update(angle);

//                projectileHandler.setPowerUp(PowerUp.SCALE,3);
//        projectileHandler.setRange(500);
//        projectileHandler.setPowerUp(PowerUp.MULT,30);
//                projectileHandler.setPowerUp(PowerUp.SPEED,30);

    }
}
