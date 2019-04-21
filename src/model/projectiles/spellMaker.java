package model.projectiles;

import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.Pane;
import model.player.Player;
import view.GameViewManager;

import java.util.ArrayList;
import java.util.HashMap;

public class spellMaker {

    private static final Pane gamePane = GameViewManager.gamePane;

    private long lastFireTime;

    private double angle;
    private ProjectileType type;
    private ArrayList<Projectile> projArr;
    private Player playerFiring;

    private boolean mousePressed;

    public enum buttons {PRIMARY, SECONDARY}
    private final buttons projectileBtn;
    private buttons lastPressed;

    private HashMap<PowerUp, Double> powerUp;

    private boolean rangeEnable = false;
    private double range;

    public spellMaker(ProjectileType projectile, buttons projectileBtn, Player playerFiring,
                      ArrayList<Projectile> projArr) {

        //todo: class needs renaming
        this.type = projectile;
        this.projectileBtn = projectileBtn;
        this.projArr = projArr;
        this.playerFiring = playerFiring;
        this.powerUp = new HashMap<>();
        initializePowerUp();

    }

    private void initializePowerUp() {
        for (PowerUp pup : PowerUp.values()) {
            powerUp.put(pup, (double) 0);
        }
        powerUp.put(PowerUp.MULT, (double) 1);
    }

    public void fireProjectile() {
        if (mousePressed) {
            isProjectileBtnPressed();//todo: func name needs refactoring
        }
    }

    public void mouseEvents() {
        gamePane.addEventFilter(MouseEvent.ANY, this::detectBtnType);
        gamePane.addEventFilter(TouchEvent.ANY, e -> isProjectileBtnPressed());//law el shasha touch xD

        gamePane.addEventFilter(MouseEvent.MOUSE_PRESSED,e -> mousePressed = true);
        gamePane.addEventFilter(MouseEvent.MOUSE_RELEASED,e -> mousePressed = false);

    }

    public void update(double angle){
        this.angle = angle;
        moveProjectile();
        fireProjectile();
        mouseEvents();
    }

    private void isProjectileBtnPressed() {
        if (lastPressed == projectileBtn) {
            createProjectile();
        }
    }

    private void detectBtnType(MouseEvent e) {

        if (e.isPrimaryButtonDown()) {
            lastPressed = buttons.PRIMARY; //buttons da enum ana 3amlo
        } else if (e.isSecondaryButtonDown()) {
            lastPressed = buttons.SECONDARY; //buttons da enum ana 3amlo
        }
    }

    private void createProjectile() {

        if (System.currentTimeMillis() > (lastFireTime + 1000 / type.FIRERATE)) {
            for (int mult = 0; mult < powerUp.get(PowerUp.MULT); mult++) {

                Projectile projectile = new Projectile(playerFiring.getSpawner(),
                        type, angle + mult * type.MULTANGLE * Math.pow(-1, mult));

                projectile.setScale(powerUp.get(PowerUp.SCALE));
                projectile.addSpeed(powerUp.get(PowerUp.SPEED));

                projArr.add(projectile);
                lastFireTime = System.currentTimeMillis();
                gamePane.getChildren().add(projectile);
            }
        }
    }

    public void moveProjectile() {

        if (projArr.size() > 0) {

            ArrayList<Projectile> projArrRemove = new ArrayList<>();

            for (Projectile p : projArr) {
                p.move();
                //if the object crossed the boundary adds it to the remove list
                if (p.getLayoutY() > GameViewManager.HEIGHT || p.getLayoutY() < 0 )
                {
                    projArrRemove.add(p);

                } else if (p.getLayoutX() > GameViewManager.WIDTH || p.getLayoutX() < 0)
                {
                    projArrRemove.add(p);

                } else if (rangeEnable && rangeTooFar(p))
                {
                    projArrRemove.add(p);
                }
            }

            gamePane.getChildren().removeAll(projArrRemove);
            projArr.removeAll(projArrRemove);

        }
    }

    private boolean rangeTooFar(Projectile p) {
        return Math.hypot(p.getLayoutX() - playerFiring.getLayoutX(), p.getLayoutY() - playerFiring.getLayoutY())
                > range;
    }

    public void setType(ProjectileType type) {
        this.type = type;
    }

    public void setPowerUp(PowerUp key, double value) {
        powerUp.put(key, value);
    }
    public void setRange(double range) {
        this.range = range;
        rangeEnable = true;
    }

    public void disableRange() {
        rangeEnable = false;
    }
}
