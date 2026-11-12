package userinterface.components;

import userinterface.listeners.ButtonListener;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.time.Duration;

public interface Button extends Label {
    /**
     * register a new object to be notified when the button is clicked
     */
    void addButtonListener(ButtonListener listener);

    void setImage(BufferedImage image);

    void setTemporaryStyle(Color backgroundColor, Color textColor, String text, Duration duration);

    void enable();
    void disable();
}
