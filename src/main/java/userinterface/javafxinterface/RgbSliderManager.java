package userinterface.javafxinterface;

import javafx.scene.Node;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import shared.ColorUtility;

class RgbSliderManager implements SliderManager {

    private final Slider redSlider;
    private final Slider greenSlider;
    private final Slider blueSlider;
    private Color color = new Color(0, 0, 0, 1);

    private JavaFxSliderColorPicker context;
    private boolean isUpdating = false;

    public RgbSliderManager() {
        redSlider = createSlider("#FF0000");
        greenSlider = createSlider("#00FF00");
        blueSlider = createSlider("#0000FF");
    }

    @Override
    public void initialize(Pane parent, JavaFxSliderColorPicker context) {
        parent.getChildren().addAll(redSlider, greenSlider, blueSlider);
        this.context = context;
    }

    private Slider createSlider(String gradientEnd) {
        Slider slider = new Slider(0, 255, 0);
        slider.skinProperty().addListener((obs, oldSkin, newSkin) -> {
            Node track = slider.lookup(".track");
            if (track instanceof Region region) {
                region.setPrefHeight(10);
                region.setStyle(
                    "-fx-background-color: linear-gradient(to right, #000000, " + gradientEnd + ");" +
                    "-fx-border-radius: 10px;" +
                    "-fx-background-radius: 10px;"
                );
            }

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

    private void updateSliderGradients() {
        Color redMinimum = new Color(0, color.getGreen(), color.getBlue(), 1);
        Color redMaximum = new Color(1, color.getGreen(), color.getBlue(), 1);
        setSliderGradient(redSlider, redMinimum, redMaximum);

        Color greenMinimum = new Color(color.getRed(), 0, color.getBlue(), 1);
        Color greenMaximum = new Color(color.getRed(), 1, color.getBlue(), 1);
        setSliderGradient(greenSlider, greenMinimum, greenMaximum);

        Color blueMinimum = new Color(color.getRed(), color.getGreen(), 0, 1);
        Color blueMaximum = new Color(color.getRed(), color.getGreen(), 1, 1);
        setSliderGradient(blueSlider, blueMinimum, blueMaximum);
    }

    private void setSliderGradient(Slider slider, Color start, Color end) {
        String startHex = ColorUtility.colorToHex(JavaFxConverter.toAwtColor(start));
        String endHex = ColorUtility.colorToHex(JavaFxConverter.toAwtColor(end));

        slider.applyCss();

        Node track = slider.lookup(".track");
        if (track instanceof Region region) {
            region.setStyle(
                "-fx-background-color: linear-gradient(to right, " + startHex + ", " + endHex + ");" +
                "-fx-border-radius: 10px;" +
                "-fx-background-radius: 10px;"
            );
        }
    }

    private void processSliderMove() {
        if (isUpdating) {
            return;
        }
        double red = readSliderAsPercentage(redSlider);
        double green = readSliderAsPercentage(greenSlider);
        double blue = readSliderAsPercentage(blueSlider);

        Color before = color;
        color = new Color(red, green, blue, 1);

        if (!before.equals(color)) {
            context.processColorChange(before, color);
            updateSliderGradients();
        }
    }

    private double readSliderAsPercentage(Slider slider) {
        return slider.getValue() / slider.getMax();
    }

    @Override
    public Color get() {
        return color;
    }

    @Override
    public void set(Color color) {
        if (this.color.equals(color)) {
            return;
        }

        this.color = color;
        isUpdating = true;
        setSliderToPercentage(redSlider, color.getRed());
        setSliderToPercentage(greenSlider, color.getGreen());
        setSliderToPercentage(blueSlider, color.getBlue());
        updateSliderGradients();

        isUpdating = false;
    }

    private void setSliderToPercentage(Slider slider, double percentage) {
        double absolute = percentage * slider.getMax();
        slider.setValue(absolute);
    }
}
