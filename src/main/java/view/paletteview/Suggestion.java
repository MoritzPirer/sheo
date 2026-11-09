package view.paletteview;

import userinterface.components.UiContainer;

import java.awt.*;

public interface Suggestion {
    UiContainer getUiComponent();
    String getName();

    void onSelect();
    void onColorChange(Color newColor);
}
