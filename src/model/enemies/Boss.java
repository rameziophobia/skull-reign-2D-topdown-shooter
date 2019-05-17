package model.enemies;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import model.player.Player;
import model.projectiles.EnemyProjectileControl;
import model.projectiles.ProjectileType;
import view.GameViewManager;
import view.game.stats.HealthBars;
import view.game.stats.StatBar;

import static view.GameViewManager.*;

public class Boss extends Enemy {

    private final stageEnum stage;
    //    private final StackPane HPStack;
    private final static int hitScore = 1;
    private EnemyProjectileControl bossProjectileControl1;
    private EnemyProjectileControl bossProjectileControl2;
    private EnemyProjectileControl bossProjectileControl3;
    private StatBar HPRectangle;

    public enum stageEnum {
        STAGE1(90),
        STAGE2(60),
        STAGE3(30);

        private int ringAngle;

        stageEnum(int ringAngle) {
            this.ringAngle = ringAngle;
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
        bossProjectileControl2.addSpawnRingBoss(500, 60);

        bossProjectileControl3 = new EnemyProjectileControl(ProjectileType.SKULL);
        bossProjectileControl3.addMissiles(300, 5);

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
        Player.increaseCurrentScore(hitScore);
        //        HPRectangle.decreaseCurrent(dmg);
//        barScaleAnimator(HPRectangle, MAX_HP);
//        hp = HPRectangle.getCurrentValue();
    }

    @Override
    public void update() {
        super.update();
        bossProjectileControl2.update(angle, getSpawner());
        bossProjectileControl3.update(angle, getSpawner());
    }
}
