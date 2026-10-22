package userinterface;

import userinterface.components.*;

/**
 * Defines an Abstract Factory to create Ui components that are compatible with each other. Ui Components will guarantee
 * that they work with components created by the same factory.
 */
public interface UiFactory {
    /**
     * creates a button displaying the given text
     * @param text the text to display
     * @return a new button
     */
    Button createButton(String text);

    /**
     * creates a new label displaying the given text
     * @param text the text to display
     * @return a new label
     */
    Label createLabel(String text);

    /**
     * creates a new UiContainer in which components are arranged below each other
     */
    UiContainer createVerticalUiContainer();

    /**
     * creates a new UiContainer in which components are arranged next to each other
     */
    UiContainer createHorizontalUiContainer();

    /**
     * @param uiContainer the root element of the scene
     */
    Scene createScene(UiContainer uiContainer);

    /**
     * @param title the title of the window
     */
    UiApplication createUiApplication(String title);

}
