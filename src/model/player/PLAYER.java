package model.player;

import model.Sprite;
import view.GameViewManager;

public class Player extends Sprite {

    final static int WIDTH = 28;
    final static int HEIGHT = 43;
    final static double SPEED = 6;
    private static final double MAX_HP = 100;
    private double currentHp = MAX_HP;

    public Player(PLAYERS player) { //todo: change magics
        super(player.getUrlPlayer(), WIDTH, HEIGHT,SPEED,player.spawner);
        setLayoutX(WIDTH / 2 - getFitWidth() / 2);
        setLayoutY(HEIGHT / 2 - getFitHeight() / 2);
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
}
