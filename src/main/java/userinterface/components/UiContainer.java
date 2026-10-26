package userinterface.components;

import userinterface.Padding;

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

    void stretch();

    void setPadding(Padding padding);

    void setSpacing(double spacing);
}
