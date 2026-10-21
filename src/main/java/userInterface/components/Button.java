package userInterface.components;

import userInterface.listeners.ButtonListener;

import java.awt.image.BufferedImage;

public interface Button extends Label {
    /**
     * register a new object to be notified when the button is clicked
     */
    void addButtonListener(ButtonListener listener);

    void setImage(BufferedImage image);
}
