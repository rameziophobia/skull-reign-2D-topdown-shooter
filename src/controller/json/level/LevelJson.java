package controller.json.level;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LevelJson {
    @SerializedName("Waves")
    @Expose
    private List<List<EnemyData>> waves = null;
    @SerializedName("TimeBetweenSpawns")
    @Expose
    private int timeBetweenSpawns;
    @SerializedName("TimeBetweenWaves")
    @Expose
    private int timeBetweenWaves;
    @SerializedName("NumberOfTornados")
    @Expose
    private int numberOfTornados;
    @SerializedName("TimeBetweenTornado")
    @Expose
    private int timeBetweenTornado;
    @SerializedName("NumberOfPowerups")
    @Expose
    private int numberOfPowerups;
    @SerializedName("TimeBetweenPowerups")
    @Expose
    private int timeBetweenPowerups;

    public List<List<EnemyData>> getWaves() {
        return waves;
    }

    public int getTimeBetweenSpawns() {
        return timeBetweenSpawns;
    }

    public void setTimeBetweenSpawns(int timeBetweenSpawns) {
        this.timeBetweenSpawns = timeBetweenSpawns;
    }

    public int getTimeBetweenWaves() {
        return timeBetweenWaves;
    }

    public void setTimeBetweenWaves(int timeBetweenWaves) {
        this.timeBetweenWaves = timeBetweenWaves;
    }

    public int getNumberOfTornados() {
        return numberOfTornados;
    }

    public void setNumberOfTornados(int numberOfTornados) {
        this.numberOfTornados = numberOfTornados;
    }

    public int getTimeBetweenTornado() {
        return timeBetweenTornado;
    }

    public void setTimeBetweenTornado(int timeBetweenTornado) {
        this.timeBetweenTornado = timeBetweenTornado;
    }

    public int getNumberOfPowerups() {
        return numberOfPowerups;
    }

    public void setNumberOfPowerups(int numberOfPowerups) {
        this.numberOfPowerups = numberOfPowerups;
    }

    public int getTimeBetweenPowerups() {
        return timeBetweenPowerups;
    }

    public void setTimeBetweenPowerups(int timeBetweenPowerups) {
        this.timeBetweenPowerups = timeBetweenPowerups;
    }
}
