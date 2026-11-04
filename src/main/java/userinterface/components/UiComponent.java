package userinterface.components;

import userinterface.ContentAlignment;
import userinterface.Size;
import userinterface.listeners.HoverListener;

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
     * Specify the component's height and width to be a percentage of its parent's height and width
     * @param heightPercentage what percentage of the parent's height the component's height should be
     * @param widthPercentage what percentage of the parent's width the component's width should be
     */
    void setPercentageSize(double heightPercentage, double widthPercentage);
    void setPercentageWidth(double percentageWidth);
    void setPercentageHeight(double percentageHeight);

    void fillHeight();
    void fillWidth();

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

    /**
     * Add a tool tip to this component that is shown when the user mouses over the component.
     * @param text the text to display on hover
     */
    void setToolTip(String text);
}
