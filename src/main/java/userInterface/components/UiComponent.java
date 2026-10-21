package userInterface.components;

import userInterface.ContentAlignment;
import userInterface.Size;
import userInterface.listeners.HoverListener;

import java.awt.*;

/**
 * Anything that is part of the user interface
 */
public interface UiComponent {
    /**
     * Fill the background with the given color
     */
    void setBackground(Color color);

    /**
     * Updates the minimum size of the component. It is up to the implementation
     * how conflicting min / max preferred sizes are handled
     */
    void setMinimumSize(Size size);

    /**
     * Updates the maximum size of the component. It is up to the implementation
     * how conflicting min / max preferred sizes are handled
     */
    void setMaximumSize(Size size);

    /**
     * Updates the preferred size of the component. It is up to the implementation
     * how conflicting min / max preferred sizes are handled
     */
    void setPreferredSize(Size size);

    /**
     * Updates where the contents of the component (e.g. text, other components) are aligned within the component
     */
    void setContentAlignment(ContentAlignment alignment);

    void hide();
    void show();

    /**
     * register a new hoverListener to be notified if this component is being hovered over
     * @param hoverListener the listener to notify
     */
    void addHoverListener(HoverListener hoverListener);
}
