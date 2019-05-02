package model.projectiles;

import view.Main;

public enum PowerUpType {
    MULT(Constants.PATH_RESOURCES_SPRITES_POWERUPS + "blue-40x40.png",1,1),
    SCALE(Constants.PATH_RESOURCES_SPRITES_POWERUPS + "red-40x40.png",1,2),
    SPEEDUP(Constants.PATH_RESOURCES_SPRITES_POWERUPS + "yellow-40x40.png",1.2f,1);



    private final String URL;
    private final float scale;
    private final float SPEED;


    PowerUpType(String URL, float SPEED,float scale){
        this.URL = URL;
        this.SPEED = SPEED;
        this.scale = scale;

    }
    public String getURL() {
        return URL;
    }
    public float getScale() {
        return scale;
    }
    public float getSPEED() {
        return SPEED;
    }
    private static class Constants {
        private static final String PATH_RESOURCES_SPRITES_POWERUPS = Main.PATH_RESOURCES_SPRITES + "powerups/";
    }
}
