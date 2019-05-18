package model.enemies;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import model.player.Player;
import model.projectiles.EnemyProjectileControl;
import model.projectiles.ProjectileType;
import view.GameViewManager;
import view.LevelManager;
import view.game.stats.StatBar;
import javafx.scene.shape.Rectangle;

import static view.GameViewManager.*;

public class Boss extends Enemy {

    private final static int hitScore = 1;
    private EnemyProjectileControl laser;
    private EnemyProjectileControl knifeContinuousPulse;
    private EnemyProjectileControl randomSkulls;
    private EnemyProjectileControl shower;
    private EnemyProjectileControl knifeChargePulse;
    private StatBar HPRectangleBoss;
    private StackPane HPStack;
    private final static int CONTROL_INTERVAL = 40 * 1000;
    private long nextControl;
    private int control;
    private final static int CONTROLS_NUM = 3;

    public enum EnemyStageEnum {
        STAGE1(EnemyType.MAGE1, 90, 120, 550, 500, 4, 1, 350, 120, 25,4000),
        STAGE2(EnemyType.MAGE2, 60, 60, 400, 350, 5, 2, 250, 100, 20,3000),
        STAGE3(EnemyType.MAGE3, 45, 45, 330, 250, 6, 3, 150, 90, 17,2000);

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

        EnemyStageEnum(EnemyType enemyType, int pulseAngle, int knifeRate, int knifeAngle, int skullRate,
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

    public Boss(EnemyStageEnum stage, EnemyStageEnum currentStage) {
        super(currentStage.enemyType);
        this.stage = stage;
        this.currentStage = currentStage;
        this.boss = true;
        bossInit(currentStage);
    }

    public Boss(EnemyStageEnum stage) {
        super(EnemyType.MAGE1);
        this.stage = stage;
        this.boss = true;
        bossInit(currentStage);

    }

    private void bossInit(EnemyStageEnum currentStage) {
        projectileControlInit(currentStage);

        setLayoutY((HEIGHT >> 1) - (height >> 1));
        setLayoutX((WIDTH >> 1) - (width >> 1));

        createHPBar();

        HPStack.setLayoutY(900);
        HPStack.setPrefWidth(WIDTH);
        HPStack.setAlignment(Pos.CENTER);
        GameViewManager.getMainPane().addToUIPane(HPStack);
    }

    private void projectileControlInit(EnemyStageEnum stage) {
        laser = new EnemyProjectileControl(ProjectileType.REDLASER01);
        laser.addPulseBoss((long) getImageWidth(laser.getType().getURL()), stage.getPulseAngle());

        knifeContinuousPulse = new EnemyProjectileControl(ProjectileType.KNIFE);
        knifeContinuousPulse.addKnives(stage.knifeRate, stage.knifeAngle);

        randomSkulls = new EnemyProjectileControl(ProjectileType.SKULL);
        randomSkulls.addMissiles(stage.skullRate, stage.skullSpeed);

        shower = new EnemyProjectileControl(ProjectileType.SKULL);
        shower.addShower(200);

        knifeChargePulse = new EnemyProjectileControl(ProjectileType.KNIFE);
        knifeChargePulse.addRing1by1(100,20);

        knifeChargePulse = new EnemyProjectileControl(ProjectileType.KNIFE);
        knifeChargePulse.addPulse(stage.knifeChargeRate,5);



    }


    @Override
    public void takeDmg(double dmg) {
        super.takeDmg(dmg);
        Player.increaseCurrentScore(hitScore);
        HPRectangleBoss.decreaseCurrent(dmg);
        HPRectangleBoss.barScaleAnimator(MAX_HP);
        hp = HPRectangleBoss.getCurrentValue();
    }

    @Override
    protected void checkAlive() {
        if (hp <= 0 && currentStage.index < stage.index) {
            super.checkAlive();
            Boss b = new Boss(stage, EnemyStageEnum.getEnemyStage(currentStage.index + 1));
            LevelManager.getEnemyArrayList().add(b);
            GameViewManager.getMainPane().addToGamePane(b);
        } else if (hp <= 0 && currentStage.index >= stage.index) {
            System.out.println(currentStage.index + " " + stage.index);
            super.checkAlive();
        }
    }

    @Override
    public void update() {
        super.update();
        long timeNow = System.currentTimeMillis();
        if (nextControl < timeNow) {
            control = (control + 1) % CONTROLS_NUM;
            nextControl = timeNow + CONTROL_INTERVAL;
        }
        switch (control) {
            case 0:
                knifeContinuousPulse.update(angle, getSpawner());
                randomSkulls.update(angle, getSpawner());
                knifeChargePulse.update(angle, getSpawner());

                break;
            case 1:
                laser.update(angle, getSpawner());
//                shower.update(angle, getSpawner());
                knifeChargePulse.update(angle, getSpawner());

                break;
            case 2:
//                shower.update(angle, getSpawner());
                knifeChargePulse.update(angle, getSpawner());
                break;
        }

    }

    private void createHPBar() {
        HPStack = new StackPane();
        HPRectangleBoss = new StatBar(20, 9 * WIDTH / 10, Color.PURPLE, false, MAX_HP);
        Rectangle limitRec = new Rectangle(9 * WIDTH / 10, 20, Color.TRANSPARENT);
        limitRec.setStrokeWidth(2);
        limitRec.setStroke(Color.PURPLE);

        HPStack.getChildren().addAll(limitRec, HPRectangleBoss);
    }
}
