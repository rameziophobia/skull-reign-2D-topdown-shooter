package model.obstacles;

import controller.Animation.AnimationClip;
import controller.Animation.SpriteSheet;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import javafx.util.Duration;
import view.Main;

import java.util.ArrayList;
import java.util.Random;

import static view.GameViewManager.HEIGHT;
import static view.GameViewManager.WIDTH;

    public class Obstacle {

        private static final String PATH_RESOURCES_SPRITES_OBSTACLES = Main.PATH_RESOURCES_SPRITES + "obstacles/";
        private static final String FILE_TORNADO = PATH_RESOURCES_SPRITES_OBSTACLES + "tornado-animated-64x64.png";
        private static ArrayList<AnimationClip> tornadoAnimation = new ArrayList<>();

        //todo: bug spawns at the upper left corner of the screen
        public static Group createRandomRotator() {//todo: add hitbox (bounds)
            Group randomRotator = new Group();
            Random rand = new Random();
            final int startX = rand.nextInt(WIDTH);
            final int startY = rand.nextInt(HEIGHT);

            ImageView tornadoImgView = new ImageView(FILE_TORNADO);
            final SpriteSheet spriteSheet = new SpriteSheet(FILE_TORNADO, 0);
            tornadoAnimation.add(new AnimationClip(spriteSheet,
                    spriteSheet.getFrameCount() * 2f,true,
                    Integer.MAX_VALUE,tornadoImgView)); //TODO: indefinite repeats

            final Path path = new Path();
            path.getElements().add(new MoveTo(startX, startY));
            path.getElements().add(new QuadCurveTo(
                    startX ,
                    startY ,
                    rand.nextInt(WIDTH) , rand.nextInt(HEIGHT) ));

            path.setOpacity(0);

            final PathTransition pathTransition = new PathTransition();

            pathTransition.setDuration(Duration.seconds(4.0));
            pathTransition.setDelay(Duration.seconds(.5));
            pathTransition.setPath(path);
            pathTransition.setNode(tornadoImgView);
            pathTransition.setCycleCount(Timeline.INDEFINITE);
            pathTransition.setAutoReverse(true);
            pathTransition.play();

            randomRotator.getChildren().add(path);
            randomRotator.getChildren().add(tornadoImgView);
            return randomRotator;
        }
        public static void update(){
            for(Object t:tornadoAnimation)
                ((AnimationClip)t).animate();
        }
}
