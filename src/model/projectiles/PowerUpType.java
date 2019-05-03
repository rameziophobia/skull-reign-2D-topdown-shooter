package model.projectiles;

import view.Main;

public enum PowerUpType {
    MULT(Constants.PATH_RESOURCES_SPRITES_POWERUPS + "blue-40x40.png",1,1,700),
    SCALE(Constants.PATH_RESOURCES_SPRITES_POWERUPS + "red-40x40.png",1,4,400),
    SPEEDPROJECTILE(Constants.PATH_RESOURCES_SPRITES_POWERUPS + "yellow-40x40.png",1,1.3f,400),
    SPEEDUP(Constants.PATH_RESOURCES_SPRITES_POWERUPS + "yellow-40x40.png",1.2f   ,1,400);



    private final String URL;
    private float scale ;
    private float speed ;
    private final int range;


    PowerUpType(String URL, float speed, float scale,int range){
        this.URL = URL;
        this.speed = speed;
        this.scale = scale;
        this.range = range;

    }
    public String getURL() {
        return URL;
    }
    public float getScale() {
        return scale;
    }
    public float getSpeed() {
        return speed;
    }
    public int getRange() {
        return range;
    }
    private static class Constants {
        private static final String PATH_RESOURCES_SPRITES_POWERUPS = Main.PATH_RESOURCES_SPRITES + "powerups/";
    }
}
