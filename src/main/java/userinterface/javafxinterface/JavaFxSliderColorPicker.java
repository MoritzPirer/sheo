package userinterface.javafxinterface;

import javafx.geometry.Pos;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import userinterface.ContentAlignment;
import userinterface.components.ColorPicker;
import userinterface.listeners.ColorPickerListener;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

class JavaFxSliderColorPicker extends JavaFxComponent implements ColorPicker {
    private final List<ColorPickerListener> listeners = new ArrayList<>();

    private final VBox container = new VBox();
    private final SliderManager sliderManager;

    public JavaFxSliderColorPicker(SliderManager manager) {
        this.sliderManager = manager;
        this.sliderManager.initialize(container, this);
    }

    @Override
    public void setColor(Color color) {
        Color before = getColor();
        if (before.equals(color)) {
            return;
        }

        javafx.scene.paint.Color fxColor = JavaFxConverter.toJavaFxColor(color);
        sliderManager.set(fxColor);

        listeners.forEach(l -> l.onChange(this, before, color));
    }

    /**
     * called by the SliderManager when sliders are moved
     */
    public void processColorChange(javafx.scene.paint.Color before, javafx.scene.paint.Color after) {
        Color beforeAwt = JavaFxConverter.toAwtColor(before);
        Color afterAwt = JavaFxConverter.toAwtColor(after);

        listeners.forEach(l -> l.onChange(this, beforeAwt, afterAwt));
    }

    @Override
    public Color getColor() {
        javafx.scene.paint.Color fxColor = sliderManager.get();
        return JavaFxConverter.toAwtColor(fxColor);
    }

    @Override
    public void addListener(ColorPickerListener listener) {
        if (listener != null && !listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    @Override
    Region getJavaFxNode() {
        return container;
    }

    @Override
    public void setContentAlignment(ContentAlignment alignment) {
        Pos pos = JavaFxConverter.toPos(alignment);
        container.setAlignment(pos);
    }
}
