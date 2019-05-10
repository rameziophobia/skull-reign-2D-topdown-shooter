package controller.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.json.settings.AudioSettings;
import view.Main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JsonParser {//todo generic
    private static final String PATH_SETTINGS = Main.PATH_RESOURCES.substring(5) + "settings/";
    private static final Path FILE_SETTINGS_AUDIO = Paths.get(PATH_SETTINGS + "audioSettings.json");

    private static Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
            .setPrettyPrinting()
            .serializeNulls()
            .create();

    private JsonParser() {
    }

    public static AudioSettings readAudioSettings() {
        String fileData = null;
        try {
            byte[] bytes = Files.readAllBytes(FILE_SETTINGS_AUDIO);
            fileData = new String(bytes);
        }catch (NoSuchFileException e){
            System.out.println("Json Parser file not found");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return gson.fromJson(fileData, AudioSettings.class);
    }

    public static boolean writeAudioSettings(AudioSettings audioSettings) {
        try {
            Files.write(FILE_SETTINGS_AUDIO, gson.toJson(audioSettings).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return false; // todo use for notifying for errors in saving
        }
        return true;
    }
}
