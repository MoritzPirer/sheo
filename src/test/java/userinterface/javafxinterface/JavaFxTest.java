package userinterface.javafxinterface;

import javafx.application.Platform;
import javafx.scene.Scene;
import org.junit.jupiter.api.BeforeAll;
import userinterface.components.UiComponent;

class JavaFxTest {
    @BeforeAll
    static void initJavaFX() {
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException ignored) { }
    }

    // Helper to force JavaFX layout pass in tests
    protected void layoutComponent(UiComponent component) {
        if (component instanceof JavaFxComponent fxComponent) {
            // Wrap in a dummy scene with a fixed size and force pulse/layout
            Scene scene = new Scene(fxComponent.getJavaFxNode(), 200, 200);
            fxComponent.getJavaFxNode().applyCss();
            fxComponent.getJavaFxNode().layout();
        }
    }
}
