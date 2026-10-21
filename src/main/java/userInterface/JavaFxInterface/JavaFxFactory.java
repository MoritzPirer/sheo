package userInterface.JavaFxInterface;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import userInterface.UiFactory;
import userInterface.components.*;

public class JavaFxFactory implements UiFactory {
    @Override
    public Button createButton(String text) {
        return new JavaFxButton(text);
    }

    @Override
    public Label createLabel(String text) {
        return new JavaFxLabel(text);
    }

    @Override
    public UiContainer createVerticalUiContainer() {
        return new JavaFxContainer(new VBox());
    }

    @Override
    public UiContainer createHorizontalUiContainer() {
        return new JavaFxContainer(new HBox());
    }

    @Override
    public Scene createScene(UiContainer uiContainer) {
        if (uiContainer instanceof JavaFxContainer) {
            return new JavaFxScene(uiContainer);
        }

        throw new IllegalArgumentException("UiContainer was not created by this factory");
    }

    @Override
    public UiApplication createUiApplication(String title) {
        return new JavaFxApplication(title);
    }
}
