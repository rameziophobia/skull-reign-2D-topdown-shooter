package controller.map;

import javafx.scene.paint.Color;

import java.util.HashMap;

public enum  MapKey {
    FLAG("d00a0a"),
    PILLAR("300ad0"),
    SIDE_CORNER_LEFT("1f8ed5"),
    SIDE_CORNER_RIGHT("1b6fa4"),
    SKULL("93989a"),
    WALL("230b17"),
    WALL_FRONT("641d41"),
    WALL_ALONE("aa0c86"),
    WALL_BARS_BOTTOM("0a1d4d"),
    WALL_BARS_MIDDLE("0d2a72"),
    WALL_BROKEN_1("2b2416"),
    WALL_BROKEN_2("1c1508"),
    WALL_FRONT_END_LEFT("e3ae4a"),
    WALL_FRONT_END_RIGHT("c19644"),
    WALL_SIDE_LEFT("471893"),
    WALL_SIDE_RIGHT("5f20c5"),
    EMPTY("000000"),
    GROUND("422835"),
    WALL_END_LEFT("097309"),
    WALL_END_RIGHT("075407");

    private static final HashMap<Color, MapKey> COLOR_MAP_KEY_HASH_MAP;

    static {
        COLOR_MAP_KEY_HASH_MAP = new HashMap<>();
        for (MapKey mapKey: values()) {
            COLOR_MAP_KEY_HASH_MAP.put(mapKey.getColor(), mapKey);
        }
    }

    private Color color;

    MapKey(String colorHex) {
        this.color = Color.web("0x" + colorHex);
    }

    public Color getColor() {
        return color;
    }

    public static MapKey getMapKeyFrom(Color color){
        return COLOR_MAP_KEY_HASH_MAP.getOrDefault(color, null);
    }
}
