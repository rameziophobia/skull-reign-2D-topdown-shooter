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
    private final int MAX_SPEED;

    private float currentMult = 1;
    private float currentScale = 1;
    private float currentProjSpeed;

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
        currentProjSpeed = this.type.getSPEED();
        MAX_SPEED = (int)(currentProjSpeed *1.5);
        powerUp = new HashMap<>();

        rangeEnable = false;
        setWeapon(projectileBtn.getIndex(), projectile.getURL());
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
            createProjectile();//todo: functions name needs refactoring
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

    public void setPowerUp(PowerUpType key, Float value) {
        if(key==PowerUpType.MULT && currentMult < MAX_MULT){
            currentMult += value;
            powerUp.put(key, currentMult);
        }
        else if(key == PowerUpType.SCALE && currentScale <= 40){
            currentScale += value;
            powerUp.put(key, currentScale);
        }
        else if(key == PowerUpType.SPEEDPROJECTILE && currentProjSpeed <= MAX_SPEED){
            currentProjSpeed += value;
            powerUp.put(key, currentProjSpeed);
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
