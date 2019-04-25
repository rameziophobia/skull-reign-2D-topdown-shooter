package model.projectiles;

import javafx.geometry.Point2D;

import java.util.ArrayList;

public abstract class ProjectileControl {
    protected ArrayList<Projectile> projArr = new ArrayList<>();
    protected double angle;
    protected Point2D playerLocation;

    public ProjectileControl() {

    }

//    public void removeProjectile() {
//
//        if (projArr.size() > 0) {
//            ArrayList<Projectile> projArrRemove = new ArrayList<>();
//
//            gamePane.getChildren().removeAll(projArrRemove);
//            projArr.removeAll(projArrRemove);
//        }
//    }

    public ArrayList<Projectile> getProjArr() {
        return projArr;
    }
    public void moveProjectile() {
        if (projArr.size() > 0) {
            for (Projectile p : projArr) {
                p.move();
            }
        }
    }

//    public void savePlayerLocation(){
//        this.playerLocation = new Point2D(player.getLayoutX(),player.getLayoutY());
//    }
    protected void update(double angle){
        this.angle = angle;
//        removeProjectile();
//        savePlayerLocation();
//        moveProjectile(); //todo just dontttt
    }
}
