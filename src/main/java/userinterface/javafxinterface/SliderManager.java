package userinterface.javafxinterface;

import javafx.scene.Node;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import shared.ColorUtility;

import java.util.Arrays;

abstract class SliderManager {
    abstract void initialize(Pane parent, JavaFxSliderColorPicker context);
    abstract Color get();
    abstract void set(Color color);

    protected Slider createSlider(int max, String... gradientStops) {
        Slider slider = new Slider(0, max, 0);
        slider.skinProperty().addListener((obs, oldSkin, newSkin) -> {
            setSliderGradient(slider, gradientStops);
            Node thumb = slider.lookup(".thumb");
            if (thumb instanceof Region region) {
                region.setPrefHeight(20);
                region.setPrefWidth(20);
                region.setStyle("-fx-background-radius: 26;" +
                    "-fx-background-color: transparent;" +
                    "-fx-border-color: #CBCBCB;" +
                    "-fx-border-width: 5px;" +
                    "-fx-border-radius: 50%;" +
                    "-fx-background-radius: 50%;"
                );
            }
        });
        slider.valueProperty().addListener(
                (obs, oldVal, newVal) -> processSliderMove()
        );

        return slider;
    }

    abstract void processSliderMove();

    protected void setSliderGradient(Slider slider, Color... colors) {
        String[] hexCodes = Arrays.stream(colors).map(c -> ColorUtility.colorToHex(JavaFxConverter.toAwtColor(c))).toArray(String[]::new);
        setSliderGradient(slider, hexCodes);
    }

    protected void setSliderGradient(Slider slider, String... colors) {
        slider.applyCss();

        Node track = slider.lookup(".track");
        if (track instanceof Region region) {
            region.setStyle(
                "-fx-background-color: linear-gradient(to right, " + String.join(", ", colors) + ");" +
                "-fx-border-radius: 10px;" +
                "-fx-background-radius: 10px;"
            );
        }
    }

    protected void setSliderToPercentage(Slider slider, double percentage) {
        double absolute = percentage * slider.getMax();
        slider.setValue(absolute);
    }

    protected double readSliderAsPercentage(Slider slider) {
        return slider.getValue() / slider.getMax();
    }
}
