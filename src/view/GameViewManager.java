package view;

import javafx.animation.AnimationTimer;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import model.Enemies.Enemy;
import model.Enemies.normalTank;
import model.player.PLAYERS;
import model.player.Player;
import model.projectiles.PowerUp;
import model.projectiles.Projectile;
import model.projectiles.ProjectileHandler;
import model.projectiles.ProjectileType;

import java.awt.*;
import java.util.ArrayList;

import static java.lang.Math.*;
import static model.Enemies.EnemyType.TANK_SAND;
import static model.obstacles.Obstacle.createRandomRotator;


public class GameViewManager {
    public static final int HEIGHT = 768;
    public static final int WIDTH = 1200;
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
    private double angle;
    private ArrayList<Projectile> projArr;
    private ArrayList<Enemy> enemyArrayList;
    private GridPane buildings;
    private int numberOfObstacles = 0;
    private int numberOfEnemies = 0;
    private double timer;
    private ProjectileHandler projectileHandler;

    public GameViewManager() {
        initializeStage();
        createKeyListeners();
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
        double h = height / HEIGHT;  //your window hight
        Scale scale = new Scale(w, h, 0, 0);
        gamePane.getTransforms().add(scale);

    }


    private void createBackground() {
        Image backgroundImage = new Image("file:src/view/resources/floor2.png");
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        gamePane.setBackground(new Background(background));
    }

    public void createNewGame(Stage menuStage, PLAYERS chosenPlayer) {
        this.menuStage = menuStage;
        this.menuStage.hide();
        gameStage.show();
        gameStage.setFullScreen(true);

        createUI();
        createPlayer(chosenPlayer);
        createEnemy();
        setMouseListeners();
        gameLoop();
        initializeBuildings();

        projArr = new ArrayList<>();
        projectileHandler = new ProjectileHandler(ProjectileType.BULLET,
                ProjectileType.FIRE,player, projArr);
    }

    private void createPlayer(PLAYERS chosenPlayer) {
        player = new Player(chosenPlayer);
        gamePane.getChildren().add(player);
    }

    private void createUI() {
        gamePane.getChildren().add(new GameViewUI().getGroup());
    }

    private void followPlayer() {
        for (Enemy enemy : enemyArrayList) {
            enemy.updateDirection(player.getLayoutX(), player.getLayoutY());
            enemy.move();
        }
    }

    private void createEnemy() {
        enemyArrayList = new ArrayList<>();
        Enemy sandTank = new normalTank(TANK_SAND, player.getLayoutX(), player.getLayoutY());
        enemyArrayList.add(sandTank);
        gamePane.getChildren().add(sandTank);
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

                player.setRotate(calculateRotation());
                player.move(upPressed, downPressed, leftPressed, rightPressed);
                player.warp();

                projectileHandler.moveProjectile();
                projectileHandler.fireProjectile(angle);
//                projectileHandler.setPowerUpPrimary(PowerUp.SCALE,3);
//                projectileHandler.setPowerUpSecondary(PowerUp.MULT,2);
//                projectileHandler.setPowerUpSecondary(PowerUp.SPEED,30);
                projectileHandler.fireProjectile();
                followPlayer();
                checkCollision();

            }
        };
        gameTimer.start();
    }

    private void createEnemies() {

        if (timer / 7 > numberOfEnemies) {
            Enemy enemy = new normalTank(TANK_SAND, player.getLayoutX(), player.getLayoutY());
            enemyArrayList.add(enemy);
            gamePane.getChildren().add(enemy);
            numberOfEnemies++;
        }

    }

    private void createObstacles() {//todo implement timer
        if (timer / 20 > numberOfObstacles) {
            gamePane.getChildren().add(createRandomRotator());
            numberOfObstacles++;
        }
//        System.out.println(timer);

    }

    private void setMouseListeners() {
        gamePane.addEventFilter(MouseEvent.ANY,this::setMouseLocation);
    }

    private void setMouseLocation(MouseEvent e) {
        mouseXPos = e.getX();
        mouseYPos = e.getY();
    }

    private double calculateRotation() {
        angle = Math.toDegrees(atan2(mouseYPos  - player.getLayoutY(), mouseXPos  - player.getLayoutX()));
        return angle;
    }

    private void checkCollision() {//todo: enqueue & dequeue
        //todo: move collisions to a listener inside sprite classes

        ArrayList<Projectile> projArrRemove = new ArrayList<>();
        ArrayList<Enemy> enemyArrRemove = new ArrayList<>();

        for (Projectile p : projArr) {
            for (Enemy enemy : enemyArrayList) {

                if (p.isIntersects(enemy)) {
                    //3ashan my3mlsh collisions abl ma yetshal
                    p.setLayoutY(-500);

                    enemy.setHp_current(enemy.getCurrentHp() - p.getDamage());

                    projArrRemove.add(p);

                    if (enemy.getCurrentHp() <= 0) {
                        enemyArrRemove.add(enemy);
                        //todo: add points
                        //todo: respawn
                    }
                }
            }
        }
        gamePane.getChildren().removeAll(projArrRemove);//todo: this is stupid
        gamePane.getChildren().removeAll(enemyArrRemove);//todo: this is stupid
        enemyArrayList.removeAll(enemyArrRemove);
        enemyArrayList.removeAll(projArrRemove);
    }


    private void setCrosshair(Pane pane) {
        Image image = new Image("file:src/view/resources/crosshair/4.png");
        pane.setCursor(new ImageCursor(image,
                image.getWidth() / 2,
                image.getHeight() / 2));
    }

//    public  double minDistance(double x1, double x2, double y1, double y2){
//        return Math.hypot(x2 - x1, y2 - y1);
//    }
}
