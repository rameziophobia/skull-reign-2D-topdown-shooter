package model.obstacles;

import controller.Animation.AnimationClip;
import controller.Animation.SpriteSheet;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import javafx.util.Duration;
import model.GameObject;
import view.Main;

import java.util.Random;

import static view.GameViewManager.HEIGHT;
import static view.GameViewManager.WIDTH;

public class Obstacle extends GameObject {

    private static final String PATH_RESOURCES_SPRITES_OBSTACLES = Main.PATH_RESOURCES_SPRITES + "obstacles/";
    private static final String FILE_TORNADO = PATH_RESOURCES_SPRITES_OBSTACLES + "tornado-animated-64x64.png";
    private AnimationClip animationClip;

    //todo: bug spawns at the upper left corner of the screen
    public Obstacle() {
        super(FILE_TORNADO);

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
//        pathTransition.setDelay(Duration.seconds(.5));
        pathTransition.setPath(getRandPath());
        pathTransition.setNode(this);
        pathTransition.setCycleCount(Timeline.INDEFINITE);
        pathTransition.setAutoReverse(true);
        pathTransition.play();
    }

    private Path getRandPath() {
        Random rand = new Random();
        final int startX = rand.nextInt(WIDTH);
        final int startY = rand.nextInt(HEIGHT);

        final Path path = new Path();
        path.getElements().addAll(
                new MoveTo(startX, startY),
                new QuadCurveTo(
                        startX,
                        startY,
                        rand.nextInt(WIDTH), rand.nextInt(HEIGHT)));
        path.setOpacity(0);
        return path;
    }

    @Override
    public void update() {
        animationClip.animate();
    }
}
