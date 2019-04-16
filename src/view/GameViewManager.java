package view;

import javafx.animation.AnimationTimer;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.projectiles.PROJECTILE;
import model.projectiles.ProjectileMaker;
import model.player.PLAYER;

import java.util.ArrayList;

import static java.lang.Math.atan2;


public class GameViewManager {
    private AnchorPane gamePane;
    private Scene gameScene;
    private Stage gameStage;
    public static final int HEIGHT = 768;
    public static final int WIDTH = 1200;
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
    private GridPane buildings;

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
        createPlayer(chosenPlayer);
        trackMouse();
        gameLoop();
        fireProjectile();
        initializeBuildings();
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
                movePlayer();
                moveProjectile();
            }
        };
        gameTimer.start();
    }

    private void moveProjectile() {
        if (projArr.size() > 0) {
            ArrayList<ProjectileMaker> projArrRemove = new ArrayList();
            ArrayList<ImageView> projArrImgRemove = new ArrayList();
            for (ProjectileMaker p : projArr) {
                p.move();
                //if the object crossed the boundary adds it to the remove list
                if (p.getProjectileImage().getLayoutY() > GameViewManager.HEIGHT ||
                        p.getProjectileImage().getLayoutY() < 0) {
                    projArrRemove.add(p);
                    projArrImgRemove.add(p.getProjectileImage());
                } else if (p.getProjectileImage().getLayoutX() > GameViewManager.WIDTH ||
                        p.getProjectileImage().getLayoutX() < 0) {
                    projArrRemove.add(p);
                    projArrImgRemove.add(p.getProjectileImage());
                }
            }
            gamePane.getChildren().removeAll(projArrImgRemove);
            projArr.removeAll(projArrRemove);
            System.out.println(projArr.size());

        }
    }

    private void fireProjectile() {
        projArr = new ArrayList<>();
        gamePane.setOnMousePressed(e -> {
            if(e.isSecondaryButtonDown()){
                projArr.add(new ProjectileMaker(playerXPos, playerYPos,
                        PROJECTILE.FIRE, angle));
                gamePane.getChildren().add(
                        projArr.get(projArr.size() - 1).getProjectileImage());
            }else
            {
                projArr.add(new ProjectileMaker(playerXPos, playerYPos,
                        PROJECTILE.BULLET, angle));
                gamePane.getChildren().add(
                        projArr.get(projArr.size() - 1).getProjectileImage());
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
        playerYPos = (playerYPos < 0) ? (playerYPos + HEIGHT): (playerYPos % HEIGHT);
        playerXPos = (playerXPos < 0) ? (playerXPos + WIDTH): (playerXPos % WIDTH);

    }

    private void setCrosshair(Pane pane) {
        Image image = new Image("file:src/view/resources/crosshair/4.png");
        pane.setCursor(new ImageCursor(image,
                image.getWidth() / 2,
                image.getHeight() / 2));
    }

}
