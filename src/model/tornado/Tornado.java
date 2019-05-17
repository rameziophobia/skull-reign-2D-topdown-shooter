package model.tornado;

import controller.LevelManager;
import controller.animation.AnimationClip;
import controller.animation.SpriteSheet;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import javafx.util.Duration;
import model.GameObject;
import model.player.Player;
import view.GameViewManager;
import view.Main;

import java.util.Random;

import static view.GameViewManager.HEIGHT;
import static view.GameViewManager.WIDTH;

public class Tornado extends GameObject {

    private static final String PATH_RESOURCES_SPRITES_OBSTACLES = Main.PATH_RESOURCES_SPRITES + "tornado/";
    private static final String FILE_TORNADO = PATH_RESOURCES_SPRITES_OBSTACLES + "tornado-animated-64x64.png";
    private final LevelManager levelManager;
    private AnimationClip animationClip;
    private static double limStartX = 0;
    private static double limStartY = 0;
    private static double limEndX = WIDTH;
    private static double limEndY = HEIGHT;
    private int damageMultiplier;

    //todo: bug spawns at the upper left corner of the screen
    public Tornado(int DmgMultiplier, LevelManager levelManager) {
        super(FILE_TORNADO);
        this.levelManager = levelManager;
        damageMultiplier = DmgMultiplier;
        setUpRandMov();

        animationClip = new AnimationClip(
                new SpriteSheet(FILE_TORNADO, 0),
                2,
                true,
                AnimationClip.INF_REPEATS,
                this);
    }

    private void setUpRandMov() {
        final PathTransition pathTransition = new PathTransition();

        pathTransition.setDuration(Duration.seconds(4.0));
        pathTransition.setPath(getRandPath());
        pathTransition.setNode(this);
        pathTransition.setCycleCount(Timeline.INDEFINITE);
        pathTransition.setAutoReverse(true);
        pathTransition.play();
    }

    private Path getRandPath() {
        Random rand = new Random();
        int startX = rand.nextInt((int) (limEndX - limStartX)) + (int) limStartX;
        int startY = rand.nextInt((int) (limEndY - limStartY)) + (int) limStartY;
        int EndX = rand.nextInt((int) (limEndX - limStartX)) + (int) limStartX;
        int EndY = rand.nextInt((int) (limEndY - limStartY)) + (int) limStartY;

        Point2D start = new Point2D(startX, startY);
        Point2D end = new Point2D(EndX, EndY);

        while (start.distance(end) < 400) {
            startX = rand.nextInt((int) (limEndX - limStartX)) + (int) limStartX;
            startY = rand.nextInt((int) (limEndY - limStartY)) + (int) limStartY;
            EndX = rand.nextInt((int) (limEndX - limStartX)) + (int) limStartX;
            EndY = rand.nextInt((int) (limEndY - limStartY)) + (int) limStartY;

            start = new Point2D(startX, startY);
            end = new Point2D(EndX, EndY);
        }
        final Path path = new Path();
        path.getElements().addAll(
                new MoveTo(startX, startY),
                new QuadCurveTo(
                        startX,
                        startY,
                        EndX, EndY));
        path.setOpacity(0);
        return path;
    }

    @Override
    public void update() {
        animationClip.animate();
        playerCollisionCheck(GameViewManager.getPlayer());
    }

    public void setDamageMultiplier(int multiplier) {
        damageMultiplier = multiplier;
    }

    public static void setMapLimits(double StartX, double EndX, double StartY, double EndY) {
        limStartX = StartX;
        limEndX = EndX;
        limStartY = StartY;
        limEndY = EndY;
    }

    public void playerCollisionCheck(Player player) {
        if (isIntersects(player)) {
            player.takeDmg(damageMultiplier);
            levelManager.reduceNumOfTornado(this);
            GameViewManager.getMainPane().removeFromGamePane(this);
        }
    }

    @Override
    public Node[] getChildren() {
        return null;
    }
}
