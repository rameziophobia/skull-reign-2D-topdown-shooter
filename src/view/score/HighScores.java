package view.score;

public class HighScores {
    private String name;
    private int score;

    public HighScores(String Data) {
        final String[] raw = Data.split(":");
        score = Integer.valueOf(raw[0]);
        name = raw.length == 1 ? "No Name" : raw[1];
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
}