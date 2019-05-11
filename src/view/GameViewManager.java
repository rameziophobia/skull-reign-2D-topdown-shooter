package view;

import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import model.GameObject;
import model.player.Player;
import model.player.PlayerType;
import view.menu.GameEnd;
import view.menu.mainmenu.menus.HallOfFameMenu;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class GameViewManager {
    public static final int HEIGHT = 1080;//todo this should only be used for scaling not in the entire code base (what's the point of scaling then ?)
    public static final int WIDTH = 1920;

    private static AnchorPane gamePane;
    private static GameEnd gameEnd;
    private static boolean gameEnded;
    private Scene gameScene;
    private static Stage gameStage = new Stage();
    private static Player player;
    private static Label lbl_currentScore;
    private GameViewUI GVUI;
    private static AnimationTimer gameLoop;

    public GameViewManager() {
        gamePane = new AnchorPane();
        gameScene = new Scene(gamePane, WIDTH, HEIGHT);
        gameStage.setScene(gameScene);
        gameStage.setFullScreen(true);

        GameUI.createBackground(gamePane);
        GameUI.setCrosshair(gamePane);
        setWindowScaling();

        GVUI = new GameViewUI();

        gameEnded = false;
        gameEnd = new GameEnd();
        gameEnd.setOnMouseClicked(e -> {
            HallOfFameMenu.addScoreInput(player.getCurrentScore(), true);
            player.resetScore();
        });

        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                gameUpdate();
            }
        };
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

    @Deprecated
    public static AnchorPane getGamePane() {
        return gamePane;
    }

    private void setWindowScaling() {
        Dimension resolution = Toolkit.getDefaultToolkit().getScreenSize();
        gamePane.getTransforms().add(new Scale(
                resolution.getWidth() / WIDTH,
                resolution.getHeight() / HEIGHT,
                0, 0));
    }

    public void createNewGame(PlayerType chosenPlayer) {
        Main.switchToGame();

        createPlayer(chosenPlayer);

        startGameLoop();
    }

    private void createPlayer(PlayerType chosenPlayer) {
        player = new Player(chosenPlayer, GVUI.getHealthBars().getHPRectangle(), GVUI.getHealthBars().getShieldRectangle());
        addGameObjectTOScene(player);
        player.toFront();//todo walls ?
    }

    private void createUI() {
        gamePane.getChildren().addAll(GVUI.getGroup(), GVUI.getHealthBars());
        createScoreLabel();
    }

    private void startGameLoop() {
        gameStart();
        gameLoop.start();
    }

    public void createScoreLabel() {
        lbl_currentScore = new Label("Current Score: 0");
        lbl_currentScore.setPrefWidth(GameViewManager.WIDTH);
        lbl_currentScore.setAlignment(Pos.TOP_CENTER);
        lbl_currentScore.setTextAlignment(TextAlignment.CENTER);
        lbl_currentScore.setTextFill(Color.YELLOW);
        lbl_currentScore.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        addGameObjectTOScene(lbl_currentScore);
    }

    public static void updateLabel() {
        lbl_currentScore.setText("Current Score: " + player.getCurrentScore());
    }

    public static void endGameSequence() {
        if (!gameEnded) {
            gameEnded = true;

            gameEnd.setName("Reported");//todo
            gameEnd.setScore(player.getCurrentScore());
            addGameObjectTOScene(gameEnd);
            gameEnd.show();
        }
    }

    public static void endGame() {
        gameLoop.stop();
        Main.switchToMainMenu();
    }

    public static Stage getGameStage() {
        return gameStage;
    }

    private void gameStart() {
        createUI();

        LevelManager.setSpawnable(true);

        InputManager.setPlayer(player);
        InputManager.setKeyListener(gameScene);
        InputManager.setMouseListeners(gamePane);
    }

    private void gameUpdate() {
        LevelManager.createEnemies();
        LevelManager.createObstacles();

        List<GameObject> gameObjects = gamePane.getChildren().stream().filter(n -> (n instanceof GameObject)).map(n ->
                (GameObject) n
        ).collect(Collectors.toList());
        gameObjects.forEach(GameObject::update);
    }
}
