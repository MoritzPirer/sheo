package userInterface.JavaFxInterface;

import userInterface.listeners.HoverListener;
import userInterface.components.UiComponent;

interface JavaFxComponent extends UiComponent {
    javafx.scene.Parent getJavaFxNode();

    @Override
    default void addHoverListener(HoverListener hoverListener) {
        getJavaFxNode().hoverProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                hoverListener.onHoverStart(this);
            }
            else {
                hoverListener.onHoverStop(this);
            }
        });
    }
}
