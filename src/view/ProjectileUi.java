package view;

import controller.Animation.SpriteSheet;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;

import static model.Sprite.*;

public class ProjectileUi extends TilePane{

    private static final int weaponSlotsNum = 4;
    private static final StackPane[] weapons = new StackPane[weaponSlotsNum];

    public ProjectileUi() {
        setOrientation(Orientation.HORIZONTAL);
        setHgap(10);
        Image background = new Image("file:src/view/resources/black-weapon-background-150x150.png",
                        60, 60 , true, true);

        for(int i = 0; i < weaponSlotsNum; i++){
            weapons[i] = new StackPane(new ImageView(background));
        }

        getChildren().addAll(weapons[1],weapons[2]);
        setLayoutX(GameViewManager.WIDTH - 200);
        setLayoutY(GameViewManager.HEIGHT - 90);
    }

    public static void setWeapon(int index, String projectileURL){
        index++;
        System.out.println(index);
        ImageView weaponImage;

//        if(isAnimated(projectileURL)){
//            weaponImage =  new ImageView(new Image(projectileURL,
//                    getImageWidth(projectileURL),getImageHeight(projectileURL),
//                    true,false));
//        }else{
//            weaponImage = new ImageView(projectileURL);
//        }

        weaponImage = !isAnimated(projectileURL) ?
                new ImageView(projectileURL) :
                new ImageView(SpriteSheet.getFirstSprite(projectileURL));
//new Image(projectileURL,
//                getImageWidth(projectileURL), getImageHeight(projectileURL),
//                true, false)
        weaponImage.setRotate(270);
        ObservableList<Node> currentSlot = weapons[index].getChildren();
        if (currentSlot.size() > 1){
            currentSlot.remove(1);
        }
        currentSlot.add(weaponImage);
    }
}
