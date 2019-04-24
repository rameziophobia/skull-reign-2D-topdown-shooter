package view;

import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.player.Player;
import model.projectiles.PowerUpTypes;
import model.projectiles.ProjectileType;

public class InputManager {//todo temp static ?

    private static Player player;

    private static double mouseYPos;
    private static double mouseXPos;

    private InputManager(){

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
                    player.getSecondaryBtnHandler().addType(ProjectileType.EYEBALL,true);
                    break;
                }
                case DIGIT2: {
                    player.getSecondaryBtnHandler().addType(ProjectileType.FIREBALL,true);
                    break;
                }
                case DIGIT3: {
                    player.getSecondaryBtnHandler().addType(ProjectileType.FLAMEBALL,true);
                    player.getSecondaryBtnHandler().setPowerUp(PowerUpTypes.MULT, 3f);//todo Magic Number
                    break;
                }
                case DIGIT4: {
                    player.getSecondaryBtnHandler().addType(ProjectileType.SHOCK,true);
                    player.getSecondaryBtnHandler().setPowerUp(PowerUpTypes.MULT, 4f);//todo Magic Number
                    break;
                }
                case DIGIT5: {
                    player.getSecondaryBtnHandler().addType(ProjectileType.ICEICLE,true);
                    break;
                }
                case DIGIT6: {
                    player.getSecondaryBtnHandler().addType(ProjectileType.ICEICLE,true);
                    player.getSecondaryBtnHandler().setPowerUp(PowerUpTypes.MULT, 5f);//todo Magic Number
                    break;
                } case TAB: {
                    player.getSecondaryBtnHandler().addType(ProjectileType.WHIRLWIND,true);
                    player.getSecondaryBtnHandler().setPowerUp(PowerUpTypes.MULT, 3f);//todo Magic Number
                    break;
                } case CAPS: {
                    player.getSecondaryBtnHandler().addType(ProjectileType.ELECTRIC,true);
                    player.getSecondaryBtnHandler().setPowerUp(PowerUpTypes.MULT, 3f);//todo Magic Number
                    break;
                }
                case DIGIT7: {
                    player.getPrimaryBtnHandler().addType(ProjectileType.GREENLASER01,false);
                    break;
                }
                case DIGIT8: {
                    player.getPrimaryBtnHandler().addType(ProjectileType.REDLASER02,false);
                    break;
                }
                case DIGIT9: {
                    player.getPrimaryBtnHandler().addType(ProjectileType.GREENLASER03,false);
                    break;
                }case R: {
                    player.getSecondaryBtnHandler().addType(ProjectileType.CAT,true);
                    break;
                }case Q: {
                    player.getPrimaryBtnHandler().setToNextType(false);
                    break;
                }case E: {
                    player.getSecondaryBtnHandler().setToNextType(true);
                    break;
                }case SHIFT: {
                    player.getPrimaryBtnHandler().setPowerUp(PowerUpTypes.SCALE, 3f);//todo Magic Number
                    player.getPrimaryBtnHandler().setPowerUp(PowerUpTypes.MULT, 4f);//todo Magic Number
                    player.getPrimaryBtnHandler().setRange(700);//todo Magic Number
                    break;
                }case SPACE: {
                    player.getSecondaryBtnHandler().setRange(500);//todo Magic Number
                    player.getSecondaryBtnHandler().setPowerUp(PowerUpTypes.MULT, 3f);//todo Magic Number
                    break;
                }

            }

        });
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

    public static void setMouseListeners(AnchorPane gamePane) {
        gamePane.addEventFilter(MouseEvent.ANY, event -> {
            mouseXPos = event.getX();
            mouseYPos = event.getY();
        });
    }

}
