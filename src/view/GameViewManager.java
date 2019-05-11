package view;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.GameObject;
import model.player.Player;
import model.player.PlayerType;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Label;
import view.menu.mainmenu.menus.HallOfFameMenu;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class GameViewManager {
    public static final int HEIGHT = 1080;//todo this should only be used for scaling not in the entire code base (what's the point of scaling then ?)
    public static final int WIDTH = 1920;
    private static AnchorPane gamePane ;
    private Scene gameScene;
    private static Stage gameStage = new Stage();
    private static Stage menuStage;
    private static Player player;
    private static Label lbl;

    private GameViewUI GVUI;

    public GameViewManager() {

        gamePane= new AnchorPane();
        gameScene= new Scene(gamePane, WIDTH, HEIGHT);
        gameStage.setScene(gameScene);

        GameUI.createBackground(gamePane);
        GameUI.setCrosshair(gamePane);
        setWindowScaling();

        GVUI = new GameViewUI();
    }

    public static Player getPlayer() {
        return player;
    }

    public static void removeGameObjectFromScene(GameObject gameObject) {
        gamePane.getChildren().remove(gameObject);
    }

    public static void addGameObjectTOScene(Node node) {
        gamePane.getChildren().add(node);
    }

    public static AnchorPane getGamePane() {
        return gamePane;
    }

    private void setWindowScaling() {
        Dimension resolution = Toolkit.getDefaultToolkit().getScreenSize();
        double width = resolution.getWidth();
        double height = resolution.getHeight();
        double w = width / WIDTH;  //your window width
        double h = height / HEIGHT;  //your window height
        Scale scale = new Scale(w, h, 0, 0);
        gamePane.getTransforms().add(scale);
    }

    public void createNewGame(Stage menuStage, PlayerType chosenPlayer) {
        LevelManager.setSpawnable(true);
        this.menuStage = menuStage;
        this.menuStage.hide();
        gameStage.show();
        gameStage.setFullScreen(true);

        createUI();
        createPlayer(chosenPlayer);

        InputManager.setPlayer(player);
        InputManager.setKeyListener(gameScene);
        InputManager.setMouseListeners(gamePane);

        createScoreLabel();

        startGameLoop();
    }

    private void createPlayer(PlayerType chosenPlayer) {
        player = new Player(chosenPlayer, GVUI.getHealthBars().getHPRectangle(), GVUI.getHealthBars().getShieldRectangle());
        addGameObjectTOScene(player);
        player.toFront();//todo walls ?
    }

    private void createUI() {
        gamePane.getChildren().addAll(GVUI.getGroup(), GVUI.getHealthBars());
    }

    private void startGameLoop() {
//        gameStart();

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                gameUpdate();
            }
        }.start();
    }

//    private void gameStart() { //todo: do we need that ?
//
//    }

    private void gameUpdate() {
        LevelManager.createEnemies();
        LevelManager.createObstacles();

        List<GameObject> gameObjects = gamePane.getChildren().stream().filter(n -> (n instanceof GameObject)).map(n ->
                (GameObject) n
        ).collect(Collectors.toList());
        gameObjects.forEach(GameObject::update);
    }

    public void createScoreLabel() {
        lbl = new Label("Current Score: " + player.getCurrentScore());
        lbl.setPrefWidth(GameViewManager.WIDTH);
        lbl.setAlignment(Pos.TOP_CENTER);
        lbl.setTextAlignment(TextAlignment.CENTER);
        lbl.setTextFill(Color.YELLOW);
        lbl.setStyle("-fx-font-size: 20px;-fx-font-weight: bold");
        gamePane.getChildren().add(lbl);
    }
    public static void updateLabel(){
        lbl.setText("Current Score: " + player.getCurrentScore());
    }

    public static void endGameSequence(){
        StackPane view = new StackPane();
        Rectangle rec = new Rectangle(0,0,WIDTH,HEIGHT);
        rec.setFill(Color.BLACK);
        VBox end = new VBox();
        Label text =new Label("YOU DIED");
        Label textclk =new Label("\n\nclick here to continue");
        text.setTextFill(Color.RED);
        textclk.setTextFill(Color.DARKORANGE);
        text.setStyle("-fx-font-size: 140px;-fx-font-weight: bold");
        textclk.setStyle("-fx-font-size: 20px");

        end.setLayoutX(0);
        end.setLayoutY(0);
        end.setPrefHeight(HEIGHT);
        end.setPrefWidth(WIDTH);
        end.setAlignment(Pos.CENTER);
        end.setSpacing(90);
        end.getChildren().addAll(text,textclk);
        view.getChildren().addAll(rec,end);
        view.setOpacity(0);
        view.setMouseTransparent(true);

        FadeTransition fader = new FadeTransition(Duration.millis(2000),view);
        fader.setToValue(1);
        fader.play();
        fader.setOnFinished(e->view.setMouseTransparent(false));
        addGameObjectTOScene(view);
        view.setOnMouseClicked(e->{
            HallOfFameMenu.addScoreInput(player.getCurrentScore());
            player.resetScore();
            endGame();
        });
    }
    public static void endGame(){
        menuStage.show();
        gameStage.hide();
    }
}
