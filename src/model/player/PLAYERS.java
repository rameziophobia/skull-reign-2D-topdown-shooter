package model.player;

import javafx.geometry.Point2D;
import view.Main;

public enum PLAYERS {
    HITMAN(Constants.PATH_RESOURCES_SPRITES_PLAYER + "Hitman 1/hitman1_gun-49x43.png", 45, 30),
    BLUE(Constants.PATH_RESOURCES_SPRITES_PLAYER + "Man Blue/manBlue_gun-49x43.png", 45, 30),
    BROWN(Constants.PATH_RESOURCES_SPRITES_PLAYER + "Man Brown/manBrown_gun-49x43.png", 45, 30),
    ROBOT(Constants.PATH_RESOURCES_SPRITES_PLAYER + "Robot 1/robot1_gun-49x43.png", 45, 30),
    SOLDIER(Constants.PATH_RESOURCES_SPRITES_PLAYER + "Soldier 1/soldier1_gun-52x43.png", 45, 30);
    //todo: change assets

    public String URL;
    public Point2D spawner;

    PLAYERS(String urlPlayer, double spawnX, double spawnY)
    {
        this.URL = urlPlayer;
        spawner = new Point2D(spawnX, spawnY);
    }

    public Point2D getSpawner() {
        return spawner;
    }

    private static class Constants{
        static final String PATH_RESOURCES_SPRITES_PLAYER = Main.PATH_RESOURCES_SPRITES + "player/";
    }

}
