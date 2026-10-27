package userinterface.components;

import userinterface.listeners.ColorPickerListener;

import java.awt.*;

public interface ColorPicker extends UiComponent {
    void setColor(Color color);
    Color getColor();

    void addListener(ColorPickerListener listener);
}
