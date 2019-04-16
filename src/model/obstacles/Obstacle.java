package model.obstacles;

import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import javafx.util.Duration;

import java.util.Random;

import static view.GameViewManager.HEIGHT;
import static view.GameViewManager.WIDTH;

public class Obstacle {

    public static Group createRandomRotator() {
        Group randomRotator = new Group();
        Random rand = new Random();
        final int startX = rand.nextInt(WIDTH);
        final int startY = rand.nextInt(HEIGHT);
        final Circle circle = new Circle(startX, startY, 15);
        circle.setFill(Color.DARKRED);

        final Path path = new Path();
        path.getElements().add(new MoveTo(startX, startY));
        path.getElements().add(new QuadCurveTo(rand.nextInt(WIDTH) , rand.nextInt(HEIGHT) ,
                rand.nextInt(WIDTH) , rand.nextInt(HEIGHT) ));

        path.setOpacity(0);

        final PathTransition pathTransition = new PathTransition();

        pathTransition.setDuration(Duration.seconds(4.0));
        pathTransition.setDelay(Duration.seconds(.5));
        pathTransition.setPath(path);
        pathTransition.setNode(circle);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setCycleCount(Timeline.INDEFINITE);
        pathTransition.setAutoReverse(true);
        pathTransition.play();

        randomRotator.getChildren().add(path);
        randomRotator.getChildren().add(circle);
        return randomRotator;
    }
}
