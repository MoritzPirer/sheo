package userinterface.components;

import userinterface.listeners.ColorPickerListener;

import java.awt.*;

public interface ColorPicker extends UiComponent {
    /**
     * update the color currently selected. Alerts all listeners unless that color was already set
     * @param color the color to set to
     */
    void setColor(Color color);

    /**
     * @return the currently selected color
     */
    Color getColor();

    /**
     * add a new listener to be notified whenever the color picker's value is changed, either by the user or by
     * calling setColor()
     */
    void addListener(ColorPickerListener listener);
}
