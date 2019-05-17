package model.enemies;

import model.player.Player;
import model.projectiles.EnemyProjectileControl;
import model.projectiles.ProjectileType;
import org.omg.CORBA.INTERNAL;
import view.game.stats.StatBar;

import static view.GameViewManager.*;

public class Boss extends Enemy {

    private final stageEnum stage;
    //    private final StackPane HPStack;
    private final static int hitScore = 1;
    private EnemyProjectileControl bossProjectileControl1;
    private EnemyProjectileControl bossProjectileControl2;
    private EnemyProjectileControl bossProjectileControl3;
    private EnemyProjectileControl bossProjectileControl4;
    private StatBar HPRectangle;
    private final static int CONTROL_INTERVAL = 40 * 1000;
    private long nextControl;
    private int control;
    private final static int CONTROLS_NUM = 2;

    public enum stageEnum {
        STAGE1(90, 120, 400,500, 4),
        STAGE2(60, 60, 275,350, 5),
        STAGE3(30, 45, 225, 250, 6);

        private int ringAngle;
        private int knifeRate;
        private int knifeAngle;
        private int skullRate;
        private int skullSpeed;

        stageEnum(int ringAngle, int knifeAngle, int knifeRate, int skullRate, int skullSpeed) {
            this.ringAngle = ringAngle;
            this.knifeAngle = knifeAngle;
            this.knifeRate = knifeRate;
            this.skullRate = skullRate;
            this.skullSpeed = skullSpeed;
        }

        public int getRingAngle() {
            return ringAngle;
        }
    }

    public Boss(EnemyType enemyType, stageEnum stage) {
        super(enemyType);
        this.stage = stage;
        bossProjectileControl1 = new EnemyProjectileControl(ProjectileType.REDLASER01);
        bossProjectileControl1.addSpawnRingBoss((long) getImageWidth(bossProjectileControl1.getType().getURL()), stage.getRingAngle());

        bossProjectileControl2 = new EnemyProjectileControl(ProjectileType.KNIFE);
        bossProjectileControl2.addSpawnRingBoss(stage.knifeRate, stage.knifeAngle);

        bossProjectileControl3 = new EnemyProjectileControl(ProjectileType.SKULL);
        bossProjectileControl3.addMissiles(stage.skullRate, stage.skullSpeed);

        bossProjectileControl4 = new EnemyProjectileControl(ProjectileType.SKULL);
        bossProjectileControl4.addShowerVertical(100);

        setLayoutY((HEIGHT >> 1) - (height >> 1));
        setLayoutX((WIDTH >> 1) - (width >> 1));

//        HPStack = new HealthBars().getStackPane(1600,20,Color.VIOLET,false,MAX_HP);
////        HPRectangle =  GVUI.getPlayerHealthBars().getRectangle(HealthBars.Bars.BOSS);
//        GameViewManager.addTOScene(HPStack);
//        HPStack.setLayoutX(150);
//        HPStack.setLayoutY(1000);
    }


    @Override
    public void takeDmg(double dmg) {
        super.takeDmg(dmg);
        Player.increaseCurrentScore(hitScore);
        System.out.println(hp);

        //        HPRectangle.decreaseCurrent(dmg);
//        barScaleAnimator(HPRectangle, MAX_HP);
//        hp = HPRectangle.getCurrentValue();
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
                bossProjectileControl1.update(angle, getSpawner());
//                bossProjectileControl4.update(angle, getSpawner());
                break;
            case 1:
                bossProjectileControl2.update(angle, getSpawner());
                bossProjectileControl3.update(angle, getSpawner());
//                bossProjectileControl4.update(angle, getSpawner());
                break;
        }

    }
}
