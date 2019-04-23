package model.projectiles;

import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.Pane;
import model.player.Player;
import view.GameViewManager;

import java.util.*;

import static view.ProjectileUi.setWeapon;


public class PlayerProjectileControl {

    private static final Pane gamePane = GameViewManager.gamePane;

    private long lastFireTime;

    private double angle;
    private ProjectileType type;
    private ArrayList<Projectile> projArr;
    private Player playerFiring;

    private boolean mousePressed;

    public enum buttons {
        PRIMARY(0), SECONDARY(1);
        int index;

        buttons(int i) {
            index = i;
        }

        public int getIndex() {
            return index;
        }
    }

    private final buttons projectileBtn;
    private buttons lastPressed;

    private HashMap<PowerUp, Double> powerUp;
    private LinkedHashMap<ProjectileType, HashMap<PowerUp, Double>> weaponSettings = new LinkedHashMap<>();
    private LinkedList<ProjectileType> weaponList = new LinkedList<>();
    //dictionary of weapons used with their respective powerUp dict

    private boolean rangeEnable;
    private double range = 2000; //bound akbar mn el shasha
    private double lastFireLocationX;
    private double lastFireLocationY;

    public PlayerProjectileControl(ProjectileType projectile, buttons projectileBtn, Player playerFiring) {

        //todo: class needs renaming
        this.type = projectile;
        this.projectileBtn = projectileBtn;
        this.playerFiring = playerFiring;

        projArr = new ArrayList<>();
        powerUp = new HashMap<>();

        rangeEnable = false;
        setWeapon(projectileBtn.getIndex(), projectile.URL);
        powerUp = initializePowerUp();
        weaponSettings.put(projectile, powerUp);
        weaponList.add(type);
    }

    //sets powerUp to zero
    private HashMap<PowerUp, Double> initializePowerUp() {
        HashMap<PowerUp, Double> power = new HashMap<>();
        for (PowerUp pup : PowerUp.values()) {
            power.put(pup, (double) 0);
        }
        power.put(PowerUp.MULT, (double) 1);

        return power;
    }

    public void fireProjectile() {
        if (mousePressed) {
            isProjectileBtnPressed();//todo: func name needs refactoring
        }
    }

    public void mouseEvents() {
        gamePane.addEventFilter(MouseEvent.ANY, this::detectBtnType);
        gamePane.addEventFilter(TouchEvent.ANY, e -> isProjectileBtnPressed());//law el shasha touch xD

        gamePane.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> mousePressed = true);
        gamePane.addEventFilter(MouseEvent.MOUSE_RELEASED, e -> mousePressed = false);

    }

    public void update(double angle) {
        this.angle = angle;
        mouseEvents();
        fireProjectile();
        moveProjectile();

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
                        type, angle + mult * type.MULTANGLE * Math.pow(-1, mult));//todo odd multiples look weird

                projectile.setScale(powerUp.get(PowerUp.SCALE));
                projectile.addSpeed(powerUp.get(PowerUp.SPEED));

                projArr.add(projectile);
                lastFireLocationX = playerFiring.getLayoutX();
                lastFireLocationY = playerFiring.getLayoutY();
                lastFireTime = System.currentTimeMillis();
                gamePane.getChildren().add(projectile);
                projectile.toBack();
            }
        }
    }

    public void moveProjectile() {

        if (projArr.size() > 0) {

            ArrayList<Projectile> projArrRemove = new ArrayList<>();

            for (Projectile p : projArr) {
                p.move();
                //if the object crossed the boundary adds it to the remove list
                if (p.getLayoutY() > GameViewManager.HEIGHT || p.getLayoutY() < 0) {
                    projArrRemove.add(p);

                } else if (p.getLayoutX() > GameViewManager.WIDTH || p.getLayoutX() < 0) {
                    projArrRemove.add(p);

                } else if (rangeEnable && rangeTooFar(p)) {
                    projArrRemove.add(p);
                }
            }

            gamePane.getChildren().removeAll(projArrRemove);
            projArr.removeAll(projArrRemove);

        }
    }

    private boolean rangeTooFar(Projectile p) {
        return Math.hypot(lastFireLocationX - p.getLayoutX(), lastFireLocationY - p.getLayoutY())
                > range;
    }

    public void addType(ProjectileType type, boolean special) {
        this.type = type;
        weaponSettings.putIfAbsent(type, initializePowerUp());
        this.powerUp = weaponSettings.get(type);

        int weaponSlot = special ? 1:0;
        setWeapon(weaponSlot,type.URL);
        if (weaponList.size() < weaponSettings.size()){ //w8 a sec omal eh faydet el 2 slots if i can switch ? -.-
            weaponList.add(type);
        }
    }

    public void setToNextType(boolean special) {
        weaponList.indexOf(type);
        ProjectileType nextType = weaponList.get((weaponList.indexOf(type) + 1) % weaponList.size());
        powerUp = weaponSettings.get(nextType);
        type = nextType;

        int weaponSlot = special ? 1:0;
        setWeapon(weaponSlot, type.URL); //todo ui slot kda msh dynamic but screw it i need my brain cells ughhhh nvm this needs to be done
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

    public ArrayList<Projectile> getProjArr() {
        return projArr;
    }
}
