package model.spawner;

import controller.animation.AnimationClip;
import controller.animation.SpriteSheet;
import controller.map.MapLoader;
import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import model.GameObject;
import view.Main;

public class SpawnPoint extends GameObject {

    private static final SpriteSheet SPAWNING_SPRITE_SHEET = new SpriteSheet(Main.PATH_RESOURCES_SPRITES + "SpawnEffect-64x64.png", 0);
    private static final Duration PULSE_FADE_DURATION = Duration.seconds(1.25);

    private final ImageView img_pulseActive;
    private final ImageView img_spawningVFX;
    private boolean active;
    private final FadeTransition pulseFadeTransition;
    private boolean spawning;
    private final AnimationClip spawningAnimation;

    public SpawnPoint(int x, int y) {
        super(new Image(Main.PATH_RESOURCES_SPRITES + "pent/Pent_IDLE-16x16.png",
                MapLoader.BLOCK_SIZE, MapLoader.BLOCK_SIZE, true, false));

        this.setLayoutX(x);
        this.setLayoutY(y);

        img_pulseActive = new ImageView(new Image(Main.PATH_RESOURCES_SPRITES + "pent/Pent_ACTIVE-16x16.png",
                MapLoader.BLOCK_SIZE, MapLoader.BLOCK_SIZE, true, false));

        img_pulseActive.setLayoutX(x);
        img_pulseActive.setLayoutY(y);

        pulseFadeTransition = new FadeTransition(PULSE_FADE_DURATION, img_pulseActive);
        pulseFadeTransition.setFromValue(0);
        pulseFadeTransition.setToValue(1);
        pulseFadeTransition.setAutoReverse(true);
        pulseFadeTransition.setCycleCount(Transition.INDEFINITE);

        img_spawningVFX = new ImageView();
        img_spawningVFX.setLayoutX(x);
        img_spawningVFX.setLayoutY(y);

        spawningAnimation = new AnimationClip(
                SPAWNING_SPRITE_SHEET,
                12,
                img_spawningVFX
        );
    }

    public double getSpawnPointX() {
        return this.getLayoutX() + 16;//todo magic
    }

    public double getSpawnPointY() {
        return this.getLayoutY() + 16;//todo magic
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isSpawning() {
        return spawning;
    }

    public void setSpawning(boolean spawning) {
        if ((!this.spawning) && spawning) {
            img_spawningVFX.setOpacity(1);
            spawningAnimation.reset();
        }
        this.spawning = spawning;
    }

    @Override
    public void update() {
        if (active) {
            pulseFadeTransition.play();
        } else {
            img_pulseActive.setOpacity(0);
            pulseFadeTransition.stop();
        }

        if (spawning) {
            spawningAnimation.animate();
            if (spawningAnimation.isDone()) {
                spawning = false;
                img_spawningVFX.setOpacity(0);
            }
        }
    }

    @Override
    public Node[] getChildren() {
        return new Node[]{img_pulseActive, img_spawningVFX};
    }
}
