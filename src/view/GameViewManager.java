package view;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
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
import javafx.util.Duration;
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
    private static Label lbl_floatingScore;
    private static FadeTransition lblfader;
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
            HallOfFameMenu.setNewRecord(player.getName(), player.getCurrentScore());
            player.resetScore();
            endGame();
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
        node.toBack();
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

    @Deprecated
    public void createNewGame(PlayerType chosenPlayer) {
        createNewGame(chosenPlayer, "");
    }

    public void createNewGame(PlayerType chosenPlayer, String playerName) {
        Main.switchToGame();

        createPlayer(chosenPlayer, playerName);

        startGameLoop();
    }

    private void createPlayer(PlayerType chosenPlayer, String playerName) {
        player = new Player(chosenPlayer, GVUI.getHealthBars().getHPRectangle(), GVUI.getHealthBars().getShieldRectangle());
        player.setName(playerName);
        addGameObjectTOScene(player);
        player.toBack();
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
        lbl_currentScore = new Label("CURRENT SCORE: 0");
        lbl_currentScore.setPrefWidth(GameViewManager.WIDTH);
        lbl_currentScore.setPadding(new Insets(10,0,0,0));
        lbl_currentScore.setAlignment(Pos.BOTTOM_CENTER);
        lbl_currentScore.setTextAlignment(TextAlignment.CENTER);
        lbl_currentScore.setTextFill(Color.GHOSTWHITE);
        lbl_currentScore.setFont(Main.FutureThinFont);
        lbl_currentScore.setStyle("-fx-stroke: firebrick;-fx-stroke-width: 12px;");
        addGameObjectTOScene(lbl_currentScore);

        lbl_floatingScore = new Label("");
        lbl_floatingScore.setFont(Font.font("Arial", FontWeight.BOLD, 35));
        lbl_floatingScore.setTextFill(Color.GHOSTWHITE);
        lblfader = new FadeTransition();
        lbl_floatingScore.setLayoutX(player.getLayoutX());
        addGameObjectTOScene(lbl_floatingScore);
    }

    public static void updateLabel(int amount,double X,double Y) {
        lbl_currentScore.setText("CURRENT SCORE: " + player.getCurrentScore());
        lbl_floatingScore.setText("+"+amount);
        playFloatingLabelAnimation(X,Y);

    }
    public static void playFloatingLabelAnimation(double X,double Y) {
        lblfader.setToValue(0);
        lblfader.setFromValue(1);
        lblfader.setDuration(Duration.millis(1250));
        TranslateTransition lblmover = new TranslateTransition();
        lblmover.setDuration(Duration.millis(1500));
        lbl_floatingScore.setLayoutX(X);
        lblmover.setFromY(Y);
        lblmover.setByY(-65.0);
        lblfader.setNode(lbl_floatingScore);
        lblmover.setNode(lbl_floatingScore);
        lblfader.play();
        lblmover.play();
    }

    public static void endGameSequence() {
        if (!gameEnded) {
            gameEnded = true;

            gameEnd.setName(player.getName());
            gameEnd.setScore(player.getCurrentScore());
            addGameObjectTOScene(gameEnd);
            gameEnd.show();
            gameEnd.toFront();
        }
    }

    public void endGame() {
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
        LevelManager.createPowerUp();


        List<GameObject> gameObjects = gamePane.getChildren().stream().filter(n -> (n instanceof GameObject)).map(n ->
                (GameObject) n
        ).collect(Collectors.toList());
        gameObjects.forEach(GameObject::update);
    }
}