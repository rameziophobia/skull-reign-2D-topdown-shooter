package model.ui.mainmenu;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SmallLabel extends Label {

    private final static String FONT_PATH = "src/model/resources/kenvector_future_thin.ttf";

    public SmallLabel(String text) {
        setPadding(new Insets(10, 10, 10, 10));
        setText(text);
        setWrapText(true);
        setLabelFont();
    }

    private void setLabelFont() {
        try {
            setFont(Font.loadFont(new FileInputStream(FONT_PATH), 23));
        } catch (FileNotFoundException e) {
            setFont(Font.font("Verdana", 23));
        }
    }


}
