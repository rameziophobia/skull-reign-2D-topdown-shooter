package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;

public class ScoreBoard {
    private final int MAX_DIGITS = 5;
    private final String FILE_PATH = "resources/savedata/";
    private final String FILE_NAME = "highscores.dat";
    private ArrayList<String> data;
    private ObservableList<HighScores> sorted;
    //score data format --> SCORE:NAME
    private File scoreFile;

    public ScoreBoard() {
        scoreFile = new File(FILE_PATH,FILE_NAME);
            try {
                if (scoreFile.createNewFile()) {
                    System.out.println("File created");
                }
                else {
                    System.out.println("File exists");
                }
            } catch (Exception e) {
                System.out.println(e+": Line31");
            }
        data = new ArrayList<>();
        loadScoreData();
        sorted = createList();
    }
    private ObservableList<HighScores> createList(){
        ObservableList<HighScores> list = FXCollections.observableArrayList();
        for(String s: data){
            list.add(new HighScores(s));
        }
        return list;
    }
    private void loadScoreData() {
        FileReader fileReader;
        BufferedReader reader;
        try {
            fileReader = new FileReader(scoreFile);
            reader = new BufferedReader(fileReader);
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                data.add(line);
            }
            reader.close();
        } catch (Exception e) {
            System.out.println(e+": Line52");
        }
        System.out.println(data);
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
            System.out.println(e+": Line70");
        }
        if (!scoreFile.exists()) {
            try {
                scoreFile.createNewFile();
            } catch (IOException e) {
                System.out.println(e+": Line76");
            }
        }
        int i = 0;
        while (i < scores.size()) {
            String entry = scores.get(i);
            try {
                writer.write(entry);
                writer.newLine();

            } catch (Exception e) {
                System.out.println(e +": Line87");
            }
            i++;
        }
        if (writer != null)
            try {
                writer.close();
            } catch (Exception e) {
                System.out.println(e +": Line95");
            }
    }
    public void addNewScore(String Name,int highScore){
        String scoreString = String.valueOf(highScore);
        while(scoreString.length()<MAX_DIGITS){
            scoreString='0'+scoreString;
        }
        addToData(Name,scoreString);
        loadScoreData();
    }
    public ObservableList<HighScores> getHighScores(){

        return sorted;
    }
}
