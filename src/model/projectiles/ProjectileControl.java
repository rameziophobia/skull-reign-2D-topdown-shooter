package model.projectiles;

import javafx.geometry.Point2D;
import model.player.Player;
import view.GameViewManager;

import java.util.ArrayList;

import static view.GameViewManager.gamePane;

public abstract class ProjectileControl {
    protected ArrayList<Projectile> projArr = new ArrayList<>();
    protected Player player;
    protected double angle;
    protected Point2D playerLocation;

    public ProjectileControl(Player player) {
        this.player = player;
    }

    public void removeProjectile() {

        if (projArr.size() > 0) {
            ArrayList<Projectile> projArrRemove = new ArrayList<>();

            for (Projectile p : projArr) {
                //if the object crossed the boundary adds it to the remove list
                if (p.getLayoutY() > GameViewManager.HEIGHT + 200 || p.getLayoutY() < -200) {
                    projArrRemove.add(p);
                } else if (p.getLayoutX() > GameViewManager.WIDTH +200 || p.getLayoutX() < -200) {
                    projArrRemove.add(p);
                }
            }
            gamePane.getChildren().removeAll(projArrRemove);
            projArr.removeAll(projArrRemove);
        }
    }

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
    public void savePlayerLocation(){
        this.playerLocation = new Point2D(player.getLayoutX(),player.getLayoutY());
    }
    protected void update(double angle){
        this.angle = angle;
        removeProjectile();
        savePlayerLocation();
//        moveProjectile(); //todo just dontttt
    }
}
