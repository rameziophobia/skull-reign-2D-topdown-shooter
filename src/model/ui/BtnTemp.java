package model.ui;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import view.Main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class BtnTemp extends Button {

    private final static String FONT_PATH = Main.PATH_RESOURCES + "fonts/kenvector_future_thin.ttf";
    private final static String BUTTON_PRESSED_STYLE = "-fx-background-color: transparent; -fx-background-image: url('file:resources/sprites/ui/menu/buttonLong_blue_pressed.png') ";
    private final static String BUTTON_FREE_STYLE = "-fx-background-color: transparent; -fx-background-image: url('file:resources/sprites/ui/menu/buttonLong_blue.png')";

    public BtnTemp(String text)
    {
        setText(text);
        setButtonFont();
        setPrefWidth(190);
        setPrefHeight(49);
        setStyle(BUTTON_FREE_STYLE);
        initializeBtnListeners();
    }

    private void setButtonFont()
    {
        try {
            setFont(Font.loadFont(new FileInputStream(FONT_PATH), 23));
        } catch (FileNotFoundException e){
            setFont(Font.font("Verdana", 23));
        }
    }

    private void setButtonPressedStyle()
    {
        setStyle(BUTTON_PRESSED_STYLE);
        setPrefHeight(4);
        setLayoutY(getLayoutY() + 4);
    }
    private void setButtonFreeStyle()
    {
        setStyle(BUTTON_FREE_STYLE);
        setPrefHeight(49);
        setLayoutY(getLayoutY() - 4);
    }
    private void initializeBtnListeners()
    {
        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton().equals(MouseButton.PRIMARY))
                    setButtonPressedStyle();
            }
        });
        setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton().equals(MouseButton.PRIMARY))
                    setButtonFreeStyle();
            }
        });
        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setEffect(new DropShadow());
            }
        });
        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setEffect(null);
            }
        });


    }
}
