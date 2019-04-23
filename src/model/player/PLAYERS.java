package model.player;

import javafx.geometry.Point2D;

public enum PLAYERS {
    HITMAN("file:src/model/player/resources/Hitman 1/hitman1_gun-49x43.png", 45, 30),
    BLUE("file:src/model/player/resources/Man Blue/manBlue_gun-49x43.png", 45, 30),
    BROWN("file:src/model/player/resources/Man Brown/manBrown_gun-49x43.png", 45, 30),
    ROBOT("file:src/model/player/resources/Robot 1/robot1_gun-49x43.png", 45, 30),
    SOLDIER("file:src/model/player/resources/Soldier 1/soldier1_gun-52x43.png", 45, 30);
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

}
