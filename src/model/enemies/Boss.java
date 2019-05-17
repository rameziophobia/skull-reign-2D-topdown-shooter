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

        createHPBar();

        HPStack.setLayoutY(900);
        HPStack.setPrefWidth(WIDTH);
        HPStack.setAlignment(Pos.CENTER);
        GameViewManager.addTOScene(HPStack);

    }


    @Override
    public void takeDmg(double dmg) {
        Player.increaseCurrentScore(hitScore);
        HPRectangleBoss.decreaseCurrent(dmg);
        HPRectangleBoss.barScaleAnimator(MAX_HP);
        hp = HPRectangleBoss.getCurrentValue();
    }

    @Override
    public void update() {
        super.update();
        bossProjectileControl2.update(angle, getSpawner());
        bossProjectileControl3.update(angle, getSpawner());
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
