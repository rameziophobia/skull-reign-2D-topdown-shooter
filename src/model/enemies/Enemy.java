package model.enemies;

import controller.animation.AnimationClip;
import controller.animation.SpriteSheet;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import model.Entity;
import model.player.Player;
import model.projectiles.EnemyProjectileControl;
import model.projectiles.ProjectileType;
import view.GameViewManager;
import view.LevelManager;

import java.util.Random;

public class Enemy extends Entity {
    private static final Duration FLOATING_SCORE_TRANSLATE_DURATION = Duration.millis(1500);
    private static final Duration FLOATING_SCORE_FADE_DURATION = Duration.millis(1250);
    private static final Font FLOATING_SCORE_FONT = Font.font("Arial", FontWeight.BOLD, 35);
    private static final double FLOATING_SCORE_TRANSLATE_Y_BY = -65.0;
    protected final double MAX_HP;
    private final Text damageTxt = new Text("");
    private final Label lbl_floatingScore;
    private final TranslateTransition lblmover;
    private final ParallelTransition floatingScoreTransition;
    private AnimationClip animationClip;

    private EnemyType enemyType;
    double angle;
    private double randomAngle = Math.random() * 360;

    private double minDistance;
    private long moveInterval;
    private boolean intervalExists = false;
    private MoveMode mode;

    protected double hp;

    private EnemyProjectileControl enemyProjectileControl;
    private long nextMove;
    private boolean farFromPlayer;
    private short randomSign;

    public enum MoveMode {stationary, followPlayer, random, circular}

    public Enemy(EnemyType enemyType, ProjectileType projectileType, MoveMode mode, double minDistance, long move_interval_ms) {
        this(enemyType, projectileType, mode, minDistance);
        this.moveInterval = move_interval_ms;
        this.intervalExists = true;
    }

    public Enemy(EnemyType enemyType, ProjectileType projectileType, MoveMode mode, double minDistance) {
        this(enemyType, projectileType, mode);
        this.minDistance = minDistance;
    }

    public Enemy(EnemyType enemyType, ProjectileType projectileType, MoveMode mode) {
        this(enemyType);
        this.mode = mode;
        enemyProjectileControl = new EnemyProjectileControl(projectileType);
    }

    public Enemy(EnemyType enemyType) {
        super(enemyType.getURL(), enemyType.getSPEED());
        this.enemyType = enemyType;
        this.MAX_HP = enemyType.getHP();
        Random r = new Random();
        this.randomSign = (short) (r.nextBoolean() ? -1 : 1);
        hp = MAX_HP;

        Random rand = new Random();
        setLayoutY(height + rand.nextInt(GameViewManager.HEIGHT - 2 * height - 10));
        setLayoutX(width + rand.nextInt(GameViewManager.WIDTH - 2 * width - 10));

        lbl_floatingScore = new Label("");
        lbl_floatingScore.setFont(FLOATING_SCORE_FONT);
        lbl_floatingScore.setTextFill(Color.GHOSTWHITE);

        final FadeTransition lblfader = new FadeTransition(FLOATING_SCORE_FADE_DURATION);
        lblfader.setToValue(0);
        lblfader.setFromValue(1);

        lblmover = new TranslateTransition(FLOATING_SCORE_TRANSLATE_DURATION);
        lblmover.setByY(FLOATING_SCORE_TRANSLATE_Y_BY);

        floatingScoreTransition = new ParallelTransition(lbl_floatingScore, lblfader, lblmover);
        floatingScoreTransition.setOnFinished(e -> GameViewManager.getMainPane().removeFromUIPane(lbl_floatingScore));

        if (enemyType.isANIMATED()) {
            this.animated = true;
            SpriteSheet spriteSheet = new SpriteSheet(enemyType.getURL(), 0);
            animationClip = new AnimationClip(spriteSheet,
                    spriteSheet.getFrameCount() * 1.2f,
                    false,
                    AnimationClip.INF_REPEATS,
                    this);
            animationClip.animate();
        }
    }

    public EnemyProjectileControl getEnemyProjectileControl() {
        return enemyProjectileControl;
    }

    @Override
    public void takeDmg(double dmg) {

        this.hp -= dmg;
    }

    @Override
    public void heal(float amount) {
        hp = Math.min(amount + hp, MAX_HP);
    }

    private void calculateDistance() {
        farFromPlayer = Math.hypot(
                getLayoutX() - GameViewManager.getPlayer().getLayoutX(),
                getLayoutY() - GameViewManager.getPlayer().getLayoutY())
                > minDistance;
    }
    private void updateAngle() {
        angle = Math.toDegrees(Math.atan2(GameViewManager.getPlayer().getLayoutY() - getLayoutY(),
                GameViewManager.getPlayer().getLayoutX() - getLayoutX()));
    }

    public void setMoveMode(MoveMode mode) {
        this.mode = mode;
    }

    protected void move() {

        if ((!intervalExists || System.currentTimeMillis() % (moveInterval * 2) > moveInterval) && farFromPlayer) {
            switch (mode) {
                case followPlayer: {
                    moveImageView(angle);
                    break;
                }
                case circular: {
                    moveImageView(angle + 90 * randomSign);
                    break;
                }
                //todo remove it if it looks stupid with the new assets
                case random: {

                    if (nextMove < System.currentTimeMillis()) {
                        randomAngle = -30 + Math.random() * 30;
                        nextMove = System.currentTimeMillis() + moveInterval;
                    }
                    moveImageView(randomAngle + angle);
                    break;
                }
                default:
                case stationary: {
                    break;
                }
            }
        }

    }

    private void moveImageView(double angle) {
        double nextX = getLayoutX() + Math.cos(Math.toRadians(angle)) * enemyType.getSPEED();
        double nextY = getLayoutY() + Math.sin(Math.toRadians(angle)) * enemyType.getSPEED();

        if (nextX + 15 + width < GameViewManager.WIDTH && nextX > 15) {
            setLayoutX(nextX);
        } else {
            randomSign *= -1;
        }
        if (nextY + 15 + height < GameViewManager.HEIGHT && nextY > 15) {
            setLayoutY(nextY);
        } else {
            randomSign *= -1;
        }

    }

    private void checkAlive() {
        if (hp <= 0 || !LevelManager.isSpawnable()) {
            if (hp <= 0) {
                Player.increaseCurrentScore(this.getScoreValue());
                lbl_floatingScore.setText("+" + this.getScoreValue());
                lbl_floatingScore.setLayoutX(this.getLayoutX());
                lblmover.setFromY(this.getLayoutY());
                GameViewManager.getMainPane().addToUIPane(lbl_floatingScore);
                floatingScoreTransition.play();
            }
            GameViewManager.getMainPane().removeFromGamePane(this);
            LevelManager.removeEnemy(this);
        }
    }

    @Override
    public void update() {
        updateAngle();
        calculateDistance();
//        setRotate(angle);
//        move();

        if (enemyProjectileControl != null)
            enemyProjectileControl.update(angle, getSpawner());

        checkAlive();
        if (animated) {
            animationClip.animate();
        }
    }

    public int getScoreValue() {
        return enemyType.getScore();
    }
}
