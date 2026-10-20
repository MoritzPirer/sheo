package userInterface.components;

import userInterface.ContentAlignment;
import userInterface.SizeConfiguration;

import java.awt.*;

/**
 * Anything that is part of the user interface
 */
public interface UiComponent {
    /**
     * Fill the background with the given color
     */
    UiComponent setBackground(Color color);

    UiComponent setSizeConfiguration(SizeConfiguration config);

    UiComponent setContentAlignment(ContentAlignment alignment);
}
