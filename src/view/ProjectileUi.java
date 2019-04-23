package view;

import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;

public class ProjectileUi extends TilePane{

    private static final int weaponSlotsNum = 2;
    private static final StackPane[] weapons = new StackPane[weaponSlotsNum];

    public ProjectileUi() {
        setOrientation(Orientation.HORIZONTAL);
        setHgap(10);
        Image background = new Image("file:src/view/resources/black-weapon-background-150x150.png",
                        60, 60 , true, true);

        weapons[0] = new StackPane(new ImageView(background));//todo momkn tet3ml for loop
        weapons[1] = new StackPane(new ImageView(background));
        getChildren().addAll(weapons);
        setLayoutX(GameViewManager.WIDTH - 200);
        setLayoutY(GameViewManager.HEIGHT - 90);
    }

    public TilePane projectileUiBar() {
        return this;
    }

    public static void setWeapon(int index, String projectileURL){
        System.out.println(index);
        ImageView weaponImage = new ImageView(projectileURL);
        weaponImage.setRotate(270);
        ObservableList<Node> currentSlot = weapons[index].getChildren();
        if (currentSlot.size() > 1){
            currentSlot.remove(1);
        }
        currentSlot.add(weaponImage);
    }
}
