package userinterface.components;

import java.util.Collection;
import java.util.List;

/**
 * An Ui Component that can contain multiple other Ui Components
 */
public interface UiContainer extends UiComponent {

    /**
     * Adds the given component to the end of the contained elements
     * @param uiComponent the component to add
     */
    void addChild(UiComponent uiComponent);

    void addChild(int index, UiComponent uiComponent);

    default void addChildren(Collection<UiComponent> uiComponents) {
        for (UiComponent uiComponent : uiComponents) {
            addChild(uiComponent);
        }
    }

    void removeChild(UiContainer uiComponent);

    List<UiComponent> getChildren();

    /**
     * Adds the component being wrapped to the end of the contained elements
     * @param uiComponentWrapper the wrapper of the component to add
     */
    void addChild(UiComponentWrapper uiComponentWrapper);


    void stretch();

}
