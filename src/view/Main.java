package view;

import controller.SettingsManager;
import controller.audiomanager.AudioManager;
import javafx.application.Application;
import javafx.stage.Stage;
import view.menu.mainmenu.MenuScene;

public class Main extends Application {

    public static final String PATH_RESOURCES = "file:resources/";
    public static final String PATH_RESOURCES_SPRITES = PATH_RESOURCES + "sprites/";

    private static Stage stage;
    private static MenuScene menuScene;

    @Override
    public void start(Stage primaryStage) {
//        ViewManager manager = new ViewManager();
//        primaryStage = manager.getMainStage();
        AudioManager.init();
        SettingsManager.init();

        stage = primaryStage;
        menuScene = new MenuScene(1280, 720, stage);
        stage.setScene(menuScene);

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void exit() {
        stage.close();
    }

    public static void switchToMainMenu(){
        stage.show();
        menuScene.startLoop();
        GameViewManager.getGameStage().hide();
    }
    public static void switchToGame(){
        stage.hide();
        GameViewManager.getGameStage().show();
    }
}