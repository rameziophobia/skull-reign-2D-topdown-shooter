package view;

import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class GameUI { //todo temp static

    private GameUI() {

    }

    public static void createBackground(AnchorPane gamePane) {
        Image backgroundImage = new Image("file:resources/sprites/tiles/floor2.png");
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        gamePane.setBackground(new Background(background));
    }

    public static void setCrosshair(Pane pane) {
        Image image = new Image("file:resources/sprites/crosshair/4.png"); // todo use path constants
        pane.setCursor(new ImageCursor(image,
                image.getWidth() / 2,
                image.getHeight() / 2));
    }
}
