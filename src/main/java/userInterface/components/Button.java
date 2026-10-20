package userInterface.components;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface Button extends Label {
    /**
     * register a new object to be notified when the button is clicked
     */
    void addListener(ButtonListener listener);

    void setImage(BufferedImage image);
}
