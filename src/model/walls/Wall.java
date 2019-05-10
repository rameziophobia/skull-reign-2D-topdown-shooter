package model.walls;

import model.Entity;
import model.GameObject;
import view.Main;

import java.util.ArrayList;



public class Wall extends GameObject {

    private final static int MARGIN = 8;


    public Wall(double x, double y) {
        super(Main.PATH_RESOURCES_SPRITES + "walls/" + "wall-250x60.png");
        this.setLayoutY(y);
        this.setLayoutX(x);
    }

    public static boolean canMoveUp(Entity entity, ArrayList<Wall> wallArrayList) {

        return canMove(entity, wallArrayList, false, -60);
    }

    public static boolean canMoveDown(Entity entity, ArrayList<Wall> wallArrayList) {
        return canMove(entity, wallArrayList, false, (int)entity.getImage().getHeight());
    }

    public static boolean canMoveLeft(Entity entity, ArrayList<Wall> wallArrayList) {
        return canMove(entity, wallArrayList, true, -250);
    }

    public static boolean canMoveRight(Entity entity, ArrayList<Wall> wallArrayList) {
        return canMove(entity, wallArrayList, true, (int)entity.getImage().getHeight());
    }

    public static boolean canMove(GameObject gameObject, ArrayList<Wall> wallArrayList, boolean horizontal, int offset){
        for(Wall wall: wallArrayList) {
            if (wall.getBoundsInParent().intersects(gameObject.getBoundsInParent())) {
                if (horizontal) {
                    if(Math.abs(gameObject.getLayoutX() + offset - wall.getLayoutX() ) < MARGIN){
                        return false;
                    }
                }
                else{
                    if(Math.abs(gameObject.getLayoutY() + offset - wall.getLayoutY() ) < 8){
                        return false;
                    }
                }
            }
            if(offset == 0){
                return wall.getBoundsInParent().intersects(gameObject.getBoundsInParent());
            }
        }
        return true;
    }

    @Override
    public void update() {

    }
}
