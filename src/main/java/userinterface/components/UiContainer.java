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

    /**
     * Adds the given component at the given index in the containers list of child components
     * @param index where to insert the component. Must be 0 <= index <= # child components.
     * @param uiComponent the component to add
     */
    void addChild(int index, UiComponent uiComponent);

    default void addChildren(Collection<UiComponent> uiComponents) {
        for (UiComponent uiComponent : uiComponents) {
            addChild(uiComponent);
        }
    }

    /**
     * removes a specified component from the container. If the component is not part of this container,
     * this function has no effect.
     * @param uiComponent the component to remove
     */
    void removeChild(UiComponent uiComponent);

    List<UiComponent> getChildren();

    /**
     * Encourage child components to stretch perpendicular to the containers alignment axis to fill space
     * e.g. in a vertical container, encourage children to stretch horizontally.
     */
    void stretch();

    /**
     * Set padding to the inside of the container, i.e. enforce a minimum distance between content
     * and the container boundary. It is up to the implementation how this interacts with applied size constraints.
     */
    void setPadding(Padding padding);

    /**
     * Set the spacing between elements in this container.
     */
    void setSpacing(double spacing);
}
