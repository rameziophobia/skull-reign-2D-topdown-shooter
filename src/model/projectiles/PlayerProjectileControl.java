package model.projectiles;

import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import model.player.Player;
import view.GameViewManager;

import java.util.*;

import static view.game.ProjectileUI.setWeapon;


public class PlayerProjectileControl {

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

    private HashMap<PowerUpTypes, Float> powerUp;
    private LinkedHashMap<ProjectileType, HashMap<PowerUpTypes, Float>> weaponSettings = new LinkedHashMap<>();
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
        setWeapon(projectileBtn.getIndex(), projectile.getURL());
        powerUp = initializePowerUp();
        weaponSettings.put(projectile, powerUp);
        weaponList.add(type);
    }

    //sets powerUp to zero
    private HashMap<PowerUpTypes, Float> initializePowerUp() {
        HashMap<PowerUpTypes, Float> power = new HashMap<>();
        for (PowerUpTypes powerUpTypes : PowerUpTypes.values()) {
            power.put(powerUpTypes, 0f);
        }
        power.put(PowerUpTypes.MULT, 1f);

        return power;
    }

    public void fireProjectile() {
        if (mousePressed) {
            isProjectileBtnPressed();//todo: func name needs refactoring
        }
    }

    public void mouseEvents() {
        GameViewManager.getGamePane().addEventFilter(MouseEvent.ANY, this::detectBtnType);
        GameViewManager.getGamePane().addEventFilter(TouchEvent.ANY, e -> isProjectileBtnPressed());

        GameViewManager.getGamePane().addEventFilter(MouseEvent.MOUSE_PRESSED, e -> mousePressed = true);
        GameViewManager.getGamePane().addEventFilter(MouseEvent.MOUSE_RELEASED, e -> mousePressed = false);

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
            lastPressed = buttons.PRIMARY;
        } else if (e.isSecondaryButtonDown()) {
            lastPressed = buttons.SECONDARY;
        }
    }

    private void createProjectile() {

        if (System.currentTimeMillis() > (lastFireTime + 1000 / type.getFIRERATE())) {
            for (int mult = 0; mult < powerUp.get(PowerUpTypes.MULT); mult++) {

                Projectile projectile = new Projectile(playerFiring.getSpawner(),
                        type, angle + mult * type.getMULTANGLE() * Math.pow(-1, mult));//todo odd multiples look weird

                projectile.setScale(powerUp.get(PowerUpTypes.SCALE));
                projectile.addSpeed(powerUp.get(PowerUpTypes.SPEED));

                projArr.add(projectile);
                lastFireLocationX = playerFiring.getLayoutX();
                lastFireLocationY = playerFiring.getLayoutY();
                lastFireTime = System.currentTimeMillis();
                GameViewManager.addGameObjectTOScene(projectile);
                projectile.toBack();
            }
        }
    }

    public void moveProjectile() {
//        if (rangeEnable && rangeTooFar(p)) {
//            projArrRemove.add(p);
//        }
    }

    private boolean rangeTooFar(Projectile p) {
        return Math.hypot(lastFireLocationX - p.getLayoutX(), lastFireLocationY - p.getLayoutY())
                > range;
    }

    public void addType(ProjectileType type, boolean special) {
        this.type = type;
        weaponSettings.putIfAbsent(type, initializePowerUp());
        this.powerUp = weaponSettings.get(type);

        int weaponSlot = special ? 1 : 0;
        setWeapon(weaponSlot, type.getURL());
        if (weaponList.size() < weaponSettings.size()) {
            weaponList.add(type);
        }
    }

    public void setToNextType(boolean special) {
        weaponList.indexOf(type);
        ProjectileType nextType = weaponList.get((weaponList.indexOf(type) + 1) % weaponList.size());
        powerUp = weaponSettings.get(nextType);
        type = nextType;

        int weaponSlot = special ? 1 : 0;
        setWeapon(weaponSlot, type.getURL()); //todo ui slot kda msh dynamic but screw it i need my brain cells ughhhh nvm this needs to be done
    }

    public void setPowerUp(PowerUpTypes key, Float value) {
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
