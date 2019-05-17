package view;

import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import model.GameObject;
import model.player.Player;
import model.player.PlayerType;
import model.ui.game.ScoreLabel;
import model.walls.Wall;
import view.map.AStarPathFinder;
import view.map.GameMap;
import view.map.Path;
import view.menu.GameEnd;
import view.menu.mainmenu.menus.HallOfFameMenu;

import java.awt.*;
import java.util.ArrayList;


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
    private static ArrayList<Rectangle> rectangleArrayList;


    private static GameMap map;
    private static AStarPathFinder finder;
    public GameViewManager() {
        gamePane = new AnchorPane();
        map = new GameMap();
        finder = new AStarPathFinder(map, 1000, true);

        gameScene = new Scene(gamePane, WIDTH, HEIGHT);
        gameStage.setScene(gameScene);
        gameStage.setFullScreen(false);

        gamePane.setStyle(" -fx-background-color:black;");
//        GameUI.createBackground(gamePane);
        GameUI.setCrosshair(gamePane);

        setWindowScaling();

        GVUI = new GameViewUI();
        initMap();
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

    public static void initMap() {
        rectangleArrayList = new ArrayList<>();

        for (int y=0;y<map.getHeightInTiles();y++) {
            for (int x=0;x<map.getWidthInTiles();x++) {
                if(map.getTerrain(x+56*x,y+56*y) == 1){
                    LevelManager.addToWallArrayList(new Wall(500+56*x,225+56*y));
                    addTOScene(new Wall(500+56*x,225+56*y));

                }
            }
        }
//        Wall x = new Wall(500,225);
//        x.setFitWidth(300);
//        x.setFitHeight(56);
//        LevelManager.addToWallArrayList(x);
//        addTOScene(x);
    }

    public static Player getPlayer() {
        return player;
    }
    public static GameMap getMap() {
        return map;
    }
    public static AStarPathFinder getFinder() {
        return finder;
    }
    public static void removeFromScene(Node node) {
        gamePane.getChildren().remove(node);
    }

    /**
     * @p   aram gameObject to be removed
     *
     * @deprecated use {@link #removeFromScene(Node)}instead.
     */
    @Deprecated
    public static void removeGameObjectFromScene(GameObject gameObject) {
        gamePane.getChildren().remove(gameObject);
    }

    /**
     * @param node
     *
     * @deprecated use {@link #addTOScene(Node)}instead.
     */
    @Deprecated
    public static void addGameObjectTOScene(Node node) {
        gamePane.getChildren().add(node);
        node.toBack();
    }

    public static void addTOScene(Node node) {
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
        lbl_currentScore = new ScoreLabel(); //todo move to GameUI
        addGameObjectTOScene(lbl_currentScore);
    }

    public static void updateLabel(int amount) {
        lbl_currentScore.setText("CURRENT SCORE: " + player.getCurrentScore());
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

        Object[] ob = gamePane.getChildren().toArray();
        for (Object node : ob) {
            if(node instanceof GameObject)
                ((GameObject) node).update();
        }

    }
}