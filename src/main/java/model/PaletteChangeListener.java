package model;

import java.util.List;
import java.awt.*;

public interface PaletteChangeListener {
    //TODO: maybe divide into different events
    void onChange(List<Color> before, List<Color> after);
}
