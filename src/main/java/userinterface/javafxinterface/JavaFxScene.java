package userinterface.javafxinterface;

import userinterface.components.Scene;
import userinterface.components.UiContainer;

class JavaFxScene extends Scene {
    private final javafx.scene.Scene scene;

    public JavaFxScene(UiContainer root) {
        super(root);
        if (root instanceof JavaFxContainer javaFxContainer) {
            scene = new javafx.scene.Scene(javaFxContainer.getJavaFxNode());
        }
        else {
            throw new IllegalArgumentException("Type mismatch");
        }
    }

    public javafx.scene.Scene getScene() {
        return scene;
    }

    @Override
    public String toString() {
        return "JavaFxScene{\n" +
            "root=" + root + "\n" +
            "}";
    }
}