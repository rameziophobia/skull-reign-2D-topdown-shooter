package view;

import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import model.GameObject;
import model.player.PlayerType;
import model.player.Player;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class GameViewManager {
    public static final int HEIGHT = 1080;//todo this should only be used for scaling not in the entire code base (what's the point of scaling then ?)
    public static final int WIDTH = 1920;
    private static AnchorPane gamePane = new AnchorPane();
    private Scene gameScene = new Scene(gamePane, WIDTH, HEIGHT);
    private Stage gameStage = new Stage();
    private Stage menuStage;
    private static Player player;

    private GameViewUI GVUI;

    public GameViewManager() {
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
        this.menuStage = menuStage;
        this.menuStage.hide();
        gameStage.show();
        gameStage.setFullScreen(true);

        createUI();
        createPlayer(chosenPlayer);

        InputManager.setPlayer(player);
        InputManager.setKeyListener(gameScene);
        InputManager.setMouseListeners(gamePane);

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
        
        if (nextRegenTime < System.currentTimeMillis()) { //todo masheeh mn hna
            nextRegenTime = System.currentTimeMillis() + regenerationTimeLimitms;
            player.regenerate();
        }
    }

}
