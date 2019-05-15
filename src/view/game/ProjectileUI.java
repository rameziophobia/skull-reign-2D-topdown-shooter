package view.game;

import controller.animation.SpriteSheet;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import view.GameViewManager;

import static model.GameObject.*;

public class ProjectileUI extends HBox {

    private static final int weaponSlotsNum = 4;
    private static final StackPane[] weapons = new StackPane[weaponSlotsNum];
    private static ImageView[] prevWeaponViews = new ImageView[2];

    public ProjectileUI() {
        setSpacing(10);
        Image background = new Image("file:resources/sprites/ui/game/black-weapon-background-150x150.png",
                60, 60, true, true);
        Image backgroundSmall = new Image("file:resources/sprites/ui/game/black-weapon-background-150x150.png",
                30, 30, true, true);

        for (int i = 0; i < weaponSlotsNum; i++) {
            weapons[i] = new StackPane(new ImageView(background));
        }
        weapons[0] = new StackPane(new ImageView(backgroundSmall));
        weapons[3] = new StackPane(new ImageView(backgroundSmall));
        weapons[0].prefHeight(30);
        weapons[0].prefWidth(30);

        getChildren().addAll(weapons);
        setLayoutX(GameViewManager.WIDTH - 300);
        setLayoutY(GameViewManager.HEIGHT - 90);
    }

    public static void setWeapon(int index, String projectileURL) {

        ImageView weaponImage;

        if (prevWeaponViews[index] != null) {
            int i = (index == 0) ? 0 : 3;
            ObservableList<Node> activeSlot = weapons[i].getChildren();
            if (activeSlot.size() > 1) {
                activeSlot.remove(1);
            }
            prevWeaponViews[index].setScaleX(0.5);
            prevWeaponViews[index].setScaleY(0.5);
            activeSlot.add(prevWeaponViews[index]);
        }

        index++;

        weaponImage = !isAnimated(projectileURL) ?
                new ImageView(projectileURL) :
                new ImageView(SpriteSheet.getFirstSprite(projectileURL));

        weaponImage.setRotate(270);
        ObservableList<Node> currentSlot = weapons[index].getChildren();
        if (currentSlot.size() > 1) {
            currentSlot.remove(1);
        }
        currentSlot.add(weaponImage);
        prevWeaponViews[--index] = weaponImage;
    }
}
