package model.wall;


import controller.map.Map;
import javafx.scene.Node;
import javafx.scene.image.Image;
import model.Entity;
import model.GameObject;

import java.util.ArrayList;

public class Wall extends GameObject {
    private final static int MARGIN = 8;

    public Wall(Image image) {
        super(image);
    }

    public static boolean canMoveUp(Entity entity, ArrayList<Wall> wallArrayList) {
        return canMove(entity, wallArrayList, false, -Map.BLOCK_SIZE);
    }

    public static boolean canMoveDown(Entity entity, ArrayList<Wall> wallArrayList) {
        return canMove(entity, wallArrayList, false, (int) entity.getImage().getHeight());
    }

    public static boolean canMoveLeft(Entity entity, ArrayList<Wall> wallArrayList) {
        return canMove(entity, wallArrayList, true, -Map.BLOCK_SIZE);
    }

    public static boolean canMoveRight(Entity entity, ArrayList<Wall> wallArrayList) {
        return canMove(entity, wallArrayList, true, (int) entity.getImage().getHeight());
    }


    public static boolean canMove(GameObject gameObject, ArrayList<Wall> wallArrayList, boolean horizontal, double offset) {
        if (wallArrayList == null)
            return true;

        for (Wall wall : wallArrayList) {
            if (wall.getBoundsInParent().intersects(gameObject.getBoundsInParent())) {
                if (horizontal) {
                    if (Math.abs(gameObject.getLayoutX() + offset - wall.getLayoutX()) < MARGIN)
                        return false;
                } else {
                    if (Math.abs(gameObject.getLayoutY() + offset - wall.getLayoutY()) < 8)
                        return false;
                }
            }
            if (offset == 0)
                return wall.getBoundsInParent().intersects(gameObject.getBoundsInParent());
        }
        return true;
    }

    @Override
    public void update() {

    }

    @Override
    public Node[] getChildren() {
        return null;
    }
}
