package view;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Enemies.Enemy;
import model.Enemies.normalTank;
import model.projectiles.PROJECTILE;
import model.projectiles.ProjectileMaker;
import model.player.PLAYER;

import java.util.ArrayList;

import static java.lang.Math.atan2;
import static model.Enemies.ENEMY_ENUM.TANK_SAND;
import static model.obstacles.Obstacle.createRandomRotator;


public class GameViewManager {
    public static final int HEIGHT = 768;
    public static final int WIDTH = 1200;
    private AnchorPane gamePane;
    private Scene gameScene;
    private Stage gameStage;
    private Stage menuStage;
    private ImageView playerImage;
    private double mouseXPos;
    private double mouseYPos;
    private double playerXPos;
    private double playerYPos;
    private boolean upPressed;
    private boolean downPressed;
    private boolean rightPressed;
    private boolean leftPressed;
    private AnimationTimer gameTimer;
    private final double SPEED = 4;
    private double angle;
    private ArrayList<ProjectileMaker> projArr;
    private ArrayList<Enemy> enemyArrayList;
    private GridPane buildings;
    private int numberOfObstacles = 0;
    private int numberOfEnemies = 0;
    private double score;

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

    }

    private void createPlayer(PLAYER chosenPlayer) {

        playerImage = new ImageView(new Image(chosenPlayer.getUrlPlayer()));
        playerXPos = WIDTH / 2 - playerImage.getFitWidth() / 2;
        playerYPos = HEIGHT / 2 - playerImage.getFitHeight() / 2;
        playerImage.setLayoutX(playerXPos);
        playerImage.setLayoutY(playerYPos);
        gamePane.getChildren().add(playerImage);
    }

    private void createBackground() {
        Image backgroundImage = new Image("file:src/view/resources/floor2.png");
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        gamePane.setBackground(new Background(background));
    }

    public void createNewGame(Stage menuStage, PLAYER chosenPlayer) {
        this.menuStage = menuStage;
        this.menuStage.hide();
        gameStage.show();
        createUI();
        createPlayer(chosenPlayer);
        createEnemy();
        trackMouse();
        gameLoop();
        fireProjectile();
        initializeBuildings();
    }

    private void createUI() {
        gamePane.getChildren().add(new GameViewUI().getGroup());
    }

    private void followPlayer() {
        for (Enemy enemy : enemyArrayList) {
            enemy.updateDirection(playerXPos,playerYPos);
            enemy.move();
        }
    }

    private void createEnemy() {
        enemyArrayList = new ArrayList<>();
        Enemy sandTank = new normalTank(TANK_SAND, playerXPos,playerYPos);
        enemyArrayList.add(sandTank);
        gamePane.getChildren().add(sandTank);
    }

    private void initializeBuildings() {//todo initialize random buildings with gridpane
    }

    private void trackMouse() {
        gamePane.setOnMouseMoved(e -> {
            mouseXPos = e.getX() - playerXPos;
            mouseYPos = e.getY() - playerYPos;
            double angle = calculateRotation();
            playerImage.setRotate(angle);
        });
    }

    private double calculateRotation() {
        angle = Math.toDegrees(atan2(mouseYPos, mouseXPos));
        return angle;
    }

    private void gameLoop() {
        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                score++;
                createEnemies();
                createObstacles();
                movePlayer();
                moveProjectile();
                followPlayer();
                checkCollision();
            }
        };
        gameTimer.start();
    }

    private void createEnemies(){
        if(score / 100 > numberOfEnemies){
            Enemy enemy = new normalTank(TANK_SAND, playerXPos,playerYPos);
            enemyArrayList.add(enemy);
            gamePane.getChildren().add(enemy);
            numberOfEnemies++;
        }

    }
    private void createObstacles() {//todo implement score
        if(score / 1000 > numberOfObstacles){
            gamePane.getChildren().add(createRandomRotator());
            numberOfObstacles++;
            System.out.println("here");
        }
        System.out.println(score);

    }

    private void moveProjectile() {
        if (projArr.size() > 0) {
            ArrayList<ProjectileMaker> projArrRemove = new ArrayList();
            ArrayList<ImageView> projArrImgRemove = new ArrayList();
            for (ProjectileMaker p : projArr) {
                p.move();
                //if the object crossed the boundary adds it to the remove list
                if (p.getLayoutY() > GameViewManager.HEIGHT ||
                        p.getLayoutY() < 0) {
                    projArrRemove.add(p);
                    projArrImgRemove.add(p);
                } else if (p.getLayoutX() > GameViewManager.WIDTH ||
                        p.getLayoutX() < 0) {
                    projArrRemove.add(p);
                    projArrImgRemove.add(p);
                }
            }
            gamePane.getChildren().removeAll(projArrImgRemove);
            projArr.removeAll(projArrRemove);
//            System.out.println(projArr.size());

        }
    }

    private void fireProjectile() {
        projArr = new ArrayList<>();
        gamePane.setOnMousePressed(e -> {
            if (e.isSecondaryButtonDown()) {
                projArr.add(new ProjectileMaker(playerXPos, playerYPos,
                        PROJECTILE.FIRE, angle));
                gamePane.getChildren().add(
                        projArr.get(projArr.size() - 1));
            } else {
                projArr.add(new ProjectileMaker(playerXPos, playerYPos,
                        PROJECTILE.BULLET, angle));
                gamePane.getChildren().add(
                        projArr.get(projArr.size() - 1));
            }

        });

    }

    private void movePlayer() { //todo can be coded more efficiently
        final double DIAGONAL_FACTOR = 1.5;

        if (upPressed) {
            if (rightPressed || leftPressed) {
                playerImage.setLayoutY(playerYPos - SPEED / DIAGONAL_FACTOR); // to avoid moving fast
                playerYPos -= SPEED / DIAGONAL_FACTOR;                        // diagonally
            } else {
                playerImage.setLayoutY(playerYPos - SPEED);
                playerYPos -= SPEED;
            }

        }
        if (downPressed) {
            if (rightPressed || leftPressed) {
                playerImage.setLayoutY(playerYPos + SPEED / DIAGONAL_FACTOR);
                playerYPos += SPEED / DIAGONAL_FACTOR;
            } else {
                playerImage.setLayoutY(playerYPos + SPEED);
                playerYPos += SPEED;
            }

        }
        if (rightPressed) {
            if (upPressed || downPressed) {
                playerImage.setLayoutX(playerXPos + SPEED / DIAGONAL_FACTOR);
                playerXPos += SPEED / DIAGONAL_FACTOR;
            } else {
                playerImage.setLayoutX(playerXPos + SPEED);
                playerXPos += SPEED;
            }
        }

        if (leftPressed) {
            if (upPressed || downPressed) {
                playerImage.setLayoutX(playerXPos - SPEED / DIAGONAL_FACTOR);
                playerXPos -= SPEED / DIAGONAL_FACTOR;
            } else {
                playerImage.setLayoutX(playerXPos - SPEED);
                playerXPos -= SPEED;
            }

        }
        //when the player leaves the screen he emerges from the other edge
        playerYPos = (playerYPos < 0) ? (playerYPos + HEIGHT) : (playerYPos % HEIGHT);
        playerXPos = (playerXPos < 0) ? (playerXPos + WIDTH) : (playerXPos % WIDTH);

    }

    private void checkCollision(){//todo: enqueue & dequeue

        ArrayList<ProjectileMaker> projArrRemove = new ArrayList<>();
        ArrayList<ImageView> arrImgRemove = new ArrayList<>();
        ArrayList<Enemy> enemyArrRemove = new ArrayList<>();

        for(ProjectileMaker p: projArr){
            for(Enemy enemy: enemyArrayList){

                final int hitRadius = p.getProj().getHitRadius();


                if((hitRadius + enemy.getHitRadius()) >
                distance(p.getLayoutX() + hitRadius, enemy.getLayoutX() + enemy.getHitRadius()
                , p.getLayoutY() + hitRadius, enemy.getLayoutY() + enemy.getHitRadius())){
                    //3ashan my3mlsh collisions abl ma yetshal
                    p.setLayoutY(-500);

                    enemy.setHp_current(enemy.getHp_current() - p.getProj().getDamage()) ;

                    projArrRemove.add(p);
                    arrImgRemove.add(p);

                    if(enemy.getHp_current() <= 0){
                        enemyArrRemove.add(enemy);
                        arrImgRemove.add(enemy);
                        //todo: add points
                        //todo: respawn
                    }
                }
            }
        }
        gamePane.getChildren().removeAll(arrImgRemove);
        enemyArrayList.removeAll(enemyArrRemove);
        enemyArrayList.removeAll(projArrRemove);
    }

    private void setCrosshair(Pane pane) {
        Image image = new Image("file:src/view/resources/crosshair/4.png");
        pane.setCursor(new ImageCursor(image,
                image.getWidth() / 2,
                image.getHeight() / 2));
    }

    public  double distance(double x1, double x2, double y1, double y2){
        return Math.hypot(x2 - x1, y2 - y1);
    }
}
