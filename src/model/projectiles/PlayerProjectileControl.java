package model.projectiles;

import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.Pane;
import model.player.Player;
import view.GameViewManager;

import java.util.*;

import static view.GameViewManager.gamePane;
import static view.ProjectileUi.setWeapon;


public class PlayerProjectileControl extends ProjectileControl{

    private long lastFireTime;
    private ProjectileType type;

    private boolean mousePressed;
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

    public PlayerProjectileControl(ProjectileType projectile, buttons projectileBtn, Player playerFiring) {

        super(playerFiring);
        this.type = projectile;
        this.projectileBtn = projectileBtn;
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
        gamePane.addEventFilter(TouchEvent.ANY, e -> isProjectileBtnPressed());

        gamePane.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> mousePressed = true);
        gamePane.addEventFilter(MouseEvent.MOUSE_RELEASED, e -> mousePressed = false);

    }

    public void update(double angle) {
        super.update(angle);
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
            lastPressed = buttons.PRIMARY;
        } else if (e.isSecondaryButtonDown()) {
            lastPressed = buttons.SECONDARY;
        }
    }

    private void createProjectile() {

        if (System.currentTimeMillis() > (lastFireTime + 1000 / type.FIRERATE)) {
            for (int mult = 0; mult < powerUp.get(PowerUp.MULT); mult++) {

                Projectile projectile = new Projectile(player.getSpawner(),
                        type, angle + mult * type.MULTANGLE * Math.pow(-1, mult));//todo odd multiples look weird

                projectile.setScale(powerUp.get(PowerUp.SCALE));
                projectile.addSpeed(powerUp.get(PowerUp.SPEED));

                projArr.add(projectile);
                lastFireLocationX = player.getLayoutX();
                lastFireLocationY = player.getLayoutY();
                lastFireTime = System.currentTimeMillis();
                gamePane.getChildren().add(projectile);
                projectile.toBack();
            }
        }
    }
    public void removeProjectile() {
        super.removeProjectile();
        if (projArr.size() > 0) {
            ArrayList<Projectile> projArrRemove = new ArrayList<>();
            for (Projectile p : projArr) {
                if (rangeEnable && rangeTooFar(p)) {
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
        if (weaponList.size() < weaponSettings.size()){
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

}
