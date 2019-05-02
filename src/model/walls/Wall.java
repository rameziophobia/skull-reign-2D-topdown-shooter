package model.walls;

import model.Entity;
import model.GameObject;
import view.Main;


import static view.GameViewManager.*;

import java.util.ArrayList;


public class Wall extends GameObject {
    private static int imageWidth;
    private static int imageHeight;
    private static int playerWidth;
    private static int playerHeight;


    public Wall(double x, double y) {
        super(Main.PATH_RESOURCES_SPRITES + "walls/" + "wall-250x60.png");
        this.setLayoutY(y);
        this.setLayoutX(x);

        imageHeight = (int) getImage().getHeight();
        imageWidth = (int) getImage().getWidth();
        playerHeight = (int) getPlayer().getImage().getHeight();
        playerWidth = (int) getPlayer().getImage().getWidth();

    }

    public static boolean canMoveUp(Entity player, ArrayList<Wall> wallArrayList) {
        return canMove(player, wallArrayList, false, -imageHeight);
    }

    public static boolean canMoveDown(Entity player, ArrayList<Wall> wallArrayList) {
        return canMove(player, wallArrayList, false, playerHeight);
    }

    public static boolean canMoveLeft(Entity player, ArrayList<Wall> wallArrayList) {
        return canMove(player, wallArrayList, true, -imageWidth);
    }

    public static boolean canMoveRight(Entity player, ArrayList<Wall> wallArrayList) {
        return canMove(player, wallArrayList, true, playerWidth);
    }


    private static boolean canMove(Entity player, ArrayList<Wall> wallArrayList, boolean horizontal, int offset) {
        for (Wall wall : wallArrayList) {
            if (wall.getBoundsInParent().intersects(player.getBoundsInParent())) {
                if (horizontal) {
                    if (Math.abs(player.getLayoutX() + offset - wall.getLayoutX()) < 8) {
                        return false;
                    }
                } else {
                    if (Math.abs(player.getLayoutY() + offset - wall.getLayoutY()) < 8) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public void update() {

    }
}
