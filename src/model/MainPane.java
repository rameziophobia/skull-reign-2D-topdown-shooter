package model;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.Collection;

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
    public void addAllToBackPane(Node... node) {
        backPane.getChildren().addAll(node);
    }
    public void addAllToBackPane(Collection<? extends Node> node) {
        backPane.getChildren().addAll(node);
    }

    public void removeFromBackPane(Node node) {
        backPane.getChildren().remove(node);
    }
    public void removeAllFromBackPane(Node... node) {
        backPane.getChildren().removeAll(node);
    }
    public void removeAllFromBackPane(Collection<? extends Node> node) {
        backPane.getChildren().removeAll(node);
    }

    public void addToGamePane(GameObject gameObject) {
        Node[] children = gameObject.getChildren();
        if (children != null){
            gamePane.getChildren().addAll(children);//todo we don't care about polymorphism :(
            for (Node child : children) {
                child.toBack();
            }
        }
        gamePane.getChildren().add(gameObject);
        gameObject.toBack();
    }

    public void removeFromGamePane(GameObject gameObject) {
        gamePane.getChildren().remove(gameObject);
        Node[] children = gameObject.getChildren();
        if (children != null)
            gamePane.getChildren().removeAll(children);//todo we don't care about polymorphism :(
    }

    public void addToFrontPane(Node node) {
        frontPane.getChildren().add(node);
    }
    public void addAllToFrontPane(Node... node) {
        frontPane.getChildren().addAll(node);
    }
    public void addAllToFrontPane(Collection<? extends Node> node) {
        frontPane.getChildren().addAll(node);
    }

    public void removeFromFrontPane(Node node) {
        frontPane.getChildren().remove(node);
    }
    public void removeAllFromFrontPane(Node... node) {
        frontPane.getChildren().removeAll(node);
    }
    public void removeAllFromFrontPane(Collection<? extends Node> node) {
        frontPane.getChildren().removeAll(node);
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
