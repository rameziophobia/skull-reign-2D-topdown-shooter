package model.enemies;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
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
    private EnemyProjectileControl bossProjectileControl1;
    private EnemyProjectileControl bossProjectileControl2;
    private EnemyProjectileControl bossProjectileControl3;
    private StatBar HPRectangleBoss;
    private StackPane HPStack;
    private EnemyProjectileControl bossProjectileControl4;
    private final static int CONTROL_INTERVAL = 40 * 1000;
    private long nextControl;
    private int control;
    private final static int CONTROLS_NUM = 2;

    public enum EnemyStageEnum {
        STAGE1(EnemyType.MAGE1, 90, 120, 550, 500, 4, 1),
        STAGE2(EnemyType.MAGE2, 60, 60, 400, 350, 5, 2),
        STAGE3(EnemyType.MAGE3, 45, 45, 330, 250, 6, 3);

        private EnemyType enemyType;
        private int ringAngle;
        private int knifeRate;
        private int knifeAngle;
        private int skullRate;
        private int skullSpeed;
        private int index;

        EnemyStageEnum(EnemyType enemyType, int ringAngle, int knifeAngle, int knifeRate, int skullRate, int skullSpeed, int index) {
            this.enemyType = enemyType;
            this.ringAngle = ringAngle;
            this.knifeAngle = knifeAngle;
            this.knifeRate = knifeRate;
            this.skullRate = skullRate;
            this.skullSpeed = skullSpeed;
            this.index = index;
        }

        public int getRingAngle() {
            return ringAngle;
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
        projectileControlInit(currentStage);
        setLayoutY((HEIGHT >> 1) - (height >> 1));
        setLayoutX((WIDTH >> 1) - (width >> 1));
        createHPBar();

        HPStack.setLayoutY(900);
        HPStack.setPrefWidth(WIDTH);
        HPStack.setAlignment(Pos.CENTER);
        GameViewManager.getMainPane().addToUIPane(HPStack);
    }

    public Boss(EnemyStageEnum stage) {
        super(EnemyType.MAGE1);
        this.stage = stage;
        this.boss = true;
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
        bossProjectileControl1 = new EnemyProjectileControl(ProjectileType.REDLASER01);
        bossProjectileControl1.addSpawnRingBoss((long) getImageWidth(bossProjectileControl1.getType().getURL()), stage.getRingAngle());

        bossProjectileControl2 = new EnemyProjectileControl(ProjectileType.KNIFE);
        bossProjectileControl2.addSpawnRingBoss(stage.knifeRate, stage.knifeAngle);

        bossProjectileControl3 = new EnemyProjectileControl(ProjectileType.SKULL);
        bossProjectileControl3.addMissiles(stage.skullRate, stage.skullSpeed);

        bossProjectileControl4 = new EnemyProjectileControl(ProjectileType.SKULL);
        bossProjectileControl4.addShowerVertical(100);
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
//        System.out.println(control);
        switch (control) {
            case 0:
                bossProjectileControl2.update(angle, getSpawner());
                bossProjectileControl3.update(angle, getSpawner());
//                bossProjectileControl4.update(angle, getSpawner());
                break;
            case 1:
                bossProjectileControl1.update(angle, getSpawner());

//                bossProjectileControl4.update(angle, getSpawner());
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
