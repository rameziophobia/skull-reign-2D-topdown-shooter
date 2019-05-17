package model.projectiles;

import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import view.GameViewManager;

import java.util.HashMap;
import java.util.LinkedList;

import static view.GameViewManager.getPlayer;
import static view.game.ProjectileUI.setWeapon;


public class PlayerProjectileControl {

    private long lastFireTime;
    private ProjectileType type;

    private boolean mousePressed;
    private final buttons projectileBtn;
    private buttons lastPressed;

    private HashMap<PowerUpType, Float> powerUp;
    private HashMap<ProjectileType, HashMap<PowerUpType, Float>> weaponSettings = new HashMap<>();
    private LinkedList<ProjectileType> weaponList = new LinkedList<>();
    //dictionary of weapons used with their respective powerUp dict

    private final static int MAX_MULT = 6;
    private final static int MAX_SCALE = 50;
    private final int MAX_SPEED;


    private boolean rangeEnable;
    private double range = 2000; //bound akbar mn el shasha
    private double lastFireLocationX;
    private double lastFireLocationY;
    private double angle;

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

    public PlayerProjectileControl(ProjectileType projectile, buttons projectileBtn) {

        this.type = projectile;
        this.projectileBtn = projectileBtn;
        MAX_SPEED = (int) (this.type.getSPEED() * 1.5);
        powerUp = new HashMap<>();

        rangeEnable = false;
        setWeapon(projectile);
        powerUp = initializePowerUp();
        weaponSettings.put(projectile, powerUp);
        weaponList.add(type);
    }

    //sets powerUp to zero
    private HashMap<PowerUpType, Float> initializePowerUp() {
        HashMap<PowerUpType, Float> power = new HashMap<>();
        for (PowerUpType powerUpTypes : PowerUpType.values()) {
            power.put(powerUpTypes, 0f);
        }
        power.put(PowerUpType.MULT, 1f);

        return power;
    }

    public void fireProjectile() {
        if (mousePressed && lastPressed.equals(projectileBtn)) {
            createProjectile();
        }
    }

    public void mouseEvents() {
        GameViewManager.getGamePane().addEventFilter(MouseEvent.ANY, this::detectBtnType);
        GameViewManager.getGamePane().addEventFilter(TouchEvent.ANY, e -> fireProjectile());

        GameViewManager.getGamePane().addEventFilter(MouseEvent.MOUSE_PRESSED, e -> mousePressed = true);
        GameViewManager.getGamePane().addEventFilter(MouseEvent.MOUSE_RELEASED, e -> mousePressed = false);

    }

    public void update(double angle) {
        this.angle = angle;
        mouseEvents();
        fireProjectile();
    }

    protected HashMap getWeaponSettings() {
        return weaponSettings;
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
            for (int mult = 0; mult < powerUp.get(PowerUpType.MULT); mult++) {

                Projectile projectile = new Projectile(getPlayer().getSpawner(),
                        type,
                        angle + mult * type.getMULTANGLE() * Math.pow(-1, mult),
                        false);//todo odd multiples look weird

                projectile.setDmgScale(powerUp.get(PowerUpType.SCALE));
                projectile.addSpeed(powerUp.get(PowerUpType.SPEEDPROJECTILE));

                lastFireLocationX = getPlayer().getLayoutX();
                lastFireLocationY = getPlayer().getLayoutY();
                lastFireTime = System.currentTimeMillis();
                GameViewManager.addGameObjectTOScene(projectile);
                projectile.toBack();
            }
        }
    }

    private boolean rangeTooFar(Projectile p) {
        return Math.hypot(lastFireLocationX - p.getLayoutX(), lastFireLocationY - p.getLayoutY())
                > range;
    }

    public void addType(ProjectileType type) {
        System.out.println(this.type + " alas " + type);
        if (this.type != type) {
            this.type = type;
            weaponSettings.putIfAbsent(type, initializePowerUp());
            this.powerUp = weaponSettings.get(type);

            setWeapon(type);
            if (weaponList.size() < weaponSettings.size()) {
                weaponList.add(type);
            }
        }

    }

    public void setToNextType() {
        if (weaponList.size() > 1) {
            weaponList.indexOf(type);
            ProjectileType nextType = weaponList.get((weaponList.indexOf(type) + 1) % weaponList.size());
            powerUp = weaponSettings.get(nextType);
            type = nextType;

            setWeapon(type);
        }
    }
    public void setPowerUp(PowerUpType key, Float value) {
        if (value == 0) {
            powerUp.put(key, 1f);
        } else if (key == PowerUpType.MULT && type.getCurrentMult() < MAX_MULT) {
            type.incCurrentMult(value);
            powerUp.put(key, type.getCurrentMult());
        } else if (key == PowerUpType.SCALE && type.getCurrentScale() <= MAX_SCALE) {
            type.incCurrentScale(value);
            powerUp.put(key, type.getCurrentScale());
        } else if (key == PowerUpType.SPEEDPROJECTILE && type.getSPEED() <= MAX_SPEED) {
            type.incCurrentSpeed(value);
            powerUp.put(key, (float) type.getSPEED());
        }

    }

    public void setRange(double range) {
        this.range = range;
        rangeEnable = true;
    }

    public void disableRange() {
        rangeEnable = false;
    }

}
