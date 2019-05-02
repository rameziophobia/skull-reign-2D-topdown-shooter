package model.walls;

import model.Entity;
import model.GameObject;
import view.Main;

import java.util.ArrayList;



public class Wall extends GameObject { //todo if needed
    public Wall(double x, double y) {
        super(Main.PATH_RESOURCES_SPRITES + "walls/" + "wall-250x60.png");//todo change null
        this.setLayoutY(y);
        this.setLayoutX(x);
    }
    public static boolean shouldNtMoveUp(Entity player, ArrayList<Wall> wallArrayList){
        return shouldNtMove(player,wallArrayList,false,-60);
    }
    public static boolean shouldNtMoveDown(Entity player,ArrayList<Wall> wallArrayList){
        return shouldNtMove(player,wallArrayList,false,43);
    }
    public static boolean shouldNtMoveLeft(Entity player,ArrayList<Wall> wallArrayList){
        return shouldNtMove(player,wallArrayList,true,-250);
    }
    public static boolean shouldNtMoveRight(Entity player,ArrayList<Wall> wallArrayList){
        return shouldNtMove(player,wallArrayList,true,49);
    }


    private static boolean shouldNtMove(Entity player, ArrayList<Wall> wallArrayList, boolean horizontal, int offset){
        for(Wall wall: wallArrayList) {
            if (wall.getBoundsInParent().intersects(player.getBoundsInParent())) {
                if (horizontal) {
                    if(Math.abs(player.getLayoutX() + offset - wall.getLayoutX() ) < 8){
                        return false;
                    }
                }
                else{
                    if(Math.abs(player.getLayoutY() + offset - wall.getLayoutY() ) < 8){
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
