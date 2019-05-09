package model.ui.menu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class SettingsSlider extends HBox {
    private static final Insets PADDING = new Insets(10);

    private final Slider slider;

    public SettingsSlider(String optionText, double value) {
        setSpacing(10);
        setAlignment(Pos.CENTER_LEFT);
        setPadding(PADDING);

        Label lbl_option = new Label(optionText);
        lbl_option.setFont(Font.font(20));
        lbl_option.setTextFill(Color.WHITE);
        lbl_option.setPrefWidth(200);

        slider = new Slider(0, 100, value);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(50);
        slider.setMinorTickCount(5);
        slider.setBlockIncrement(10);

        slider.setPrefWidth(400);

        Label lbl_sliderHover = new Label();
        lbl_sliderHover.textProperty().bind(slider.valueProperty().asString("%.0f%%"));
        lbl_sliderHover.setTextFill(Color.WHITE);

        getChildren().addAll(
                lbl_option,
                slider,
                lbl_sliderHover
        );
    }

    public void setValue(double value){
        slider.setValue(value);
    }

    public double getValue(){
        return slider.getValue();
    }
}
