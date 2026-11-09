package model.colorname;

import java.awt.*;

public interface ColorNameProvider {
    /**
     * Return a name to describe the given color.
     * @param color the color to describe
     * @return the most fitting name for the color, or an empty string if no name was found
     */
    String get(Color color);
}
