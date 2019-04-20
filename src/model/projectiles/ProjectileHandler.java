package model.projectiles;

import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.Pane;
import model.player.Player;
import view.GameViewManager;

import java.util.ArrayList;
import java.util.HashMap;

public class ProjectileHandler {

//    private static final int powerUpNum = 5;
    private static final Pane gamePane = GameViewManager.gamePane;

    private long[] lastFired;

    private double angle;
    private ProjectileType primary;
    private ProjectileType secondary;
    private ArrayList<Projectile> projArr;
    private Player playerFiring;

    private boolean mousePressed;
    private enum buttons {PRIMARY, SECONDARY}
    private buttons lastPressed;

    private HashMap<String, Double> powerUpPrimary;
    private HashMap<String, Double> powerUpSecondary;


    public ProjectileHandler(ProjectileType primary, ProjectileType secondary, Player playerFiring,
                             ArrayList<Projectile> projArr) {

        //todo: class needs renaming
        this.lastFired = new long[5];
        this.primary = primary;
        this.secondary = secondary;
        this.projArr = projArr;
        this.playerFiring = playerFiring;
        this.powerUpPrimary = new HashMap<>();
        this.powerUpSecondary = new HashMap<>();
        initializePowerUp();

    }

    private void initializePowerUp() {
        for(PowerUp pup: PowerUp.values()){
            powerUpPrimary.put(pup.name(), (double) 0);
            powerUpSecondary.put(pup.name(), (double) 0);
        }
        powerUpPrimary.put(PowerUp.MULT.name(), (double) 1);
        powerUpSecondary.put(PowerUp.MULT.name(), (double) 1);
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
            createProjectile(0, primary, powerUpPrimary);
        } else if (lastPressed == buttons.SECONDARY) {

            createProjectile(1, secondary, powerUpSecondary);
        }
    }

    private void detectBtnType(MouseEvent e) {

        if (e.isPrimaryButtonDown()) {
            lastPressed = buttons.PRIMARY; //buttons da enum ana 3amlo
        } else if (e.isSecondaryButtonDown()) {
            lastPressed = buttons.SECONDARY; //buttons da enum ana 3amlo
        }
    }

    private void createProjectile(int i, ProjectileType fire, HashMap<String, Double> powerUp) {
        if (System.currentTimeMillis() > (lastFired[i] + 1000 / fire.FIRERATE)) {
            System.out.println("yo");
            for(int mult = 0; mult < powerUp.get(PowerUp.MULT.name()); mult++){
                projArr.add(new Projectile(playerFiring.getSpawner(),
                        fire, angle + mult * fire.MULTANGLE));
                System.out.println("ysso");
                lastFired[i] = System.currentTimeMillis();

                projArr.get(projArr.size() - 1).setScale(powerUp.get(PowerUp.SCALE.name()));
                projArr.get(projArr.size() - 1).addSpeed(powerUp.get(PowerUp.SPEED.name()));
                gamePane.getChildren().add(projArr.get(projArr.size() - 1));
            }

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


    public void setPowerUpPrimary(PowerUp key, double value) {
        powerUpPrimary.put(key.name(), value);
    }

    public void setPowerUpSecondary(PowerUp key, double value) {
        powerUpSecondary.put(key.name(), value);
    }

    public void setPowerUpAll(PowerUp key,double value) {
        setPowerUpPrimary(key, value);
        setPowerUpSecondary(key, value);
    }

}
