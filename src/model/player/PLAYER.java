package model.player;

public enum PLAYER {
    HITMAN("file:src/model/player/resources/Hitman 1/hitman1_gun.png"),
    BLUE("file:src/model/player/resources/Man Blue/manBlue_gun.png"),
    BROWN("file:src/model/player/resources/Man Brown/manBrown_gun.png"),
    ROBOT("file:src/model/player/resources/Robot 1/robot1_gun.png"),
    SOLDIER("file:src/model/player/resources/Soldier 1/soldier1_gun.png");

    String urlPlayer;
    private PLAYER(String urlPlayer)
    {
        this.urlPlayer = urlPlayer;
    }

    public String getUrlPlayer() {
        return urlPlayer;
    }
}
