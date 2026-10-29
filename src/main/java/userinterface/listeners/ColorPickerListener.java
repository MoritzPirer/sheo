package userinterface.listeners;

import java.awt.*;

public interface ColorPickerListener {

    /**
     * called whenever the current value of the color picker changes, either by the user or by a different
     * part of the code setting its value
     * @param before the value before the change
     * @param after the value after the change
     */
    void onChange(Color before, Color after); //TODO: pass the relevant listener as an argument to disambiguate?
}
