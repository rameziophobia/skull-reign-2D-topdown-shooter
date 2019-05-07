package view;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import view.menu.mainmenu.MenuScene;

public class Main extends Application {

    public static final String PATH_RESOURCES = "file:resources/";
    public static final String PATH_RESOURCES_SPRITES = PATH_RESOURCES + "sprites/";
    public static final String PATH_RESOURCES_SAVES = PATH_RESOURCES + "savedata/";

    private static Stage stage;

    @Override
    public void start(Stage primaryStage) {
//        ViewManager manager = new ViewManager();
//        primaryStage = manager.getMainStage();

        stage = primaryStage;
        MenuScene menuScene = new MenuScene(1280, 720);
        stage.setScene(menuScene);

        AnimationTimer tempAnimationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                menuScene.update();
            }
        };
        tempAnimationTimer.start();

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getStage() {
        return stage;
    }
}