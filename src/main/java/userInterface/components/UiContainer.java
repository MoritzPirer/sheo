package userInterface.components;

/**
 * An Ui Component that can contain multiple other Ui Components
 */
public interface UiContainer extends UiComponent {

    /**
     * Adds the given component to the end of the contained elements
     * @param uiComponent the component to add
     */
    void addChild(UiComponent uiComponent);

    /**
     * Adds the component being wrapped to the end of the contained elements
     * @param uiComponentWrapper the wrapper of the component to add
     */
    void addChild(UiComponentWrapper uiComponentWrapper);
}
