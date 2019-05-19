package controller;

import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import model.player.Player;

public class InputManager {
    private static Player player;

    private static double mouseYPos;
    private static double mouseXPos;

    private InputManager() {

    }

    public static void setPlayer(Player player) {
        InputManager.player = player;
    }

    public static double getMouseYPos() {
        return mouseYPos;
    }

    public static double getMouseXPos() {
        return mouseXPos;
    }

    public static void setKeyListener(Scene gameScene) {
        gameScene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case W:
                case UP: {
                    player.setUpPressed(true);
                    break;
                }
                case A:
                case LEFT: {
                    player.setLeftPressed(true);
                    break;
                }
                case S:
                case DOWN: {
                    player.setDownPressed(true);
                    break;
                }
                case D:
                case RIGHT: {
                    player.setRightPressed(true);
                    break;
                }
                case Q: {
                    player.getPrimaryBtnHandler().setToNextType();
                    break;
                }
                case E: {
                    player.getSecondaryBtnHandler().setToNextType();
                    break;
                }
                case X:
                    player.killPlayer();
                    break;
            }
        });

        addReleaseKeyListners(gameScene);
    }

    private static void addReleaseKeyListners(Scene gameScene) {
        gameScene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case W:
                case UP: {
                    player.setUpPressed(false);
                    break;
                }
                case A:
                case LEFT: {
                    player.setLeftPressed(false);
                    break;
                }
                case S:
                case DOWN: {
                    player.setDownPressed(false);
                    break;
                }
                case D:
                case RIGHT: {
                    player.setRightPressed(false);
                    break;
                }
            }
        });
    }

    public static void setMouseListeners(Pane gamePane) {
        gamePane.addEventFilter(MouseEvent.ANY, event -> {
            mouseXPos = event.getX();
            mouseYPos = event.getY();
        });
    }

}
