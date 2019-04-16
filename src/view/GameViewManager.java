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
    private static final int HEIGHT = 800;
    private static final int WIDTH = 1200;
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
    }

    private void trackMouse() {
        gamePane.setOnMouseMoved(e -> {
            mouseXPos = e.getX() - playerXPos;
            mouseYPos = e.getY() - playerYPos;
            double angle = calculateRotation();
//            System.out.print(playerXPos + " " + playerYPos +
//                    mouseXPos + " " + mouseYPos + " = " + angle);
//            System.out.println();
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
                moveProjectile(true);
            }
        };
        gameTimer.start();
    }

    private void moveProjectile(boolean b) {
        if(projArr.size() > 0)
        {
            for(ProjectileMaker p:projArr)
            {
                p.move();
            }
        }

    }

    private void fireProjectile() {
        projArr = new ArrayList<>();
        gamePane.setOnMousePressed(e ->{
            ProjectileMaker proj = new ProjectileMaker(playerXPos,playerYPos,
                    PROJECTILE.BULLET,angle);
            gamePane.getChildren().add(proj.getProjectileImage());
            projArr.add(proj);
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
                }else {
                    playerImage.setLayoutX(playerXPos - SPEED);
                    playerXPos -= SPEED;
                }

            }

    }

    private void setCrosshair(Pane pane) {
        Image image = new Image("file:src/view/resources/crosshair/4.png");
        pane.setCursor(new ImageCursor(image,
                image.getWidth() / 2,
                image.getHeight() / 2));
    }

}
