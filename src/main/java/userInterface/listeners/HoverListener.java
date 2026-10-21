package userInterface.listeners;

import userInterface.components.UiComponent;

/**
 * HoverListeners can be registered to UiComponents to be notified when the mouse is hovering over that
 * component. HoverListeners may listen to multiple Components, the hovered component is passed as an argument.
 * If one listened component is within another, hovering the inner one also means hovering the outer one
 */
public interface HoverListener {

    /**
     * respond to the mouse starting to hover over the component
     * @param hovered the component being hovered over
     */
    void onHoverStart(UiComponent hovered);

    /**
     * respond to the mouse stopping to hover over the component
     * @param hovered the component no longer being hovered over
     */
    void onHoverStop(UiComponent hovered);
}
