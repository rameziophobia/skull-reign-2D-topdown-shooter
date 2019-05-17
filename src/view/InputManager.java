package view;

import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import model.player.Player;
import model.projectiles.PowerUpType;
import model.projectiles.ProjectileType;

public class InputManager {//todo temp static ?

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

    public static void setKeyListener(Scene gameScene) {//todo don't have scene as a parameter ?
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
                case DIGIT1: {
                    player.getSecondaryBtnHandler().addType(ProjectileType.EYEBALL);
                    break;
                }
                case DIGIT2: {
                    player.getSecondaryBtnHandler().addType(ProjectileType.FIREBALL);
                    break;
                }
                case DIGIT3: {
                    player.getSecondaryBtnHandler().addType(ProjectileType.FLAMEBALL);
                    player.getSecondaryBtnHandler().setPowerUp(PowerUpType.MULT, 3f);//todo Magic Number
                    break;
                }
                case DIGIT4: {
                    player.getSecondaryBtnHandler().addType(ProjectileType.SHOCK);
                    player.getSecondaryBtnHandler().setPowerUp(PowerUpType.MULT, 4f);//todo Magic Number
                    break;
                }
                case DIGIT5: {
                    player.getSecondaryBtnHandler().addType(ProjectileType.ICEICLE);
                    break;
                }
                case DIGIT6: {
                    player.getSecondaryBtnHandler().addType(ProjectileType.ICEICLE);
                    player.getSecondaryBtnHandler().setPowerUp(PowerUpType.MULT, 5f);//todo Magic Number
                    break;
                }
                case TAB: {
                    player.getSecondaryBtnHandler().addType(ProjectileType.WHIRLWIND);
                    player.getSecondaryBtnHandler().setPowerUp(PowerUpType.MULT, 3f);//todo Magic Number
                    break;
                }
                case CAPS: {
                    player.getSecondaryBtnHandler().addType(ProjectileType.ELECTRIC);
                    player.getSecondaryBtnHandler().setPowerUp(PowerUpType.MULT, 3f);//todo Magic Number
                    break;
                }
                case DIGIT7: {
                    player.getPrimaryBtnHandler().addType(ProjectileType.GREENLASER01);
                    break;
                }
                case DIGIT8: {
                    player.getPrimaryBtnHandler().addType(ProjectileType.REDLASER02);
                    break;
                }
                case DIGIT9: {
                    player.getPrimaryBtnHandler().addType(ProjectileType.GREENLASER03);
                    break;
                }
                case R: {
                    player.getSecondaryBtnHandler().addType(ProjectileType.CAT);
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
                case SHIFT: {
                    player.getPrimaryBtnHandler().setPowerUp(PowerUpType.SCALE, 3f);//todo Magic Number
                    player.getPrimaryBtnHandler().setPowerUp(PowerUpType.MULT, 4f);//todo Magic Number
                    player.getPrimaryBtnHandler().setRange(700);//todo Magic Number
                    break;
                }
                case SPACE: {
                    player.getSecondaryBtnHandler().setRange(500);//todo Magic Number
                    player.getSecondaryBtnHandler().setPowerUp(PowerUpType.MULT, 3f);//todo Magic Number
                    break;
                }
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
