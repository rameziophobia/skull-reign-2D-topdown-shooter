package view;

import controller.Campaign;
import controller.InputManager;
import controller.LevelManager;
import controller.map.Map;
import controller.json.JsonParser;
import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
import view.game.stats.HealthBars;
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
    private LevelManager gameMode;
    private Boolean isEndless;
    private static ArrayList<Wall>  wallArrayList= new ArrayList<>();

    public static GameViewManager getInstance() {
        return instance;
    }



    public GameViewManager(Boolean endless) {
        instance = this;
        isEndless=endless;
        mainPane = new MainPane();
        mainPane.addAllToBackPane(new Rectangle(WIDTH, HEIGHT, Color.BLACK));

        gameScene = new Scene(mainPane, WIDTH, HEIGHT);

        gameStage = new Stage();
        gameStage.setScene(gameScene);
        gameStage.setFullScreen(true);
        gameStage.setTitle("Skull Reign");
        gameStage.getIcons().add(new Image(Main.PATH_RESOURCES_SPRITES + "icon.png"));


        setWindowScaling();

        gameUI = new GameUI(mainPane,isEndless);

        gameEnded = false;
        gameEnd = new GameEnd();
        gameEnd.setOnMouseClicked(e -> {
            HallOfFameMenu.setNewRecord(player.getName(), player.getCurrentScore());
            player.resetScore();
            Main.switchToMainMenu();
        });

        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                gameUpdate();
            }
        };
        JsonParser.writeEnemyEnum();
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
                0, 0));
    }

    public void createNewGame(PlayerType chosenPlayer, String playerName) {
        Main.switchToGame();

        createPlayer(chosenPlayer, playerName);

        initializeMapBorder();
        if(isEndless){
            gameMode = new Endless(2000,false);
        }else{
            gameMode = new Campaign(gameUI.getLevelLabel(),gameUI.getWaveLabel());
        }
        startGameLoop();
    }

    private void createPlayer(PlayerType chosenPlayer, String playerName) {
        player = new Player(chosenPlayer,
                gameUI.getPlayerHealthBars().getRectangle(HealthBars.Bars.HP),
                gameUI.getPlayerHealthBars().getRectangle(HealthBars.Bars.SHIELD));
        player.setName(playerName);
        mainPane.addToGamePane(player);
    }

    private void createUI() {
        mainPane.addToUIPane(gameUI.getGroup());
        mainPane.addToUIPane(gameUI.getPlayerHealthBars());
        createScoreLabel();
    }

    private void startGameLoop() {
        gameStart();
        gameLoop.start();
    }

    private void initializeMapBorder(){
        Wall up = new Wall(new Image(Main.PATH_RESOURCES_SPRITES + "walls/empty-704x396.png"));
        Wall down = new Wall(new Image(Main.PATH_RESOURCES_SPRITES + "walls/empty-704x396.png"));
        Wall left = new Wall(new Image(Main.PATH_RESOURCES_SPRITES + "walls/empty-704x396.png"));
        Wall right = new Wall(new Image(Main.PATH_RESOURCES_SPRITES + "walls/empty-704x396.png"));

        up.setFitHeight(Map.BLOCK_SIZE);
        up.setFitWidth((Map.MAP_BLOCKS_WIDTH - 1) * Map.BLOCK_SIZE);
        up.setLayoutX(Map.STARTING_X);
        up.setLayoutY(Map.STARTING_Y+Map.BLOCK_SIZE);

        down.setFitWidth((Map.MAP_BLOCKS_WIDTH - 1) * Map.BLOCK_SIZE);
        down.setFitHeight(Map.BLOCK_SIZE);
        down.setLayoutX(Map.STARTING_X);
        down.setLayoutY(Map.STARTING_Y + (Map.MAP_BLOCKS_HEIGHT - 1) * Map.BLOCK_SIZE);
        left.setFitWidth(Map.BLOCK_SIZE);
        left.setFitHeight((Map.MAP_BLOCKS_HEIGHT - 1) * Map.BLOCK_SIZE);
        left.setLayoutX(Map.STARTING_X);
        left.setLayoutY(Map.STARTING_Y+Map.BLOCK_SIZE);
        right.setFitWidth(Map.BLOCK_SIZE);
        right.setFitHeight((Map.MAP_BLOCKS_HEIGHT - 1) * Map.BLOCK_SIZE);
        right.setLayoutX(Map.STARTING_X + (Map.MAP_BLOCKS_WIDTH - 1) * Map.BLOCK_SIZE);
        right.setLayoutY(Map.STARTING_Y+Map.BLOCK_SIZE);


        mainPane.addToGamePane(up);
        mainPane.addToGamePane(down);
        mainPane.addToGamePane(left);
        mainPane.addToGamePane(right);

        getWallArrayList().add(down);
        getWallArrayList().add(right);
        getWallArrayList().add(left);
        getWallArrayList().add(up);
    }
    private void createScoreLabel() {
        lbl_currentScore = new ScoreLabel();
        mainPane.addToUIPane(lbl_currentScore);
    }

    public static void updateLabel() {
        lbl_currentScore.setText("CURRENT SCORE: " + player.getCurrentScore());
    }

    public static void endGameSequence() {
        if (!gameEnded) {
            gameEnded = true;
            gameLoop.stop();
            wallArrayList.clear();
            gameEnd.setName(player.getName());
            gameEnd.setScore(player.getCurrentScore());

            mainPane.addToUIPane(gameEnd);
            gameEnd.show();
            gameEnd.toFront();
        }
    }

    public static Stage getGameStage() {
        return gameStage;
    }

    public List<Enemy> getEnemyArrayList() {
        return gameMode.getEnemyArrayList();

    }

    public ArrayList<Wall> getWallArrayList() {
        return wallArrayList;
    }

    public void removeEnemy(Enemy enemy) {
        gameMode.removeEnemy(enemy);
    }

    private void gameStart() {
        createUI();

        InputManager.setPlayer(player);
        InputManager.setKeyListener(gameScene);
        InputManager.setMouseListeners(mainPane);
    }

    private void gameUpdate() {
        gameMode.update();



        Object[] objects = mainPane.getGamePane().getChildren().toArray();
        for (Object node : objects) {
            if (node instanceof GameObject)
                ((GameObject) node).update();
        }
    }
}