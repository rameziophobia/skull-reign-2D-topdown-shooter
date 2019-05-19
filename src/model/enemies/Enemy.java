package model.enemies;

import controller.animation.AnimationClip;
import controller.animation.SpriteSheet;
import controller.audiomanager.AudioFile;
import controller.audiomanager.AudioManager;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import model.Entity;
import model.player.Player;
import model.projectiles.EnemyProjectileControl;
import model.projectiles.ProjectileType;
import model.wall.Wall;
import view.GameViewManager;

import java.util.Random;

import static view.GameViewManager.HEIGHT;
import static view.GameViewManager.WIDTH;

public class Enemy extends Entity {
    private static final Duration FLOATING_SCORE_TRANSLATE_DURATION = Duration.millis(1500);
    private static final Duration FLOATING_SCORE_FADE_DURATION = Duration.millis(1250);
    private static final Font FLOATING_SCORE_FONT = Font.font("Arial", FontWeight.BOLD, 35);
    private static final double FLOATING_SCORE_TRANSLATE_Y_BY = -65.0;
    protected final double MAX_HP;
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
    private Boolean disableRotate = false;

    protected double hp;

    private static double limStartX = 0;
    private static double limStartY = 0;
    private static double limEndX = WIDTH;
    private static double limEndY = HEIGHT;

    private EnemyProjectileControl enemyProjectileControl;
    private long nextMove;
    private boolean farFromPlayer;
    private short randomSign;
    protected boolean boss;

    public enum MoveMode {STATIONARY, FOLLOW_PLAYER, RANDOM, CIRCULAR}

    public Enemy(EnemyType enemyType, ProjectileType projectileType, ProjectileControlType projectileControlType, MoveMode mode, double minDistance, long move_interval_ms) {
        this(enemyType, projectileType, projectileControlType, mode, minDistance);
        this.moveInterval = move_interval_ms;
        this.intervalExists = true;
    }

    public Enemy(EnemyType enemyType, ProjectileType projectileType, ProjectileControlType projectileControlType, MoveMode mode, double minDistance) {
        this(enemyType, projectileType, projectileControlType, mode);
        this.minDistance = minDistance;
    }


    public Enemy(EnemyType enemyType, ProjectileType projectileType, ProjectileControlType projectileControlType, MoveMode mode) {
        this(enemyType);
        this.mode = mode;
        this.enemyProjectileControl = new EnemyProjectileControl(projectileType);

        enemyProjectileControl.addPulse(projectileControlType.getPulseRate(), projectileControlType.getPulseAngle());
        enemyProjectileControl.addSpawnToPlayer(projectileControlType.getToPlayerRate());
        enemyProjectileControl.addRing1by1(projectileControlType.getRing1by1Rate(), projectileControlType.getRing1by1Angle());
    }

