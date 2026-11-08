package userinterface.javafxinterface;

import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import shared.colorconversion.ColorConverter;

public class HslSliderManager extends SliderManager {
    private final Slider hueSlider;
    private final Slider saturationSlider;
    private final Slider lightnessSlider;
    private Color color = new Color(0, 0, 0, 1);

    private JavaFxSliderColorPicker context;
    private boolean isUpdating = false;

    public HslSliderManager() {
        hueSlider = createSlider(359,
            "#FF0000", "#FFFF00", "#00FF00", "#00FFFF", "#0000FF", "#FF00FF", "#FF0000");
        saturationSlider = createSlider(1, "#000000", "#000000");
        lightnessSlider = createSlider(1, "#000000", "#FFFFFF");
        updateSliderGradients();
    }

    @Override
    void initialize(Pane parent, JavaFxSliderColorPicker context) {
        parent.getChildren().addAll(hueSlider,  saturationSlider, lightnessSlider);
        this.context = context;
    }

    /**
     * update the saturation and lightness sliders' gradients to show what colors can be reached by moving them
     */
    private void updateSliderGradients() {
        double hue = hueSlider.getValue();
        double saturation = saturationSlider.getValue();
        double lightness = lightnessSlider.getValue();

        // need to do three steps because gradients can go e.g. black -> red -> white
        Color saturationMinimum = fromHsl(hue, 0, lightness);
        Color saturationMedium = fromHsl(hue, 0.5, lightness);
        Color saturationMaximum = fromHsl(hue, 1, lightness);
        setSliderGradient(saturationSlider, saturationMinimum, saturationMedium, saturationMaximum);

        Color lightnessMinimum = fromHsl(hue, saturation, 0);
        Color lightnessMedium = fromHsl(hue, saturation, 0.5);
        Color lightnessMaximum = fromHsl(hue, saturation, 1);
        setSliderGradient(lightnessSlider, lightnessMinimum, lightnessMedium, lightnessMaximum);
    }

    @Override
    protected void processSliderMove() {
        if (isUpdating) {
            return;
        }

        double hue = hueSlider.getValue();
        double saturation = saturationSlider.getValue();
        double lightness = this.lightnessSlider.getValue();

        Color before = color;
        color = fromHsl(hue, saturation, lightness);

        if (!before.equals(color)) {
            context.processColorChange(before, color);
        }
        updateSliderGradients();
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

        double[] hsl = toHsl(color);
        hueSlider.setValue(hsl[0]);
        saturationSlider.setValue(hsl[1]);
        lightnessSlider.setValue(hsl[2]);

        isUpdating = false;
    }

    Color fromHsl(double hue, double saturation, double lightness) {
        return JavaFxConverter.toJavaFxColor(ColorConverter.hslToRgb(hue, saturation, lightness));
    }

    double[] toHsl(Color color) {
        return ColorConverter.rgbToHsl(JavaFxConverter.toAwtColor(color));
    }
}
