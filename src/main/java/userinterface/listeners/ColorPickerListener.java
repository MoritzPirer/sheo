package userinterface.listeners;

import userinterface.components.ColorPicker;

import java.awt.*;

public interface ColorPickerListener {

    /**
     * called whenever the current value of the color picker changes, either by the user or by a different
     * part of the code setting its value
     * @param source the ColorPicker that was changed
     * @param before the value before the change
     * @param after the value after the change
     */
    void onChange(ColorPicker source, Color before, Color after);
}
