package view.menu.mainmenu;

import controller.animation.AnimationClip;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;
import model.ui.menu.Menu;
import model.ui.menu.MenuBackground;
import model.ui.menu.MenuButton;
import view.Main;
import view.menu.mainmenu.menus.*;

import java.util.HashMap;

public class MenuScene extends Scene {

    public static final String PATH_RESOURCES_SPRITES_UI = Main.PATH_RESOURCES_SPRITES + "ui/";
    private static final double BUTTON_SCALE = 0.8;

    private static final Font TITLE_FONT_SIZE = new Font(40);

    private HashMap<Menus, Menu> menuHashMap = new HashMap<>();
    private StackPane stackPane;

    private StackPane stp_menus;
    private MenuBackground menuBackground;

    public MenuScene(double width, double height) {
        super(new StackPane(), width, height);//todo width and height
        stackPane = (StackPane) getRoot();

        createScene();
    }

    private void createScene() {
        menuBackground = new MenuBackground();

        Rectangle blackScreen = new Rectangle(1280, 720, Color.BLACK);//todo width and height
        blackScreen.setOpacity(0);
        blackScreen.setMouseTransparent(true);

        FadeTransition fadeToBlack = new FadeTransition();
        fadeToBlack.setDuration(Duration.seconds(0.75));
        fadeToBlack.setToValue(1);
        fadeToBlack.setDelay(Duration.seconds(0.2));
        fadeToBlack.setNode(blackScreen);

        ImageView imgV_logo = new ImageView(PATH_RESOURCES_SPRITES_UI + "placeholder-logo-2.png");
        HBox hbx_logo = new HBox(imgV_logo);
        hbx_logo.setAlignment(Pos.CENTER);

        MenuButton.setButtonScale(BUTTON_SCALE);

        final Menu mainMenu = new MainMenu(this);
        menuHashMap.put(Menus.Main, mainMenu);

        final Menu newGameMenu = new NewGameMenu(this);
        menuHashMap.put(Menus.NewGame, newGameMenu);

        final Menu loadGameMenu = new LoadGameMenu(this);
        menuHashMap.put(Menus.LoadGame, loadGameMenu);

        final Menu hallOfFameMenu = new HallOfFameMenu(this);
        menuHashMap.put(Menus.HallOfFame, hallOfFameMenu);

        final Menu settingsMenu = new SettingsMenu(this);
        menuHashMap.put(Menus.Settings, settingsMenu);

        stp_menus = new StackPane(mainMenu, newGameMenu, loadGameMenu, hallOfFameMenu, settingsMenu);

        final VBox vbx_main = new VBox(hbx_logo, stp_menus);
        vbx_main.setPadding(new Insets(10, 0, 10, 0));

        stackPane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        stackPane.getChildren().addAll(
                menuBackground,
                vbx_main,
                blackScreen);

        mainMenu.fadeIn();
    }

    public static Label createMenuTitle(String text) {
        final Label lbl_title = new Label(text);
        lbl_title.setFont(TITLE_FONT_SIZE);
        lbl_title.setTextFill(Color.WHITE);
        VBox.setMargin(lbl_title, new Insets(10));
        return lbl_title;
    }

    public void menuTransition(Menus key) {
        if (menuHashMap.containsKey(key)) {
            menuHashMap.get(key).fadeIn();
        } else {
            throw new IllegalArgumentException("Invalid Menu key");
        }
    }

    public void openDoor() {
        menuBackground.openDoor();
    }

    public void closeDoor() {
        menuBackground.closeDoor();
    }

    public void update() {
        stp_menus.getChildren().forEach(node -> ((Menu) node).getChildren().forEach(node1 -> {
            if (node1 instanceof MenuButton) {
                ((MenuButton) node1).update();
            }
        }));
        menuBackground.getAnimationClips().forEach(AnimationClip::animate);
    }
}
