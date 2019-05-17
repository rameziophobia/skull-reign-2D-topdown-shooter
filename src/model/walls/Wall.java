package model.walls;

import model.Entity;
import model.GameObject;
import view.Main;

import java.util.ArrayList;



public class Wall extends GameObject {

    private final static int MARGIN = 8;


    public Wall(double x, double y) {
        super(Main.PATH_RESOURCES_SPRITES + "walls/" + "WallCenter-56x56.png");
        this.setLayoutY(y);
        this.setLayoutX(x);
    }

    public static boolean canMoveUp(Entity entity, ArrayList<Wall> wallArrayList) {
        return canMove(entity, wallArrayList, false, -57);
    }

    public static boolean canMoveDown(Entity entity, ArrayList<Wall> wallArrayList) {
        return canMove(entity, wallArrayList, false, (int)entity.getImage().getHeight());
    }

    public static boolean canMoveLeft(Entity entity, ArrayList<Wall> wallArrayList) {
        return canMove(entity, wallArrayList, true, -57);
    }

    public static boolean canMoveRight(Entity entity, ArrayList<Wall> wallArrayList) {
        return canMove(entity, wallArrayList, true, (int)entity.getImage().getWidth());
    }

    public static boolean canMove(GameObject gameObject, ArrayList<Wall> wallArrayList, boolean horizontal, int offset){
        if(wallArrayList == null)
            return true;//todo da ybawaz 7aga?

        for(Wall wall: wallArrayList) {
            if (wall.isIntersects(gameObject)) {
                if (horizontal) {
                    if(Math.abs(gameObject.getLayoutX() + offset - wall.getLayoutX() ) < MARGIN){
                        return false;
                    }
                }
                else{
                    if(Math.abs(gameObject.getLayoutY() + offset - wall.getLayoutY() ) < MARGIN){
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
