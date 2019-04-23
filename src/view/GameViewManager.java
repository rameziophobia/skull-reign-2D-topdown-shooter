package view;

import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import model.Enemies.Enemy;
import model.Enemies.normalTank;
import model.obstacles.Obstacle;
import model.player.PLAYERS;
import model.player.Player;
import model.projectiles.PowerUp;
import model.projectiles.Projectile;
import model.projectiles.ProjectileType;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import static model.Enemies.EnemyType.TANK_DARK;
import static model.Enemies.EnemyType.TANK_SAND;
import static model.obstacles.Obstacle.createRandomRotator;

public class GameViewManager {
    public static final int HEIGHT = 1080;
    public static final int WIDTH = 1920;
    public static AnchorPane gamePane;
    private Scene gameScene;
    private Stage gameStage;
    private Stage menuStage;
    private Player player;

    private double mouseXPos;
    private double mouseYPos;

    private boolean upPressed;
    private boolean downPressed;
    private boolean rightPressed;
    private boolean leftPressed;

    private AnimationTimer gameTimer;
    private ArrayList<Enemy> enemyArrayList = new ArrayList<>();
    private GridPane buildings;
    private int numberOfObstacles = 0;
    private int numberOfEnemies = 0;
    private double timer;
    private GameViewUI GVUI;
    public static long nextRegenTime = 0;
    public static long regenerationTimeLimitms = 5000;


    public GameViewManager() {
        initializeStage();
        createKeyListeners();
        GVUI = new GameViewUI();
    }

