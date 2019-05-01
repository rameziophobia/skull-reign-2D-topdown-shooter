package model.walls;

import javafx.geometry.Point2D;
import model.Sprite;

import java.util.ArrayList;

public class Wall extends Sprite { //todo if needed
    public Wall(double x, double y) {
        super("file:src/model/resources/wall-250x60.png",4,new Point2D(100,100),null);//todo change null
        this.setLayoutY(y);
        this.setLayoutX(x);
    }
    public static boolean shouldNtMoveUp(Sprite player, ArrayList<Wall> wallArrayList){
        return shouldNtMove(player,wallArrayList,false,-60);
    }
    public static boolean shouldNtMoveDown(Sprite player,ArrayList<Wall> wallArrayList){
        return shouldNtMove(player,wallArrayList,false,43);
    }
    public static boolean shouldNtMoveLeft(Sprite player,ArrayList<Wall> wallArrayList){
        return shouldNtMove(player,wallArrayList,true,-250);
    }
    public static boolean shouldNtMoveRight(Sprite player,ArrayList<Wall> wallArrayList){
        return shouldNtMove(player,wallArrayList,true,49);
    }


    private static boolean shouldNtMove(Sprite player,ArrayList<Wall> wallArrayList,boolean horizontal,int offset){
        for(Wall wall: wallArrayList) {
            if (wall.getBoundsInParent().intersects(player.getBoundsInParent())) {
                if (horizontal) {
                    if(Math.abs(player.getLayoutX() + offset - wall.getLayoutX() ) < 8){
                        return true;
                    }
                }
                else{
                    if(Math.abs(player.getLayoutY() + offset - wall.getLayoutY() ) < 8){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
