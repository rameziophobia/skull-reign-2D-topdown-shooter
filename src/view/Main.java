package view;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static final String PATH_RESOURCES = "file:resources/";
    public static final String PATH_RESOURCES_SPRITES = PATH_RESOURCES + "sprites/";

    @Override
    public void start(Stage primaryStage) {
        ViewManager manager = new ViewManager();
        primaryStage = manager.getMainStage();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}