    public Enemy(EnemyType enemyType) {
        super(enemyType.getURL(), enemyType.getSPEED());
        this.enemyType = enemyType;
        this.MAX_HP = enemyType.getHP();
        Random r = new Random();
        this.randomSign = (short) (r.nextBoolean() ? -1 : 1);
        hp = MAX_HP;

        Random rand = new Random();
        setLayoutY(limStartY + height + rand.nextInt((int) limEndY - 2 * height - 10 - (int) (limStartY)));
        setLayoutX(limStartX + width + rand.nextInt((int) limEndX - 2 * width - 10 - (int) (limStartX)));

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

    public static MoveMode getRandomMoveMode() {
        Random random = new Random();
        switch (random.nextInt(3)) {
            case 0:
                return MoveMode.STATIONARY;
            case 1:
                return MoveMode.CIRCULAR;
            case 2:
                return MoveMode.FOLLOW_PLAYER;
            default:
                return MoveMode.FOLLOW_PLAYER;
        }
    }

    protected void move() {

        if ((!intervalExists || System.currentTimeMillis() % (moveInterval * 2) > moveInterval) && farFromPlayer) {
            switch (mode) {
                case FOLLOW_PLAYER: {
                    moveImageView(angle);
                    break;
                }
                case CIRCULAR: {
                    moveImageView(angle + 90 * randomSign);
                    break;
                }
                case RANDOM: {

                    if (nextMove < System.currentTimeMillis()) {
                        randomAngle = -30 + Math.random() * 30;
                        nextMove = System.currentTimeMillis() + moveInterval;
                    }
                    moveImageView(randomAngle + angle);
                    break;
                }
                default:
                case STATIONARY: {
                    break;
                }
            }
        }

    }

    public static void setMapLimits(double StartX, double EndX, double StartY, double EndY) {
        limStartX = StartX;
        limEndX = EndX;
        limStartY = StartY;
        limEndY = EndY;
    }

    private void moveImageView(double angle) {
        double nextX = getLayoutX();
        double nextY = getLayoutY();
        disableRotate = false;
        if (Math.cos(Math.toRadians(angle)) < 0 && Wall.canMoveLeft(this, GameViewManager.getInstance().getWallArrayList())) {
            nextX = getLayoutX() + Math.cos(Math.toRadians(angle)) * enemyType.getSPEED();
        }
        if (Math.cos(Math.toRadians(angle)) > 0 && Wall.canMoveRight(this, GameViewManager.getInstance().getWallArrayList())) {
            nextX = getLayoutX() + Math.cos(Math.toRadians(angle)) * enemyType.getSPEED();
        }
        if (Math.sin(Math.toRadians(angle)) > 0 && Wall.canMoveDown(this, GameViewManager.getInstance().getWallArrayList())) {
            nextY = getLayoutY() + Math.sin(Math.toRadians(angle)) * enemyType.getSPEED();
        }
        if (Math.sin(Math.toRadians(angle)) < 0 && Wall.canMoveUp(this, GameViewManager.getInstance().getWallArrayList())) {
            nextY = getLayoutY() + Math.sin(Math.toRadians(angle)) * enemyType.getSPEED();
        }
        if (nextX == getLayoutX() && nextY > getLayoutY()) {
            disableRotate = true;
            setRotate(90);
        } else if (nextX == getLayoutX() && nextY < getLayoutY()) {
            disableRotate = true;
            setRotate(-90);
        } else if (nextY == getLayoutY() && nextX < getLayoutX()) {
            disableRotate = true;
            setRotate(180);
        } else if (nextY == getLayoutY() && nextX > getLayoutX()) {
            disableRotate = true;
            setRotate(0);
        }

        if (nextX + 15 + width < WIDTH && nextX > 15) {
            setLayoutX(nextX);
        } else {
            randomSign *= -1;
        }
        if (nextY + 15 + height < HEIGHT && nextY > 15) {
            setLayoutY(nextY);
        } else {
            randomSign *= -1;
        }


    }

    @Override
    public Node[] getChildren() {
        return new Node[0];
    }

    protected void checkAlive() {
        if (hp <= 0) {
            showFloatingScore();
            AudioManager.playNewAudio(AudioFile.ENEMY_DEATH, 0.15);
            Player.increaseCurrentScore(this.getScoreValue());
            GameViewManager.getMainPane().removeFromGamePane(this);
            GameViewManager.getInstance().removeEnemy(this);
        }
    }


    protected void showFloatingScore() {
        lbl_floatingScore.setText("+" + this.getScoreValue());
        lbl_floatingScore.setLayoutX(this.getLayoutX());
        lblmover.setFromY(this.getLayoutY());
        GameViewManager.getMainPane().addToUIPane(lbl_floatingScore);
        floatingScoreTransition.play();
    }

    @Override
    public void update() {
        updateAngle();
        calculateDistance();
        if (!boss) {
            if (!disableRotate) {
                setRotate(angle);
            }

            move();
            enemyProjectileControl.update(angle, getSpawner());
        }
        checkAlive();
        if (animated) {
            animationClip.animate();
        }
    }

    public int getScoreValue() {
        return enemyType.getScore();
    }
}