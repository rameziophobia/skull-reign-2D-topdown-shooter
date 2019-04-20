package model.projectiles;

import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.Pane;
import model.player.Player;
import view.GameViewManager;

import java.util.ArrayList;

public class ProjectileHandler {

    private long[] lastFired;

    private double angle;
    private ProjectileType primary;
    private ProjectileType secondary;
    private ArrayList<Projectile> projArr;
    private Player playerFiring;

    private boolean mousePressed;
    private enum buttons {PRIMARY, SECONDARY}
    private buttons lastPressed;


    private Pane gamePane;

    public ProjectileHandler(ProjectileType primary, ProjectileType secondary, Player playerFiring,
                             Pane gamePane, ArrayList<Projectile> projArr) {

        //todo: class needs renaming
        this.lastFired = new long[5];
        this.primary = primary;
        this.secondary = secondary;
        this.projArr = projArr;
        this.playerFiring = playerFiring;
        this.gamePane = gamePane;

    }

    public void fireProjectile() {
        if(mousePressed){
            detectTouchType();//todo: func name needs refactoring
        }
    }
    public void fireProjectile(double angle) {

        this.angle = angle;
        gamePane.addEventFilter(MouseEvent.ANY, this::detectBtnType);
        gamePane.addEventFilter(TouchEvent.ANY, e -> detectTouchType());//law el shasha touch xD

        gamePane.setOnMousePressed(e -> mousePressed = true);
        gamePane.setOnMouseReleased(e -> mousePressed = false);
    }

    private void detectTouchType() {
        if (lastPressed == buttons.PRIMARY) {
            createProjectile(0, primary);
        } else if (lastPressed == buttons.SECONDARY) {
            createProjectile(1, secondary);
        }
    }

    private void detectBtnType(MouseEvent e) {

        if (e.isPrimaryButtonDown()) {
            lastPressed = buttons.PRIMARY; //buttons da enum ana 3amlo
        } else if (e.isSecondaryButtonDown()) {
            lastPressed = buttons.SECONDARY; //buttons da enum ana 3amlo

        }
    }

    private void createProjectile(int i, ProjectileType fire) {
        if (System.currentTimeMillis() > (lastFired[i] + 1000 / fire.FIRERATE)) {

            projArr.add(new Projectile(playerFiring.getSpawner(),
                    fire, angle));

            lastFired[i] = System.currentTimeMillis();

            gamePane.getChildren().add(
                    projArr.get(projArr.size() - 1));
        }
    }

    public void moveProjectile() {

        if (projArr.size() > 0) {

            ArrayList<Projectile> projArrRemove = new ArrayList<>();

            for (Projectile p : projArr) {
                p.move();
                //if the object crossed the boundary adds it to the remove list
                if (p.getLayoutY() > GameViewManager.HEIGHT ||
                        p.getLayoutY() < 0) {
                    projArrRemove.add(p);
                } else if (p.getLayoutX() > GameViewManager.WIDTH ||
                        p.getLayoutX() < 0) {
                    projArrRemove.add(p);
                }
            }

            gamePane.getChildren().removeAll(projArrRemove);
            projArr.removeAll(projArrRemove);

        }
    }


    public void setPrimary(ProjectileType primary) {
        this.primary = primary;
    }

    public void setSecondary(ProjectileType secondary) {
        this.secondary = secondary;
    }
}
