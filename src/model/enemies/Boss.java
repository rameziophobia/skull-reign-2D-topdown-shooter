package model.enemies;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import model.player.Player;
import model.projectiles.EnemyProjectileControl;
import model.projectiles.ProjectileType;
import org.omg.PortableServer.POA;
import view.GameViewManager;
import view.game.stats.HealthBars;
import model.player.Player;
import model.projectiles.EnemyProjectileControl;
import model.projectiles.ProjectileType;
import org.omg.CORBA.INTERNAL;
import view.game.stats.StatBar;
import javafx.scene.shape.Rectangle;

import static view.GameViewManager.*;

public class Boss extends Enemy {

    private final stageEnum stage;
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

    public enum stageEnum {
        STAGE1(90, 120, 550,500, 4),
        STAGE2(60, 60, 400,350, 5),
        STAGE3(30, 45, 330, 250, 6);

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

        createHPBar();

        HPStack.setLayoutY(900);
        HPStack.setPrefWidth(WIDTH);
        HPStack.setAlignment(Pos.CENTER);
        GameViewManager.getMainPane().addToUIPane(HPStack);

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

   private void createHPBar(){
        HPStack = new StackPane();
        HPRectangleBoss = new StatBar(20, 9*WIDTH/10, Color.PURPLE,false,MAX_HP );
        Rectangle limitRec = new Rectangle(9*WIDTH/10,20,Color.TRANSPARENT);
        limitRec.setStrokeWidth(2);
        limitRec.setStroke(Color.PURPLE);

        HPStack.getChildren().addAll(limitRec,HPRectangleBoss);
   }
}
