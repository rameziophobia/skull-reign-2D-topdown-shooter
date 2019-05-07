package view;

public class HighScores {
    private String name;
    private int score;
    private String[] raw;

    public HighScores(String Data) {
        raw = Data.split(":");
        score = Integer.valueOf(raw[0]);
        name = raw[1];
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
}