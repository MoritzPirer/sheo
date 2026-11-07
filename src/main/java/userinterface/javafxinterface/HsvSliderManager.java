package userinterface.javafxinterface;

import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import shared.colorconversion.ColorUtility;

public class HsvSliderManager extends SliderManager {
    private final Slider hueSlider;
    private final Slider saturationSlider;
    private final Slider valueSlider;
    private Color color = new Color(0, 0, 0, 1);

    private JavaFxSliderColorPicker context;
    private boolean isUpdating = false;

    public HsvSliderManager() {
        hueSlider = createSlider(359,
            "#FF0000", "#FFFF00", "#00FF00", "#00FFFF", "#0000FF", "#FF00FF", "#FF0000");
        saturationSlider = createSlider(1, "#000000", "#000000");
        valueSlider = createSlider(1, "#000000", "#FFFFFF");
        updateSliderGradients();
    }

    @Override
    void initialize(Pane parent, JavaFxSliderColorPicker context) {
        parent.getChildren().addAll(hueSlider,  saturationSlider, valueSlider);
        this.context = context;
    }

    /**
     * update the saturation and value sliders' gradients to show what colors can be reached by moving them
     */
    private void updateSliderGradients() {
        double[] currentHsv = toHsv(color);
        double hue = currentHsv[0];
        double saturation = currentHsv[1];
        double value = currentHsv[2];

        Color saturationMinimum = fromHsv(hue, 0, value);
        Color saturationMaximum = fromHsv(hue, 1, value);
        setSliderGradient(saturationSlider, saturationMinimum, saturationMaximum);

        Color valueMinimum = fromHsv(hue, saturation, 0);
        Color valueMaximum = fromHsv(hue, saturation, 1);
        setSliderGradient(valueSlider, valueMinimum, valueMaximum);
    }

    @Override
    protected void processSliderMove() {
        if (isUpdating) {
            return;
        }

        double hue = hueSlider.getValue();
        double saturation = saturationSlider.getValue();
        double value = valueSlider.getValue();

        Color before = color;
        color = fromHsv(hue, saturation, value);

        if (!before.equals(color)) {
            context.processColorChange(before, color);
            updateSliderGradients();
        }
    }

    @Override
    Color get() {
        return color;
    }

    @Override
    void set(Color color) {
        if (this.color.equals(color)) {
            return;
        }

        this.color = color;
        isUpdating = true;

        double[] hsv = toHsv(color);
        hueSlider.setValue(hsv[0]);
        saturationSlider.setValue(hsv[1]);
        valueSlider.setValue(hsv[2]);

        isUpdating = false;
    }

    Color fromHsv(double hue, double saturation, double value) {
        return JavaFxConverter.toJavaFxColor(ColorUtility.hsvToRgb(hue, saturation, value));
    }

    double[] toHsv(Color color) {
        return ColorUtility.rgbToHsv(JavaFxConverter.toAwtColor(color));
    }
}
