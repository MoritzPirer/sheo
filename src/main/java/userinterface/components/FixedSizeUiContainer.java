package userinterface.components;

import userinterface.Padding;

public interface FixedSizeUiContainer extends UiComponent {

    /**
     * Adds the given component to the end of the contained elements
     * @param uiComponent the component to add
     * @throws IllegalStateException if the container does not have room for the additional element
     */
    void addChild(UiComponent uiComponent);

    /**
     * Adds the given components to the end of the contained elements
     * @param uiComponents the components to add
     * @throws IllegalStateException if the container does not have room for all
     * elements (in that case, NO elements are added)
     */
    default void addChildren(UiComponent... uiComponents) {
        if (size() + uiComponents.length > capacity()) {
            throw new IllegalArgumentException("insufficient capacity");
        }

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

    /**
     * @return how many elements the component has room for
     */
    int capacity();

    /**
     * @return how many elements are in the component
     */
    int size();


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
