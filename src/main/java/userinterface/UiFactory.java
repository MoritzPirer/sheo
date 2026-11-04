package userinterface;

import userinterface.components.*;

/**
 * Defines an Abstract Factory to create Ui components that are compatible with each other. Ui Components will guarantee
 * that they work with components created by the same factory.
 */
public interface UiFactory {

    /**
     * @param title the title of the window
     */
    UiApplication createUiApplication(String title);

    /**
     * @param uiContainer the root element of the scene
     */
    Scene createScene(UiContainer uiContainer);

    Scene createScene(SectionContainer sectionContainer);

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

    SectionContainer createSectionContainer();

    TileContainer createTileContainer();

    UiSlot createUiSlot();

    /**
     * creates a color picker that allows the user to select the color from a 2d gradient
     */
    ColorPicker createCanvasColorPicker();

    ColorPicker createRgbColorPicker();

    ColorPicker createHsvColorPicker();

    ColorPicker createHslColorPicker();

    ColorPicker createTextBoxColorPicker();

    /**
     * Creates a Dropdown selection of values
     * @param options the initial options of the selection
     */
    DropdownSelection createDropdownSelection(String... options);

}
