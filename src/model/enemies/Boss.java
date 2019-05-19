package model.enemies;

import controller.map.Map;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.player.Player;
import model.projectiles.EnemyProjectileControl;
import model.projectiles.ProjectileType;
import view.GameViewManager;
import view.game.stats.StatBar;

import static view.GameViewManager.WIDTH;

public class Boss extends Enemy {

    private final static int hitScore = 1;
    private EnemyProjectileControl laser;
    private EnemyProjectileControl knifeContinuousPulse;
    private EnemyProjectileControl randomSkulls;
    private EnemyProjectileControl shower;
    private EnemyProjectileControl knifeChargePulseSlow;
    private EnemyProjectileControl knifeChargePulseFast;
    private EnemyProjectileControl knife1by1;
    private StatBar HPRectangleBoss;
    private StackPane HPStack;
    private double HP_INTERVAL;
    private double hp_interval;
    private int control = -1;
    private final static int CONTROLS_NUM = 4;
    private Node[] nodes;
    private double spawnX;
    private double spawnY;

    public enum EnemyStageEnum {
        STAGE1(EnemyType.MAGE1, 90, 120, 650, 500, 4, 1, 350, 220, 20, 5000),
        STAGE2(EnemyType.MAGE2, 60, 60, 550, 350, 5, 2, 250, 150, 15, 4000),
        STAGE3(EnemyType.MAGE3, 45, 45, 450, 250, 6, 3, 150, 120, 12, 3000);

        private EnemyType enemyType;
        private int pulseAngle;
        private int knifeRate;
        private int knifeAngle;
        private int skullRate;
        private int skullSpeed;
        private int index;
        private int showerRate;
        private int ringRate;
        private int ringAngle;
        private int knifeChargeRate;

        EnemyStageEnum(EnemyType enemyType, int pulseAngle, int knifeAngle, int knifeRate, int skullRate,
                       int skullSpeed, int index, int showerRate, int ringRate, int ringAngle, int knifeChargeRate) {
            this.enemyType = enemyType;
            this.pulseAngle = pulseAngle;
            this.knifeRate = knifeRate;
            this.knifeAngle = knifeAngle;
            this.skullRate = skullRate;
            this.skullSpeed = skullSpeed;
            this.index = index;
            this.showerRate = showerRate;
            this.ringRate = ringRate;
            this.ringAngle = ringAngle;
            this.knifeChargeRate = knifeChargeRate;
        }

        public int getPulseAngle() {
            return pulseAngle;
        }

        private static EnemyStageEnum getEnemyStage(int index) {
            switch (index) {
                case 1:
                    return STAGE1;
                case 2:
                    return STAGE2;
                case 3:
                    return STAGE3;
            }
            return null;
        }

    }

    private final EnemyStageEnum stage;
    private EnemyStageEnum currentStage = EnemyStageEnum.STAGE1;

    private Boss(EnemyStageEnum stage, EnemyStageEnum currentStage, double x, double y) {
        super(currentStage.enemyType);
        this.stage = stage;
        this.currentStage = currentStage;
        this.boss = true;
        bossInit(currentStage);
        setLayoutX(Map.STARTING_X + (Map.MAP_BLOCKS_WIDTH >> 1) * Map.BLOCK_SIZE - (width >> 2));
        setLayoutY(Map.STARTING_Y + (Map.MAP_BLOCKS_HEIGHT >> 1) * Map.BLOCK_SIZE - (height >> 2));
    }

    public Boss(EnemyStageEnum stage) {
        super(EnemyType.MAGE1);
        this.stage = stage;
        this.boss = true;
        bossInit(currentStage);
    }

    private void bossInit(EnemyStageEnum currentStage) {
        projectileControlInit(currentStage);
        createHPBar();

        HPStack.setLayoutY(900);
        HPStack.setPrefWidth(WIDTH);
        HPStack.setAlignment(Pos.CENTER);
        GameViewManager.getMainPane().addToUIPane(HPStack);
        HP_INTERVAL = MAX_HP / CONTROLS_NUM;
    }

    private void projectileControlInit(EnemyStageEnum stage) {
        laser = new EnemyProjectileControl(ProjectileType.REDLASER01);
        laser.addPulseBoss((long) 9, stage.getPulseAngle());

        knifeContinuousPulse = new EnemyProjectileControl(ProjectileType.KNIFE);
        knifeContinuousPulse.addKnives(stage.knifeRate, stage.knifeAngle);

        randomSkulls = new EnemyProjectileControl(ProjectileType.SKULL);
        randomSkulls.addMissiles(stage.skullRate, stage.skullSpeed);

        shower = new EnemyProjectileControl(ProjectileType.SKULL);
        shower.addShower(stage.showerRate);

        knife1by1 = new EnemyProjectileControl(ProjectileType.KNIFE);
        knife1by1.addRing1by1(stage.ringRate, stage.ringAngle);

        knifeChargePulseSlow = new EnemyProjectileControl(ProjectileType.KNIFE);
        knifeChargePulseSlow.addPulse(stage.knifeChargeRate + 2000, 5);

        knifeChargePulseFast = new EnemyProjectileControl(ProjectileType.KNIFE);
        knifeChargePulseFast.addPulse(stage.knifeChargeRate, 5);
    }

    @Override
    public void takeDmg(double dmg) {
        super.takeDmg(dmg);
        Player.increaseCurrentScore(hitScore);
        HPRectangleBoss.decreaseCurrent(dmg);
        HPRectangleBoss.barScaleAnimator(MAX_HP);
        hp = HPRectangleBoss.getCurrentValue();
        hp_interval -= dmg;
    }

    @Override
    protected void checkAlive() {
        if (hp <= 0 && currentStage.index < stage.index) {
            super.checkAlive();
            Boss b = new Boss(stage,
                    EnemyStageEnum.getEnemyStage(currentStage.index + 1), spawnX, spawnY);

            GameViewManager.getInstance().getEnemyArrayList().add(b);
            GameViewManager.getMainPane().addToGamePane(b);
        } else if (hp <= 0 && currentStage.index >= stage.index) {
            System.out.println(currentStage.index + " " + stage.index);
            super.checkAlive();
        }
    }

    @Override
    public void update() {
        super.update();
        if (hp_interval <= 0) {
            control++;
            System.out.println(control);
            hp_interval = HP_INTERVAL;
        }
        switch (control) {
            case 0:
                knifeContinuousPulse.update(angle, getSpawner());
                randomSkulls.update(angle, getSpawner());
                break;
            case 1:
                laser.update(angle, getSpawner());
                break;
            case 2:
                shower.update(angle, getSpawner());
                knifeChargePulseFast.update(angle, getSpawner());
                break;
            case 3:
                randomSkulls.update(angle, getSpawner());
                knife1by1.update(angle, getSpawner());
                knifeChargePulseSlow.update(angle, getSpawner());
                break;
        }

    }

    private void createHPBar() {
        HPStack = new StackPane();
        HPRectangleBoss = new StatBar(20, 9 * WIDTH / 10, Color.PURPLE, false, MAX_HP);
        Rectangle limitRec = new Rectangle(9 * WIDTH / 10, 20, Color.TRANSPARENT);
        limitRec.setStrokeWidth(2);
        limitRec.setStroke(Color.PURPLE);

        nodes = new Node[]{limitRec, HPRectangleBoss};
    }

    @Override
    public Node[] getChildren() {
        return nodes;
    }
}

