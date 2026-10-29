package userinterface.javafxinterface;

import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

class RgbSliderManager extends SliderManager {

    private final Slider redSlider;
    private final Slider greenSlider;
    private final Slider blueSlider;
    private Color color = new Color(0, 0, 0, 1);

    private JavaFxSliderColorPicker context;
    private boolean isUpdating = false;

    public RgbSliderManager() {
        redSlider = createSlider(255, "#000000", "#FF0000");
        greenSlider = createSlider(255, "#000000", "#00FF00");
        blueSlider = createSlider(255,"#000000", "#0000FF");
    }

    @Override
    public void initialize(Pane parent, JavaFxSliderColorPicker context) {
        parent.getChildren().addAll(redSlider, greenSlider, blueSlider);
        this.context = context;
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

    @Override
    protected void processSliderMove() {
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
}
