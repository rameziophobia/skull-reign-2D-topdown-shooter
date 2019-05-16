package view;

import controller.LevelManager;
import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import model.GameObject;
import model.enemies.Enemy;
import model.player.Player;
import model.player.PlayerType;
import model.ui.game.ScoreLabel;
import model.walls.Wall;
import view.menu.GameEnd;
import view.menu.mainmenu.menus.HallOfFameMenu;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class GameViewManager {
    public static final int HEIGHT = 1080;//todo this should only be used for scaling not in the entire code base (what's the point of scaling then ?)
    public static final int WIDTH = 1920;

    public static final int CENTER_X = WIDTH / 2;
    public static final int CENTER_Y = HEIGHT / 2;

    private static AnchorPane gamePane;
    private static GameEnd gameEnd;
    private static boolean gameEnded;
    private static GameViewManager instance;
    private Scene gameScene;
    private static Stage gameStage = new Stage();
    private static Player player;
    private static Label lbl_currentScore;
    private GameViewUI GVUI;
    private static AnimationTimer gameLoop;
    private LevelManager levelManager;

    public static GameViewManager getInstance() {
        return instance;
    }

    public GameViewManager() {
        instance = this;

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

    public static void removeFromScene(Node node) {
        gamePane.getChildren().remove(node);
        if (node instanceof GameObject) {
            Node[] children = ((GameObject) node).getChildren();
            if (children != null)
                gamePane.getChildren().removeAll(children);//todo we don't care about polymorphism :(
        }
    }

    /**
     * @param gameObject to be removed
     * @deprecated use {@link #removeFromScene(Node)}instead.
     */
    @Deprecated
    public static void removeGameObjectFromScene(GameObject gameObject) {
        gamePane.getChildren().remove(gameObject);
    }

    /**
     * @param node
     * @deprecated use {@link #addToScene(Node)}instead.
     */
    @Deprecated
    public static void addGameObjectTOScene(Node node) {
        gamePane.getChildren().add(node);
        node.toBack();
    }

    public static void addToScene(Node node) {
        if (node instanceof GameObject) {//todo we don't care about polymorphism :(
            Node[] children = ((GameObject) node).getChildren();
            if (children != null){
                for (Node child : children) {
                    gamePane.getChildren().add(child);
                    child.toBack();
                }
            }
        }
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

        levelManager = new LevelManager();
        startGameLoop();
    }

    private void createPlayer(PlayerType chosenPlayer, String playerName) {
        player = new Player(chosenPlayer, GVUI.getHealthBars().getHPRectangle(), GVUI.getHealthBars().getShieldRectangle());
        player.setName(playerName);
        GameViewManager.addToScene(player);
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
        lbl_currentScore = new ScoreLabel(); //todo move to GameUI
        GameViewManager.addToScene(lbl_currentScore);
    }

    public static void updateLabel(int amount) {
        lbl_currentScore.setText("CURRENT SCORE: " + player.getCurrentScore());
    }

    public static void endGameSequence() {
        if (!gameEnded) {
            gameEnded = true;

            gameEnd.setName(player.getName());
            gameEnd.setScore(player.getCurrentScore());
            GameViewManager.addToScene(gameEnd);
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

    public ArrayList<Enemy> getEnemyArrayList() {
        return levelManager.getEnemyArrayList();
    }

    public ArrayList<Wall> getWallArrayList() {
        return levelManager.getWallArrayList();
    }

    public void setSpawnable(boolean state) {
        levelManager.setSpawnable(state);
    }

    public boolean isSpawnable() {
        return levelManager.isSpawnable();
    }

    public void removeEnemy(Enemy enemy) {
        levelManager.removeEnemy(enemy);
    }

    private void gameStart() {
        createUI();

        InputManager.setPlayer(player);
        InputManager.setKeyListener(gameScene);
        InputManager.setMouseListeners(gamePane);
    }

    private void gameUpdate() {
//        LevelManager.createEnemies();
//        LevelManager.createObstacles();
//        LevelManager.createPowerUp();

        levelManager.update();

        List<GameObject> gameObjects = gamePane.getChildren().stream().filter(n -> (n instanceof GameObject)).map(n ->
                (GameObject) n
        ).collect(Collectors.toList());
        gameObjects.forEach(GameObject::update);
    }
}