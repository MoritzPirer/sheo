package model.colorname;

import javafx.util.Pair;

import java.awt.*;
import java.util.List;

public interface ColorNameProvider {
    /**
     * Return a name to describe the given color.
     * @param color the color to describe
     * @return the most fitting name for the color, or an empty string if no name was found
     */
    String get(Color color);

    List<Pair<Color, String>> getNearest(Color color, int count);
}
