package userInterface.JavaFxInterface;

import userInterface.components.UiComponent;

interface JavaFxComponent extends UiComponent {
    javafx.scene.Parent getJavaFxNode();
}
