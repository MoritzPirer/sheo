package userinterface.components;

import userinterface.Padding;

/**
 * Like an Ui Container, but can only hold a single child, e.g. to be able to swap out different views.
 */
public interface UiSlot extends UiComponent {

    /**
     * Sets the given component as the component contained by this Slot
     */
    void setChild(UiComponent uiComponent);

    /**
     * Removes the child, potentially changing the UiSlot's size
     */
    void removeChild();

    /**
     * @return the current child, or null if the Slot is currently empty
     */
    UiComponent getChild();

    /**
     * Set padding to the inside of the container, i.e. enforce a minimum distance between content
     * and the container boundary. It is up to the implementation how this interacts with applied size constraints.
     */
    void setPadding(Padding padding);
}
