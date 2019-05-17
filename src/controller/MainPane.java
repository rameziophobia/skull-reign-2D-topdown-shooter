package controller;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import model.GameObject;

public class MainPane extends Pane {

    private Pane backPane;
    private Pane gamePane;
    private Pane frontPane;
    private Pane uiPane;

    public MainPane() {
        backPane = new Pane();
        gamePane = new Pane();
        frontPane = new Pane();
        uiPane = new Pane();

        this.getChildren().addAll(backPane, gamePane, frontPane, uiPane);
    }

    public void addToBackPane(Node node) {
        backPane.getChildren().add(node);
    }

    public void removeFromBackPane(Node node) {
        backPane.getChildren().remove(node);
    }

    public void addToGamePane(GameObject gameObject) {
        gamePane.getChildren().add(gameObject);
    }

    public void removeFromGamePane(GameObject gameObject) {
        gamePane.getChildren().remove(gameObject);
    }

    public void addToFrontPane(Node node) {
        frontPane.getChildren().add(node);
    }

    public void removeFromFrontPane(Node node) {
        frontPane.getChildren().remove(node);
    }

    public void addToUIPane(Node node) {
        uiPane.getChildren().add(node);
    }

    public void removeFromUIPane(Node node) {
        uiPane.getChildren().remove(node);
    }

    public Pane getGamePane() {
        return gamePane;
    }
}