    private void createKeyListeners() {
        gameScene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case W:
                case UP: {
                    upPressed = true;
                    break;
                }
                case A:
                case LEFT: {
                    leftPressed = true;
                    break;
                }
                case S:
                case DOWN: {
                    downPressed = true;
                    break;
                }
                case D:
                case RIGHT: {
                    rightPressed = true;
                    break;
                }
                case DIGIT1: {
                    player.getSecondaryBtnHandler().addType(ProjectileType.EYEBALL, true);
                    break;
                }
                case DIGIT2: {
                    player.getSecondaryBtnHandler().addType(ProjectileType.FIREBALL, true);
                    break;
                }
                case DIGIT3: {
                    player.getSecondaryBtnHandler().addType(ProjectileType.FLAMEBALL, true);
                    player.getSecondaryBtnHandler().setPowerUp(PowerUp.MULT, 3);
                    break;
                }
                case DIGIT4: {
                    player.getSecondaryBtnHandler().addType(ProjectileType.SHOCK, true);
                    player.getSecondaryBtnHandler().setPowerUp(PowerUp.MULT, 4);
                    break;
                }
                case DIGIT5: {
                    player.getSecondaryBtnHandler().addType(ProjectileType.ICEICLE, true);
                    break;
                }
                case DIGIT6: {
                    player.getSecondaryBtnHandler().addType(ProjectileType.ICEICLE, true);
                    player.getSecondaryBtnHandler().setPowerUp(PowerUp.MULT, 5);
                    break;
                }
                case TAB: {
                    player.getSecondaryBtnHandler().addType(ProjectileType.WHIRLWIND, true);
                    player.getSecondaryBtnHandler().setPowerUp(PowerUp.MULT, 3);
                    break;
                }
                case CAPS: {
                    player.getSecondaryBtnHandler().addType(ProjectileType.ELECTRIC, true);
                    player.getSecondaryBtnHandler().setPowerUp(PowerUp.MULT, 3);
                    break;
                }
                case DIGIT7: {
                    player.getPrimaryBtnHandler().addType(ProjectileType.GREENLASER01, false);
                    break;
                }
                case DIGIT8: {
                    player.getPrimaryBtnHandler().addType(ProjectileType.REDLASER02, false);
                    break;
                }
                case DIGIT9: {
                    player.getPrimaryBtnHandler().addType(ProjectileType.GREENLASER03, false);
                    break;
                }
                case R: {
                    player.getSecondaryBtnHandler().addType(ProjectileType.CAT, true);
                    break;
                }
                case Q: {
                    player.getPrimaryBtnHandler().setToNextType(false);
                    break;
                }
                case E: {
                    player.getSecondaryBtnHandler().setToNextType(true);
                    break;
                }
                case SHIFT: {
                    player.getPrimaryBtnHandler().setPowerUp(PowerUp.SCALE, 3);
                    player.getPrimaryBtnHandler().setPowerUp(PowerUp.MULT, 4);
                    player.getPrimaryBtnHandler().setRange(700);
                    break;
                }
                case SPACE: {
                    player.getSecondaryBtnHandler().setRange(500);
                    player.getSecondaryBtnHandler().setPowerUp(PowerUp.MULT, 3);
                    break;
                }

            }

        });
        gameScene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case W:
                case UP: {
                    upPressed = false;
                    break;
                }
                case A:
                case LEFT: {
                    leftPressed = false;
                    break;
                }
                case S:
                case DOWN: {
                    downPressed = false;
                    break;
                }
                case D:
                case RIGHT: {
                    rightPressed = false;
                    break;
                }
            }
        });
    }


    private void initializeStage() {
        gamePane = new AnchorPane();
        gameScene = new Scene(gamePane, WIDTH, HEIGHT);
        gameStage = new Stage();
        gameStage.setScene(gameScene);

        createBackground();
        setCrosshair(gamePane);

        Dimension resolution = Toolkit.getDefaultToolkit().getScreenSize();
        double width = resolution.getWidth();
        double height = resolution.getHeight();
        double w = width / WIDTH;  //your window width
        double h = height / HEIGHT;  //your window height
        Scale scale = new Scale(w, h, 0, 0);
        gamePane.getTransforms().add(scale);
    }

    private void createBackground() {
        Image backgroundImage = new Image("file:resources/sprites/tiles/floor2.png");
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        gamePane.setBackground(new Background(background));
    }

    public void createNewGame(Stage menuStage, PLAYERS chosenPlayer) {
        this.menuStage = menuStage;
        this.menuStage.hide();
        gameStage.show();
        gameStage.setFullScreen(true);

        createUI();
        createPlayer(chosenPlayer);
        setMouseListeners();
        initializeBuildings();
        gameLoop();


    }

    private void createPlayer(PLAYERS chosenPlayer) {
        player = new Player(chosenPlayer, GVUI.getHealthBars().getHPRectangle(), GVUI.getHealthBars().getShieldRectangle());
        gamePane.getChildren().add(player);
        player.toFront();
    }

    private void createUI() {
        gamePane.getChildren().addAll(GVUI.getGroup(), GVUI.getHealthBars());
    }

    private void initializeBuildings() {//todo initialize random buildings with gridpane
    }


    private void gameLoop() {
        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                timer += 0.016;

                createEnemies();
                createObstacles();

                player.control(upPressed, downPressed,
                        leftPressed, rightPressed,
                        mouseXPos, mouseYPos);

                Obstacle.update();

                enemyArrayList.forEach(enemy ->
                        enemy.update(player.getLayoutX(), player.getLayoutY()));

                checkCollision(); //todo: 7otaha in gameObjects ( player, enemies etc) or in projectiles

            }
        };
        gameTimer.start();
    }

    private void createEnemies() {
        if (timer / 4 > numberOfEnemies) {
            Enemy enemy = new normalTank(Math.random() > 0.5 ? TANK_SAND:TANK_DARK,  player);
            enemyArrayList.add(enemy);
            gamePane.getChildren().add(enemy);
            numberOfEnemies++;
        }
    }

    private void createObstacles() {//todo implement timer
        if (timer / 10 > numberOfObstacles) {
            gamePane.getChildren().add(createRandomRotator());
            numberOfObstacles++;
        }

    }

    private void setMouseListeners() {
        gamePane.addEventFilter(MouseEvent.ANY, this::getMouseLocation);
    }

    private void getMouseLocation(MouseEvent e) {
        mouseXPos = e.getX();
        mouseYPos = e.getY();
    }

    private void checkCollision() {//todo: enqueue & dequeue
        //todo: move collisions to a listener inside sprite classes

        ArrayList<Projectile> projArrRemove = new ArrayList<>();
        ArrayList<Enemy> enemyArrRemove = new ArrayList<>();

        for (Projectile p : player.getProjArr()) {
            for (Enemy enemy : enemyArrayList) {
                if (p.isIntersects(enemy)) {
                    //3ashan my3mlsh collisions abl ma yetshal
                    p.setLayoutY(-500);

                    enemy.setHp_current(enemy.getCurrentHp() - p.getDamage());

                    projArrRemove.add(p);

                    if (enemy.getCurrentHp() <= 0) {
                        enemyArrRemove.add(enemy);
                        enemy.setDead(true);
                        //todo: add points
                        //todo: respawn
                    }
                }
            }
        }

        gamePane.getChildren().removeAll(projArrRemove);//todo: this is stupid
        gamePane.getChildren().removeAll(enemyArrRemove);//todo: this is stupid
        enemyArrayList.removeAll(enemyArrRemove);
    }


    private void setCrosshair(Pane pane) {
        Image image = new Image("file:resources/sprites/crosshair/4.png");
        pane.setCursor(new ImageCursor(image,
                image.getWidth() / 2,
                image.getHeight() / 2));
    }

}
