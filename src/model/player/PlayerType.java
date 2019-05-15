package model.player;

import view.Main;

@Deprecated
public enum PlayerType {
    HITMAN(Constants.PATH_RESOURCES_SPRITES_PLAYER + "Hitman 1/hitman1_gun-49x43.png"),
    BLUE(Constants.PATH_RESOURCES_SPRITES_PLAYER + "Man Blue/manBlue_gun-49x43.png"),
    BROWN(Constants.PATH_RESOURCES_SPRITES_PLAYER + "Man Brown/manBrown_gun-49x43.png"),
    ROBOT(Constants.PATH_RESOURCES_SPRITES_PLAYER + "Robot 1/robot1_gun-49x43.png"),
    SOLDIER(Constants.PATH_RESOURCES_SPRITES_PLAYER + "Soldier 1/soldier1_gun-52x43.png");
    //todo: change assets

    private String URL;

    PlayerType(String urlPlayer) {
        this.URL = urlPlayer;
    }

    public String getURL() {
        return URL;
    }

    private static class Constants {
        private static final String PATH_RESOURCES_SPRITES_PLAYER = Main.PATH_RESOURCES_SPRITES + "player/";
    }

}
