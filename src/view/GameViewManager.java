package view;

import controller.InputManager;
import controller.LevelManager;
import controller.map.Map;
import controller.map.MapLoader;
import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.GameObject;
import model.MainPane;
import model.enemies.Enemy;
import model.player.Player;
import model.player.PlayerType;
import model.ui.game.ScoreLabel;
import model.wall.Wall;
import view.menu.GameEnd;
import view.menu.mainmenu.menus.HallOfFameMenu;

import java.util.ArrayList;
import java.util.List;

public class GameViewManager {
    public static final int HEIGHT = 1080;
    public static final int WIDTH = 1920;

    public static final int CENTER_X = WIDTH / 2;
    public static final int CENTER_Y = HEIGHT / 2;

    private static MainPane mainPane;
    private static GameEnd gameEnd;
    private static boolean gameEnded;
    private static GameViewManager instance;
    private Scene gameScene;
    private static Stage gameStage;
    private static Player player;
    private static Label lbl_currentScore;
    private GameUI gameUI;
    private static AnimationTimer gameLoop;
    private Endless endless;
    private Boolean isEndless;
    private static ArrayList<Wall>  wallArrayList= new ArrayList<>();

    public static GameViewManager getInstance() {
        return instance;
    }



    public GameViewManager(Boolean endless) {
        instance = this;
        isEndless=endless;
        mainPane = new MainPane();

        gameScene = new Scene(mainPane, WIDTH, HEIGHT);

        gameStage = new Stage();
        gameStage.setScene(gameScene);
        gameStage.setFullScreen(false);



        final MapLoader mapLoader = new MapLoader(Map.BASE);
        GameViewManager.getMainPane().addAllToFrontPane(mapLoader.getFrontNodes());
        GameViewManager.getMainPane().addAllToBackPane(mapLoader.getBackNodes());

        mapLoader.getWallNodes().forEach(node -> GameViewManager.getMainPane().addToGamePane(node));
        wallArrayList.addAll(mapLoader.getWallNodes());
        setWindowScaling();

        gameUI = new GameUI(mainPane,true);

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

    public static MainPane getMainPane() {
        return mainPane;
    }
    public GameUI getGameUI() {
        return gameUI;
    }

    @Deprecated
    public static Pane getGamePane() {
        return mainPane.getGamePane();
    }

    private void setWindowScaling() {
        Rectangle2D bounds = Screen.getPrimary().getBounds();
        mainPane.getTransforms().add(new Scale(
                bounds.getWidth() / WIDTH,
                bounds.getHeight() / HEIGHT,
                0, 0));//todo
    }

    public void createNewGame(PlayerType chosenPlayer, String playerName) {
        Main.switchToGame();

        createPlayer(chosenPlayer, playerName);


        if(isEndless){
            endless = new Endless(2000,false);
        }
        startGameLoop();
    }

    private void createPlayer(PlayerType chosenPlayer, String playerName) {
        player = new Player(chosenPlayer, gameUI.getHealthBars().getHPRectangle(), gameUI.getHealthBars().getShieldRectangle());
        player.setName(playerName);
        mainPane.addToGamePane(player);
    }

    private void createUI() {
        mainPane.addToUIPane(gameUI.getGroup());
        mainPane.addToUIPane(gameUI.getHealthBars());
        createScoreLabel();
    }

    private void startGameLoop() {
        gameStart();
        gameLoop.start();
    }

    private void createScoreLabel() {
        lbl_currentScore = new ScoreLabel(); //todo move to GameUI
        mainPane.addToUIPane(lbl_currentScore);
    }

    public static void updateLabel() {
        lbl_currentScore.setText("CURRENT SCORE: " + player.getCurrentScore());
    }

    public static void endGameSequence() {
        if (!gameEnded) {
            gameEnded = true;

            gameEnd.setName(player.getName());
            gameEnd.setScore(player.getCurrentScore());

            mainPane.addToUIPane(gameEnd);
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

    public List<Enemy> getEnemyArrayList() {
        if(isEndless){
            return endless.getEnemyArrayList();
        }
        return null;
    }

    public ArrayList<Wall> getWallArrayList() {
        return wallArrayList;
    }

    public void removeEnemy(Enemy enemy) {
        if(isEndless){
            endless.removeEnemy(enemy);
        }

    }

    private void gameStart() {
        createUI();

        InputManager.setPlayer(player);
        InputManager.setKeyListener(gameScene);
        InputManager.setMouseListeners(mainPane);
    }

    private void gameUpdate() {
        if(isEndless){
            endless.update();
            gameUI.getWaveLabel().setUICounter(endless.getCurrentWave());
        }


        Object[] objects = mainPane.getGamePane().getChildren().toArray();
        for (Object node : objects) {
            if (node instanceof GameObject)
                ((GameObject) node).update();
        }
    }
}