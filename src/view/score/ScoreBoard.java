package view.score;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;

public class ScoreBoard {
    private static final int MAX_DIGITS = 5;
    private static final String FILE_PATH = "resources/savedata";
    private static final String FILE_NAME = "highscores.dat";
    private ArrayList<String> data;
    //score data format --> SCORE:NAME
    private File scoreFile;

    public ScoreBoard() {
        scoreFile = new File(FILE_PATH + '/', FILE_NAME);
        final File dir = new File(FILE_PATH);
        if (!dir.exists()) {
            System.out.println(dir.mkdirs() + "the directory is created");
        }
        try {
            if (scoreFile.createNewFile()) {
                System.out.println("File created");
            } else {
                System.out.println("File exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        data = new ArrayList<>();
        loadScoreData();
    }

    private ObservableList<HighScores> createList() {
        ObservableList<HighScores> list = FXCollections.observableArrayList();
        for (String s : data) {
            list.add(new HighScores(s));
        }
        return list;
    }

    private void loadScoreData() {
        if (scoreFile.exists()) {
            FileReader fileReader;
            BufferedReader reader;
            try {
                fileReader = new FileReader(scoreFile);
                reader = new BufferedReader(fileReader);
                String line;
                while ((line = reader.readLine()) != null) {
                    data.add(line);
                }
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else
            System.out.println("file does not exist anymore");
    }

    private void addToData(String name, String score) {
        String entry = score + ":" + name;
        data.add(entry);
        writeToFile(data);
    }

    private void writeToFile(ArrayList<String> scores) {
        FileWriter writeFile;
        BufferedWriter writer = null;
        try {
            writeFile = new FileWriter(scoreFile);
            writer = new BufferedWriter(writeFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!scoreFile.exists()) {
            try {
                scoreFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        int i = 0;
        while (i < scores.size()) {
            String entry = scores.get(i);
            try {
                writer.write(entry);
                writer.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            i++;
        }
        if (writer != null)
            try {
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public void addNewScore(String Name, int highScore) {
        String scoreString = String.valueOf(highScore);
        if (scoreString.equals(""))
            scoreString = "No Name";

        while (scoreString.length() < MAX_DIGITS) {
            scoreString = '0' + scoreString;
        }
        addToData(Name, scoreString);
    }

    public ObservableList<HighScores> getHighScores() {
        return createList();
    }

}
