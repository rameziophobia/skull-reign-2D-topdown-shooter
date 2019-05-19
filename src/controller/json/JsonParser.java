package controller.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.json.level.EnemyData;
import controller.json.level.LevelJson;
import controller.json.settings.AudioSettings;
import controller.map.Map;
import model.enemies.Boss;
import model.enemies.Enemy;
import model.enemies.EnemyType;
import model.enemies.ProjectileControlType;
import model.level.Level;
import model.projectiles.ProjectileType;
import view.Main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class JsonParser {
    private static final String PATH_SETTINGS = Main.PATH_RESOURCES.substring(5) + "settings/";
    private static final Path FILE_SETTINGS_AUDIO = Paths.get(PATH_SETTINGS + "audioSettings.json");

    private static final String PATH_LEVELS = Main.PATH_RESOURCES.substring(5) + "levels/";
    private static final Path FILE_LEVELS = Paths.get(PATH_LEVELS + "Levels.json");
    private static final Path FILE_PROJECTILE_TYPE = Paths.get(PATH_LEVELS + "ProjectileType.json");
    private static final Path FILE_ENEMY_TYPE = Paths.get(PATH_LEVELS + "EnemyType.json");
    private static final Path FILE_MOVE_MODE = Paths.get(PATH_LEVELS + "MoveMode.json");
    private static final Path FILE_PROJECTILE_CONTROL_TYPE = Paths.get(PATH_LEVELS + "ProjectileControlType.json");

    private static Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
            .setPrettyPrinting()
            .serializeNulls()
            .create();

    private JsonParser() {
    }

    private static String readJson(Path fileLevels) {
        String fileData = null;
        try {
            byte[] bytes = Files.readAllBytes(fileLevels);
            fileData = new String(bytes);
        } catch (NoSuchFileException e) {
            System.out.println("Json Parser " + fileLevels + " not found");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileData;
    }

    public static AudioSettings readAudioSettings() {
        return gson.fromJson(readJson(FILE_SETTINGS_AUDIO), AudioSettings.class);
    }

    public static Level[] readLevels() {
        final LevelJson[] levelJsons = gson.fromJson(readJson(FILE_LEVELS), LevelJson[].class);
        final Level[] levels = new Level[levelJsons.length];
        for (int i = 0; i < levelJsons.length; i++) {
            final LevelJson levelJson = levelJsons[i];
            levels[i] = new Level(
                    getEnemies(levelJson.getWaves()),
                    levelJson.getTimeBetweenWaves(),
                    new Map("Level_" + i),
                    levelJson.getTimeBetweenSpawns(),
                    levelJson.getNumberOfTornados(),
                    levelJson.getTimeBetweenTornado(),
                    levelJson.getNumberOfPowerups(),
                    levelJson.getTimeBetweenPowerups()
            );
        }
        return levels;
    }

    private static Enemy[][] getEnemies(List<List<EnemyData>> waves) {
        Enemy[][] enemies = new Enemy[waves.size()][];
        for (int i = 0; i < waves.size(); i++) {
            final List<EnemyData> wave = waves.get(i);
            enemies[i] = new Enemy[wave.size()];
            for (int j = 0; j < wave.size(); j++) {
                final EnemyData enemyData = wave.get(j);
                if (enemyData.getEnemyType() == EnemyType.MAGE1) {
                    enemies[i][j] = new Boss(Boss.EnemyStageEnum.STAGE3);
                } else {
                    enemies[i][j] = new Enemy(enemyData.getEnemyType(),
                            enemyData.getProjectileType(),
                            enemyData.getProjectileControlType(),
                            enemyData.getMoveMode(),
                            150);

                }
            }
        }
        return enemies;
    }

    private static boolean writeJson(Path fileSettingsAudio, String s) {
        try {
            Files.write(fileSettingsAudio, s.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean writeAudioSettings(AudioSettings audioSettings) {
        return writeJson(FILE_SETTINGS_AUDIO, gson.toJson(audioSettings));
    }

    public static boolean writeEnemyEnum() {
        return writeJson(FILE_ENEMY_TYPE, gson.toJson(EnemyType.values())) &&
                writeJson(FILE_MOVE_MODE, gson.toJson(Enemy.MoveMode.values())) &&
                writeJson(FILE_PROJECTILE_CONTROL_TYPE, gson.toJson(ProjectileControlType.values())) &&
                writeJson(FILE_PROJECTILE_TYPE, gson.toJson(ProjectileType.values()));
    }
}
