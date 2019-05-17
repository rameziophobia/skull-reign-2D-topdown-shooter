package model.walls;

import javafx.scene.shape.Rectangle;
import model.Entity;
import model.GameObject;
import model.player.Player;
import view.GameViewManager;
import view.LevelManager;
import view.Main;

import java.util.ArrayList;




public class Wall extends GameObject {

    private final static int MARGIN = 8;
    private static Boolean cantVertical=false;
    private static Boolean cantHorizontal = false;

    public Wall(double x, double y) {
        super(Main.PATH_RESOURCES_SPRITES + "walls/" + "WallCenter-56x56.png");
        Rectangle rectangle = new Rectangle();
        rectangle.setHeight(63);
        this.setLayoutY(y);
        this.setLayoutX(x);
    }

    public static boolean canMoveUp(Entity entity, ArrayList<Wall> wallArrayList) {
        return canMove(entity, wallArrayList, false, -57,0);
    }

    public static boolean canMoveDown(Entity entity, ArrayList<Wall> wallArrayList) {
        return canMove(entity, wallArrayList, false, (int)entity.getImage().getHeight(),1);
    }

    public static boolean canMoveLeft(Entity entity, ArrayList<Wall> wallArrayList) {
        return canMove(entity, wallArrayList, true, -57,2);
    }

    public static boolean canMoveRight(Entity entity, ArrayList<Wall> wallArrayList) {
        return canMove(entity, wallArrayList, true, (int)entity.getImage().getWidth(),3);
    }

    private static void positionEntity(GameObject entity,int position,Wall wall){
        switch (position){
            case 0:
                entity.setLayoutY(wall.getLayoutY()+58);
                if(entity instanceof Player){
                    Player player = (Player) entity;
                    player.setUpPressed(false);
                }
                break;
            case 1:
                entity.setLayoutY(wall.getLayoutY()-entity.getImage().getHeight()-2);
                if(entity instanceof Player){
                    Player player = (Player) entity;
                    player.setDownPressed(false);
                }
                break;
            case 2:
                entity.setLayoutX(wall.getLayoutX()+58);
                if(entity instanceof Player){
                    Player player = (Player) entity;
                    player.setLeftPressed(false);
                }
                break;
            case 3:
                entity.setLayoutX(wall.getLayoutX()-entity.getImage().getWidth()-2);
                if(entity instanceof Player){
                    Player player = (Player) entity;
                    player.setRightPressed(false);
                }
                break;
            default:
                break;
        }

    }
    public static boolean canMove(GameObject gameObject, ArrayList<Wall> wallArrayList, boolean horizontal, int offset,int position){
        if(wallArrayList == null)
            return true;//todo da ybawaz 7aga?

        for(Wall wall: wallArrayList) {
            if (wall.isIntersects(gameObject)) {
                if (horizontal) {
                    if(Math.abs(gameObject.getLayoutX() + offset - wall.getLayoutX() ) < MARGIN){
                        positionEntity(gameObject,position,wall);
                        return false;
                    }
                }
                else{
                    if(Math.abs(gameObject.getLayoutY() + offset - wall.getLayoutY() ) < MARGIN){
                        positionEntity(gameObject,position,wall);
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
