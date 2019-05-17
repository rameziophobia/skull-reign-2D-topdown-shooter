package controller.map;

import view.Main;

public enum Map {
    BASE("Map.png"),
    LEVEL_01("Level_01.png"),
    LEVEL_02("Level_02.png");

    private String path;

    Map(String path) {
        this.path = Constants.PATH_RESOURCES_MAPS + path;
    }

    public String getPath() {
        return path;
    }

    private static class Constants {
        private static final String PATH_RESOURCES_MAPS = Main.PATH_RESOURCES + "maps/";
    }
}
