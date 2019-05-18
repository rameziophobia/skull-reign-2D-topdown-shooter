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
    private static final int DMG = 50;
    private static final Random RAND = new Random();
    private static final Duration PATH_TRANSITION_DURATION = Duration.seconds(4.0);
    private final LevelManager levelManager;
    private AnimationClip animationClip;
    private static double limStartX = 0;
    private static double limStartY = 0;
    private static double limEndX = WIDTH;
    private static double limEndY = HEIGHT;

    public Tornado(LevelManager levelManager) {
        super(FILE_TORNADO);
        this.levelManager = levelManager;
        setUpRandMov();

        animationClip = new AnimationClip(
                new SpriteSheet(FILE_TORNADO, 0),
                2,
                true,
                AnimationClip.INF_REPEATS,
                this);
    }

    private void setUpRandMov() {
        final PathTransition pathTransition = new PathTransition(PATH_TRANSITION_DURATION, getRandPath(), this);

        pathTransition.setCycleCount(Timeline.INDEFINITE);
        pathTransition.setAutoReverse(true);
        pathTransition.play();
    }

    private Path getRandPath() {
        int startX, startY, EndX, EndY;
        Point2D start, end;

        do {
            startX = RAND.nextInt((int) (limEndX - limStartX)) + (int) limStartX;
            startY = RAND.nextInt((int) (limEndY - limStartY)) + (int) limStartY;
            EndX = RAND.nextInt((int) (limEndX - limStartX)) + (int) limStartX;
            EndY = RAND.nextInt((int) (limEndY - limStartY)) + (int) limStartY;

            start = new Point2D(startX, startY);
            end = new Point2D(EndX, EndY);
        } while (start.distance(end) < 400);

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

    public void playerCollisionCheck(Player player) {
        if (isIntersects(player)) {
            player.takeDmg(DMG);
            levelManager.reduceNumOfTornado(this);
            GameViewManager.getMainPane().removeFromGamePane(this);
        }
    }

    @Override
    public void update() {
        animationClip.animate();
        playerCollisionCheck(GameViewManager.getPlayer());
    }

    @Override
    public Node[] getChildren() {
        return null;
    }

    public static void setMapLimits(double StartX, double EndX, double StartY, double EndY) {
        limStartX = StartX;
        limEndX = EndX;
        limStartY = StartY;
        limEndY = EndY;
    }
}
